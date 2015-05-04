package ParserPackage;

class Apartment {
    private final String url, description, title, ownerName, address;
    private final int storey, rooms, rental;
    private int area;
    boolean fromOwner;

    public Apartment(String url, String title, String ownerName, String address, String description, int rental) {
        this.url = url;                                         //
        this.title = title;                                     //
        this.ownerName = ownerName;                             //Заполнение строковых
        this.address = address;                                 //              полей объекта
        this.description = description;                         //
        this.rental = rental;                                   //

        String[] splitedTitle = title.split(", ");              // Разбиваем заголовок на 3 части (в качестве разделителя - запятая)

        if (splitedTitle[0].compareTo("Студия") == 0)           // Если первое слово в заголовке - Студия,
            rooms = 0;                                          // то количество комнат указываем как  0
        else                                                    // Иначе - первый символ и даст количество комнат
            rooms = splitedTitle[0].charAt(0) - '0';            //

        try {
            area = new Integer(splitedTitle[1].substring(0, 2)); // Площадь квартиры выделяем как первые три символа второй части заголовка
        }                                                        // Скорее всего метод выбросит исключение, если площадь < 10 квадратов
        catch (NumberFormatException e)
        {
            System.out.println("Код ошибки: #4890102");
            System.out.println("Было выброшено исключение с текстом: " + e.getMessage());

            area = splitedTitle[1].charAt(0) - '0';             // Исключение должно порождаться, когда указанная площадь помещения
        }                                                       // меньше 10 квадратов

        storey = new Integer(splitedTitle[2].split("/")[0]);    // Вычленяем этаж из символов до разделителя
    }

    public boolean equals(Apartment obj) {
        return (this.address.equals(obj.address) && this.storey == obj.storey &&    // По договоренности uid - совокупность адреса,
                this.area == obj.area && this.rooms == obj.rooms);                  // этажа, метража и количества комнат
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAddress() {
        return address;
    }

    public int getRental() {
        return rental;
    }

}
