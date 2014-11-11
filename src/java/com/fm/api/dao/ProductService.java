
package com.fm.api.dao;

import com.fm.api.classes.InventoryProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import com.fm.api.classes.Product;
import com.fm.api.utility.DBUtility;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductService {
    private Connection connection;
    
    public ProductService(){
        connection = DBUtility.getConnection();
    }
    
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        try{
            String sql = "SELECT * from products JOIN product_details USING (prod_id)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("details_id"));
                product.setProd_Id(rs.getInt("prod_id"));
                product.setName(rs.getString("prod_name"));
                product.setSize(rs.getString("size"));
                product.setBottles(rs.getInt("bottles"));
                product.setCases(rs.getInt("cases"));
                product.setPrice(rs.getFloat("price"));
                product.setLifespan(rs.getInt("lifespan"));
                products.add(product);
            }
            stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return products;
    }
    
    public Product getProductById(int id){
        Product product = new Product();  
        try{ 
            String sql = "SELECT * from products JOIN product_details USING (prod_id) WHERE details_id ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                product.setId(rs.getInt("details_id"));
                product.setProd_Id(rs.getInt("prod_id"));
                product.setName(rs.getString("prod_name"));
                product.setSize(rs.getString("size"));
                product.setBottles(rs.getInt("bottles"));
                product.setCases(rs.getInt("cases"));
                product.setPrice(rs.getFloat("price"));
                product.setLifespan(rs.getInt("lifespan")); 
            }
            
            stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return product;
    }
    
    public Product addProduct(String name, String brand){
        Product product = new Product();
        
        try{
            String sql="INSERT into products (prod_name, brand) values (?,?)";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, brand);
            stmt.execute();
            stmt.close();
            
            product.setName(name);
            product.setBrand(brand);
            return product;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteProduct(int id){
        try{
            String sql = "DELETE from products WHERE prod_id= ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            
            stmt.close();
            return true;
            
        }catch(Exception e){
            e.printStackTrace();
        }  
        return false;
    }
    
    public Product editProductName(int id, String name, String brand){
        Product product = new Product();
        try{
            String sql = "UPDATE products set prod_name=?, brand=? where prod_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, brand);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();
            
            product.setName(name);
            product.setBrand(brand);
            return product;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public boolean addOrder(InventoryProduct product){
        try{
            String sql = "INSERT into orders (prod_id,bottles,cases,date_received) VALUES (?,?,?, now())";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, product.getId());
            stmt.setInt(2, product.getBottles());
            stmt.setInt(3, product.getCases());
            stmt.execute();             
            
            /*
                Adds the new order in the inventory table
            */
            addToInventory(product);
            
            /*
                Check if product details if the product already exist.
                If not it adds a new record in the product_details table.
                If yes it updates the record.
            */ 
            boolean checkVariant = checkProductDetailsTable(product.getId(), product.getSize());
            if(checkVariant == false){
                addProductVariant(product);
            }else{
                Product oldProductCount = getProductById(product.getId());
                updateProductCount(product, oldProductCount);
            }
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public void addToInventory(InventoryProduct product){
        try{
            String sql = "INSERT into inventory (prod_id,bottles,cases,size,exp_date,age) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, product.getId());
            stmt.setInt(2, product.getBottles());
            stmt.setInt(3, product.getCases());
            stmt.setString(4, product.getSize());
            stmt.setString(5, product.getExpiration());
            stmt.setInt(6, 0);
            stmt.execute();
            stmt.close();
        }catch(Exception e){
            
        }
    }
    
    public boolean addProductVariant(Product product) {
        try {
            String sql = "INSERT into product_details (prod_id,bottles,cases,size,price,lifespan) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, product.getId());
            stmt.setInt(2, product.getBottles());
            stmt.setInt(3, product.getCases());
            stmt.setString(4, product.getSize());
            stmt.setDouble(5, product.getPrice());
            stmt.setInt(6, product.getLifespan());
            stmt.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean updateProductCount(Product newProducts, Product product){
        try{
            String sql = "UPDATE product_details set cases=?, bottles=? WHERE prod_id=? AND size=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, product.getCases() + newProducts.getCases());
            stmt.setInt(2, product.getBottles() + newProducts.getBottles());
            stmt.setInt(3, product.getId());
            stmt.setString(4, product.getSize());
            stmt.executeUpdate();
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    private boolean checkProductDetailsTable(int prod_id, String size){
        try{
            int exist = 0;
            String sql = "SELECT exists (select * from product_details where prod_id=? and size=?) as exist";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, prod_id);
            stmt.setString(2, size);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                exist = rs.getInt("exist");
            }
            
            if(exist == 1){
                return true;
            }
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean deleteProductVariant(int id, String size, String brand){
        try{
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean editProductVariant(Product product, String brand){
        try{
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
}
