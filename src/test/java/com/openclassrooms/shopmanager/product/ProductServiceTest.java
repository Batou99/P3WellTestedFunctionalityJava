package com.openclassrooms.shopmanager.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.shopmanager.order.OrderService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Take this test method as a template to write your test methods for
 * ProductService and OrderService. A test method must check if a definite
 * method does its job:
 *
 * Naming follows this popular convention :
 * http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@InjectMocks
	ProductService productService;

	@Mock
	ProductRepository productRepository;

	@Test
	public void getAllProducts_DbHasData_allDataReturned() {

		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("First product");

		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("First product");

		when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

		List<Product> products = productService.getAllProducts();

		assertEquals(2, products.size());
		assertEquals(1L, products.get(0).getId(), 0);
		assertEquals(2L, products.get(1).getId(), 0);

	}

	@Test
	public void deleteProduct() {
		Long productIdTest = 1L;

		ProductService deleteProduct = mock(ProductService.class);
		deleteProduct.deleteProduct(productIdTest);

		verify(deleteProduct, times(1)).deleteProduct(productIdTest);

	}

	@Test
	public void createProductAndCheckGetAdminProducts() {

		ProductService productService = mock(ProductService.class);
		ProductModel pm = new ProductModel();
		pm.setName("Ipad");
		pm.setPrice("2.0");
		pm.setQuantity("20");

		productService.createProduct(pm);

		productService.getAllAdminProducts();

		verify(productService, times(1)).createProduct(pm);

		verify(productService, times(1)).getAllAdminProducts();

	}
}