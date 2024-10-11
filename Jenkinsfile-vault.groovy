pipeline {
    agent any
    environment {
        MAVEN_HOME = "C:\\Users\\Sammy\\Downloads\\apache-maven-3.9.8"
    }
    stages {
        stage('1. Clone Code') {
            steps {
                // Cloning the repository
                git url: "https://github.com/Sammyoj10/Maven-web-app.git"
            }
        }
        
        stage('2. mvn Build') {
            steps {
                // Building the project using Maven
                bat "${env.MAVEN_HOME}\\bin\\mvn clean package"
            }
        }
        
        stage('SonarQube analysis') {
            steps {
                // Retrieve the SonarQube token from Vault
                withVault(configuration: [
                    vaultCredentialId: 'NewVaultTCred', 
                    vaultUrl: 'http://13.60.192.49:8200'
                ], vaultSecrets: [[
                    path: 'secret/sonar', 
                    secretValues: [[envVar: 'SONAR_TOKEN', vaultKey: 'token']]
                ]]) {
                    // Define the SonarQube environment and run the analysis
                    withSonarQubeEnv('SonarServer') {
                        // Use SonarQube Scanner, passing the token securely
                        sh """
                            ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.host.url=http://localhost:9000 \
                            -Dsonar.login=${SONAR_TOKEN}
                        """
                    }
                }
            }
        }
        
//         stage('Upload Artifacts') {
//     steps {
//         withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USERNAME')]) {
//             // Uploading artifacts to the repository
//             bat """
//                 ${env.MAVEN_HOME}\\bin\\mvn deploy -DaltDeploymentRepository=SammySnapshot::default::http://localhost:8081/repository/SammySnapshot/ \
//                 -DrepositoryId=SammySnapshot -Dnexus.username=${NEXUS_USERNAME} -Dnexus.password=${NEXUS_PASSWORD}
//             """
//         }
//     }
// }
        
//         stage('5. Deploy to UAT') {
//             steps {
//                 // Deploying the WAR file to Tomcat
//                 deploy adapters: [tomcat9(credentialsId: 'Tomcatlogin', path: '', url: 'http://localhost:8088/')], contextPath: null, onFailure: false, war: 'target\\maven-web-app.war'
//             }
//         }
        
//         stage('6. Manual Approval') {
//             steps {
//                 echo 'Please review the application performance'
//                 timeout(time: 600, unit: 'MINUTES') {
//                     input message: 'Application ready for deployment, Please review and approve'
//                 }
//             }
//         }
        
        /*
        stage('7. Deploy to Prod') {
            steps {
                echo 'Deploy application to the customers/production'
                // Uncomment the following line and configure for production deployment
                // deploy adapters: [tomcat9(credentialsId: 'tomcat-credentials', path: '', url: 'http://34.219.51.216:8177/')], contextPath: null, war: 'target\\maven-web-app.war'
            }
        }
        */
        
        stage('8. APM') {
            steps {
                echo 'Monitoring and observation and alerting'
                echo 'Application performance Monitoring in progress'
            }
        }
        
        stage('9. Email Notification') {
            steps {
                emailext body: '''Hi Teams,

The build and deployment status for the Maven web app is as follows.

Regards,
Landmark Technologies''', 
                recipientProviders: [buildUser(), requestor()], 
                subject: 'Build and Deployment Status', 
                to: 'sammyagwam@gmail.com'
            }
        }
    }
}

