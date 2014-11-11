
package com.fm.api.dao;

import com.fm.api.classes.CustomerOrder;
import com.fm.api.classes.InventoryProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import com.fm.api.classes.Product;
import com.fm.api.classes.ProductInfo;
import com.fm.api.utility.DBUtility;
import java.sql.Statement;

public class CustomerOrderService {
    private Connection connection = DBUtility.getConnection();
    
    public List<CustomerOrder> getAllCustomerOrders(){
        List<CustomerOrder> orders = new ArrayList<>();
        try{
            String sql = "SELECT * from cust_orders";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerOrder order = new CustomerOrder();
                order.setId(rs.getInt("cust_order_id"));
                order.setEstablishment(rs.getString("establishment_name"));
                order.setOwner(rs.getString("owner_name"));
                order.setDelivery_date(rs.getString("delivery_date"));
                order.setOrder_date(rs.getString("order_date"));
                orders.add(order);
            }
            stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return orders;
    }
    
    public CustomerOrder getOrderById(int id){
        try{
            CustomerOrder order = new CustomerOrder();
            String sql = "SELECT * from cust_orders where cust_order_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                order.setId(rs.getInt("cust_order_id"));
                order.setEstablishment(rs.getString("establishment_name"));
                order.setOwner(rs.getString("owner_name"));
                order.setDelivery_date(rs.getString("delivery_date"));
                order.setOrder_date(rs.getString("order_date")); 
            }
            
            stmt.close();
            return order;
        }catch(Exception e){
            
        }
        return null;
    }
    
    public CustomerOrder addOrder(CustomerOrder order){
        try{
            String sql = "INSERT into cust_orders (establishment_name,owner_name,delivery_date,order_date) values (?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.execute();
            
            ResultSet generatedKey = stmt.getGeneratedKeys();
            
            if(generatedKey.next()){
                CustomerOrder ord = getOrderById(generatedKey.getInt(1));
                return ord;
            }
            
            stmt.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
