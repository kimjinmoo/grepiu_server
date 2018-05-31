var stompClient = null;
// 이름
var name = "";

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        name = $("#name").val();
        $("#user_name").text(name);
        $("#name").attr("readonly",true);
    }
    else {
        $("#conversation").hide();
        name = "";
        $("#user_name").text("");
        $("#name").attr("readonly",false);
    }
    $("#greetings").html("");
}
function getName() {
    return $("#name").val();
}
function connect() {
    if(getName().length > 0) {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            stompClient.subscribe('/topic/messages', function (greeting) {
                showChat(name, JSON.parse(greeting.body).content);
            });
        });
    } else {
        alert("이름을 입력하여주십시오.");
    }
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    if($("#message").val().length > 0) {
        stompClient.send("/app/chat", {}, JSON.stringify({
            'name' : name,
            'message': $("#message").val()
        }));
        $("#message").val("");
        var elem  = $("#greetings");
        elem.animate({ scrollTop: elem.offset().top+elem.scrollTop()+100}, 500);
    }


}

function showChat(name, message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $
    $("#conversation").hide();
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});