import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.io.*;
import java.time.*;
import java.net.ServerSocket;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;

public class Bill extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
                String classname= "";
                String url="";
                String dbusername="";
                String dbpassword="";
                String Logout="";
                try {
                    FileInputStream propertyfile = new FileInputStream("C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
                    Properties property = new Properties();
                    property.load(propertyfile);
                    classname = property.getProperty("classname");
                    url = property.getProperty("url");
                    dbusername = property.getProperty("dbusername");
                    dbpassword = property.getProperty("dbpassword");
                    Logout=property.getProperty("Logout");
                   propertyfile.close();
                } catch (Exception e) {
                    out.println(e.getMessage());
                }
        out.println("<!DOCTYPE html><head>");
       
        out.println(" <link  rel=\"stylesheet\" href=\"style.css\"></head><body><div><h2 style='text-align:center;'>BILL</h2>");
        String orderhotel=request.getParameter("hotel");
        int count[]=new int[40];
        int cost[]=new int[40];
        String dish[]=new String[40];
        int i=0;
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
        int total=0;
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
          Class.forName(classname);
            connection = DriverManager.getConnection(url,dbusername,dbpassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM dishes WHERE hotelname='"+orderhotel+"' AND time='"+dishtime+"';");
            while(resultSet.next())
                {
                    String Cdish=resultSet.getString(2);
                    dish[i]=resultSet.getString(2);
                    cost[i]=Integer.parseInt(resultSet.getString(3));
                    count[i]=Integer.parseInt(request.getParameter(Cdish+"-hid"));
                    i++;
                }
            for(int j=0;j<i;j++)
            {
                if(count[j]!=0)
                {
                    int itemcost=0;
                    itemcost=count[j]*cost[j];
                    total+=itemcost;
                    out.println("<div id='finaldish'><div id='finaldishname'>");
                    out.println(dish[j]+"-------</div>");
                    out.println("<div id='finaldishamount'>");
                    out.println(cost[j]+" * "+count[j]+" = "+itemcost);
                    out.println("</div></div>");


                }
                }
                    out.println("<div id='billtotal'><div id='billtotal1'>");
                    out.println("Total = </div>");
                    out.println("<div id='billtotal2'>");
                    out.println(total);
                    out.println("</div></div>");
               
                statement.close();
                connection.close();
                
            }
             catch (Exception e) {
                e.printStackTrace();
        }
        out.println("</form><h2 style='text-align:center;'>THANK YOU</h2>");
        out.println("<a class='logout' href='"+Logout+"'>Logout</a>");
            
        out.println("</div></body></html>");

        
    }
}