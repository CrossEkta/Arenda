package ParserPackage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String args[]) throws IOException{
        long startTime = System.nanoTime();

        String startUrl = "https://www.avito.ru/krasnoyarsk/kvartiry/sdam/na_dlitelnyy_srok?i=1&user=1";
        Document document = Jsoup.connect(startUrl).get();

        Elements cutedLinks = document.select("a[title^=Объявление «]");
        HashSet<String> links = new HashSet<String>();

        for (Element e : cutedLinks) {
            links.add("https://avito.ru" + e.attr("href"));
        }

        for (String s : links) {
            document = Jsoup.connect(s).get();

            String title = document.select("h1").text();
            String rentalString = document.select("span[itemprop=price]").text();
            String ownerName = document.select("strong[itemprop=name").text();
            String address = document.select("span[itemprop=streetAddress]").text();
            String description = document.select("div[class=description description-expanded]").text() + "\n" +
                    document.select("div[class=description description-text]").text();

            int rental = AnalyzeRentalString(rentalString);

            Apartment newObject = new Apartment(s, title, ownerName, address, description, rental);
        }

        long endTime = System.nanoTime();
        long progressTime = endTime - startTime;
        System.out.println("Выполнение программы заняло " + progressTime/1000000000.0 + " секунд");
    }

    private static int AnalyzeRentalString(String rentalString) {
        int result;
        Pattern smallMonthlyRentalPattern = Pattern.compile("^[1-9] [0-9]{3} руб. в месяц.*$");
        Pattern midMonthlyRentalPattern = Pattern.compile("^[1-9][0-9] [0-9]{3} руб. в месяц.*$");
        Pattern largeMonthlyRentalPattern = Pattern.compile("^[1-9][0-9]{2} [0-9]{3} руб. в месяц.*$");

        Pattern smallQuarterlyRentalPattern = Pattern.compile("^[1-9] [0-9]{3} руб. в квартал.*$");
        Pattern midQuarterlyRentalPattern = Pattern.compile("^[1-9][0-9] [0-9]{3} руб. в квартал.*$");
        Pattern largeQuarterlyRentalPattern = Pattern.compile("^[1-9][0-9]{2} [0-9]{3} руб. в квартал.*$");

        Matcher m = smallMonthlyRentalPattern.matcher(rentalString);
        if (m.matches()) {
            result = Integer.parseInt(rentalString.charAt(0) + rentalString.substring(2, 5));
            return result;
        }

        m = midMonthlyRentalPattern.matcher(rentalString);
        if (m.matches()) {
            result = Integer.parseInt(rentalString.substring(0,2) + rentalString.substring(3,6));
            return result;
        }

        m = largeMonthlyRentalPattern.matcher(rentalString);
        if (m.matches()) {
            result = Integer.parseInt(rentalString.substring(0,3) + rentalString.substring(4,7));
            return result;
        }

        m = smallQuarterlyRentalPattern.matcher(rentalString);
        if (m.matches()) {
            result = Integer.parseInt(rentalString.charAt(0) + rentalString.substring(2,5))/3;
            return result;
        }

        m = midQuarterlyRentalPattern.matcher(rentalString);
        if (m.matches()) {
            result = Integer.parseInt(rentalString.substring(0,2) + rentalString.substring(3,6))/3;
            return result;
        }

        m = largeQuarterlyRentalPattern.matcher(rentalString);
        if (m.matches()) {
            result = Integer.parseInt(rentalString.substring(0,3) + rentalString.substring(4,7))/3;
            return result;
        }
        else {
            Scanner s = new Scanner(System.in);
            System.out.println("Программа не может правильно считать арендную плату, пожалуйста введите размер арендной платы за 1 месяц из следующей строки:");
            System.out.println(rentalString);
            result = s.nextInt();
        }

        return result;
    }
}