package com.openclassrooms.shopmanager.product;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.openclassrooms.shopmanager.order.Cart;
import com.openclassrooms.shopmanager.order.OrderRepository;
import com.openclassrooms.shopmanager.order.OrderService;



@RunWith(SpringRunner.class)
@DataJpaTest
public class LastTest {
    
	
	
	@Mock
	ProductService productService;
	@Mock
	OrderService orderService;
	
	@Autowired
	private ProductRepository repository;
	
	@Mock
	private OrderRepository orderRepository;
		
	
	@Before
	public void createProduct() {
		ProductService productService = new ProductService(repository);
		OrderService orderService = new OrderService(orderRepository, productService);
		
		ProductModel pm = new ProductModel();
		pm.setName("Nokia");
		pm.setPrice("2.0");
		pm.setQuantity("10");
		pm.setDescription("Mejor movil");
		pm.setDetails("es caro");
		productService.createProduct(pm);
		
	}
	
	@After
	public void deleteP() {
		ProductService productService = new ProductService(repository);
		OrderService orderService = new OrderService(orderRepository, productService);
		
		repository.deleteAll();
	
	}
	
	@Test
	public void createProductAndAddtoTheCartAndRemove() {
		ProductService productService = new ProductService(repository);
		OrderService orderService = new OrderService(orderRepository, productService);
		//setid product to 6L
		productService.getAllProducts().get(5).setId(6L);
		System.out.println(productService.getAllProducts().get(5).getId());
		
		boolean resultExpected =orderService.addToCart(6L);
		
		
		assertEquals(true, resultExpected);
	
	}
	
	@Test
	public void CheckExistIntheCart() {
		
		ProductService productService = new ProductService(repository);
		OrderService orderService = new OrderService(orderRepository, productService);
		productService.getAllProducts().get(5).setId(6L);
		//setid product to 6L
		System.out.println(productService.getAllProducts().get(5).getId());
		orderService.addToCart(6L);
		Cart found = orderService.getCart();
		int  resultWanted  =found.getCartLineList().size();
		String resultado =found.getCartLineList().get(0).getProduct().getName();
		
		assertEquals(1, resultWanted);
		assertEquals("Nokia", resultado);
	}
	
	@Test
	public void RemoveFromtheCart() {
		
		ProductService productService = new ProductService(repository);
		OrderService orderService = new OrderService(orderRepository, productService);
		orderService.addToCart(6L);
		Cart removed = orderService.getCart();
		orderService.removeFromCart(6L);
		assertEquals(0, removed.getCartLineList().size());
		
	}
}