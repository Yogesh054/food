import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.io.*;
import java.time.*;
import java.util.Properties;
import java.net.ServerSocket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Order extends HttpServlet 
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
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
            propertyfile.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.println("<!DOCTYPE html><head> <link  rel=\"stylesheet\" href=\"style.css\"></head><body><h2 style='text-align:center;'>WELCOME</h2><div class='hotelname'><form action='Order' method='get'><select class='disp' name='dish' id='dish'>");
      
        out.println("<option value=''>Selectdish</option>");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
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
            connection = DriverManager.getConnection(url, dbusername,dbpassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT DISTINCT dishname FROM dishes WHERE time='"+dishtime+"';");
            while (resultSet.next()) {
                String dishnamerecord=resultSet.getString("dishname");
                out.println("<br/>");
                
                out.println("<option value='"+dishnamerecord+"'>"+dishnamerecord+"</option>");
               }
            out.println("</select><input class='menuorder' type='submit' value='Search'/></form>"); 
            String dname=request.getParameter("dish");
            resultSet = statement.executeQuery("SELECT * FROM dishes;");
            out.println("<form action='menuorder.jsp' method='get'><div class='hotelcontent'>");
            while (resultSet.next()) {
                String hotel=resultSet.getString("hotelname");
                String selecteddishname=resultSet.getString("dishname");
                if(selecteddishname.equals(dname))
                out.println("<input type='radio' name='hotelname' value='"+hotel+"' autocomplete='off'/><label for='hotelname'>"+hotel+"--"+selecteddishname+"</label><br>");
                
            }
            out.println("<input class='billbutton' type='submit' value='Cart'></div></form>"); 
            statement.close();
            connection.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("</div></body></html>");
    }
}
