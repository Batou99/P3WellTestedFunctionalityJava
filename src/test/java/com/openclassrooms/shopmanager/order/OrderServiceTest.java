package com.openclassrooms.shopmanager.order;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.shopmanager.product.Product;
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
	public void testAddProductToCart() {

		Product product = new Product();
		product.setId(1L);
		product.setPrice(12.0);

		when(productService.getByProductId(Mockito.anyLong())).thenReturn(product);
		boolean resultExpected = orderService.addToCart(product.getId());

		assertEquals(true, resultExpected);

	}

	@Test
	public void testAddItemInCart() {

		Product product = new Product();
		product.setName("Nokia");
		product.setPrice(2.0);
		product.setQuantity(10);
		product.setId(1L);

		orderService.getCart().addItem(product, 1);

		List<CartLine> lista = orderService.getCart().getCartLineList();

		int resultExpected = lista.get(0).getQuantity();
		String stringExpected = lista.get(0).getProduct().getName();
		boolean cartIsNotEmpty = orderService.isCartEmpty();

		assertEquals(1, resultExpected);
		assertEquals("Nokia", stringExpected);
		assertEquals(false, cartIsNotEmpty);

	}

	@Test
	public void testRemoveFromCart() {

		Product product = new Product();
		product.setId(1L);
		product.setPrice(12.0);

		when(productService.getByProductId(Mockito.anyLong())).thenReturn(product);
		orderService.addToCart(product.getId());

		orderService.removeFromCart(product.getId());
		Cart cartFound = orderService.getCart();
		int resultExpected = cartFound.getCartLineList().size();

		boolean removedFromCart = cartFound.getCartLineList().isEmpty();

		assertEquals(true, removedFromCart);
		assertEquals(0, resultExpected);

	}

}
