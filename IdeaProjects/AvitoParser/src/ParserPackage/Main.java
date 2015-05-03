package ParserPackage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

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
            String rental = document.select("span[itemprop=price]").text();
            String ownerName = document.select("strong[itemprop=name").text();
            String address = document.select("span[itemprop=streetAddress]").text();
            String description = document.select("div[class=description description-expanded]").text() + "\n" +
                    document.select("div[class=description description-text]").text();

            Apartment newObject = new Apartment(s, title, ownerName, address, description, rental);
        }

        long endTime = System.nanoTime();
        long progressTime = endTime - startTime;
        System.out.println("Выполнение программы заняло " + progressTime/1000000000.0 + " секунд");
    }
}