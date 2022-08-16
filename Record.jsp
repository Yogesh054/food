<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.*"%>
<%@page import="java.util.Properties"%>
<%@page import="de.taimos.totp.TOTP"%>
<%@page import="org.apache.commons.codec.binary.*"%>
<%@page import="java.security.*"%>


<%
String userauth=request.getParameter("authenticatortype");
String usertotp=request.getParameter("utotp");
Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
int i=0;
String classname = "";
        String url = "";
        String dbusername = "";
        String dbpassword = "";
        String SQLforLogin = "";
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
            SQLforLogin = property.getProperty("SQLforLogin");
            adminhome = property.getProperty("adminhome");
            Addhotel=property.getProperty("Addhotel");
            propertyfile.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }

try {
            Class.forName(classname);
            connection = DriverManager.getConnection(url,dbusername,dbpassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQLforLogin);

            while (resultSet.next()) {
                String uname = resultSet.getString("username");
                String Securitycode=null;
                if(userauth.equals("googlecode"))
                {
                  Securitycode = resultSet.getString("googlecode");
                }
                 
                else
                {
                 Securitycode = resultSet.getString("microsoftcode");
                }
                Base32 base32 = new Base32();
                byte[] bytes = base32.decode(Securitycode);
                String hexKey = Hex.encodeHexString(bytes);
                String code = TOTP.getOTP(hexKey);
                if(uname.equals("Admin"))
                {
                    if (code.equals(usertotp)) {
                        response.sendRedirect(adminhome);
                        }
                }
                else{
                    if (code.equals(usertotp)) {
                        response.sendRedirect("Order");
                        }
                }
                
                    }
            
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
%>