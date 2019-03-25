package com.openclassrooms.shopmanager.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.validation.Valid;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping( value = {"/products" , "/"})
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products")
    public String getAdminProducts(Model model) {
        model.addAttribute("products", productService.getAllAdminProducts());
        return "productsAdmin";
    }


    @GetMapping("/admin/product")
    public String productForm(Model model) {
        model.addAttribute("product",new ProductModel());
        return "product";
    }

    // i changed the modelAtribbute to product instead of productModel for make it works
    @PostMapping("/admin/product")
    public String createProduct(@Valid @ModelAttribute("product") ProductModel productModel, BindingResult result)
    {
    	// add println for see the errors
    	System.out.println("binding result: " + result);
    	
    	List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors ) {
            System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            
        }
        if (!result.hasErrors()) {
            productService.createProduct(productModel);
            return "redirect:/admin/products";
        } else {
            return "product";
        }
    }

    @PostMapping("/admin/deleteProduct")
    public String deleteProduct(@RequestParam("delProductId") Long delProductId,Model model)
    {
        productService.deleteProduct(delProductId);
        model.addAttribute("products", productService.getAllAdminProducts());

        return "productsAdmin";
    }
}
