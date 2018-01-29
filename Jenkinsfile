pipeline {
  agent none
  stages {
    stage ('WRITE'){
      echo "Build Number: $BUILD_NUMBER" >> build_number
    }
    stage ('READ'){
      echo "Reading build number file: $build_number"
    }
  }
  post {
    archiveArtifacts artifacts: 'build_number', fingerprint: true
  }
} 
