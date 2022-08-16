import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.io.*;
import java.util.Properties;
import java.net.ServerSocket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Updatedish extends HttpServlet {

    
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
Connection connection = null;
          Statement statement = null;
          ResultSet resultSet = null;
          int n=1;
        
          PrintWriter out=response.getWriter();
          String classname = "";
        String url = "";
        String dbusername = "";
        String dbpassword = "";
        String SQLfordishes = "";
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
            SQLfordishes = property.getProperty("SQLfordishes");
            adminhome = property.getProperty("adminhome");
            propertyfile.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
          out.println("<!DOCTYPE html><head> <link  rel=\"stylesheet\" href=\"style.css\"></head><body><h2 style='text-align:center;'>Updating dish cost</h2><div>");
          out.println(" <form action='Updatedish' method='get'>");
          
         
         
          try
            {
                Class.forName(classname);
                connection = DriverManager.getConnection(url,dbusername,dbpassword);
                statement=connection.createStatement();
                out.println("<div class='dishupdate'>");
                resultSet = statement.executeQuery(SQLfordishes);
                while(resultSet.next())
                    {
                        String hotelnamerecord=resultSet.getString(1);
                        String dishnamerecord=resultSet.getString(2);
                        String dishcostrecord=resultSet.getString(3);
                        String dishtimerecord=resultSet.getString(4);
                        out.println("<input type='radio' id='hotelname' name='dishname' value='"+hotelnamerecord+" "+dishnamerecord+" "+dishcostrecord+" "+dishtimerecord+"'><label for='hotelname'/>" +hotelnamerecord+"   "+dishnamerecord+"  "+dishcostrecord+"   "+dishtimerecord+ "</label>");
                        out.println("<br/>");  
                    }
                    out.println("<h3 style='position: relative;top: 31px;left: -42px;'>Enter the new cost:</h3>");
                    out.println("<input style='position: relative;top: -21px;left: 195px;' type='number' name='dishcost' autocomplete='off' value=''/></div>");
                    out.println("<input style='position: relative;left: 749px;top: -34px;' type='submit' value='update'></form></div>");
                    String dishname=request.getParameter("dishname");
                    String[] result = dishname.split(" ");
                    String newcost=request.getParameter("dishcost");
                   
                int success1=statement.executeUpdate("UPDATE public.dishes SET cost='"+newcost+"' WHERE hotelname='"+result[0]+"' AND dishname='"+result[1]+"' AND time='"+result[3]+"';");
                
                
                 
                statement.close();
                connection.close();
                response.setIntHeader("Refresh",20);
} 
catch (Exception e) 
{
    e.printStackTrace();
}
out.println("<a href='"+adminhome+"'><h2 style='text-align:center;color:white;'>Admin page</h2></a>");
out.println("</div></body></html>");

            }}