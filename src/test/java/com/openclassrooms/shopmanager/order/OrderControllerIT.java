package com.openclassrooms.shopmanager.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductModel;
import com.openclassrooms.shopmanager.product.ProductRepository;
import com.openclassrooms.shopmanager.product.ProductService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderControllerIT {

	@Mock
	private ProductService productService;
	@Mock
	private OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Mock
	private OrderRepository orderRepository;

	private Product product;

	@Before
	public void createProduct() {
		ProductService productService = new ProductService(productRepository);

		ProductModel pm = new ProductModel();
		pm.setName("Nokia");
		pm.setPrice("2.0");
		pm.setQuantity("10");
		pm.setDescription("Mejor movil");
		pm.setDetails("es caro");

		product = productService.createProduct(pm);

	}

	@After
	public void deleteProductCreated() {
		productRepository.deleteAll();
	}

	@Test
	public void createProductAndAddtoTheCart() {
		ProductService productService = new ProductService(productRepository);
		OrderService orderService = new OrderService(orderRepository, productService);

		boolean resultExpected = orderService.addToCart(product.getId());
		assertEquals(true, resultExpected);

	}

	@Test
	public void CheckProductExistIntheCart() {

		ProductService productService = new ProductService(productRepository);
		OrderService orderService = new OrderService(orderRepository, productService);

		orderService.addToCart(product.getId());

		Cart cart = orderService.getCart();
		Product result = cart.findProductInCartLines(product.getId());

		assertEquals(product.getId(), result.getId());
		assertNotNull(result);
	}

	@Test
	public void addProductAndRemoveFromtheCart() {

		ProductService productService = new ProductService(productRepository);
		OrderService orderService = new OrderService(orderRepository, productService);
		
		orderService.addToCart(product.getId());
		orderService.removeFromCart(product.getId());
		
		Cart cart = orderService.getCart();

		assertEquals(0, cart.getCartLineList().size());

	}
}
