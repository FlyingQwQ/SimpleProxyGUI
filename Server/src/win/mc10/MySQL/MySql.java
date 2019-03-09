package win.mc10.MySQL;

import win.mc10.Log;

import java.sql.*;

public class MySql {

    private String SQLURL = "";
    private String SQLUser = "";
    private String SQLPass = "";

    private static Connection con = null;
    private static Statement statement = null;

    public MySql() {
        this.SQLURL = "jdbc:mysql://10mc.win:3306/simplect?autoReconnect=true";
        this.SQLUser = "root";
        this.SQLPass = "root";

        this.Connect();
    }

    public void Connect() {
        try {
            con = DriverManager.getConnection(this.SQLURL, this.SQLUser, this.SQLUser);
            this.statement = con.createStatement();
            Log.INFO("数据库连接成功！");
        } catch (SQLException e) {
            Log.INFO("数据库连接失败！");
        }
    }

    public static ResultSet ExecutSQL(String SQL) {
        try {
            if(statement != null) {
                return statement.executeQuery(SQL);
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PreparedStatement ExecutPreparedSQL(String SQL) {
        if(statement != null) {
            try {
                return con.prepareStatement(SQL);
            } catch (SQLException e) {
                return null;
            }
        }
        return null;
    }
}
