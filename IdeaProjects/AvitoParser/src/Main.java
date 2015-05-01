package ParserPackage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Game-Class5 on 22.04.2015.
 */
public class Main {

    private static PrintWriter w;
    private static String url = "https://www.avito.ru/krasnoyarsk/kvartiry/sdam/na_dlitelnyy_srok?i=1&user=1";

    public static void main(String args[]) throws IOException{
        long startTime = System.nanoTime();
        Document document = Jsoup.connect(url).get();

        Elements cuttedLinks = document.select("a[title^=Объявление «]");
        HashSet<String> links = new HashSet<String>();
        Iterator<Element> iterator = cuttedLinks.iterator();
        while (iterator.hasNext()) {
            //Element element = iterator.next();
            links.add("https://avito.ru" + iterator.next().attr("href").toString());
        }

        Iterator<String> stringIterator = links.iterator();
        while (stringIterator.hasNext()) {
            String url = stringIterator.next();
            document = Jsoup.connect(url).get();

            String title = document.select("h1").text();
            String rental = document.select("span[itemprop=price]").text();
            String ownerName = document.select("strong[itemprop=name").text();
            String address = document.select("span[itemprop=streetAddress]").text();
            String description = document.select("div[class=description description-expanded]").text() + "\n" +
                    document.select("div[class=description description-text]").text();

            ParserPackage.Apartment newObject = new ParserPackage.Apartment(url, title, ownerName, address, description, rental);
        }

        long endTime = System.nanoTime();
        long progressTime = endTime - startTime;
        System.out.println("Выполнение программы заняло " + progressTime + " наносекунд");
    }
}