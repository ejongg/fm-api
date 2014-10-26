
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
    
    private String[] chooseTable(String brand){
        String[] tables = new String[2];
        switch (brand) {
            case "coca-cola":
                tables[0] = "coke_prod_names";
                tables[1] = "coke_inventory";
                break;
            case "beer":
                tables[0] = "beer_prod_names";
                tables[1] = "beer_inventory";
                break;
        }
        return tables;
    }
    
    public List<Product> getAllProducts(String brand){
        List<Product> products = new ArrayList<>();
        try{
            String[] tables = chooseTable(brand);
            String sql = "SELECT prod_id,prod_name,size,price,logical_count,physical_count from "+ tables[0] + " JOIN " + tables[1] + " USING (prod_id)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
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
    
    public Product getProductById(int id, String brand){
        Product product = new Product();
        
        try{ 
            String[] tables = chooseTable(brand);
            String sql = "SELECT * from "+ tables[0] +" JOIN "+ tables[1] +" USING (prod_id) WHERE coke_inventory.prod_id= ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
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
    
    public boolean addProduct(String name, String brand){
        try{
            String[] tables = chooseTable(brand);
            String sql="INSERT into "+ tables[0] + " (prod_name) values (?)";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.execute();

            stmt.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteProduct(int id, String brand){
        try{
            String[] tables = chooseTable(brand);
            String sql = "DELETE from "+ tables[0] +" WHERE prod_id= ?";
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
    
    public boolean editProductName(int id, String name, String brand){
        try{
            String[] tables = chooseTable(brand);
            String sql = "UPDATE "+ tables[0] +" set prod_name=? where prod_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean addProductVariant(Product product, String brand) {
        try {
            String[] tables = chooseTable(brand);
            String sql = "INSERT into " + tables[1] + " (size, price, logical_count, physical_count, prod_id) values (?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, product.getSize());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getLogical_Count());
            stmt.setInt(4, product.getPhysical_Count());
            stmt.setInt(5, product.getId());
            stmt.execute();
            stmt.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteProductVariant(int id, String size, String brand){
        try{
            String[] tables = chooseTable(brand);
            String sql = "DELETE from " + tables[1] + " where prod_id=? AND size=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, size);
            stmt.execute();
            stmt.close();
            return true;
        }catch(Exception e){
            
        }
        return false;
    }
    
    public boolean editProductVariant(Product product, String brand){
        try{
            String[] tables = chooseTable(brand);
            String sql = "UPDATE "+ tables[1] + " set price=?, logical_count=?, physical_count=? where prod_id=? AND size=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, product.getPrice());
            stmt.setInt(2, product.getLogical_Count());
            stmt.setInt(3, product.getPhysical_Count());
            stmt.setInt(4, product.getId());
            stmt.setString(5, product.getSize());
            stmt.executeUpdate();
        }catch(Exception e){
            
        }
        return false;
    }
}
