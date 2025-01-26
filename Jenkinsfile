node {
  
    def mavenHome = tool name: "maven.home"
  
    echo "GitHub BranchName: ${env.BRANCH_NAME}"
    echo "Jenkins Job Number: ${env.BUILD_NUMBER}"
    echo "Jenkins Node Name: ${env.NODE_NAME}"
  
    echo "Jenkins Home: ${env.JENKINS_HOME}"
    echo "Jenkins URL: ${env.JENKINS_URL}"
    echo "Job Name: ${env.JOB_NAME}"
  
    stage("CheckOutCodeGit") {
        git branch: 'task/mytraining', url: 'https://github.com/Sammyoj10/Maven-web-app.git'
    }
 
    stage("Build") {
        bat "${mavenHome}/bin/mvn clean package"
    }
 
    stage("ExecuteSonarCloudAnalysis") {
        withSonarQubeEnv('SonarCloud') { // Ensure 'SonarCloud' matches the server ID in Jenkins
            bat """
                ${mavenHome}\\bin\\mvn sonar:sonar ^
                -Dsonar.projectKey=training10 ^  // Replace with actual project key
                -Dsonar.organization=Training10 ^ // Replace with actual organization key
                -Dsonar.host.url=https://sonarcloud.io ^
                -Dsonar.login=${SONAR_TOKEN}   // Use the SONAR_TOKEN injected from Jenkins credentials
            """
        }
    }

    /* 
    stage("UploadArtifactsintoNexus") {
        bat "${mavenHome}/bin/mvn deploy"
    }
 
    stage("DeployAppTomcat") {
        sshagent(['423b5b58-c0a3-42aa-af6e-f0affe1bad0c']) {
            bat "scp -o StrictHostKeyChecking=no target/maven-web-application.war ec2-user@15.206.91.239:/opt/apache-tomcat-9.0.34/webapps/"
        }
    }

    stage('EmailNotification') {
        mail bcc: 'mylandmarktech@gmail.com', body: '''Build is over

        Thanks,
        Landmark Technologies,
        +14372152483.''', cc: 'mylandmarktech@gmail.com', from: '', replyTo: '', subject: 'Build is over!!', to: 'mylandmarktech@gmail.com'
    }
    */
}