package com.pbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Koneksi {
    // 1. Downlaod mysql:mysql-connector-java

    private Connection conn;
    private Statement st;

    public Koneksi(){
        try{
            final String urldb = "jdbc:mysql://localhost:3306/vendingmachine";
            final String user = "root";
            final String password = "jumanji.123";
            conn = DriverManager.getConnection(urldb, user, password);
            System.out.println("Koneksi berhasil disambungkan");
        } catch(SQLException e){
            System.out.println("Koneksi gagal : " + e);
        } catch(Exception e){
            System.out.println("Terjadi error yang tidak diketahui");
        }
    }

     //Method untuk mengambil data- SELECT
    public ResultSet getData(String query){
            try{
                st = conn.createStatement();
                return st.executeQuery(query);
            } catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }


    // Method untuk insert, Update, Delete
    public int manipulasiData(String query){
        try{
            st = conn.createStatement();
            return st.executeUpdate(query);
        } catch(SQLException e){
            System.out.println("SQL Error : " + e);

        } catch (Exception e){
            System.out.println("Terjadi error yang tidak diketahui: " + e);
        }
        return 0;
    }

    public void disconnect(){
        try{
            conn.close();
            st.close();
        } catch(SQLException e){
            System.out.println("Koneksi tidak berhasil ditutup : " + e);
        } catch (Exception e){
            System.out.println("Terjadi error yang tidak diketahui: " + e);
        }
    }
}
