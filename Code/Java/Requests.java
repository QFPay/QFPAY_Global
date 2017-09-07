import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Requests{
    public static String sendPostRequest(String inputURL,String data,String appcode,String key) {
        String resp="";
        try {

            // Send the request
            URL url = new URL(inputURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("X-QF-APPCODE", appcode);
            conn.setRequestProperty("X-QF-SIGN",QFPayUtils.getMd5Value(data+key));


            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            //write parameters
            writer.write(data);
            writer.flush();

            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();

            resp=answer.toString();



        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resp;



    }

}
