package com.openclassrooms.shopmanager.product;
	

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
	
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;	
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
	


	
	
	
@WithMockUser(username="admin", password="password",roles = "ADMIN")
@WebMvcTest(ProductController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerIT {
	
	
	@Autowired
	private WebApplicationContext webContext;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;
	
	@Mock
	private ProductController controller;
	
	
	@Before
	public void setupMockmvc() {
	   mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	
	    }
	
	  
	@Test
	public void testCreateProductWithCorrectValues() throws Exception {
	
	    mockMvc.perform(post("/admin/product")
	    		.param("name", "Nokia")
	    		.param("price", "2.0")
	    		.param("quantity", "10")
	    		.with(csrf()))
	    	.andExpect(view().name("redirect:/admin/products"))
	    	.andExpect(model().errorCount(0))
	    	.andExpect(status().is3xxRedirection())
	    	.andExpect(redirectedUrl("/admin/products"))
	    	;
	    }
	
	@Test
	public void testCreateProductWhereNameIsWrittenWithIncorrectCharacters() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("price", "1.2")
	    		.param("quantity", "10")
	    		.param("name", "Nokia%%%%")
	    		.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));	
	    }
	
	@Test
	public void testCreateProductWhereNameIsRequired() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    		.param("price", "1.2")
	    		.param("quantity", "10")
	    		.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));	
	    }
	
	@Test
	public void testCreateProductMissingPrice() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "IPad")
	    			.param("quantity", "10")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));	
	    }
 
	@Test
	public void testCreateProductWithPriceAsNotNumberValue() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "Nokia")
	    			.param("price", "ten bucks")
	    			.param("quantity", "15")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));
	
	
	    }
	
	@Test
	public void testCreateProductWithPriceNotGreaterThanZero() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "Nokia")
	    			.param("price", "-2.3")
	    			.param("quantity", "15")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));
	
	
	    }
	  
	@Test
	public void testCreateProductMissingQuantity() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "IPad")
	    			.param("price", "1.0")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));	
	    }

	@Test
	public void testCreateProductWithdQuantityNotInteger() throws Exception {
		mockMvc.perform(post("/admin/product")
	    			.param("name", "Ipad")
	    			.param("quantity","ten")
	    			.param("price", "23.2")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));	
	    }
	
	@Test
	public void testCreateProductWithQuantityNotGreaterThanZero() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "Nokia")
	    			.param("price", "2.3")
	    			.param("quantity", "0")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));
	    }
	    
	@Test
	public void testCreateProductWithNameOnlyAsNumeric() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "12")
	    			.param("quantity", "10")
	    			.param("price", "1.2")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));	
	    }
	
	@Test
	public void testCreateProductWithNameOnlyAsDecimal() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "1.200")
	    			.param("quantity", "10")
	    			.param("price", "1.2")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));	
	}

}
