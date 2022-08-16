import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.sql.Connection;
import java.io.*;
import java.net.ServerSocket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HotelRecord extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        String Addhotel="";
        try {
            FileInputStream propertyfile = new FileInputStream(
                    "C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
            Properties property = new Properties();
            property.load(propertyfile);
            classname = property.getProperty("classname");
            url = property.getProperty("url");
            dbusername = property.getProperty("dbusername");
            dbpassword = property.getProperty("dbpassword");
            SQLforHotelname = property.getProperty("SQLforHotelname");
            adminhome = property.getProperty("adminhome");
            Addhotel=property.getProperty("Addhotel");
            propertyfile.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        String newhotelname = request.getParameter("hotel_name");
        out.println("<!DOCTYPE html><body><div>");
        try {
            Class.forName(classname);
            connection = DriverManager.getConnection(url,dbusername,dbpassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQLforHotelname);
            while (resultSet.next()) {
                String hotelnamerecord = resultSet.getString("hotelname");

                if (hotelnamerecord.equals(newhotelname)) {

                    response.sendRedirect(Addhotel);
                }

            }
            int success = statement.executeUpdate("INSERT INTO hotel(hotelname) VALUES('" + newhotelname + "')");
            if (success == 1)
                response.sendRedirect("Adddish");
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("</div></body></html>");

    }
}