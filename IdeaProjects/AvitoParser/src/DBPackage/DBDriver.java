package DBPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Elk on 05.05.2015.
 * Adapter, can work with Oracle, MySql
 * Extended methods of java.sql to "good" state
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

    /***
     * Execute the sql query and return value of first columns from first row
     * @param sqlStatement - text the sql query
     * @param param - parameteres
     * @throws Exception
     */
    public Object executeQueryFirst(String sqlStatement, HashMap<String, Object> param) throws Exception
    {
        Object res = null;
        PreparedStatement p = createStatement(sqlStatement, param);

        ResultSet rs = p.executeQuery();

        if(rs.next())
            res = rs.getObject(1);

        return res;
    }

    /***
     * Execute the sql query and return ResultSet
     * @param sqlStatement - text the sql query
     * @param param - parameteres
     * @return ResultSet
     * @throws Exception
     */
    public ResultSet executeQuery(String sqlStatement, HashMap<String, Object> param) throws Exception
    {
        ResultSet res = null;
        PreparedStatement p = createStatement(sqlStatement, param);

        res = p.executeQuery();

        return res;
    }

    /***
     *  Calling "tricky" method with parameteres
     * The Parameteres are indicated by ':'
     * Example: SELECT * FROM table t WHERE t.id = :id
     * @param sqlStatement - text the sql query
     * @param param - parameteres
     * @throws Exception
     */
    public PreparedStatement createStatement(String sqlStatement, HashMap<String, Object> param) throws Exception
    {
        PreparedStatement p = null;

        sqlStatement = sqlStatement.toUpperCase();

        String [] par = sqlStatement.split(":");

        if(par.length > 1 && (param == null || param.isEmpty()))
            throw new Exception("Is missing parameters");

        if(par.length <= 1)
            return con.prepareStatement(sqlStatement);

        for (int i = 1; i < par.length; i ++)
        {
            String s = ":" + par[i].split(" ")[0];

            sqlStatement = sqlStatement.replace(s,"?");
        }

        p = con.prepareStatement(sqlStatement);

        for (int i = 1; i < par.length; i ++)
        {
            if(! param.containsKey(par[i])) throw new Exception("Don`t find parametr:" + par[i]);

            p.setObject(i, param.get(par[i]));
        }

        return p;
    }



}


