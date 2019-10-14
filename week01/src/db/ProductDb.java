package db;


import domain.model.Product;

import java.util.List;

public interface ProductDb {
    Product get(String personId);

    List<Product> getAll();

    void add(Product product);

    void update(Product product);

    void delete(String personId);

    int getNumberOfProducts();
}
