
package com.fm.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fm.api.classes.Product;
import com.fm.api.dao.ProductService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("product")
public class ProductController {
    ProductService productService = new ProductService();
    
    @RequestMapping(method = RequestMethod.GET,headers="Accept=application/json")
     public List<Product> getAllProducts() {
      List<Product> products = productService.getAllProducts();
      return products;
     }
         
    @RequestMapping(value="/{id}", method=RequestMethod.GET, headers="Accept=application/json")
    public Product getUserById(@PathVariable int id){
        Product product = productService.getProductById(id);
        return product;
    }
    
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public boolean addProduct(@RequestBody Product product){
        boolean isCreated = productService.addProduct(product);
        return isCreated;
    }
}
