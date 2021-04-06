package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp;

    @FXML
    private Text feels_like;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text pressure;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=54ebdd56b6b8cb83e2686c21df7fa164");
                System.out.println(output);

                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    DecimalFormat df = new DecimalFormat("##.00");
                    temp.setText("Temperature:  " + df.format(obj.getJSONObject("main").getDouble("temp") - 273.15));
                    feels_like.setText("Feels like: " + df.format(obj.getJSONObject("main").getDouble("feels_like") - 273.15));
                    temp_max.setText("Maximum:  " + df.format(obj.getJSONObject("main").getDouble("temp_max") - 273.15));
                    temp_min.setText("Minimum:  " + df.format(obj.getJSONObject("main").getDouble("temp_min") - 273.15));
                    pressure.setText("Pressure: " + obj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });
    }

    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();

        try{
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while((line = br.readLine()) != null){
                content.append(line + "\n");
            }
            br.close();
        }catch (Exception e){
            System.out.println("The city is not found!");
        }
        return content.toString();
    }

}
