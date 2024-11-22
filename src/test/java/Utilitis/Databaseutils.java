package Utilitis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Databaseutils {

    static String dbUrl = "jdbc:mysql://stack-overflow.cfse9bqqndon.us-east-1.rds.amazonaws.com/CraterDBS";
    static String userName = "craterdbuser";
    static String password = "ptschool2023";


    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(dbUrl, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  static List<List<String>> executeQuery(String query){
        List<List<String>> dataSet = new ArrayList<>();
        try {

            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colCount = rsmd.getColumnCount();
            while(resultSet.next()){

                List<String> row = new ArrayList<>();
                for (int i = 1; i <colCount ; i++) {
                    //System.out.print(resultSet.getString(i) + "|| ");
                    row.add(resultSet.getString(i));
                }
                dataSet.add(row);

            }
            System.out.println(dataSet);

            resultSet.close();
            statement.close();
            connection.close();
            return dataSet;
        } catch (SQLException e) {
            System.out.println("Connection not successful");
            e.printStackTrace();
        }
        return dataSet;
    }



    public  static List<String> selectRecord(String query){
        List<String> dataSet = new ArrayList<>();
        try {

            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colCount = rsmd.getColumnCount();
            if(resultSet.next()){
                for (int i = 1; i < colCount; i++) {
                    dataSet.add(resultSet.getString(i));
                }
            }
            System.out.println(dataSet);
            //close all connections
            resultSet.close();
            statement.close();
            connection.close();
            return dataSet;
        } catch (SQLException e) {
            System.out.println("Connection not successful");
            e.printStackTrace();
        }
        return dataSet;
    }



    public  static String selectRecord(String query, String colName){
        String dataSet= null;

        try {

            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colCount = rsmd.getColumnCount();
            if(resultSet.next()){
                dataSet= resultSet.getString(colName);
            }
            System.out.println(dataSet);

            resultSet.close();
            statement.close();
            connection.close();
            return dataSet;
        } catch (SQLException e) {
            System.out.println("Connection not successful");
            e.printStackTrace();
        }
        return dataSet;
    }
}
