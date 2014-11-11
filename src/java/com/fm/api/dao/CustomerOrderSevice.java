
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

public class CustomerOrderSevice {
    private Connection connection = DBUtility.getConnection();
    
    public List<CustomerOrder> getAllCustomerOrders(){
        try{
            List<CustomerOrder> orders = new ArrayList<>();
            String sql = "SELECT * from cust_orders";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerOrder order = new CustomerOrder();
            }
        }catch(Exception e){
            
        }
        return null;
    }
}
