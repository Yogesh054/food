<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String registeration="";
try
{
    FileInputStream propertyfile = new FileInputStream("C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");

    Properties property = new Properties();
    property.load(propertyfile);
    registeration = property.getProperty("NewRegistration");
    propertyfile.close();
}
catch (Exception e)
{
    out.println(e.getMessage());
}
%>
<head>
    <style>
        body {
            background-color: #293637e0;;
            font-family: Century Gothic;
        }

        .heading {
            text-align: center;
            font-family: Century Gothic;
            position: relative;
            top: 107px;
            left: -17px;
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
            top: 48px;
            left: 53px;
        }

        .input {
            border-radius: 5px;
            text-align: center;
            font-size: large;
            position: relative;
            top: 7px;
            left: 333px;
        }

        .button {
            position: relative;
            top: 12px;
            left: 413px;
            border-radius: 10px;
        }
        a{
            text-align: center;
            text-decoration: none;
            position: relative;
    top: 43px;
    left: 250px;
    color: black;
    font-weight: bold;
        }
    </style>
</head>
<body>
    <h1 class="heading">LOGIN</h1>
    <div class="login">
        <form action="j_security_check" method="post">
            <p>Please enter Username:</p> <input class="input" type="text" name="j_username" autocomplete="off" /> <br />
            <p>Please enter Password:</p> <input class="input" type="password" name="j_password" autocomplete="off" />
            <br />
           <br />
            <input class="button" type="submit" value="Login" />
        </form>
        <a href="<%=registeration%>">New Registeration</a>

    </div>

</body>

</html>