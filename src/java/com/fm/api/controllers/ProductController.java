
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
    
    @RequestMapping(method = RequestMethod.GET,headers="Accept=application/json")
    public List<Product> getAllProducts(@PathVariable String brand) {
      List<Product> products = productService.getAllProducts(brand);
      return products;
    }
    
    // Use this to search for a specific product
    @RequestMapping(value="/{id}", method=RequestMethod.GET, headers="Accept=application/json")
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
    public boolean addProduct(@RequestBody Product product){
        boolean isCreated = productService.addProduct(product.getName(), product.getBrand());
        return isCreated;
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
            "id" : "1",
            "name" : "Coke",
            "brand" : "SMB"
        }
    */
    @RequestMapping(value="/edit", method=RequestMethod.PUT)
    public boolean editProduct(@RequestBody Product product){
        boolean isEdited = productService.editProductName(product.getId(), product.getName(), product.getBrand());
        return isEdited;
    }
    
    /*
        {
            "id" : 1,
            "size" : "1L",
            "price" : 25.00,
            "bottles" : 500,
            "cases" : 3000,
            "lifespan" : 6,
            "expiration" : "5/8/2015"
        }
     */
    @RequestMapping(value = "/add/order", method = RequestMethod.POST)
    public boolean addProductVariant(@RequestBody InventoryProduct product) {
        boolean isCreated = productService.addOrder(product);
        return isCreated;
    }
    
    /*
        Use this to delete a variant of a product.
        Example: /delete/variant/1?size=1L
    */
    @RequestMapping(value="/delete/variant/{id}", method=RequestMethod.DELETE)
    public boolean deleteVariant(@PathVariable String brand, @PathVariable int id, @RequestParam(value="size", required=false) String size){
        boolean isDeleted = productService.deleteProductVariant(id, size, brand);
        return isDeleted;
    }
    
    @RequestMapping(value="/edit/variant", method=RequestMethod.PUT)
    public boolean editVariant(@PathVariable String brand, @RequestBody Product product){
        boolean isEdited = productService.editProductVariant(product, brand);
        return isEdited;
    }
}
