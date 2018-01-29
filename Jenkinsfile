pipeline {
  agent any
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
  }
  post {
    always {
      archiveArtifacts artifacts: 'build_number', fingerprint: true
    }
  }
} 
