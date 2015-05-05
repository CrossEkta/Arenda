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
            int rental;
            document = Jsoup.connect(s).get();

            String title = document.select("h1").text();
            String rentalString = document.select("span[itemprop=price]").text();
            String ownerName = document.select("strong[itemprop=name").text();
            String address = document.select("span[itemprop=streetAddress]").text();
            String description = document.select("div[class=description description-expanded]").text() + "\n" +
                    document.select("div[class=description description-text]").text();

            try {
                rental = AnalyzeRentalString(rentalString);
            }
            catch (NumberFormatException e) {
                System.out.println("Код ошибки: #4890100");
                System.out.println("Было выброшено исключение с текстом: " + e.getMessage());

                System.out.println("Программа не может правильно распознать сумму ежемесячного платежа в следующей строке:");
                System.out.println(rentalString);
                System.out.println("Пожалуйста, введите сумму ЕЖЕМЕСЯЧНОГО платежа из этой строки:");

                Scanner sc = new Scanner(System.in);
                rental = sc.nextInt();
            }

            Apartment newObject = new Apartment(s, title, ownerName, address, description, rental);
        }

        long endTime = System.nanoTime();
        long progressTime = endTime - startTime;
        System.out.println("Выполнение программы заняло " + progressTime/1000000000.0 + " секунд");
    }

    private static int AnalyzeRentalString(String rentalString) {
        String[] strings = rentalString.split(" | ");
        int dateModifier = 1;
        if (rentalString.endsWith("квартал"))
            dateModifier = 3;

        return Integer.parseInt(strings[0].concat(strings[1]))/dateModifier;
    }
}