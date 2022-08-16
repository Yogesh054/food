import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.security.*;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.*;
import java.io.*;
import java.util.Properties;
import java.net.ServerSocket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class addrecord extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                PrintWriter out=response.getWriter();
                String classname= "";
                String url="";
                String dbusername="";
                String dbpassword="";
                String SQLforLogin="";
                String Login="";
                try {
                    FileInputStream propertyfile = new FileInputStream("C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
                    Properties property = new Properties();
                    property.load(propertyfile);
                    classname = property.getProperty("classname");
                    url = property.getProperty("url");
                    dbusername = property.getProperty("dbusername");
                    dbpassword = property.getProperty("dbpassword");
                    SQLforLogin = property.getProperty("SQLforLogin");
                    Login=property.getProperty("Login");
                   propertyfile.close();
                } catch (Exception e) {
                    out.println(e.getMessage());
                }
                out.println("<!DOCTYPE html><head> <link  rel=\"stylesheet\" href=\"style.css\"></head><body><div class='newregister'>");
String newuname=request.getParameter("nu_name");
String newpass=request.getParameter("nu_pass");
Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;

try
{
    Class.forName(classname);
    connection = DriverManager.getConnection(url,dbusername,dbpassword);
    statement=connection.createStatement();
    resultSet = statement.executeQuery(SQLforLogin);
    while(resultSet.next())
    {
        String recorduser=resultSet.getString(1);
        String recordpass=resultSet.getString(2);
        if((newuname.equals(recorduser))&&(newpass.equals(recordpass)))
            {
                out.print("<h3 style='text-align:center;'>Account  Registeration failed</h3>");
                out.print("<h3 style='text-align:center;'>Username already exist</h3>");
                out.println("<a style='text-decoration:none;' href='"+Login+"'><h3 style='text-align:center;'>Home Page</h3></a>");
                
            }
    }
    SecureRandom google_random = new SecureRandom();
    byte[] google_bytes = new byte[20];
    google_random.nextBytes(google_bytes);
    Base32 google_base32 = new Base32();
    String google_securecode=google_base32.encodeToString(google_bytes);
    SecureRandom microsoft_random = new SecureRandom();
    byte[] microsoft_bytes = new byte[20];
    microsoft_random.nextBytes(microsoft_bytes);
    Base32 microsoft_base32 = new Base32();
    String microsoft_securecode=microsoft_base32.encodeToString(microsoft_bytes);
    int success=statement.executeUpdate("INSERT INTO login(username,password,googlecode,microsoftcode) VALUES('"+newuname+"','"+newpass+"','"+google_securecode+"','"+microsoft_securecode+"');");
    if(success==1)
    {
        out.println("<h2 style='text-align:center;'> Account Successfully Registered</h2>");
        out.println("<br/>");
        out.println("<h2 style='text-align:center;'>");
        out.println(google_securecode);
        out.println("</h2><br/>");
        out.println("<br/>");
        out.println("<h2 style='text-align:center;'>Type this above code in Google Authenticator for Two step verification process</h2>");
        out.println("<br/>");
        out.println("<h2 style='text-align:center;'>");
        out.println(microsoft_securecode);
        out.println("</h2><br/>");
        out.println("<br/>");
        out.println("<h2 style='text-align:center;'>Type this above code in Microsoft Authenticator for Two step verification process</h2>");
        out.println("<br/>");
        out.println("<h2 style='text-align:center;'>Click here to move to the login page</h2>");
        out.println("<br/>");


out.println("<a style='text-decoration:none;'"+Login+"'><h3 style='text-align:center;'>Login</h3></a>");
  
    }
    else
    {
       

    }
    statement.close();
    connection.close();

} 
catch (Exception e) 
{
    e.printStackTrace();
}
out.println("</div></body></html>");
            }
}