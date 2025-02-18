package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        String viewName = productController.createProductPost(product, model);

        verify(productService).create(product);
        assertNotNull(product.getProductId());
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        List<Product> products = Arrays.asList(
                new Product(),
                new Product()
        );
        when(productService.findAll()).thenReturn(products);

        String viewName = productController.productListPage(model);

        verify(model).addAttribute("products", products);
        assertEquals("productList", viewName);
    }

    @Test
    void testEditProductPage() {
        String productId = "12345";
        Product product = new Product();
        product.setProductId(productId);
        when(productService.findById(productId)).thenReturn(product);

        String viewName = productController.editProductPage(productId, model);

        verify(model).addAttribute("product", product);
        assertEquals("EditProduct", viewName);
    }

    @Test
    void testEditProduct() {
        String productId = "12345";
        Product product = new Product();
        product.setProductName("Updated Product");
        product.setProductQuantity(20);

        String viewName = productController.editProduct(productId, product);

        verify(productService).edit(product);
        assertEquals(productId, product.getProductId());
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testDeleteProduct() {
        String productId = "12345";
        Product product = new Product();
        product.setProductId(productId);
        when(productService.findById(productId)).thenReturn(product);

        String viewName = productController.deleteProduct(productId);

        verify(productService).delete(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testDeleteProductNotFound() {
        String productId = "3454543";
        when(productService.findById(productId)).thenReturn(null);

        String viewName = productController.deleteProduct(productId);

        verify(productService, never()).delete(any());
        assertEquals("redirect:/product/list", viewName);
    }
}
