
package com.fm.api.dao;

import java.sql.*;
import java.util.*;
import com.fm.api.classes.Product;
import com.fm.api.utility.DBUtility;

public class ProductService {
    private Connection connection;
    
    public ProductService(){
        connection = DBUtility.getConnection();
    }
    
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from coke_prod_names, coke_inventory WHERE coke_prod_names.prod_id = coke_inventory.prod_id");
            while(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("prod_id"));
                product.setName(rs.getString("prod_name"));
                product.setSize(rs.getString("size"));
                product.setPrice(rs.getDouble("price"));
                product.setLogicalCount(rs.getInt("logical_count"));
                product.setPhysicalCount(rs.getInt("physical_count"));
                products.add(product);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return products;
    }
    
    public Product getProductById(int id){
        Product product = new Product();
        
        try{  
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from coke_prod_names, coke_inventory WHERE coke_prod_names.prod_id = coke_inventory.prod_id AND coke_inventory.prod_id='"+id+"'");
            while(rs.next()){
                product.setId(rs.getInt("prod_id"));
                product.setName(rs.getString("prod_name"));
                product.setSize(rs.getString("size"));
                product.setPrice(rs.getDouble("price"));
                product.setLogicalCount(rs.getInt("logical_count"));
                product.setPhysicalCount(rs.getInt("physical_count"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return product;
    }
    
    public boolean addProduct(Product product){
        try{
            String sql = "INSERT into coke_prod_names (prod_name) values (?)";
            String sql2 = "INSERT into coke_inventory (size, price, logical_count, physical_count, prod_name) values (?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            PreparedStatement stmt2 = connection.prepareStatement(sql2);
            stmt.setString(1, product.getName());
            stmt.execute();
            stmt.close();
            
            stmt2.setString(1, product.getSize());
            stmt2.setDouble(2, product.getPrice());
            stmt2.setInt(3, product.getLogicalCount());
            stmt2.setInt(4, product.getPhysicalCount());
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
