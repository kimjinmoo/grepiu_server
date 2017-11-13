Spring boot
인텔리 J CLI 이용

패키지<br>
Web<br>
Freemarker<br>
Mybatis<br>

vue.js 이용 예정


#로컬 몽고DB 유저 등록<br>
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
use admin
db.createUser( { user: "<username>",
           pwd: "<password>",
           roles: [ "userAdminAnyDatabase" ]
})
use study
db.createUser( { user: "<username>",
           pwd: "<password>",
           roles: [ "readWrite"  ] 
})
</pre>
step4. 유저등록 후 몽고 서비스 재기동<br>
<pre>
mongod --auth
</pre>
<br>구조 참조</br>
https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html
<br>properties 설정 참조</br>
https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
<br><br>build&execute<br>
gradlew build && java -jar build/libs/springboot-0.0.1.jar