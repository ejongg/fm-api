
package com.fm.api.controllers;

import com.fm.api.classes.InventoryProduct;
import com.fm.api.classes.Order;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fm.api.classes.Product;
import com.fm.api.classes.ProductInfo;
import com.fm.api.dao.ProductService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("products")
public class ProductController {
    ProductService productService = new ProductService();
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products;
    }
    
    @RequestMapping(value="/list",method=RequestMethod.GET)
    public List<ProductInfo> getProductList(){
        List<ProductInfo> products = productService.getProductList();
        return products;
    }
    
    @RequestMapping(value="/inventory", method=RequestMethod.GET)
    public List<InventoryProduct> getInventoryList(){
        List<InventoryProduct> inventory = productService.getInventory();
        return inventory;
    }
    
    @RequestMapping(value="/orders", method=RequestMethod.GET)
    public List<Order> getAllOrders(){
        List<Order> orders = productService.getAllOrders();
        return orders;
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
    "id" : 1,
    "prod_Id" : 1,
    "size" : "1L",
    "bottles" : 6000,
    "cases" : 500,
    "expiration" : "01/01/2015"
    }
     */
    @RequestMapping(value = "/replenish", method = RequestMethod.PUT)
    public Product replenish(@RequestBody InventoryProduct product) {
        Product prod = productService.replenish(product);
        return prod;
    }
    
    /*
    {
    "prod_Id" : 1,
    "size" : "1L",
    "price" : 25.00,
    "lifespan" : 6
    }
    */
    @RequestMapping(value="/add/variant", method=RequestMethod.POST)
    public Product addProductVariant(@RequestBody Product product){
        Product prod = productService.addProductVariant(product);
        return prod;
    }
}
