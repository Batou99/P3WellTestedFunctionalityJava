package com.openclassrooms.shopmanager.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.shopmanager.order.Cart;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
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
		product2.setName("Second product");

		when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

		List<Product> products = productService.getAllProducts();

		assertEquals(2, products.size());
		assertEquals(1L, products.get(0).getId(), 0);
		assertEquals(2L, products.get(1).getId(), 0);

	}

	@Test
	public void getAllAdmin_Products_allDataReturned() {

		Product productOne = new Product();
		productOne.setId(1L);
		productOne.setName("First product");

		Product productTwo = new Product();
		productTwo.setId(2L);
		productTwo.setName("Second product");

		when(productRepository.findAllByOrderByIdDesc()).thenReturn(Arrays.asList(productOne, productTwo));

		List<Product> products = productService.getAllAdminProducts();

		assertEquals(2, products.size());
		assertEquals(1L, products.get(0).getId(), 0);
		assertEquals(2L, products.get(1).getId(), 0);

	}

	@Test
	public void getProductById_returnProductbyId() {
		Product product = new Product();
		product.setId(1L);
		product.setPrice(12.0);

		when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		Product productFound = productService.getByProductId(product.getId());

		assertEquals(1L, productFound.getId(), 0);
		assertEquals(12.0, productFound.getPrice(), 0);
	}

	@Test
	public void createProduct_returnProduct() {

		ProductModel productModel = new ProductModel();
		productModel.setId(1L);
		productModel.setPrice("12.0");
		productModel.setQuantity("10");
		productModel.setName("Ipad");

		Product product = new Product();
		product.setId(productModel.getId());
		product.setPrice(Double.parseDouble(productModel.getPrice()));
		product.setQuantity(Integer.parseInt(productModel.getQuantity()));
		product.setName(productModel.getName());

		when(productService.createProduct(productModel)).thenReturn(product);

		Product productCreated = productService.createProduct(productModel);

		assertEquals(12.0, productCreated.getPrice(), 0);
		assertEquals("Ipad", productCreated.getName());

	}

	@Test
	public void testInvokation_deleteProductTest() {
		Long productIdTest = 1L;

		ArgumentCaptor<Long> arg = ArgumentCaptor.forClass(Long.class);

		productService.deleteProduct(productIdTest);

		verify(productRepository, times(1)).deleteById(arg.capture());

		assertEquals(1L, arg.getValue(), 0);

	}

	@Test
	public void testInvokation_updateProductQuantities() {

		Product product = new Product();
		product.setId(1L);
		product.setQuantity(10);

		Cart cart = new Cart();
		cart.addItem(product, 1);

		ArgumentCaptor<Product> arg = ArgumentCaptor.forClass(Product.class);

		when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		productService.updateProductQuantities(cart);

		verify(productRepository, times(1)).save(arg.capture());
		assertEquals(product, arg.getValue());

	}

}