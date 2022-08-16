<%
String mode=request.getParameter("authenticator");
%>
<!DOCTYPE html>
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
            top: 120px; 
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
<div class="login">
<form action="Record.jsp" method="post">
<input type="hidden" name="authenticatortype" value="<%=mode%>"/>
<p>Please enter TOTP:</p> <input class="input" type="number" name="utotp" autocomplete="off" />
        <input class="button" type="submit" value="submit">

</form>
</div>
</body>
</html>
