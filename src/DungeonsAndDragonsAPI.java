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

                //parsing the JSON to pull and print information
                String name = obj.getString("name");
                JSONArray descriptionArray = obj.getJSONArray("desc");
                JSONArray atHigherLevels = obj.getJSONArray("higher_level");
                String range = obj.getString("range");
                JSONArray componentArray = obj.getJSONArray("components");
                String componentString = "";
                String neededMaterials = obj.getString("material");
                String spellLevel = obj.getString("level");
                JSONObject damage = obj.getJSONObject("damage");
                String damageType = damage.getJSONObject("damage_type").getString("name");

                //turn the component array to a string
                for(int i = 0; i < componentArray.length(); i++){
                    componentString += componentArray.getString(i);
                }

                System.out.println(name);
                System.out.println(descriptionArray.getString(0));
                System.out.println("At higher levels: " + atHigherLevels.getString(0));
                System.out.println("Has a range of " + range);
                System.out.println("Components " + componentString);
                System.out.println("The spell needs " + neededMaterials);
                System.out.println("The base spell level is " + spellLevel);
                System.out.println("Damage type " + damageType);
            }
        }catch (Exception ex) {
            System.out.println("Error: " + ex);
            return;
        }
    }
}
