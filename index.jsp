<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" 
	content="text/html; charset=UTF-8" />
<title>Insert title here</title>
 <style>
        body {
            background-color: #293637e0;;
            font-family: Century Gothic;
        }

        .heading {
            text-align: center;
            font-family: Century Gothic;
            position: relative;
            top: 43px;
        }

        .login {
            height: 300px;
            font-family: Century Gothic;
            width: 600px;
            position: relative;
            top: 128px;
            left: 435px;
            background: linear-gradient(to bottom, rgb(61 211 222), rgb(235 181 243));
            border-radius: 26px 70px 0px;
        }

        p {
            font-size: larger;
            position: relative;
            top: 106px;
            left: 53px;
        }

        .input {
            border-radius: 5px;
            text-align: center;
            font-size: large;
            position: relative;
            top: 62px;
            left: 266px;
        }

        .button {
            position: relative;
            left: 121px;
            border-radius: 10px;
        }
        .form
        {
            position: relative;
            top: 20px;
            left: 155px;
            font-size: x-large;
        }
    </style>
</head>
<body>
<%
String username = request.getRemoteUser();
%>
<div class="login">
<h1 class="heading">Second Step Verification</h1><br/><br/>
<form  class="form" action="userauthenticator.jsp" method="post">
        <input type="radio" value="googlecode" name="authenticator"><label for="authenticator">Google Authenticator</label><br/>
        <br/>
        <input type="radio" value="microsoftcode" name="authenticator"><label for="authenticator">Microsoft Authenticator</label><br/>
        <br/>
        <input class="button" type="submit" value="submit">
    </form>
    </div>
</body>
</html>