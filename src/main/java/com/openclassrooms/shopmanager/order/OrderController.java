package com.openclassrooms.shopmanager.order;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.openclassrooms.shopmanager.product.Product;
import com.openclassrooms.shopmanager.product.ProductService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.annotation.processing.FilerException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class OrderController {

    private OrderService orderService;

  
    @Autowired
    public OrderController( OrderService orderService)
    {
        this.orderService = orderService;
    }
    
 
    @GetMapping("/order/cart")
    public String getCart(Model model)
    {
        model.addAttribute("cart", orderService.getCart());
        return "cart";
    }

    @PostMapping("/order/addToCart")
    public String addToCart(@RequestParam("productId") Long productId) 
    {
    	
        boolean success = orderService.addToCart(productId);

        if (success) {
            return "redirect:/order/cart";
        } else  {
            return "redirect:/products";
        }
		
    }
   
    @PostMapping("order/removeFromCart")
    public String removeFromCart(@RequestParam Long productId)
    {
        orderService.removeFromCart(productId);

        return "redirect:/order/cart";
    }

    @GetMapping("/order")
    public String getOrderForm(Order order)
    {
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(@Valid @ModelAttribute("order") Order order, BindingResult result)
    {
        if (orderService.isCartEmpty()){
            result.reject("cart.empty");
        }

        if (!result.hasErrors()) {
            orderService.createOrder(order);
            return "orderCompleted";
        } else {
            return "order";
        }
    }
    
    @ExceptionHandler({NoSuchElementException.class})
    public ModelAndView handleException(NoSuchElementException exception) {
    ModelAndView modelAndView = new ModelAndView("redirect:/products");
    modelAndView.addObject("redirect:/products", "The product is not anymore in our inventory");
    //modelAndView.addObject("message","The product is not anymore in our inventory");
    return modelAndView;
    }
   
}
