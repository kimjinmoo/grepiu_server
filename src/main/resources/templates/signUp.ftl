<!DOCTYPE html>
<html>
<head>
  <title>회원가입</title>
  <title>Hello World - IU</title>
  <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <script src="/webjars/jquery/jquery.min.js"></script>
  <script src="/webjars/sockjs-client/sockjs.min.js"></script>
  <script src="/webjars/stomp-websocket/stomp.min.js"></script>
  <script>
    $(function() {
      $("#cancel").click(function() {
        location.href="/";
      })
    })
  </script>
</head>
<body>
<div id="main-content" class="container">
  #회원가입
  <form action="/signUp" method="post">
    <input type="text" name="id" id="id" class="form-control" placeholder="아이디" autocomplete="false">
    <input type="password" name="password" id="name" class="form-control" placeholder="비밀번호" autocomplete="false">
    <input type="password" name="passwordRepeated" id="name" class="form-control" placeholder="비밀번호 재입력" autocomplete="false">
    <button id="cancel" class="btn btn-secondary" type="button">취소</button>
    <button id="connect" class="btn btn-success" type="submit">가입</button>
  </form>
</div>
</body>
</html>

