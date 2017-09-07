import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

//Generate MD5 signature using hexadecimal format.
public class QFPayUtils {
    public static String getMd5Value(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes( "UTF-8" ));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append( String.format( "%02x", array[i]));
            }
            return sb.toString().toUpperCase();
        } catch ( NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
    }

    //	Generate Pre-signed string for MD5 signature。
    public static <T> String getDataString(Map resultMap) {
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        return obj1.compareTo(obj2);
                    }
                });

        Iterator<Map.Entry<String, String>> it = resultMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            map.put(entry.getKey(), entry.getValue());
        }

        StringBuilder sb = new StringBuilder();
        it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }


}






