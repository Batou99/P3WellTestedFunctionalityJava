package com.openclassrooms.shopmanager.product;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
		
	private
	Product product;
	/*
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
		
		product = productService.createProduct(pm);
		
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
		
		boolean resultExpected =orderService.addToCart(product.getId());
		assertEquals(true, resultExpected);
	
	}
	
	@Test
	public void CheckExistIntheCart() {
		
		ProductService productService = new ProductService(repository);
		OrderService orderService = new OrderService(orderRepository, productService);
		
		
		//List<Product> products = productService.getAllProducts();
	
		orderService.addToCart(product.getId());
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
		//List<Product> products = productService.getAllProducts();
		orderService.addToCart(product.getId());
		
		
		orderService.removeFromCart(product.getId());
		Cart removed = orderService.getCart();
		assertEquals(0, removed.getCartLineList().size());
		
	}*/
}