/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lueva
 */
public class DBQuery {
    private static String query;
  //statement reference
    private static Statement statement;
    private static ResultSet result;
    
    public static void makeQuery(String q){
        query =q;
        try{
         
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=statement.executeQuery(q);
             if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                statement.executeUpdate(q);
            
        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
    public static ResultSet getResult(){
        return result;
    }
    

    
    
    //Creating statement object
    public static void setStatement(Connection conn) throws SQLException{
        statement = conn.createStatement();
    }
    
    //return statement object
    public static Statement getStatement()
    {
        return statement;
    }
}
