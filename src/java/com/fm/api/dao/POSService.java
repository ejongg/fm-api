
package com.fm.api.dao;

import com.fm.api.classes.BodegaTransaction;
import com.fm.api.classes.BodegaTransactionProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import com.fm.api.classes.Product;
import com.fm.api.utility.DBUtility;

public class POSService {
    private Connection connection;
    
    public POSService(){
        connection = DBUtility.getConnection();
    }
    
    public boolean saveTransactionHistory(BodegaTransaction data){
        //BodegaTransaction transaction = new BodegaTransaction();
        try{
            String sql = "INSERT into bodega_transactions (trasaction_id,cases,bottles,price,totalprice,date) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, data.getTransaction_id());
            stmt.setInt(2, data.getCases());
            stmt.setInt(3, data.getBottles());
            stmt.setDouble(4, data.getPrice());
            stmt.setDouble(5, data.getTotalPrice());
            stmt.setString(6, data.getDate());
            
            stmt.execute();
            
            saveTransactionProducts(data.getProducts());
            
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean saveTransactionProducts(List<BodegaTransactionProduct> prods){
        //BodegaTransactionProducts transaction = new BodegaTransactionProducts();
        try{
            String sql = "INSERT into bodega_transactions_products (trasaction_id,prod_id,cases,bottles) VALUES (?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            for(BodegaTransactionProduct prod : prods){
                stmt.setInt(1, prod.getProductID());
                stmt.setInt(2, prod.getCases());
                stmt.setInt(3, prod.getBottles());
                stmt.addBatch();
            }
            stmt.executeBatch();
            
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
