<!DOCTYPE html>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%
String Adddish="";
String Addhotel="";
String Deletedish="";
String Deletehotel="";
String Updatedish="";
String Logout="";
try
{
    FileInputStream propertyfile = new FileInputStream("C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");

    Properties property = new Properties();
    property.load(propertyfile);
    Adddish= property.getProperty("Adddish");
    Addhotel= property.getProperty("Addhotel");
    Deletedish= property.getProperty("Deletedish");
    Deletehotel= property.getProperty("Deletehotel");
    Updatedish= property.getProperty("Updatedish");
    Logout= property.getProperty("Logout");

}
catch (Exception e)
{
    out.println(e.getMessage());
}
%>

<head>
    <script>
        function addhotel() {

            document.getElementById("addhotel").innerHTML = this.responseText;
            window.location = "<%=Addhotel%>";
        }
        function adddish() {

            document.getElementById("adddish").innerHTML = this.responseText;
            window.location = "<%=Adddish%>";
        }
        function deletehotel() {

            document.getElementById("deletehotel").innerHTML = this.responseText;
            window.location = "<%=Deletehotel%>";
        }
        function deletedish() {

            document.getElementById("deletedish").innerHTML = this.responseText;
            window.location = "<%=Deletedish%>";
        }
        function updatedish() {

            document.getElementById("update").innerHTML = this.responseText;
            window.location = "<%=Updatedish%>";
        }
        function logout() {
            document.getElementById("update").innerHTML = this.responseText;
            window.location = "<%=Logout%>";
        }
    </script>
    <style>
        .admin {
            height: 516px;
            width: 600px;
            position: relative;
            top: 100px;
            left: 435px;
            background: linear-gradient(to bottom, rgb(61 211 222), rgb(235 181 243));
                border-radius: 28px 138px 0px;
        }

        p {
            font-size: larger;
            position: relative;
            top: -10px;
            left: 1px;
        }

        .input {
            border-radius: 5px;
            text-align: center;
            font-size: large;
            position: relative;
            top: 7px;
            left: 277px;
        }

        .adminbutton1 {

            width: 217px;
            height: 30px;

            margin: 20px;
            margin-left: 186px
        }
        .adminbutton2 {

width: 217px;
height: 30px;
background-color: black;
color: whitesmoke;
margin: 20px;
margin-left: 186px
}
    </style>
</head>

<body style="font-family: Century Gothic;background-color: #293637e0;;">
    <div class="admin">
        <h2 style='text-align:center;'>ADMIN PAGE</h2>
        <h2 style='text-align:center;'>Select the function to be performed</h2>
        <button class="adminbutton1" id="addhotel" onclick="addhotel()">
            <p>Addhotel</p>
        </button><br />
        <button class="adminbutton1" id="adddish" onclick="adddish()">
            <p>Adddish</p>
        </button><br />
        <button class="adminbutton1" id="deletehotel" onclick="deletehotel()">
            <p>RemoveHotel</p>
        </button><br />
        <button class="adminbutton1" id="deletedish" onclick="deletedish()">
            <p>Removedish</p>
        </button><br />
        <button class="adminbutton1" id="update" onclick="updatedish()">
            <p>Updatedish</p>
        </button><br />
        <button class="adminbutton2" id="logout" onclick="logout()">
            <p>Logout</p>
        </button><br />
        
    </div>
</body>

</html>