<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSE</title>
</head>
<body>
	<h3>Async</h3>
	<div id="defer"></div>

	<script src="assets/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		deferred();
		data_ = "";
		function deferred() {
			$.get("defer", function(data) {
				data_ += data + "</br>"
				$("#defer").html(data_);
				deferred();
			})
		}

		deferred();
	</script>
</body>
</html>