<%--
  Created by IntelliJ IDEA.
  User: Vladimir
  Date: 17.09.2021
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8"
         isErrorPage="true"
         pageEncoding="UTF-8"%>

<%
    String exceptionName = exception.getClass().getName();
    String exceptionMessage = exception.getMessage();
%>

<html>
<head>
    <title>Exception</title>
</head>
<body>
<h2>Exception occurred while processing the request</h2>
<p>Type: <%=exceptionName%> </p>
<p>Message: <%=exceptionMessage%></p>

</body>
</html>
