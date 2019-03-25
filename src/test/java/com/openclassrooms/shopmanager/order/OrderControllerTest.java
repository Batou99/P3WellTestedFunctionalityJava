package com.openclassrooms.shopmanager.order;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.shopmanager.login.LoginController;
import com.openclassrooms.shopmanager.product.ProductController;
import com.openclassrooms.shopmanager.product.ProductRepository;
import com.openclassrooms.shopmanager.product.ProductService;

@WebMvcTest({LoginController.class,OrderController.class,ProductController.class})
@RunWith(SpringJUnit4ClassRunner.class)

public class OrderControllerTest {

	@Autowired
	private WebApplicationContext webContext;
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private OrderService orderService;
    
    @MockBean
    private ProductService productService;
    
    @Mock
    private ProductRepository productRepository;
    
    @Before
    public void setupMockmvc() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    			
    }
    
  
    @Test
    public void testOrderProduct() throws Exception {
   
    	mockMvc.perform(post("/order")
    			.param("name", "Nokia")
    			.param("country", "peru")
    			//.with(csrf())
    			)
    	.andDo(print())
    	.andExpect(view().name("orderCompleted"))
    	.andExpect(model().errorCount(0))
    	.andExpect(model().attribute("order", hasProperty("name",is("Nokia"))))
    	.andExpect(status().is2xxSuccessful())
    	//.andExpect(redirectedUrl("/admin/products"))
    	;
  
    }
    
    @WithMockUser(username="admin", password="password",roles = "ADMIN")
    @Test
    public void testCreateProductWithCorrectValues() throws Exception {
   
    	mockMvc.perform(post("/admin/product")
    			.param("name", "Nokia")
    			.param("price", "2.0")
    			.param("quantity", "10")
    			.with(csrf()))
    	.andDo(print())
    	.andExpect(view().name("redirect:/admin/products"))
    	.andExpect(model().errorCount(0))
    	.andExpect(status().is3xxRedirection())
    	.andExpect(redirectedUrl("/admin/products"))
    	;
    	System.out.println(productRepository.findAll());
    	
    	
  
    }
    
}


/*a. Create Product and Check that Product is in the database
b. Add the product to the cart and Check the product is in the cart
c. Add the product to the cart, Remove the product from the cart and Check the product is not in the cart*/
