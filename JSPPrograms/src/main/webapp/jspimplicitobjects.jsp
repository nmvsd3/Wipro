<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%! int count = 0;
public String message()
{
	return "Welcome to jsp implicit object demo";
}
%>
<%= request.getParameter("txt1") %>
<%= message() %>

<% 
if(request.getParameter("txt1").equals("shashi")){
response.sendRedirect("home.jsp"); 
}
%>

</body>
</html>