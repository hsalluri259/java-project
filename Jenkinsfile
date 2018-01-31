pipeline {
  agent none
/*  options{
    buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
  }*/
  environment {
    MAJOR_VERSION = 1
  }
  stages {
    stage('Say Hello') {
      agent any
      steps {
        sayHello 'Awesome Student!'
      }
    }
    stage('Git Information') {
      agent any
      steps {
        echo "My Branch Name: ${env.BRANCH_NAME}"
        script {
          def myLib = new organization.git.gitStuff();
          echo "My Commit: ${myLib.gitCommit("${env.WORKSPACE}/.git")}"
        }
        
      } 
    }
    stage('Unit Test') {
      agent {
        label 'apache'
      }
      steps {
        sh 'ant -f test.xml -v'
        junit 'reports/result.xml'
      }
    }
    stage('Build') {
      agent {
        label 'apache'
      }
      steps{
        sh 'ant -f build.xml -v'
      }
      post {
        success {
          archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
        }
      }
    }
    stage('Deploy') {
      agent {
        label 'apache'
      }
      steps{
        sh "if ![ -d /var/www/html/rectangles/all/${env.BRANCH_NAME} ]; then mkdir /var/www/html/rectangles/all/${env.BRANCH_NAME}; fi"
        sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/${env.BRANCH_NAME}"
      }
    }
    stage('Running on CentOS') {
      agent {
        label 'CentOS'
      }
      steps {
        sh "wget http://hsalluri2591.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 3 4"
      }
    }
/*    stage('Testing on Debian') {
      agent{
        docker 'openjdk:8u121-jre'
      }
      steps {
        sh "wget http://hsalluri2591.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 3 4"
      }
    } */
    stage('Promote to Green') {
      agent {
        label 'apache'
      }
      when {
        branch 'master'
      }
      steps {
        sh "cp /var/www/html/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/"
      }
    }
    stage('Promote Development branch to Master') {
      agent {
        label 'apache'
      }
      when {
        branch 'development'
      }
      steps {
        echo "Stashing any local changes"
        sh 'git stash'
        echo 'Checking out development branch'
        sh 'git checkout development'
        sh 'git pull origin'
        echo 'checking out Master branch'
        sh 'git checkout master'
        echo 'Merging Development branch into master'
        sh 'git merge development'
        echo 'Pushing to Origin Master'
        sh 'git push origin master'
        echo 'tagging the Release'
        sh "git tag rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
        sh "git push origin rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
      }
      post {
        success {
          emailext(
            subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] Development Promoted to Master",
            body: """<p>'${env.JOB_NAME} [${env.BUILD_NUMBER}]' Development Promoted to Master":</p>
            <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
            to: "hsalluri259@gmail.com"
          )
        }
      }  
    }
  }
  post {
    failure {
      emailext(
        subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] failed!",
       /* body: """<p>'${env.JOB_NAME} [${env.BUILD_NUMBER}]' failed!":</p>
        <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",*/
        body: """
        '${env.JOB_NAME} [${env.BUILD_NUMBER}]' Ran!": Check console output at ${env.JOB_NAME} [${env.BUILD_NUMBER}]/a> """,
        to: "hsalluri259@gmail.com"
      )
    }
  }
}
