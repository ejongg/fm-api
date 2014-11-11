
package com.fm.api.controllers;

import com.fm.api.classes.InventoryProduct;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fm.api.classes.Product;
import com.fm.api.dao.ProductService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("products")
public class ProductController {
    ProductService productService = new ProductService();
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getAllProducts() {
      List<Product> products = productService.getAllProducts();
      return products;
    }
    
    // Use this to search for a specific product
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Product getUserById(@PathVariable int id){
        Product product = productService.getProductById(id);
        return product;
    }
    
    /*
        Use this to add a new product.
        Note: Product names are unique.
        {
            "name" : "Coke",
            "brand" : "Coca-Cola"
        }
    */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public Product addProduct(@RequestBody Product product){
        Product prod = productService.addProduct(product.getName(), product.getBrand());
        return prod;
    }
    
    /*
        Use this to delete a product. 
        Caution: When a product is deleted, all of its variant is also deleted.
    */ 
    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public boolean deleteProduct(@PathVariable int id){
        boolean isDeleted = productService.deleteProduct(id);
        return isDeleted;
    }
    
    /*
        Use this to edit a product name.
        {
            "prod_Id" : "1",
            "name" : "Coke",
            "brand" : "SMB"
        }
    */
    @RequestMapping(value="/edit", method=RequestMethod.PUT)
    public Product editProduct(@RequestBody Product product){
        Product prod = productService.editProductName(product.getProd_Id(), product.getName(), product.getBrand());
        return prod;
    }
    
    /*
        {
            "id" : 1,
            "price" : 26.50,
            "lifespan" : 8
        }
    */
    @RequestMapping(value="/edit/details", method=RequestMethod.PUT)
    public Product editProductDetails(@RequestBody Product product){
        Product prod = productService.editProductDetails(product);
        return prod;
    }
    
    /*
        {
            "prod_Id" : 1,
            "size" : "1L",
            "price" : 25.00,
            "bottles" : 500,
            "cases" : 3000,
            "lifespan" : 6,
            "expiration" : "5/8/2015"
        }
     */
    @RequestMapping(value = "/replenish", method = RequestMethod.POST)
    public boolean addProductVariant(@RequestBody InventoryProduct product) {
        boolean isCreated = productService.replenish(product);
        return isCreated;
    }
}
