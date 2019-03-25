package com.openclassrooms.shopmanager.product;
	
	import static org.hamcrest.CoreMatchers.containsString;
	import static org.hamcrest.CoreMatchers.hasItem;
	import static org.hamcrest.CoreMatchers.is;
	import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
	
	import static org.hamcrest.CoreMatchers.notNullValue;
	import static org.hamcrest.Matchers.hasProperty;
	import static org.hamcrest.Matchers.hasSize;
	import static org.hamcrest.Matchers.isEmptyOrNullString;
	import static org.hamcrest.Matchers.nullValue;
	import static org.hamcrest.Matchers.samePropertyValuesAs;
	import static org.junit.Assert.assertEquals;
	import static org.mockito.Mockito.verifyZeroInteractions;
	import static org.mockito.Mockito.when;
	import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
	import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
	import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
	
	import java.util.Arrays;
	import java.util.List;
	import java.util.Map;
	import java.util.Map.Entry;
	
	import javax.transaction.Transactional;
	
	import org.apache.catalina.mapper.Mapper;
	import org.junit.Before;
	import org.junit.Test;
	import org.junit.runner.RunWith;
	import org.mockito.ArgumentCaptor;
	import org.mockito.Mock;
	import org.mockito.Mockito;
	import org.mockito.MockitoAnnotations;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.SpringBootConfiguration;
	import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
	import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
	import org.springframework.boot.test.mock.mockito.MockBean;
	import org.springframework.http.MediaType;
	import org.springframework.mock.web.MockHttpServletRequest;
	import org.springframework.mock.web.MockHttpServletResponse;
	import org.springframework.security.test.context.support.WithMockUser;
	import org.springframework.test.context.ContextConfiguration;
	import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
	import org.springframework.test.context.junit4.SpringRunner;
	import org.springframework.test.context.web.WebAppConfiguration;
	import org.springframework.test.web.servlet.MockMvc;
	import org.springframework.test.web.servlet.ResultActions;
	import org.springframework.test.web.servlet.ResultMatcher;
	import org.springframework.test.web.servlet.setup.MockMvcBuilders;
	import org.springframework.util.LinkedMultiValueMap;
	import org.springframework.util.MultiValueMap;
	import org.springframework.validation.BeanPropertyBindingResult;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.context.WebApplicationContext;
	import org.springframework.web.servlet.ModelAndView;
	import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
	
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.fasterxml.jackson.databind.ObjectReader;
	import com.openclassrooms.shopmanager.login.LoginController;
	
	
	
	
	@WebMvcTest({LoginController.class,ProductController.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	public class ProductControllerIT {
	
	
		@Autowired
		private WebApplicationContext webContext;
	
	    @Autowired
	    private MockMvc mockMvc;
	
	    @Mock
		private BindingResult mockBindingResult;
	
	    @MockBean
	    private ProductService service;
	
	    @Mock
	    private ProductController controller;
	
	
	    @Before
	    public void setupMockmvc() {
	    	mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	
	    }
	
	    @WithMockUser(username="admin", password="password",roles = "ADMIN")
	    @Test
	    public void testCreateProductWithCorrectValues() throws Exception {
	
	    	mockMvc.perform(post("/admin/product")
	    			.param("name", "Nokia")
	    			.param("price", "2.0")
	    			.param("quantity", "10")
	    			.with(csrf()))
	    	//.andDo(print())
	    	.andExpect(view().name("redirect:/admin/products"))
	    	.andExpect(model().errorCount(0))
	    	.andExpect(status().is3xxRedirection())
	    	.andExpect(redirectedUrl("/admin/products"))
	    	;
	
	    }
	
	    @WithMockUser(username="admin", password="password",roles = "ADMIN")
	    @Test
	    public void testCreateProductWithPriceAndQuantityAsWrongValue() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "Nokia")
	    			.param("price", "20")
	    			.param("quantity", "1.5")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(2));
	
	
	    }
	
	    @WithMockUser(username="admin", password="password",roles = "ADMIN")
	    @Test
	    public void testCreateProductWithEmptyValues() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "")
	    			.param("price", "")
	    			.param("quantity", "")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(6));	
	    }
	
	    @WithMockUser(username="admin", password="password",roles = "ADMIN")
	    @Test
	    public void testCreateProductWithOneValue() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "IPad")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(2));	
	    }
	
	    @WithMockUser(username="admin", password="password",roles = "ADMIN")
	    @Test
	    public void testCreateProductWithNullValues() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(3));	
	    }
	
	    @WithMockUser(username="admin", password="password",roles = "ADMIN")
	    @Test
	    public void testCreateProductWithPriceAndQuantityAsString() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "Ipad")
	    			.param("quantity","Ten")
	    			.param("price", "Paco")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(2));	
	    }
	
	    @WithMockUser(username="admin", password="password",roles = "ADMIN")
	    @Test
	    public void testCreateProductWithNameAsNumeric() throws Exception {
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
	
	    @WithMockUser(username="admin", password="password",roles = "ADMIN")
	    @Test
	    public void testCreateProductWithNameAsDecimal() throws Exception {
	    mockMvc.perform(post("/admin/product")
	    			.param("name", "1.2")
	    			.param("quantity", "10")
	    			.param("price", "1.2")
	    			.with(csrf()))
	    	.andExpect(view().name("product"))
	    	.andExpect(model().attributeExists("product"))
	    	.andExpect(status().is2xxSuccessful())
	    	.andExpect(model().errorCount(1));	
	    }
	
	}
