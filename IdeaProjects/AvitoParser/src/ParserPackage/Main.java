package ParserPackage;

import DBPackage.DBDriver;
import DBPackage.JdbcOracleCon;

import java.io.IOException;

public class Main {

    public static void main(String args[]) {
        long startTime = System.nanoTime();

        String startUrl = "https://www.avito.ru/krasnoyarsk/kvartiry/sdam/na_dlitelnyy_srok?i=1&user=1";

        DBDriver dbDriver = null;

        try
        {
            // коннект к БД
            dbDriver = new DBDriver(new JdbcOracleCon(Global.host, Global.port, Global.sid, Global.username, Global.password));

            ParserRun(dbDriver, startUrl);
        }
        catch (Exception e)
        {
            System.out.println("Было выброшено исключение с текстом: " + e.toString());
        }
        finally
        {
            if(dbDriver != null)
                dbDriver.Close();
        }

        long endTime = System.nanoTime();
        long progressTime = endTime - startTime;
        System.out.println("Выполнение программы заняло " + progressTime/1000000000.0 + " секунд");
    }

    private static void ParserRun(DBDriver dbDriver, String startUrl) throws IOException
    {
        //AvitoParser ap = new AvitoParser();
        //ap.parseApartmentFromUrl(startUrl);
        NgsParser np = new NgsParser();
        np.parseApartmentFromUrl();
    }
}