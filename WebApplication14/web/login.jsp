
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body
        <form action="MainController" method="post">
        <input type="hidden" name="action" value="login"/>
        UserID : <input type="text" name="strUserID" /> <br/> 
        Password : <input type="password" name="strPassword" /> </br>
        <input type="submit" value="Login"/>
    </form>
    <span style="color: red">${requestScope.message}</span>
</body>
</html>