package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Config {
    public static Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/tubes_alpro";
//        String url = "jdbc:mysql://localhost:3306/tubes_pl";
        String user = "root";
        String pass = "";

        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Koneksi gagal : " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        try {
            Connection c = Config.getConnection();
            System.out.println("Koneksi Berhasil ke database : "+ c.getCatalog());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
