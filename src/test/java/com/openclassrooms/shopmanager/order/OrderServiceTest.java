package com.openclassrooms.shopmanager.order;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
	public void addProductToCartReturnBooleanTrue() {

		Product product = new Product();
		product.setId(1L);
		product.setPrice(12.0);

		when(productService.getByProductId(Mockito.anyLong())).thenReturn(product);
		boolean resultExpected = orderService.addToCart(product.getId());

		assertEquals(true, resultExpected);

	}

	@Test
	public void testInvokation_saveOrder() {

		Order order = new Order();
		ArgumentCaptor<Order> arg = ArgumentCaptor.forClass(Order.class);
		orderService.saveOrder(order);

		verify(orderRepository,times(1)).save(arg.capture());

		assertEquals(order, arg.getValue());

	}

	@Test
	public void getCartTest_returnTheCart() {

		OrderService orderServiceMock = mock(OrderService.class);

		Product product = new Product();
		product.setName("Nokia");
		Cart cart = new Cart();
		cart.addItem(product, 1);

		when(orderServiceMock.getCart()).thenReturn(cart);

		Cart cartFound = orderServiceMock.getCart();

		assertEquals(1, cartFound.getCartLineList().get(0).getQuantity());
		assertEquals("Nokia", cartFound.getCartLineList().get(0).getProduct().getName());

	}

	@Test
	public void testRemoveFromCart_ThenReturnZero() {

		Product product = new Product();
		product.setId(1L);

		when(productService.getByProductId(Mockito.anyLong())).thenReturn(product);
		orderService.addToCart(product.getId());

		orderService.removeFromCart(product.getId());

		Cart cartFound = orderService.getCart();
		int resultExpected = cartFound.getCartLineList().size();

		assertEquals(0, resultExpected);

	}

	@Test
	public void isCartEmptyTest_ReturnBooleanTrue() {

		Cart cartFound = orderService.getCart();

		boolean removedFromCart = cartFound.getCartLineList().isEmpty();

		assertEquals(true, removedFromCart);
	}

	@Test
	public void testInvokation_CreateOrder() {

		Order order = new Order();
		ArgumentCaptor<Order> arg = ArgumentCaptor.forClass(Order.class);
		ArgumentCaptor<Cart> arg2 = ArgumentCaptor.forClass(Cart.class);
		
		orderService.createOrder(order);

		verify(orderRepository,times(1)).save(arg.capture());
		verify(productService,times(1)).updateProductQuantities(arg2.capture());

		assertEquals(order, arg.getValue());
		assertNotNull(arg2.getValue());

	}

}
