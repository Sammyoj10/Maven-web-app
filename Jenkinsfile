node
 {
  
  def mavenHome = tool name: "maven.home"
  
      echo "GitHub BranhName ${env.BRANCH_NAME}"
      echo "Jenkins Job Number ${env.BUILD_NUMBER}"
      echo "Jenkins Node Name ${env.NODE_NAME}"
  
      echo "Jenkins Home ${env.JENKINS_HOME}"
      echo "Jenkins URL ${env.JENKINS_URL}"
      echo "JOB Name ${env.JOB_NAME}"
  
  
  stage("CheckOutCodeGit")
  {
   git branch: 'task/mytraining', url: 'https://github.com/Sammyoj10/Maven-web-app.git'
 }
 
 stage("Build")
 {
 bat "${mavenHome}/bin/mvn clean package"
 }
 
 stage("ExecuteSonarQubeReport") {
    withSonarQubeEnv('SonarCloud') { // Replace 'SonarCloud' with the ID of your configured SonarQube server in Jenkins
        bat """
            ${mavenHome}\\bin\\mvn sonar:sonar ^
            -Dsonar.projectKey=training10 ^
            -Dsonar.organization=Training10 ^
            -Dsonar.host.url=https://sonarcloud.io ^
            -Dsonar.login=046a21d6ab2d1df716da73174a4682c53cf9c9dc
        """
    }
}

/* 
 stage("UploadArtifactsintoNexus")
 {
 sh "${mavenHome}/bin/mvn deploy"
 }
 
  stage("DeployAppTomcat")
 {
  sshagent(['423b5b58-c0a3-42aa-af6e-f0affe1bad0c']) {
    sh "scp -o StrictHostKeyChecking=no target/maven-web-application.war  ec2-user@15.206.91.239:/opt/apache-tomcat-9.0.34/webapps/" 
  }
 }
 
 stage('EmailNotification')
 {
 mail bcc: 'mylandmarktech@gmail.com', body: '''Build is over

 Thanks,
 Landmark Technologies,
 +14372152483.''', cc: 'mylandmarktech@gmail.com', from: '', replyTo: '', subject: 'Build is over!!', to: 'mylandmarktech@gmail.com'
 }
 */
 
 }
