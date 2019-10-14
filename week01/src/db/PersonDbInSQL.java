package db;

import domain.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PersonDbInSQL implements PersonDb {
    private Properties properties= new Properties();
    String url = "jdbc:postgresql://databanken.ucll.be:51920/2TX34?currentSchema=HamelryckAxelWeb3.person";

    public PersonDbInSQL(){
        String url = "jdbc:postgresql://databanken.ucll.be:51920/2TX34?currentSchema=HamelryckAxelWeb3.person";
        properties.setProperty("user", "local_r0743950");
        properties.setProperty("password", "Sqnhe\"Dlèx4T5!");
        properties.setProperty("ssl", "true");
        properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        properties.setProperty("sslmode","prefer");
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DbException(e.getMessage(),e);
        }
    }

    @Override
    public List<Person> getAll() {
        List<Person> people = new ArrayList<Person>();
        try(Connection connection = DriverManager.getConnection(url,properties);
            Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT * FROM \"HamelryckAxelWeb3\".person");
            while(result.next()){
                String userid = result.getString("userid");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String email = result.getString("email");
                String password = result.getString("password");
                Person person = new Person(userid, email, password, firstName,lastName);
                people.add(person);
                System.out.println(person.toString());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(),e);
        }
        return people;
    }

    @Override
    public Person get(String userId) {
        Person person = null;
        String sql = "SELECT * FROM \"HamelryckAxelWeb3\".person WHERE \"HamelryckAxelWeb3\".person.userid = ?";
        try(Connection connection = DriverManager.getConnection(url,properties);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, userId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                String userid = result.getString("userid");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String email = result.getString("email");
                String password = result.getString("password");
                try {	// validation of data stored in database
                    person = new Person(userid, email, password, firstName,lastName);
                    System.out.println(person.toString());
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw  new DbException(e.getMessage(),e);
        }
        return person;
    }

    @Override
    public void add(Person person) {
        if(person == null){
            throw new DbException("Nothing to add");
        }
        String sql = "INSERT INTO \"HamelryckAxelWeb3\".person (userid, \"lastName\", \"firstName\", email, password) " +
                "VALUES (?, ? , ? , ? , ?)";
        try(Connection connection = DriverManager.getConnection(url, properties);
        PreparedStatement statement= connection.prepareStatement(sql)){
            statement.setString(1, person.getUserid());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getFirstName());
            statement.setString(4, person.getEmail());
            statement.setString(5, person.getPassword());
            statement.execute();
        }
        catch(SQLException e){
            throw new DbException(e.getMessage(),e);
        }
    }

    @Override
    public void update(Person person) {
        if(person == null){
            throw new DbException("Nothing to add");
        }
        String sql = "UPDATE \"HamelryckAxelWeb3\".person SET \"HamelryckAxelWeb3\".person.userid = ? " +
                "SET \"HamelryckAxelWeb3\".person.lastName = ? " +
                "SET \"HamelryckAxelWeb3\".person.firstName = ? " +
                "SET \"HamelryckAxelWeb3\".person.email = ?" +
                "SET \"HamelryckAxelWeb3\".person.password = ? ";
        try(Connection connection = DriverManager.getConnection(url, properties);
            PreparedStatement statement= connection.prepareStatement(sql)){
            statement.setString(1, person.getUserid());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getFirstName());
            statement.setString(4, person.getEmail());
            statement.setString(5, person.getPassword());
            statement.execute(sql);
        }
        catch(SQLException e){
            throw new DbException(e.getMessage(),e);
        }
    }

    @Override
    public void delete(String personId) {
        String sql = "DELETE FROM \"HamelryckAxelWeb3\".person WHERE \"HamelryckAxelWeb3\".person.userid = ?";
        try(Connection connection = DriverManager.getConnection(url,properties);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, personId);
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DbException(e.getMessage(),e);
        }
    }

    @Override
    public int getNumberOfPersons() {
        int x = 0;
        try(Connection connection = DriverManager.getConnection(url,properties);
            Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT count(\"HamelryckAxelWeb3\".person.userid) FROM \"HamelryckAxelWeb3\".person");
            x=Integer.parseInt(result.getString("count"));
        } catch (SQLException e) {
            throw new DbException(e.getMessage(),e);
        }
        return x;
    }
}
