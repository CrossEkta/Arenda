package DBPackage;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Elk on 05.05.2015.
 */
public class DBDriver
{

    Connection con = null;

    public DBDriver(IJdbcCon jdbcCon)
    {
        con = jdbcCon.CreateConnect();
    }

    public void Close()
    {
        if(con != null)
        {
            try {
                con.close();
            } catch (SQLException e) {
                con = null;
            }
        }


    }


}


