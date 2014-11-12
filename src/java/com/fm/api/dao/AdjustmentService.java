
package com.fm.api.dao;

import com.fm.api.classes.BadOrder;
import com.fm.api.classes.CustomerOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import com.fm.api.utility.DBUtility;
import java.sql.Statement;

public class AdjustmentService {
    private Connection connection = DBUtility.getConnection();
    
    public List<BadOrder> getAllBadOrders(){
        List<BadOrder> badOrders = new ArrayList<>();
        
        try{
            String sql = "SELECT * from adjustments";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                BadOrder bo = new BadOrder();
                bo.setId(rs.getInt("adj_id"));
                bo.setName(rs.getString("prod_name"));
                bo.setBrand(rs.getString("brand"));
                bo.setSize(rs.getString("size"));
                bo.setBottles(rs.getInt("bottles"));
                bo.setPrice(rs.getDouble("price"));
                bo.setExpense(rs.getDouble("expense"));
                bo.setDate(rs.getString("date"));
                badOrders.add(bo);
            }
            
            stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return badOrders;
    }
    
    public BadOrder getBadOrderById(int id){
        try {
            String sql = "SELECT * from adjustments";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            BadOrder badOrder = new BadOrder();
            while (rs.next()) {
                badOrder.setId(rs.getInt("adj_id"));
                badOrder.setName(rs.getString("prod_name"));
                badOrder.setBrand(rs.getString("brand"));
                badOrder.setSize(rs.getString("size"));
                badOrder.setBottles(rs.getInt("bottles"));
                badOrder.setPrice(rs.getDouble("price"));
                badOrder.setExpense(rs.getDouble("expense"));
                badOrder.setDate(rs.getString("date"));
            }

            stmt.close();
            return badOrder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
   public BadOrder addBadOrder(BadOrder bo){
       try{
           String sql = "INSERT into adjustments (prod_name,size,brand,bottles,price,expense,date) values (?,?,?,?,?,?,?)";
           PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           stmt.setString(1, bo.getName());
           stmt.setString(2, bo.getSize());
           stmt.setString(3, bo.getBrand());
           stmt.setInt(4, bo.getBottles());
           stmt.setDouble(5, bo.getPrice());
           stmt.setDouble(6, bo.getExpense());
           stmt.setString(7, bo.getDate());
           
           stmt.execute();
           
           ResultSet generatedKey = stmt.getGeneratedKeys();
           while(generatedKey.next()){
               BadOrder badOrder = getBadOrderById(generatedKey.getInt(1));
               return badOrder;
           }
           
           stmt.close();
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
   }
}
