package ParserPackage;

import DBPackage.DBDriver;
import DBPackage.JdbcOracleCon;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws IOException{
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
            System.out.println("Было выброшено исключение с текстом: " + e.getMessage());
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
        Document document = Jsoup.connect(startUrl).get();

        Elements elements = document.select("a[title^=Объявление «]");

        for (Element e : elements) {
            int rental;
            String url = e.attr("abs:href");

            document = Jsoup.connect(url).get();

            String title = document.select("h1").text();
            String rentalString = document.select("span[itemprop=price]").text();
            String ownerName = document.select("strong[itemprop=name").text();
            String address = document.select("span[itemprop=streetAddress]").text();
            String description = document.select("div[class=description description-expanded]").text() + "\n" +
                    document.select("div[class=description description-text]").text();

            try {
                rental = AnalyzeRentalString(rentalString);
            }
            catch (NumberFormatException exc) {
                System.out.println("Код ошибки: #4890100");
                System.out.println("Было выброшено исключение: " + exc.toString());

                rental = manualAnalyzeRentalString(rentalString);
            }

            Apartment newObject = new Apartment(url, title, ownerName, address, description, rental);
        }
    }

    private static int manualAnalyzeRentalString(String rentalString) {
        int rental;
        System.out.println("Программа не может правильно распознать сумму ежемесячного платежа в следующей строке:");
        System.out.println(rentalString);
        System.out.println("Пожалуйста, введите сумму ЕЖЕМЕСЯЧНОГО платежа из этой строки:");

        Scanner sc = new Scanner(System.in);
        rental = sc.nextInt();
        //System.out.println(rental);
        sc.close();

        return rental;
    }

    private static int AnalyzeRentalString(String rentalString) {

        if (rentalString.equalsIgnoreCase("Не указана"))
            return 15000;

        String[] strings = rentalString.split(" | ");

        int dateModifier = 1;
        if (rentalString.endsWith("квартал"))
            dateModifier = 3;

        return Integer.parseInt(strings[0].concat(strings[1]))/dateModifier;
    }
}