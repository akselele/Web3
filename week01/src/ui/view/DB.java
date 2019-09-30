package ui.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import domain.model.Person;

public class DB {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        String url = "jdbc:postgresql://databanken.ucll.be:51920/2TX34?currentSchema=HamelryckAxelWeb3.person";
        properties.setProperty("user", "local_r0743950");
        properties.setProperty("password", "Sqnhe\"Dlèx4T5!");
        properties.setProperty("ssl", "true");
        properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        properties.setProperty("sslmode","prefer");

        Connection connection = DriverManager.getConnection(url,properties);
        Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM \"HamelryckAxelWeb3\".person");

        while(result.next()){
            String userid = result.getString("userid");
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String email = result.getString("email");
            String password = result.getString("password");
            try {	// validation of data stored in database
                Person person = new Person(userid, email, password, firstName,lastName);
                System.out.println(person.toString());
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        statement.close();
        connection.close();
    }
}
