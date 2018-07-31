/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author hansaka
 */
public class dbconnect {
    
    public static Connection dbcon()
            {
                Connection conn=null;
                
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sithro","root","1234");
                }
                catch(Exception e)
                {
                    System.out.println("sss");
                    
                }
                
                return conn;
            }
    
    }
