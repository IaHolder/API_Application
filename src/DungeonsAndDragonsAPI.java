import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class DungeonsAndDragonsAPI {

    public static void getInformation(String _spellName){
        //becuse of the open API a call does not require a key
        String baseURL = "https://www.dnd5eapi.co/api/";
        String searchCatagory = "spells";
        String searchIndex = _spellName;
        String urlString = baseURL + searchCatagory + "/" + searchIndex + "/";
        URL url;
        try{
            url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status != 200) {
                System.out.printf("Error: Could not find spell: " + status);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                System.out.println("Output: " + content.toString());
                //converts the receved string to a JSON
                JSONObject obj = new JSONObject(content.toString());
                String description = obj.getString("name");
                System.out.println(description);
            }
        }catch (Exception ex) {
            System.out.println("Error: " + ex);
            return;
        }
    }
}
