import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Yong on 2021/12/18.
 */
public class MySqlDemo {

    private String driver;
    private String url;
    private String userName;
    private String pwd;

    private Connection connection;

    public MySqlDemo() {
        this.driver = "com.mysql.cj.jdbc.Driver";
        this.url = "jdbc:mysql://localhost:3306/eshop?useUnicode=true&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai";
        this.userName = "root";
        this.pwd = "************";

        loadDriver();
        connection = createConnection();
    }

    public void insert() {

        String template = "insert into `order` (order_id, account_id, price_total, goods_number, status) values ('%s','%s', %s, %s, 0)";

        long now = System.currentTimeMillis();
        try {
            Statement stat = connection.createStatement();

            for (int i = 0; i < 1000000; i++) {
                String sql = String.format(template, i, i, i, i);

                stat.execute(sql);
            }

            stat.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert() takes 695057ms
        System.out.println("Insert() takes " + (System.currentTimeMillis() - now) + "ms");
    }

    public void insertBatch() {
        String template = "insert into `order` (order_id, account_id, price_total, goods_number, status) values ('%s','%s', %s, %s, 0)";

        long now = System.currentTimeMillis();
        try {
            Statement stat = connection.createStatement();

            for (int i = 0; i < 1000000; i++) {

                String sql = String.format(template, i, i, i, i);
                stat.addBatch(sql);

                if (i % 1000 == 0) {
                    stat.executeBatch();
                }
            }

            stat.executeBatch();

            stat.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert() takes 587853ms
        System.out.println("Insert() takes " + (System.currentTimeMillis() - now) + "ms");
    }

    public void insertPrepare() {
        String template = "insert into `order` (order_id, account_id, price_total, goods_number, status) values (?, ?, ?, ?, 0)";

        long now = System.currentTimeMillis();
        try {
            PreparedStatement stat = connection.prepareStatement(template);

            for (int i = 0; i < 1000000; i++) {
                String str = String.valueOf(i);
                stat.setString(1, str);
                stat.setString(2, str);
                stat.setInt(3, i);
                stat.setInt(4, i);

                stat.execute();
            }

            stat.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert() takes 716278ms
        System.out.println("Insert() takes " + (System.currentTimeMillis() - now) + "ms");
    }

    public void insertPrepareBatch() {
        String template = "insert into `order` (order_id, account_id, price_total, goods_number, status) values (?, ?, ?, ?, 0)";

        long now = System.currentTimeMillis();
        try {
            PreparedStatement stat = connection.prepareStatement(template);

            for (int i = 0; i < 1000000; i++) {
                String str = String.valueOf(i);
                stat.setString(1, str);
                stat.setString(2, str);
                stat.setInt(3, i);
                stat.setInt(4, i);

                stat.addBatch();

                if (i % 1000 == 0) {
                    stat.executeBatch();
                }
            }

            stat.executeBatch();

            stat.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert() takes 558208ms
        System.out.println("Insert() takes " + (System.currentTimeMillis() - now) + "ms");
    }

    private void loadDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, pwd);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void main(String[] args) {
        MySqlDemo mySqlDemo = new MySqlDemo();

        mySqlDemo.insert();                // takes 695057ms
//        mySqlDemo.insertPrepare();       // takes 716278ms
//        mySqlDemo.insertBatch();         // takes 587853ms
//        mySqlDemo.insertPrepareBatch();  // takes 558208ms
    }
}
