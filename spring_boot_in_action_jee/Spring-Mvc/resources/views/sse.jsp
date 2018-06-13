<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSE</title>
</head>
<body>
	<h3>SSE</h3>
	<div id="msgFromPush"></div>
	
	<script src="assets/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		if (!!window.EventSource) {
			var source = new EventSource("push");
			data_ = "";
			source.addEventListener("message", function(e) {
				data_ += e.data + "</br>";
				$("#msgFromPush").html(data_);
			});

			source.addEventListener("open", function(e) {
				console.log("连接打开");
			},false);	
			
			source.addEventListener("error", function(e) {
				if(e.readyState == EventSource.CLOSED){
					console.log("连接关闭");
				}else{
					console.log(e.readyState);
				}
			},false);	
					
		} else {
			console.log("浏览器不支持SSE");
		}
	</script>
</body>
</html>