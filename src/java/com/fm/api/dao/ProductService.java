
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
            ResultSet rs = statement.executeQuery("SELECT prod_id,prod_name,size,price,logical_count,physical_count from coke_prod_names JOIN coke_inventory USING (prod_id)");
            while(rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("prod_id"));
                product.setName(rs.getString("prod_name"));
                product.setSize(rs.getString("size"));
                product.setPrice(rs.getFloat("price"));
                product.setLogical_Count(rs.getInt("logical_count"));
                product.setPhysical_Count(rs.getInt("physical_count"));
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
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from coke_prod_names JOIN coke_inventory USING (prod_id) WHERE coke_inventory.prod_id='"+id+"'");
            while(rs.next()){
                product.setId(rs.getInt("prod_id"));
                product.setName(rs.getString("prod_name"));
                product.setSize(rs.getString("size"));
                product.setPrice(rs.getFloat("price"));
                product.setLogical_Count(rs.getInt("logical_count"));
                product.setPhysical_Count(rs.getInt("physical_count"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return product;
    }
    
    private int addToNames(String name){
        int last_key = 0;
        
        try{
            String sql = "INSERT into coke_prod_names (prod_name) values (?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            while (rs.next()) {
                last_key = rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return last_key;
    }
    
    public boolean addProduct(Product product){
        try{
            int last_key = addToNames(product.getName());
            
            if(last_key == 0){
                return false;
            }else{
                String sql = "INSERT into coke_inventory (size, price, logical_count, physical_count, prod_id) values (?,?,?,?,?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, product.getSize());
                stmt.setDouble(2, product.getPrice());
                stmt.setInt(3, product.getLogicalCount());
                stmt.setInt(4, product.getPhysical_Count());
                stmt.setInt(5, last_key);
                stmt.execute();

                stmt.close();

                return true;
            }    
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteProduct(int id){
        try{
            String sql = "DELETE from coke_prod_names WHERE prod_id= ?";
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
}
