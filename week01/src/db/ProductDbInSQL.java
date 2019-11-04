package db;

import domain.model.Person;
import domain.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductDbInSQL implements ProductDb {
    private Properties properties= new Properties();
    String url = "jdbc:postgresql://databanken.ucll.be:51920/2TX34?currentSchema=HamelryckAxelWeb3.product";

    public ProductDbInSQL(){
        String url = "jdbc:postgresql://databanken.ucll.be:51920/2TX34?currentSchema=HamelryckAxelWeb3.product";
        properties.setProperty("user", "local_r0743950");
        properties.setProperty("password", "Sqnhe\"Dlèx4T5!");
        properties.setProperty("ssl", "true");
        properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        properties.setProperty("sslmode","prefer");
        properties.setProperty("stringType", "unspecified");
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DbException(e.getMessage(),e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<Product>();
        try(Connection connection = DriverManager.getConnection(url,properties);
            Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT * FROM \"HamelryckAxelWeb3\".product");
            while(result.next()){
                int userid = Integer.parseInt(result.getString("productid"));
                String name = result.getString("name");
                String description = result.getString("description");
                Double price = Double.parseDouble(result.getString("price"));
                Product product = new Product(userid ,name, description, price);
                products.add(product);
                System.out.println(product.toString());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(),e);
        }
        return products;
    }

    @Override
    public Product get(String productId) {
        Product product = null;
        String sql = "SELECT * FROM \"HamelryckAxelWeb3\".product WHERE \"HamelryckAxelWeb3\".product.productid = ?";
        try(Connection connection = DriverManager.getConnection(url,properties);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, productId);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int userid = Integer.parseInt(result.getString("productid"));
                String name = result.getString("name");
                String description = result.getString("description");
                double price = Double.parseDouble(result.getString("price"));
                try {	// validation of data stored in database
                    product = new Product(userid ,name, description, price);
                    System.out.println(product.toString());
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw  new DbException(e.getMessage(),e);
        }
        return product;
    }

    @Override
    public void add(Product product) {
        if(product == null){
            throw new DbException("Nothing to add");
        }
        String sql = "INSERT INTO \"HamelryckAxelWeb3\".product (productid, name , description, price)" +
                "VALUES (?, ? , ? , ?::numeric)";
        try(Connection connection = DriverManager.getConnection(url, properties);
            PreparedStatement statement= connection.prepareStatement(sql)){
            statement.setString(1, Integer.toString(product.getProductId()));
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setString(4, String.valueOf(product.getPrice()));
            statement.execute();
        }
        catch(SQLException e){
            throw new DbException(e.getMessage(),e);
        }
    }

    @Override
    public void update(Product product) {
        if(product == null){
            throw new DbException("Nothing to add");
        }
        String sql = "UPDATE \"HamelryckAxelWeb3\".product SET \"HamelryckAxelWeb3\".product.productid = ? " +
                "SET \"HamelryckAxelWeb3\".product.name = ?" +
                "SET \"HamelryckAxelWeb3\".product.description = ?" +
                "SET \"HamelryckAxelWeb3\".product.price = ?";
        try(Connection connection = DriverManager.getConnection(url, properties);
            PreparedStatement statement= connection.prepareStatement(sql)){
            statement.setString(1, Integer.toString(product.getProductId()));
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setString(4, Double.toString(product.getPrice()));
            statement.execute(sql);
        }
        catch(SQLException e){
            throw new DbException(e.getMessage(),e);
        }
    }

    @Override
    public void delete(String productid) {
        String sql = "DELETE FROM \"HamelryckAxelWeb3\".product WHERE \"HamelryckAxelWeb3\".product.productid = ?";
        try(Connection connection = DriverManager.getConnection(url,properties);
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, productid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage(),e);
        }
    }

    @Override
    public int getNumberOfProducts() {
        int x = 0;
        try(Connection connection = DriverManager.getConnection(url,properties);
            Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery("SELECT count(\"HamelryckAxelWeb3\".product.productid) FROM \"HamelryckAxelWeb3\".product");
            x=Integer.parseInt(result.getString("count"));
        } catch (SQLException e) {
            throw new DbException(e.getMessage(),e);
        }
        return x;
    }
}
