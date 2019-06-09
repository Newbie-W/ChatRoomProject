<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Web聊天室</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    Welcome, <%=request.getParameter("username") %><br>
    <input id="message" type="text" size=80 placeholder="请输入内容" />
    <button id="sendBtn" onclick="send()">发送</button>
    <div id="msgDisplay"></div>
  </body>
  
  	<script type = "text/javascript">
		var webSocket = new WebSocket("ws://localhost:8080/ChatroomWeb/chat");
	
		webSocket.onopen = function() {
			var uname = '<%=request.getParameter("username") %>';
			sendMessageToDisplay("<font color='cyan'>系统消息：连接成功,你好" + uname+"</font>");
			webSocket.send(uname);
			document.getElementById('message').onkeydown = send;
			document.getElementById('sendBtn').onclick = sendMsg;
		};
		
		webSocket.onmessage = function(event) {
			sendMessageToDisplay(event.data);
			msgDisplay.scrollTop = msgDisplay.scrollHeight;
		}
		
		webSocket.onclose = function() {
			sendMessageToDisplay("连接关闭");
			document.getElementById('message').onkeydown = null;
			document.getElementById('sendBtn').onclick = null;
		}
		
		window.onbeforeunload = function() {
			webSocket.close();
		}
		
		function sendMessageToDisplay(msg) {
			document.getElementById('msgDisplay').innerHTML += msg+'<br>'
		}
		
		function send() {
			var msg = document.getElementById('message');
			if (event.keyCode == 13) {
				if ("" == msg.value) {
					alert("内容不能为空");
				} else {
					//document.getElementById('msgDisplay').innerHTML += (msg+'<br>');
					webSocket.send(msg.value);
					msg.value = "";
				}
			}
		}
		function sendMsg() {
			var msg = document.getElementById('message');
			if ("" == msg.value) {
				alert("内容不能为空");
			} else {
				//document.getElementById('msgDisplay').innerHTML += (msg+'<br>');
				webSocket.send(msg.value);
				msg.value = "";
			}
		}
	</script>
  
</html>
