package CommonPackage;

import java.util.HashMap;

/**
 * Created by Elk on 5/6/2015.
 * Extending HashMap
 */
public class DataPack extends HashMap<String, Object>
{
    @Override
    public Object put(String key, Object value) {
        return super.put(key.toUpperCase(), value);
    }
}
