package module.service.Weather;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apryakhin on 09.12.2015.
 */
public class WeatherDay extends WeatherForecastItem {
    public WeatherDay(JSONObject weather_object){
        try{
            /*JSONObject weather = (JSONObject) weather_object.getJSONArray("weather").get(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Long timestamp = weather_object.getLong("dt") * 1000;

            this.item_label = dateFormat.format( new Date(timestamp) );
            this.temperature_celsius = weather_object.getJSONObject("main").getInt("temp") - 273;
            this.temperature_fahrenheit = 1.8 * (weather_object.getJSONObject("main").getInt("temp") - 273) + 32;
            this.icon_ref = WeatherService.getIconURL(
                    WeatherService.getIconsBase(),
                    weather.getString("icon"),
                    "png"
            );*/
        }
        catch(Exception e){
            System.out.println("Exception during the creation of WeatherHour object " + e.getMessage());
        }
    }

    public void addToForecastMap(int key, JSONObject forecast_object){

    }
}
