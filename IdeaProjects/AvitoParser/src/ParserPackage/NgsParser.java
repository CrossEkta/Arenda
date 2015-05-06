package ParserPackage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Game-Class5 on 06.05.2015.
 */
public class NgsParser implements IApartmentParser {
    private static String urlToParse = Global.getStartUrl(3);               //Global.getStartUrl(Integer type) with argument 3
                                                                            //refers to ngs24.ru start url
    public NgsParser() {

    }

    public ArrayList<Apartment> parseApartmentFromUrl(String url) throws IOException {
        urlToParse = url;
        return parseApartment();
    }

    public ArrayList<Apartment> parseApartmentFromUrl() throws IOException{
        return parseApartment();
    }

    private ArrayList<Apartment> parseApartment() throws IOException {
        ArrayList<Apartment> list = new ArrayList<Apartment>();

        Document document = Jsoup.connect(urlToParse).get();
        int k = 0;
        Elements elements = document.select("td[class=module-search__price-column]");
        for (Element e : elements) {
            //Element e = elements.first();
            Boolean fromOwner = false;
            int rental;
            //String url = e.attr("abs:href");
            String htmlText = e.html();
            String url = htmlText.substring(9, 47);//.indexOf("http://");

            System.out.println(url);

            document = Jsoup.connect(url).get();

            Elements trye = document.select("[class^=organization-informer]");
            if (trye.isEmpty()) {
                fromOwner = true;
            }

            String title = document.select("h2[id=card_header]").text();
            String address = document.select("p[class=card__address]").text();
            String reference_point = document.select("p[class=card__reference-point").text();
            String rentalString = document.select("p[class=card__cost").text();
            rental = AnalyzeRentalString(rentalString);

            Elements areaElements = document.select("dd[class=sms-card-list__value");
            double fullArea = -1,
                    livingArea = -1,
                    kitchenArea = -1;
            if (!areaElements.isEmpty()) {
                String[] text = areaElements.text().split(" м2 | м2");
                switch (text.length) {
                    case 1:
                    case 2:
                        fullArea = Double.valueOf(text[0]);
                        break;
                    case 3:
                        fullArea = Double.valueOf(text[0]);
                        livingArea = Double.valueOf(text[1]);
                        kitchenArea = Double.valueOf(text[2]);
                        break;
                    default:
                        fullArea = Double.valueOf(text[0]);
                        livingArea = Double.valueOf(text[1]);
                        kitchenArea = Double.valueOf(text[2]);
                        System.out.println("Размер массива строк с данными по площадям аренды больше трёх по этой ссылке:");
                        System.out.println(url);
                        break;
                }
            }

            String description = document.select("div[class=card__comments-section").select("p").text();
            String phoneString = document.select("li[class=card__phones-list-item").text();
            String ownerName = document.select("a[class^=re-link card__author-name").text();

            Elements details = document.select("div.card__details");
            Elements keys = details.select("dt.key-value__key");
            Elements values = details.select("dd.key-value__value");

            int offset = 0;
            if (keys.size() > values.size())
                offset = 1;

            Integer size = keys.size(), storey = -1, numberOfStoreys = -1;
            String key, value, material = "", type = "", layout = "", WCtype = "";
            Boolean balcony = false, phone = false, furniture = false, fridge = false, ethernet = false;

            for (int i = 0 ; i < size - 1  ; i++)
            {
                key = keys.get(i).text();
                if (i < 1) {
                    value = values.get(0).text();
                }
                else {
                    value = values.get(i-offset).text();
                }

                try {

                    if (key.equalsIgnoreCase("Этаж"))
                        storey = Integer.valueOf(value);
                    if (key.equalsIgnoreCase("Этажность"))
                        numberOfStoreys = Integer.valueOf(value);
                    if (key.equalsIgnoreCase("Материал дома"))
                        material = value;
                    if (key.equalsIgnoreCase("Тип квартиры"))
                        type = value;
                    if (key.equalsIgnoreCase("Планировка"))
                        layout = value;
                    if (key.equalsIgnoreCase("Санузел"))
                        WCtype = value;
                    if (key.equalsIgnoreCase("Балкон"))
                        balcony = true;
                    if (key.equalsIgnoreCase("Телефон"))
                        if (value.equalsIgnoreCase("Есть"))
                            phone = true;
                    if (key.equalsIgnoreCase("Мебель"))
                        if (value.equalsIgnoreCase("Есть"))
                            furniture = true;
                    if (key.equalsIgnoreCase("Холодильник"))
                        if (value.equalsIgnoreCase("Есть"))
                            fridge = true;
                    if (key.equalsIgnoreCase("Интернет"))
                        if (value.equalsIgnoreCase("Да"))
                            ethernet = true;
                }
                catch (Exception exc) {
                    System.out.println(exc.toString());
                    //System.out.println(url + String.valueOf(storey) + String.valueOf(numberOfStoreys));
                    System.out.println(keys.text());
                    System.out.println(values.text());
                }

            }

            Apartment newObject = new Apartment(url, title, ownerName, address, description, reference_point,
                                                material, type, layout, WCtype, phoneString, rental, storey,
                                                numberOfStoreys, -1, fullArea, livingArea, kitchenArea,
                                                fromOwner, phone, balcony, furniture, fridge, ethernet);

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

        //if (rentalString.equalsIgnoreCase("Не указана"))
        //    return 15000;

        String[] strings = rentalString.split(" | ");

        //int dateModifier = 1;
        //if (rentalString.endsWith("квартал"))
            //dateModifier = 3;

        return Integer.parseInt(strings[0].concat(strings[1]));//dateModifier;
    }


}
