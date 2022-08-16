<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.time.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.Properties"%>
<!DOCTYPE html>
<head>
    <link  rel="stylesheet" href="style.css">
    <script>
        order = [];
        
        function addToCart(dish,name,cost) {
            var f = 0;
            for (var i = 0; i < order.length; i++) {
                if (order[i][0] === dish) {
                    order[i][2] += 1;
                    document.getElementById(dish+'-qty').innerHTML=parseInt(document.getElementById(dish+'-qty').innerHTML)+1;
                    document.getElementById("total").innerHTML = parseInt(document.getElementById("total").innerHTML) + parseInt(cost);
                    document.getElementById(dish+'-hid').value=parseInt(document.getElementById(dish+'-hid').value)+1;
                    f = 1;
                }
            }
            if (f === 0) {
                o = [dish, 1];
                order.push(o);
                document.getElementById(dish+'-qty').innerHTML=parseInt(document.getElementById(dish+'-qty').innerHTML)+1;
                document.getElementById("total").innerHTML = parseInt(document.getElementById("total").innerHTML) + parseInt(cost);
                document.getElementById(dish+'-hid').value=parseInt(document.getElementById(dish+'-hid').value)+1;
            }
        }
        function subfromCart(dish,name,cost) {
            

                for (var i = 0; i < order.length; i++) {
                    if (order[i][0] === dish &&( document.getElementById(dish+'-qty').innerHTML)>0) {
                        order[i][2] -= 1;
                        document.getElementById(dish+'-qty').innerHTML=parseInt(document.getElementById(dish+'-qty').innerHTML)-1;
                        document.getElementById("total").innerHTML = parseInt(document.getElementById("total").innerHTML) - parseInt(cost);
                        document.getElementById(dish+'-hid').value=parseInt(document.getElementById(dish+'-hid').value)-1;
                      
                    }
                }
            

        }
    </script>
</head>
<body><div>
<%
 
          Connection connection = null;
          Statement statement = null;
          ResultSet resultSet = null;
           String disharray[] = new String[50];
    int costarray[] = new int[50];
    int countarray[] = new int[50];
    int i = 0;
    String classname = "";
        String url = "";
        String dbusername = "";
        String dbpassword = "";
        try {
            FileInputStream propertyfile = new FileInputStream(
                    "C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
            Properties property = new Properties();
            property.load(propertyfile);
            classname = property.getProperty("classname");
            url = property.getProperty("url");
            dbusername = property.getProperty("dbusername");
            dbpassword = property.getProperty("dbpassword");
        } catch (Exception e) {
            out.println(e.getMessage());
        }

          String hotel=request.getParameter("hotelname");
          %>
          <h2 style="text-align:center;"><%=hotel%></h2>
          <%
          LocalTime systemtime = LocalTime.now();
    int Hour = systemtime.getHour();
    String dishtime = null;
    if (Hour <= 11)
      dishtime = "M";
    else if (Hour <= 15)
      dishtime = "A";
    else if (Hour <= 19)
      dishtime = "E";
    else if (Hour <= 23)
      dishtime = "N";

    try {
        Class.forName(classname);
      connection = DriverManager.getConnection(url,dbusername,dbpassword);
      statement = connection.createStatement();
      resultSet = statement.executeQuery("SELECT * FROM dishes WHERE hotelname='"+hotel+"' AND time='"+dishtime+"';");
      while (resultSet.next()) {
        String Hotelname = resultSet.getString(1);
        String Dishname = resultSet.getString(2);
        int Dcost = Integer.parseInt(resultSet.getString(3));
        String dtime = resultSet.getString(4);
        disharray[i] = Dishname;
        costarray[i] = Dcost;
        countarray[i] = 0;
        %>
        <div class='main'>
        <div  class='one' id='<%=Dishname%>'>
        <h2><%=Dishname%> -- Rs  <%=Dcost%></h2></div>
        <div class='two'>
        <button  class='addbutton'onclick="addToCart('<%=Dishname%>','<%=hotel%>','<%=Dcost%>')">+</button>
        <h2 id='<%=Dishname%>-qty'>0</h2>
        <button class='addbutton' onclick="subfromCart('<%=Dishname%>','<%=hotel%>','<%=Dcost%>')">-</button></div></div>
        <%
        i++;
      }%>
      
      <div class="total">
      <div class="texttotal">Total</div>

      <div class="numbertotal"><span id='total'>0</span></div>
      <form action="Bill" method="get">
      <input type="hidden" name="hotel" value='<%=hotel%>'/>
      <%
      resultSet = statement.executeQuery("SELECT * FROM dishes WHERE hotelname='"+hotel+"' AND time='"+dishtime+"';");
      while (resultSet.next()) {
        String chotelname = resultSet.getString(1);
        String cDishname = resultSet.getString(2);
        int cDcost= Integer.parseInt(resultSet.getString(3));
        String dtime = resultSet.getString(4);
        %>
        <input type="hidden" id='<%=cDishname%>-hid' name='<%=cDishname%>-hid' value='0'/>
<%
      }
      %>
      <input class='bill' type="submit" value="Bill">
      </form>
      <%
    } catch (Exception e) {
            e.printStackTrace();
        }
    
%>
</div></div></body>
</html>