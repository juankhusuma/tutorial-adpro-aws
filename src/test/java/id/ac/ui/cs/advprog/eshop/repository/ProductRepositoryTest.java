package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("ebb558egf-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("ebb558egf-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("aof9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditSuccessful() {
        Product product = new Product();
        product.setProductId("ebb558egf-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        product.setProductName("Sampo Cap Pacil");
        product.setProductQuantity(200);
        Product editedProduct = productRepository.edit(product);

        assertNotNull(editedProduct);
        assertEquals("Sampo Cap Pacil", editedProduct.getProductName());
        assertEquals(200, editedProduct.getProductQuantity());
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals("Sampo Cap Pacil", savedProduct.getProductName());
        assertEquals(200, savedProduct.getProductQuantity());
        assertEquals(product.getProductId(), savedProduct.getProductId());
    }

    @Test
    void testEditNonExistentProduct() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("23213213-2132-1321-2132-132132132132");
        nonExistentProduct.setProductName("I bet on losing dogs");
        nonExistentProduct.setProductQuantity(100);

        Product result = productRepository.edit(nonExistentProduct);
        assertNull(result);
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteSuccessful() {
        Product product = new Product();
        product.setProductId("2321321534-2132-1321-2132-132132132132");
        product.setProductName("Feather");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> beforeDelete = productRepository.findAll();
        assertTrue(beforeDelete.hasNext());
        Product savedProduct = beforeDelete.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());

        productRepository.delete(product);
        Iterator<Product> afterDelete = productRepository.findAll();
        assertFalse(afterDelete.hasNext());
    }

    @Test
    void testDeleteNonExistentProduct() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("34324324-2132-1321-2132-132132132132");
        nonExistentProduct.setProductName("Mama I'm in love with a criminal");
        nonExistentProduct.setProductQuantity(100);

        productRepository.delete(nonExistentProduct);
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditDoesNotCreateNewProduct() {
        Product product = new Product();
        product.setProductId("87687687-2132-1321-2132-132132132132");
        product.setProductName("Class of 2019");
        product.setProductQuantity(100);

        Product editResult = productRepository.edit(product);
        assertNull(editResult);
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
}