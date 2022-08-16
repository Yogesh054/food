import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.io.*;
import java.util.Properties;
import java.net.ServerSocket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public class Adddish extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String classname= "";
        String url="";
        String dbusername="";
        String dbpassword="";
        String adminhome="";
        String SQLforHotelname="";
        try {
            FileInputStream propertyfile = new FileInputStream("C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
            Properties property = new Properties();
            property.load(propertyfile);
            classname = property.getProperty("classname");
            url = property.getProperty("url");
            dbusername = property.getProperty("dbusername");
            dbpassword = property.getProperty("dbpassword");
            adminhome = property.getProperty("adminhome");
            SQLforHotelname=property.getProperty("SQLforHotelname");
            propertyfile.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }

        out.println(
                "<!DOCTYPE html><head> <link  rel=\"stylesheet\" href=\"style.css\"></head><body><h2 style='text-align:center;position: relative;top: 62px;font-size: 40px;'>Addind new dishes</h2><div class='add-dish'><form action='Adddish' method='get'>");

        out.print("<br/>");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int n = 1;
        out.println("");
        try {
            Class.forName(classname);
            connection = DriverManager.getConnection(url,dbusername,dbpassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQLforHotelname);
            out.println("<p>Select the name of the hotel<p><div class=adddish1>");
            while (resultSet.next()) {

                String hotelnamerecord = resultSet.getString("hotelname");
                out.println("<input type='radio' id='hotelname' name='hotelname' value='" + hotelnamerecord
                        + "'><label for='hotelname'/>" + hotelnamerecord + "</label>");
            }
            out.println(
                    "</div><div class=adddish2><p>Enter the dish name:</p><input type='text'id='radio' name='dish'autocomplete='off'/></div>");
            out.println(
                    "<div class=adddish3><p>Enter the dish cost</p><input  id='radio' type='text' name='cost'autocomplete='off'/></div>");
            out.println(
                    "<div class=adddish4><p>Enter the time at which the dish will be avaliable(M/A/E/N)</p><input type='radio' value='M' name='time'autocomplete='off'/><label for='time'/>M</label>");
            out.println("<input type='radio' value='A' name='time'autocomplete='off'/><label for='time'/>A</label>");
            out.println("<input  type='radio' value='E' name='time'autocomplete='off'/><label for='time'/>E</label>");
            out.println(
                    "<input  type='radio' value='N' name='time'autocomplete='off'/><label for='time'/>N</label></div><div class=adddish5><input id='addbutton' type='submit' value='Add'></form>");
            String hotelname = request.getParameter("hotelname");
            String dishname = request.getParameter("dish");
            String dishcost = request.getParameter("cost");
            String dishavailtime = request.getParameter("time");
            int success = statement.executeUpdate("INSERT INTO public.dishes(hotelname, dishname, cost, time)VALUES ('"
                    + hotelname + "','" + dishname + "','" + dishcost + "', '" + dishavailtime + "');");

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("<a href='"+adminhome+"'><h3 style='text-align:center;'>Admin page</h3></a>");
        out.println("</div></body></html>");
    }
}
