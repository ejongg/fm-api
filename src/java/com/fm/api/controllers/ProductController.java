
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
@RequestMapping("products/{brand}")
public class ProductController {
    ProductService productService = new ProductService();
    
    @RequestMapping(method = RequestMethod.GET,headers="Accept=application/json")
     public List<Product> getAllProducts(@PathVariable String brand) {
      List<Product> products = productService.getAllProducts(brand);
      return products;
     }
         
    @RequestMapping(value="/{id}", method=RequestMethod.GET, headers="Accept=application/json")
    public Product getUserById(@PathVariable String brand, @PathVariable int id){
        Product product = productService.getProductById(id, brand);
        return product;
    }
    
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public boolean addProduct(@RequestBody String product, @PathVariable String brand){
        boolean isCreated = productService.addProduct(product, brand);
        return isCreated;
    }
    
    @RequestMapping(value="/add/variant", method=RequestMethod.POST)
    public boolean addProductVariant(@RequestBody Product product, @PathVariable String brand){
        boolean isCreated = productService.addProductVariant(product);
        return isCreated;
    }
    
    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public boolean deleteProduct(@PathVariable int id){
        boolean isDeleted = productService.deleteProduct(id);
        return isDeleted;
    }
    
    @RequestMapping(value="/edit/{id}", method=RequestMethod.PUT)
    public boolean editProduct(@RequestBody String name, @PathVariable int id){
        boolean isEdited = productService.editProductName(id, name);
        return isEdited;
    }
    
    @RequestMapping(value="/delete/variant/{id}", method=RequestMethod.DELETE)
    public boolean deleteVariant(@RequestBody String size, @PathVariable int id){
        boolean isDeleted = productService.deleteProductVariant(id, size);
        return isDeleted;
    }
}
