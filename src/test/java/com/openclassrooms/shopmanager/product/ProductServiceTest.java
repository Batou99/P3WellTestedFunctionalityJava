package com.openclassrooms.shopmanager.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.shopmanager.order.Cart;
import com.openclassrooms.shopmanager.order.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
		product2.setName("Second product");

		when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

		List<Product> products = productService.getAllProducts();

		assertEquals(2, products.size());
		assertEquals(1L, products.get(0).getId(), 0);
		assertEquals(2L, products.get(1).getId(), 0);

	}

	@Test
	public void getAllAdmin_Products() {

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
	public void getProductByIdTest() {
		Product product = new Product();
		product.setId(1L);
		product.setPrice(12.0);

		when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));

		Product test = productService.getByProductId(product.getId());

		assertEquals(1L, test.getId(), 0);
		assertEquals(12.0, test.getPrice(), 0);
	}

	@Test
	public void createProductTest() {

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

		assertEquals(12.0, productService.createProduct(productModel).getPrice(), 0);
		assertEquals("Ipad", productService.createProduct(productModel).getName());

	}

	@Test
	public void deleteProductTest() {
		Long productIdTest = 1L;

		ArgumentCaptor<Long> arg = ArgumentCaptor.forClass(Long.class);

		productService.deleteProduct(productIdTest);

		verify(productRepository).deleteById(arg.capture());

		assertEquals(productIdTest, arg.getValue());

	}

	@Test
	public void updateProductQuantities() {

		ProductService productService = mock(ProductService.class);
		
		ArgumentCaptor<Cart> arg = ArgumentCaptor.forClass(Cart.class);
		Cart cart = new Cart();
		productService.updateProductQuantities(cart);

		verify(productService, times(1)).updateProductQuantities(arg.capture());
		
		assertEquals(cart, arg.getValue());

	}

}