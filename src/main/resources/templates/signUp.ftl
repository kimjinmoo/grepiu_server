<!DOCTYPE html>
<html>
<head>
  <title>회원가입</title>
  <title>Hello World - IU</title>
  <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <script src="/webjars/jquery/jquery.min.js"></script>
  <script src="/webjars/sockjs-client/sockjs.min.js"></script>
  <script src="/webjars/stomp-websocket/stomp.min.js"></script>
</head>
<body>
회원가입
<form action="/signUp" method="post">
  <input type="text" name="id" id="id" class="form-control" placeholder="아이디" autocomplete="false">
  <input type="password" name="password" id="name" class="form-control" placeholder="비밀번호" autocomplete="false">
  <input type="password" name="passwordRepeated" id="name" class="form-control" placeholder="비밀번호 재입력" autocomplete="false">
  <button id="connect" class="btn btn-default" type="submit">가입</button>
</form>
</body>
</html>

