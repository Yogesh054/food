import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.DriverManager;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class FoodLoginModule implements LoginModule {

	private CallbackHandler handler;
	private Subject subject;
	private UserPrincipal userPrincipal;
	private RolePrincipal rolePrincipal;
	private String login;
	private List<String> userGroups;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {

		handler = callbackHandler;
		this.subject = subject;
	}

	@Override
	public boolean login() throws LoginException {

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("login");
		callbacks[1] = new PasswordCallback("password", true);
		Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
		
		String classname = "";
        String url = "";
        String dbusername = "";
        String dbpassword = "";
        String SQLforLogin = "";
        try {
            FileInputStream propertyfile = new FileInputStream(
                    "C:/apache-tomcat-9.0.65/webapps/food/META-INF/config.properties");
            Properties property = new Properties();
            property.load(propertyfile);
            classname = property.getProperty("classname");
            url = property.getProperty("url");
            dbusername = property.getProperty("dbusername");
            dbpassword = property.getProperty("dbpassword");
            SQLforLogin= property.getProperty("SQLforLogin");
            propertyfile.close();
        } catch (Exception e) {
            e.getMessage();
        }

		try {
			handler.handle(callbacks);
			String name = ((NameCallback) callbacks[0]).getName();
			String password = String.valueOf(((PasswordCallback) callbacks[1])
					.getPassword());
					try {
						Class.forName(classname);
						connection = DriverManager.getConnection(url,dbusername,dbpassword);
						statement = connection.createStatement();
						resultSet = statement.executeQuery(SQLforLogin);
						while (resultSet.next()) {
							String recorduser = resultSet.getString("username");
							String recordpass = resultSet.getString("password");
							if((name.equals(recorduser))&&(password.equals(recordpass)))
							{
								if (name != null && name.equals("Admin") && password != null && password.equals("Zoho")) 
								{
								login = name;
								userGroups = new ArrayList<String>();
								userGroups.add("admin");
								return true;
			                    }
								else
								{
									login = name;
								userGroups = new ArrayList<String>();
								userGroups.add("user");
								return true;

								}

							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
			

			throw new LoginException("Authentication failed");

		} catch (IOException e) {
			throw new LoginException(e.getMessage());
		} catch (UnsupportedCallbackException e) {
			throw new LoginException(e.getMessage());
		}

	}

	@Override
	public boolean commit() throws LoginException {

		userPrincipal = new UserPrincipal(login);
		subject.getPrincipals().add(userPrincipal);

		if (userGroups != null && userGroups.size() > 0) {
			for (String groupName : userGroups) {
				rolePrincipal = new RolePrincipal(groupName);
				subject.getPrincipals().add(rolePrincipal);
			}
		}

		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(userPrincipal);
		subject.getPrincipals().remove(rolePrincipal);
		return true;
	}

}
