package ru.vasyukov;

import java.sql.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class Test {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PSW = "rfr_,snm_postgre";
    public static Connection conn;

    public static void main(String[] args) {
        try {
            Enumeration<Driver> dr = DriverManager.getDrivers();
            Iterator<Driver> it = dr.asIterator();
            while (it.hasNext()) {
                Driver el = it.next();
                System.out.println(el.getClass().getName());
            }
            conn = DriverManager.getConnection(URL, USER, PSW);
            System.out.println("valid: "+conn.isValid(1));
            System.out.println("close: "+conn.isClosed());
            Properties pr = conn.getClientInfo();
            pr.forEach((i,j)-> System.out.println(i+" | "+j));
            Array ar= conn.createArrayOf("ARRAY", new Object[]{});
            System.out.println(ar.getBaseTypeName());
            System.out.println(ar);
            ResultSet res = execQuery("select * from students");
            if (res != null) {
                System.out.println(res.getRow());
                while (res.next()) {
                    System.out.println(res.getRow());
                    System.out.println(res.getString("name"));
                }
            }
        }
        catch (SQLException e) { e.printStackTrace(); }

        try { if(conn!=null) conn.close(); }
        catch (SQLException e) { e.printStackTrace(); }

    }

    private static ResultSet execQuery(String query) throws SQLException {
        Statement stat= conn.createStatement();
        //return stat.executeUpdate();
        return stat.execute(query) ? stat.getResultSet() : null;
    }
}
