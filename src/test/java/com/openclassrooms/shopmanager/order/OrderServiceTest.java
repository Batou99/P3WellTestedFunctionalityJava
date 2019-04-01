package com.openclassrooms.shopmanager.order;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductModel;
import com.openclassrooms.shopmanager.product.ProductRepository;
import com.openclassrooms.shopmanager.product.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	ProductRepository productRepository;

	@Mock
	ProductService productService;


	@Test
	public void testAddToCart() {
		ProductService productService = new ProductService(productRepository);
		OrderService orderService = new OrderService(orderRepository, productService);

		Product p = new Product();
		p.setName("Nokia");
		p.setPrice(2.0);
		p.setQuantity(10);
		p.setId(1L);

		when(productService.getAllProducts()).thenReturn(Arrays.asList(p));

		List<Product> list = productRepository.findAll();

		boolean test = orderService.addToCart(list.get(0).getId());

		assertEquals(true, test);

	}

	@Test
	public void testAddItemInCart() {

		Product p = new Product();
		p.setName("Nokia");
		p.setPrice(2.0);
		p.setQuantity(10);
		p.setId(1L);

		orderService.getCart().addItem(p, 1);

		List<CartLine> lista = orderService.getCart().getCartLineList();
		int resultExpected = lista.get(0).getQuantity();
		assertEquals(1, resultExpected);

	}


}
