<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
body {
	padding: 20px;
}

#console {
	height: 400px;
	overflow: auto;
}

.username-msg {
	color: orange;
}

.connect-msg {
	color: green;
}

.disconnect-msg {
	color: red;
}

.send-msg {
	color: #888
}
</style>
<script src="/static/js/jquery-2.0.3.min.js"></script>
<script src="/static/js/socket.io.js"></script>
<script>
	var socket = io.connect('http://192.168.10.215:8200');

	socket.on('connect', function() {
		output('<span class="connect-msg">Client has connected to the server!</span>');
	});

	socket.on('web', function(data) {
		output('<span class="username-msg"> server data:' + data.message + ':</span> ');
	});

	socket.on('disconnect', function() {
		output('<span class="disconnect-msg">The client has disconnected!</span>');
	});

	function sendDisconnect() {
		socket.disconnect();
	}

	function sendMessage() {
		var message = $('#msg').val();

		var jsonObject = {
			message : "[前端]" + message,
			data : 200
		};
		socket.emit('server', jsonObject);
	}

	function output(message) {
		var element = $("<div>" + message + "</div>");
		$('#console').prepend(element);
	}

	$(document).keydown(function(e) {
		if (e.keyCode == 13) {
			$('#send').click();
		}
	});
</script>
</head>
<body>
	<h1>Netty-socketio Demo Chat</h1>
	<br />

	<div id="console" class="well"></div>

	<form class="well form-inline" onsubmit="return false;">
		<input id="msg" class="input-xlarge" type="text"
			placeholder="Type something..." />
		<button type="button" onClick="sendMessage()" class="btn" id="send">Send</button>
		<button type="button" onClick="sendDisconnect()" class="btn">Disconnect</button>
	</form>


</body>
</html>