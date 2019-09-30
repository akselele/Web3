package ui.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import domain.db.Secret;
import domain.model.Country;

public class TestDB {
	public static void main(String[] args) throws SQLException {
		Properties properties = new Properties();
		String url = "jdbc:postgresql://databanken.ucll.be:51920/webontwerp?currentSchema=\"HamelryckAxelWeb3\".person;
		properties.setProperty("user", "local_r0743950");
		properties.setProperty("password", "Sqnhe\"Dlèx4T5!");
		Secret.setPass(properties);	// implements line 17 and 18
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		properties.setProperty("sslmode","prefer");
		
		Connection connection = DriverManager.getConnection(url,properties);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery( "SELECT * FROM person" );

		while(result.next()){
			String userid = result.getString("userid");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			String email = result.getString("email");
			String password = result.getString("password");
			try {	// validation of data stored in database
				Person person = new Person(name, numberOfInhabitants, capital, numberOfVotes);
				System.out.println(country.toString());
			}
			catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		statement.close();
		connection.close();
	}
}
