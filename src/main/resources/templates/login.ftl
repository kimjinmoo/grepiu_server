<!DOCTYPE html>
<html>
<head>
  <title>회원 로그인</title>
  <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="/resources/css/main.css" rel="stylesheet">
  <script src="/webjars/jquery/jquery.min.js"></script>
  <script>
    $(function(){
      $("#login").click(function(){
        $("#loginForm").submit();
      })
      $("#signUp").click(function(){
        location.href="/signUp";
      })
    })
  </script>
</head>
<body>
<div id="main-content" class="container">
  <form action="/login" method="post" id="loginForm">
    <h1>#로그인</h1>
    <#if RequestParameters.error??>
        잘못된 비밀번호입니다.
    </#if>
    <div><label> User Name : <input type="text" name="id"/> </label></div>
    <div><label> Password: <input type="password" name="passwd"/> </label></div>
    <div>
      <button type="button" class="btn btn-primary" id="login">로그인</button>
      <#--<input type="submit" value="Sign In"/>-->
      <button type="button" class="btn btn-light" id="signUp">SignUp</button>
    </div>

  </form>
</div>
</body>
</html>