package DBPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Elk on 05.05.2015.
 */
public class JdbcOracleCon implements IJdbcCon{

    final private String driverName = "oracle.jdbc.driver.OracleDriver";

    private String url = "";

    private String host = "";
    private String port = "";
    private String sid = "";
    private String user = "";
    private String password = "";

    private Connection con = null;

    public JdbcOracleCon(String host, String port, String sid, String user, String password)
    {
        url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;

        this.host = host;
        this.port = port;
        this.sid = sid;

        this.user = user;
        this.password = password;

    }

    public Connection CreateConnect()
    {
        try
        {
            Class.forName(driverName);


            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            con = null;
        } catch (ClassNotFoundException e) {
            con = null;
        }

        return con;
    }
}
