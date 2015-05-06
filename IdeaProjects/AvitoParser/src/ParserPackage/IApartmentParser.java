package ParserPackage;

import java.io.IOException;
import java.util.ArrayList;

interface IApartmentParser {
    public ArrayList<Apartment> parseApartmentFromUrl(String url) throws IOException;
}