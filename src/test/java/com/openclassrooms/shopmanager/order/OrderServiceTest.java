package com.openclassrooms.shopmanager.order;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.validation.BindingResult;

import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductController;
import com.openclassrooms.shopmanager.product.ProductModel;
import com.openclassrooms.shopmanager.product.ProductRepository;
import com.openclassrooms.shopmanager.product.ProductService;

import ch.qos.logback.classic.net.SyslogAppender;

@RunWith(MockitoJUnitRunner.class) 
public class OrderServiceTest {


	
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;
    
    @Mock
    ProductService productService;
    
    @Mock
    ProductRepository repository;
    
    @Mock
    ProductController productController;
    
    
    @Test
    public void addToCartTest(){

    	
   
    ProductService productService = new ProductService(repository);
    
    OrderService orderService = new OrderService(orderRepository,productService);
   
    ProductModel pm = new ProductModel();
    
    pm.setName("Hola");
    pm.setPrice("2.0");
    pm.setQuantity("10");
    
   productService.createProduct(pm);
   
   Product p = new Product();
   p.setId(1L);
   
   orderService.addToCart(1L);
   System.out.println(productService.getAllProducts().add(p));
   System.out.println(productService.getAllProducts());
   
    
 
	
		
    
		

		
	//	boolean test = orderService.addToCart(1L);
		
		/*if(test == true) {
			
			System.out.println("Yes");
		} else {
			System.out.println("NO");
		}*/
		
    	
    	
   
   
    }
}
