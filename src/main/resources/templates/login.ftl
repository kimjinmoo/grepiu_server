<!DOCTYPE html>
<html>
<head>
  <title>로그인</title>
</head>
<body>
<form action="/login" method="post">
  <#if RequestParameters.error??>
      잘못된 비밀번호입니다.
  </#if>
  <div><label> User Name : <input type="text" name="id"/> </label></div>
  <div><label> Password: <input type="password" name="passwd"/> </label></div>
  <div><input type="submit" value="Sign In"/></div>
  <a href="/signUp">SignUp</a>
</form>
</body>
</html>