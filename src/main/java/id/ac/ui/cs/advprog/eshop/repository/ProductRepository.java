package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product edit(Product updatedProduct) {
        int index = 0;
        for (Product product : productData) {
            if (product.getProductId().equals(updatedProduct.getProductId())) {
                productData.set(index, updatedProduct);
                return updatedProduct;
            }
            index++;
        }
        return null;
    }

    public Product delete(Product deleteProduct) {
        productData.remove(deleteProduct);
        return null;
    }

}
