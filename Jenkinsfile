pipeline {
  agent {
    label 'master'
  }
  stages {
    stage('PRINT'){
      steps {
        sh 'echo "Job_Name: $JOB_NAME"'
      }
    }
    stage('WRITE'){
      steps {
        sh 'echo "Build Number: $BUILD_NUMBER" >> build_number'
      }
    }
    stage('READ'){
      steps {
         echo "Reading build number file: $build_number"
      }
    }
    stage('Notification Time') {
      steps {
        echo "Notification time: ${DATE}"
      }
       
    }
   
    }
  post {
    success {
      emailext( 
          subject: "Notification Pipeline Build Number: ${env.BUILD_NUMBER} Ran!",
	  body: """
          '${env.JOB_NAME} [${env.BUILD_NUMBER}]' Ran!": Check console output at ${env.JOB_NAME} [${env.BUILD_NUMBER}]/a> """,
           to: "hsalluri259@gmail.com"
          /*body: """<p>Notification Pipeline Build Number: '${env.BUILD_NUMBER}' Ran!"</p>
          <p>Check console output at Notification Pipeline &QUOT;<a href='${env.BUILD_NUMBER}'</a>&QUOT;</p>""*/
        )
     /* archiveArtifacts artifacts: 'build_number', fingerprint: true*/
    }
  }
} 
