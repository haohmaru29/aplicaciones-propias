<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		try {
			if(request.getSession(false).getAttribute("usuario") == null) {
				%><jsp:include page="view/index/login.jsp"></jsp:include><%
			} else {
				%><jsp:include page="view/index/home.jsp"></jsp:include><%
			}
		} catch(Exception e ) {
			%><jsp:include page="view/index/login.jsp"></jsp:include><%
		}
	%>
</body>
</html>