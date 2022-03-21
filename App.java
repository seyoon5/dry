import java.sql.*;

public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://dry3.cwtqfevqzd7m.ap-northeast-2.rds.amazonaws.com:3306/dry", "root", "12345678");                    
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select nick from board;");

            while (rs.next()) {
                System.out.println(rs.getString((1)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}