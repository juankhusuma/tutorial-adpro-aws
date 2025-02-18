package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(product)).thenReturn(product);

        Product created = productService.create(product);

        verify(productRepository).create(product);
        assertNotNull(created);
        assertEquals(product.getProductId(), created.getProductId());
        assertEquals(product.getProductName(), created.getProductName());
        assertEquals(product.getProductQuantity(), created.getProductQuantity());
    }

    @Test
    void testFindAllProducts() {
        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product2.setProductName("Test Product 2");
        product2.setProductQuantity(200);

        Iterator<Product> iterator = Arrays.asList(product, product2).iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> products = productService.findAll();

        verify(productRepository).findAll();
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(product.getProductId(), products.get(0).getProductId());
        assertEquals(product2.getProductId(), products.get(1).getProductId());
    }

    @Test
    void testFindProductById() {
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product found = productService.findById(product.getProductId());

        verify(productRepository).findById(product.getProductId());
        assertNotNull(found);
        assertEquals(product.getProductId(), found.getProductId());
        assertEquals(product.getProductName(), found.getProductName());
        assertEquals(product.getProductQuantity(), found.getProductQuantity());
    }

    @Test
    void testFindProductByIdNotFound() {
        String nonExistentId = "23423423";
        when(productRepository.findById(nonExistentId)).thenReturn(null);

        Product found = productService.findById(nonExistentId);

        verify(productRepository).findById(nonExistentId);
        assertNull(found);
    }

    @Test
    void testEditProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(150);

        when(productRepository.edit(updatedProduct)).thenReturn(updatedProduct);

        Product edited = productService.edit(updatedProduct);

        verify(productRepository).edit(updatedProduct);
        assertNotNull(edited);
        assertEquals(updatedProduct.getProductId(), edited.getProductId());
        assertEquals(updatedProduct.getProductName(), edited.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), edited.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).delete(product);

        productService.delete(product);

        verify(productRepository).delete(product);
    }

    @Test
    void testFindAllWithEmptyRepository() {
        when(productRepository.findAll()).thenReturn(Arrays.<Product>asList().iterator());

        List<Product> products = productService.findAll();

        verify(productRepository).findAll();
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }
}