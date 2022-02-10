import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc {
    Connection con;
    java.sql.Statement stmt;
    ResultSet rs;

    String url = "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry";
    String id = "root";
    String pw = "123qwe123";

    public Jdbc() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getConnection() {
        try {
            con = DriverManager.getConnection(url, id, pw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getData() {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select user from chat_room");

            while (rs.next()) {
                System.out.println(rs.getString("user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Jdbc jdbc = new Jdbc();
        jdbc.getConnection();
        jdbc.getData();
        jdbc.closeConnection();
    }

}