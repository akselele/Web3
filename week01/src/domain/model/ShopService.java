package domain.model;

import db.PersonDb;
import db.PersonDbInSQL;
import db.ProductDb;
import db.ProductDbInSQL;

import java.util.List;
import java.util.Properties;

public class ShopService {
    private PersonDb personDb;
    private ProductDb productDb = new ProductDbInSQL();

    public ShopService(Properties properties){
        personDb = new PersonDbInSQL(properties);
    }


    public Person getPerson(String personId) {
        return getPersonDb().get(personId);
    }

    public List<Person> getAll() {
        return getPersonDb().getAll();
    }

    public void add(Person person) {
        getPersonDb().add(person);
    }

    public void updatePersons(Person person) {
        getPersonDb().update(person);
    }

    public void deletePerson(String id) {
        getPersonDb().delete(id);
    }

    private ProductDb getProductDb() {
        return productDb;
    }

    public Product getProduct(String productid) {
        return getProductDb().get(productid);
    }

    public List<Product> getAllProduct() {
        return getProductDb().getAll();
    }

    public void addProduct(Product product) {
        getProductDb().add(product);
    }

    public void updateProducts(Product product) {
        getProductDb().update(product);
    }

    public void deleteProduct(String id) {
        getProductDb().delete(id);
    }

    private PersonDb getPersonDb() {
        return personDb;
    }
}
