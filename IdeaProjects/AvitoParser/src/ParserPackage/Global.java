package ParserPackage;

/**
 * Created by Elk on 05.05.2015.
 */
public class Global
{
    final static public String host = "25.80.40.139";
    final static public String port = "1521";
    final static public String sid = "XE";
    final static public String username = "AR";
    final static public String password = "fhtylf1";

    final static private String avitoStartUrl = "https://www.avito.ru/krasnoyarsk/kvartiry/sdam/na_dlitelnyy_srok?i=1&user=1";
    final static private String _24auStartUrl = " ";
    final static private String ngsStartUrl = "http://arenda.gilcom.ru/snyat/srok-dlitelniy/?on_page=25&by=_orderDate&order=DESC";

    static public String getStartUrl(Integer type) {
        switch (type) {
            case 1:
                return avitoStartUrl;
            case 2:
                return _24auStartUrl;
            case 3:
                return ngsStartUrl;
            default:
                return null;
        }

    }

}
