Spring boot
##인텔리J CLI
패키지<br>
Web<br>
Freemarker<br>
Mybatis<br>

vue.js 이용 예정


##로컬 몽고DB 유저 등록<br>
step1. 몽고 DB 설치<br>
step2. mongod 실행<br>
<pre>
mongod
</pre>
step3. mongo shell 시작<br>
<pre>
mongo 엔터
use study 엔터
</pre>
step4. 유저 등록
<pre>
관지자 계정
use admin
db.createUser( { user: "<username>",
           pwd: "<password>",
           roles: [ "root" ]
})
ex) db.createUser( { user: "iukim21c",pwd: "test1!", roles: [ "root" ]});
use study
db.createUser( { user: "<username>",
           pwd: "<password>",
           roles: [ "readWrite"  ] 
})
ex)
use study
db.createUser( { user: "iukim21c", pwd: "xptmxm1!", roles: [ { role: "readWrite", db: "study" } ] })
</pre>
step4. 유저등록 후 몽고 서비스 재기동<br>
<pre>
mongod --auth
</pre>
##Swagger UI 적용
<pre>
[host]:[port]/swagger-ui.html
</pre>

##WebSocket 적용 - STOMP
<pre>
[host]:[port]/ws
</pre>

##Spring Security 적용
 <pre>
[host]:[port]/login
</pre>
##구조
<pre>
test
   java 
      com.iuom.springboot.test
          process   - 업무용 TDD
          [*] - 일반적인 샘플러
src
   main
      java
         com.iuom.springboot
              common - 공통기능[global use]
                   config - 설정
                   crawler - 크롤러 
                   db - DB factory
                   develop - 개발자 도구
                   job - 배치
                   task - task 단위 처리 Helper
                   util - 유틸
               process
                   api - api /{ver}/기능
                       controller  - 컨트롤러
                       dao - repository 
                       domain - 도메인 
                       service - 서비스
                    sample - 기능 샘플[참조]
                        controller - 컨트롤러
                        dao - repository
                        domain - 도메인
                        service - service
      resources-dev  - 개발 환경
          mappers  - mybatis xml
          static   - 정적파일
          templates - 어플리케이션 템플릿[freemarker]
      resources-local - 로컬 환경
          mappers  - mybatis xml
          static   - 정적파일
          templates - 어플리케이션 템플릿[freemarker]
      resources-prod  - 운영 환경
          mappers  - mybatis xml
          static   - 정적파일
          templates - 어플리케이션 템플릿[freemarker]
build.gradle - gradle build 파일         
</pre>

##build&execute
Windows<br>
gradlew build && java -jar build/libs/springboot-0.0.1-SNAPSHOT.jar<br>
centos-local<br>
gradle build && java -jar build/libs/springboot-0.0.1-SNAPSHOT.jar<br>
centos-prod<br>
gradle build -Pprofile=prod && java -jar build/libs/springboot-0.0.1-SNAPSHOT.jar<br>

##참조
https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html
<br>properties 설정 참조</br>
https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html