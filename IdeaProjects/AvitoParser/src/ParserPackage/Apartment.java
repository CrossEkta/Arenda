package ParserPackage;

class Apartment {
    private final String url, description, title, ownerName, address, referencePoint, material, type, layout, WCtype,
                         phoneNumber;
    private final Integer storey, rooms, rental, numberOfStoreys;
    private Integer fullArea, livingArea, kitchenArea;
    boolean fromOwner, phone, balcony, furniture, fridge, ethernet;

    public Apartment(String url, String title, String ownerName, String address, String description,
                     String material, Integer rental, Integer storey, Integer numberOfStoreys, Integer rooms,
                     Double fullArea)
    {
        this(url, title, ownerName, address, description, "", material, "", "", "",
             "", rental, storey, numberOfStoreys, rooms, fullArea, -1.0, -1.0, false,
             false, false, false, false, false);
    }

    public Apartment(String url, String title, String ownerName, String address, String description,
                     String referencePoint, String material, String type, String layout, String WCtype,
                     String phoneNumber, Integer rental, Integer storey, Integer numberOfStoreys, Integer rooms,
                     Double fullArea, Double livingArea, Double kitchenArea, Boolean fromOwner, Boolean phone,
                     Boolean balcony, Boolean furniture, Boolean fridge, Boolean ethernet) {


        this.url = url;                                         //
        this.description = description;
        this.title = title;                                     //
        this.ownerName = ownerName;                             //Заполнение строковых
        this.address = address;                                 //              полей объекта
        this.referencePoint = referencePoint;
        this.material = material;
        this.type = type;
        this.layout = layout;
        this.WCtype = WCtype;
        this.phoneNumber = phoneNumber;
        this.storey = storey;
        this.numberOfStoreys = numberOfStoreys;
        this.rooms = rooms;
        this.rental = rental;
        this.fromOwner = fromOwner;
        this.phone = phone;
        this.balcony = balcony;
        this.furniture = furniture;
        this.fridge = fridge;
        this.ethernet = ethernet;
    }

    public boolean equals(Apartment obj) {
        return (this.address.equals(obj.address) && this.storey == obj.storey &&    // По договоренности uid - совокупность адреса,
                this.fullArea == obj.fullArea && this.rooms == obj.rooms);                  // этажа, метража и количества комнат
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
