import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.io.*;
import java.util.Properties;
import java.net.ServerSocket;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Deletehotel extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        PrintWriter out = response.getWriter();
        String classname = "";
        String url = "";
        String dbusername = "";
        String dbpassword = "";
        String SQLforHotelname = "";
        String adminhome = "";
        try {
            FileInputStream propertyfile = new FileInputStream(
                    "C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
            Properties property = new Properties();
            property.load(propertyfile);
            classname = property.getProperty("classname");
            url = property.getProperty("url");
            dbusername = property.getProperty("dbusername");
            dbpassword = property.getProperty("dbpassword");
            SQLforHotelname= property.getProperty("SQLforHotelname");
            adminhome = property.getProperty("adminhome");
            propertyfile.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.println(
                "<!DOCTYPE html><head> <link  rel=\"stylesheet\" href=\"style.css\"></head><body><h2 style='text-align:center;'>Deleting hotel</h2><div>");
        out.println(
                "<form action='Deletehotel' method='get'><h2 style='text-align:center;'>Enter the hotel to be deleted:</h2><br/>");

        try {
            Class.forName(classname);
            int n = 1;
            connection = DriverManager.getConnection(url,dbusername,dbpassword);
            statement = connection.createStatement();
            out.println("<div class='hoteldelete'>");
            resultSet = statement.executeQuery(SQLforHotelname);
            while (resultSet.next()) {
                String hotelnamerecord = resultSet.getString("hotelname");
                out.println("<input type='radio' id='hotelname' name='hotelname' value='" + hotelnamerecord
                        + "'><label for='hotelname'/>" + hotelnamerecord + "</label>");
                out.println("<br/>");
            }
            out.println("</div><input type='submit'class='deletebutton' value='delete'></form>");
            String hotelname = request.getParameter("hotelname");
            int success = statement.executeUpdate("DELETE FROM public.hotel WHERE (hotelname='" + hotelname + "');");
            int success1 = statement.executeUpdate("DELETE FROM public.dishes WHERE (hotelname='" + hotelname + "');");

            statement.close();
            connection.close();

            response.setIntHeader("Refresh", 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("<a href='"+adminhome+"'><h3 style='text-align:center;'>Admin page</h3></a>");
        out.println("</div></body></html>");

    }
}