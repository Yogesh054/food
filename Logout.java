import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		String Index = "";
        FileInputStream propertyfile = new FileInputStream("C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
		Properties property = new Properties();
		property.load(propertyfile);
		Index = property.getProperty("Index");
		propertyfile.close();

		request.getSession().invalidate();

		response.sendRedirect(Index);

	}

}
