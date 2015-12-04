package module.service;

import module.Module;
import module.iface.serviceModuleInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apryakhin on 05.11.2015.
 */
public class WeatherService extends Module implements serviceModuleInterface {
    protected String base = "http://api.openweathermap.org/data/2.5/weather?q=";
    protected String key  = "appid=997b6111d6996259c286d92520b9d6ab";
    protected String icons_base = "http://openweathermap.org/img/w/";

    protected String request_type = "weather";

    protected String urlRequest;
    protected String jsonResponse;

    protected String searchCity;
    protected String weatherCity;
    protected double longitude;
    protected double latitude;
    protected double temperature_celsius;
    protected double temperature_fahrenheit;
    protected double pressure;
    protected int humidity;
    protected String icon_ref;

    public String requestDate;

    public WeatherService(String searchCity){
        this.searchCity = searchCity;
    }

    public void requestWeather() throws IOException {
        switch (this.request_type){
            case "weather":
                this.urlRequest = this.base + this.searchCity + "&" + this.key;
                break;
            case "forecast":
                // TODO implement forecast logic
                break;
            default:
                this.urlRequest = this.base + this.searchCity + "&" + this.key;
                break;
        }

        this.getResponse();
        this.proceedResponse();
    }

    public String   getWeatherCity()            { return this.weatherCity; }
    public double   getLongitude()              { return this.longitude; }
    public double   getLatitude()               { return this.latitude; }
    public double   getTemperatureCelsius()     { return this.temperature_celsius; }
    public double   getTemperatureFahrenheit()  { return this.temperature_fahrenheit; }
    public double   getPressure()               { return this.pressure; }
    public int      getHumidity()               { return this.humidity; }
    public String   getIconRef()                { return this.icon_ref; }


    protected void getResponse() throws IOException {
        ExchangeService myExchangeObject = new ExchangeService();
        myExchangeObject.setTarget(this.urlRequest);
        myExchangeObject.createRequest();
        this.jsonResponse = myExchangeObject.getContent();
        System.out.println("I'm looking for weather!");
    }

    protected void proceedResponse(){
        JSONObject obj = new JSONObject(this.jsonResponse);
        int response = obj.getInt("cod");
        if(response == 200){
            switch (this.request_type){
                case "weather":
                    this.proceedWeatherResponse(obj);
                    break;
                case "forecast":
                    this.proceedWeatherForecastResponse(obj);
                    break;
                default:
                    this.proceedWeatherResponse(obj);
                    break;
            }
        }
        else{
            System.out.println("Service is temporary unavailable");
        }
    }

    private void proceedWeatherResponse(JSONObject response){
        try {
            this.weatherCity = response.getString("name");
            this.longitude = response.getJSONObject("coord").getDouble("lon");
            this.latitude = response.getJSONObject("coord").getDouble("lat");

            double kelvin_temperature = response.getJSONObject("main").getDouble("temp");
            this.temperature_celsius = kelvin_temperature - 273;
            this.temperature_fahrenheit = 1.8 * (kelvin_temperature - 273) + 32;
            double pressure_pascals = response.getJSONObject("main").getDouble("pressure");
            this.pressure = pressure_pascals * 0.75;
            this.humidity = response.getJSONObject("main").getInt("humidity");

            JSONArray weather_array = response.getJSONArray("weather");
            JSONObject weather_object = (JSONObject) weather_array.get(0);

            this.icon_ref = this.icons_base + weather_object.getString("icon") + ".png";

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy HH:mm");
            this.requestDate = dateFormat.format( new Date() );

            System.out.println("Date : " + this.requestDate + " | temp is " + this.temperature_celsius);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void proceedWeatherForecastResponse(JSONObject response){}
}
