package ui.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import domain.model.Person;

import javax.swing.*;

public class DB {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        String url = "jdbc:postgresql://databanken.ucll.be:51920/2TX34?currentSchema=HamelryckAxelWeb3.person";
        properties.setProperty("user", "local_r0743950");
        properties.setProperty("password", "Sqnhe\"Dlèx4T5!");
        properties.setProperty("ssl", "true");
        properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        properties.setProperty("sslmode","prefer");

        JFrame frame = new JFrame("Input for new user");
        String fname = JOptionPane.showInputDialog(frame, "First name");
        String lname = JOptionPane.showInputDialog(frame, "Last name");
        String uid = JOptionPane.showInputDialog(frame, "UserId");
        String em = JOptionPane.showInputDialog(frame, "email");
        String pw = JOptionPane.showInputDialog(frame, "Password");

        Connection connection = DriverManager.getConnection(url,properties);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO \"HamelryckAxelWeb3\".person (userid, \"lastName\", \"firstName\", email, password) " +
                "VALUES ('" + uid + "', '" + lname +"', '" + fname +"', '" + em+"' ,  '" +  pw +"')");
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
