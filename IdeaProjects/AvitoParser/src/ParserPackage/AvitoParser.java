package ParserPackage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Game-Class5 on 05.05.2015.
 */
public class AvitoParser implements IApartmentParser {
    private static String urlToParse = Global.getStartUrl(1);               //Global.getStartUrl(Integer type) with argument 1
                                                                            //refers to avito.ru start url
    public AvitoParser() {

    }

    public ArrayList<Apartment> parseApartmentFromUrl(String url) throws IOException {
        urlToParse = url;
        return parseApartment();
    }

    public ArrayList<Apartment> parseApartmentFromUrl() throws IOException{
        return parseApartment();
    }

    private ArrayList<Apartment> parseApartment() throws IOException{
        ArrayList<Apartment> list = new ArrayList<Apartment>();

        Document document = Jsoup.connect(urlToParse).get();

        Elements elements = document.select("a[title^=Объявление «]");
        int k = 0;
        for (Element e : elements) {
            k++;
            Integer rental, rooms, storey, numberOfStoreys;
            Double fullArea;
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

            String type = document.select("div[class=description description-expanded]").select("a[title^=Тип дома]").attr("title").split("— ")[1];


            String[] splitedTitle = title.split(", ");              // Разбиваем заголовок на 3 части (в качестве разделителя - запятая)

            if (splitedTitle[0].compareTo("Студия") == 0)           // Если первое слово в заголовке - Студия,
                rooms = 0;                                          // то количество комнат указываем как  0
            else                                                    // Иначе - первый символ и даст количество комнат
                rooms = splitedTitle[0].charAt(0) - '0';            //

            try {
                fullArea = Double.valueOf(splitedTitle[1].substring(0, 2)); // Площадь квартиры выделяем как первые три символа второй части заголовка
            }                                                        // Скорее всего метод выбросит исключение, если площадь < 10 квадратов
            catch (NumberFormatException exc)
            {
                System.out.println("Код ошибки: #4890102");
                System.out.println("Было выброшено исключение с текстом: " + exc.getMessage());

                fullArea = Double.valueOf(splitedTitle[1].substring(0, 1));             // Исключение должно порождаться, когда указанная площадь помещения
            }                                                       // меньше 10 квадратов

            String[] storeyInfo = splitedTitle[2].split("/| ");
            storey = new Integer(storeyInfo[0]);                        // Вычленяем этаж из символов до разделителя
            numberOfStoreys = new Integer(storeyInfo[1]);

            Apartment newObject = new Apartment(url, title, ownerName, address, description, "", type, "", "",
                                                "", "", rental, storey, numberOfStoreys, rooms, (Double)fullArea, -1.0, -1.0, false,
                                                false, false, false, false, false);

            list.add(newObject);

        }
        return list;
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
