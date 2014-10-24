
package com.fm.api.utility;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBUtility {
    private static Connection connection= null;
    
    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }else{
            try{
                String driver = "com.mysql.jdbc.Driver";
                Class.forName(driver);
                connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/fm_marketing", "root", "");
            }
            catch(ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }
            return connection;
        }
    }
}
