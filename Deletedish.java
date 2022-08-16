import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.io.*;
import java.util.Properties;
import java.net.ServerSocket;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class Deletedish extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int n = 1;
        PrintWriter out = response.getWriter();
        String classname = "";
        String url = "";
        String dbusername = "";
        String dbpassword = "";
        String SQLfordishes = "";
        String adminhome = "";
        try {
            FileInputStream propertyfile = new FileInputStream("C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
            Properties property = new Properties();
            property.load(propertyfile);
            classname = property.getProperty("classname");
            url = property.getProperty("url");
            dbusername = property.getProperty("dbusername");
            dbpassword = property.getProperty("dbpassword");
            SQLfordishes = property.getProperty("SQLfordishes");
            adminhome = property.getProperty("adminhome");
            propertyfile.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.println(
                "<!DOCTYPE html><head> <link  rel=\"stylesheet\" href=\"style.css\"></head><body><h2 style='text-align:center;'>Deleting Dish</h2><div>");
        out.println(" <form action='Deletedish' method='get'>");

        try {
            Class.forName(classname);
            connection = DriverManager.getConnection(url,dbusername,dbpassword);
            statement = connection.createStatement();
            out.println("<div class='deletedish'>");
            resultSet = statement.executeQuery(SQLfordishes);
            while (resultSet.next()) {
                String hotelnamerecord = resultSet.getString(1);
                String dishnamerecord = resultSet.getString(2);
                String dishcostrecord = resultSet.getString(3);
                String dishtimerecord = resultSet.getString(4);
                out.println("<input type='radio' id='hotelname' name='dishname' value='" + hotelnamerecord + " "
                        + dishnamerecord + " " + dishcostrecord + " " + dishtimerecord + "'><label for='hotelname'/>"
                        + hotelnamerecord + "   " + dishnamerecord + "  " + dishcostrecord + "   " + dishtimerecord
                        + "</label>");
                out.println("<br/>");

            }
            out.println("</div><input type='submit'class='deletebutton' value='delete'></form>");
            String dishname = request.getParameter("dishname");
            String[] result = dishname.split(" ");

            int success1 = statement.executeUpdate("DELETE FROM public.dishes WHERE (hotelname='" + result[0]
                    + "' AND dishname='" + result[1] + "' AND cost='" + result[2] + "' AND time='" + result[3] + "');");
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