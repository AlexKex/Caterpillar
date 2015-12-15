package module.service.Weather;

import module.Module;
import module.iface.serviceModuleInterface;
import module.service.ExchangeService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by apryakhin on 05.11.2015.
 */
public class WeatherService extends Module implements serviceModuleInterface {
    protected static HashMap<String, Integer> day_terms;

    protected String base = "http://api.openweathermap.org/data/2.5/";
    protected String get_parameters;
    protected String key  = "appid=997b6111d6996259c286d92520b9d6ab";
    protected static String icons_base = "http://openweathermap.org/img/w/";

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

    protected WeatherHour[] hourlyForecast; // массив с прогнозом по времени дня (день, вечер, утро, ночь)
    protected WeatherDay[] dailyForecast; // массив с прогнозом на 5 дней вперёд

    public String requestDate;

    public void init(){
        WeatherService.day_terms = new HashMap<String, Integer>();
        WeatherService.day_terms.put("day", 12);
        WeatherService.day_terms.put("evening", 18);
        WeatherService.day_terms.put("night", 0);
        WeatherService.day_terms.put("morning", 6);
    }

    public WeatherService(String searchCity){
        this.init();

        this.searchCity = searchCity;

        this.hourlyForecast = new WeatherHour[4];
        this.dailyForecast = new WeatherDay[5];
    }

    public void requestWeather(String request_type) {
        try {
            this.request_type = request_type;

            switch (this.request_type) {
                case "weather":
                    this.get_parameters = "?q=" + this.searchCity + "&" + this.key;
                    break;
                case "forecast":
                    this.get_parameters = "?q=" + this.searchCity + "&" + this.key;
                    break;
                default:
                    throw new Exception("Unknown request type");
            }

            this.buildURL();
            this.getResponse();
            this.proceedResponse();
        }
        catch (Exception e){
            System.out.println("Undefined exception while getting response " + e.getMessage());
        }
    }

    public String   getWeatherCity()            { return this.weatherCity; }
    public double   getLongitude()              { return this.longitude; }
    public double   getLatitude()               { return this.latitude; }
    public double   getTemperatureCelsius()     { return this.temperature_celsius; }
    public double   getTemperatureFahrenheit()  { return this.temperature_fahrenheit; }
    public double   getPressure()               { return this.pressure; }
    public int      getHumidity()               { return this.humidity; }
    public String   getIconRef()                { return this.icon_ref; }
    public static String   getIconsBase()       { return WeatherService.icons_base; }


    protected void getResponse() {
        try{
            ExchangeService myExchangeObject = new ExchangeService();
            myExchangeObject.setTarget(this.urlRequest);
            System.out.println("I'm looking for " + this.request_type + "!");
            myExchangeObject.createRequest();
            this.jsonResponse = myExchangeObject.getContent();
        }
        catch (IOException e){
            System.out.println("IOException while getting the Response " + e.getMessage());
        }
        catch (Exception e){
            System.out.println("Undefined exception while getting the Response " + e.getMessage());
        }
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

    public void buildURL(){
        this.urlRequest = this.base + this.request_type + this.get_parameters;

        System.out.println(this.urlRequest);
    }

    public static String getIconURL(String base, String icon_ref, String extension){
        return base + icon_ref + "." + extension;
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

            this.icon_ref = WeatherService.getIconURL(WeatherService.icons_base, weather_object.getString("icon"), "png");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yy HH:mm");
            this.requestDate = dateFormat.format( new Date() );
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void proceedWeatherForecastResponse(JSONObject response){
        try{
            JSONArray data_list = response.getJSONArray("list");

            Date date = new Date();
            long timestamp = date.getTime() / 1000;
            int day     = 0; // counter for daily forecast
            int hour    = 0; // counter for hourly forecast

            for(int i = 0; i < data_list.length(); i++){
                JSONObject weather_object = (JSONObject) data_list.get(i);

                int forecast_time = weather_object.getInt("dt");
                if(hour < this.hourlyForecast.length){
                    this.hourlyForecast[hour] = new WeatherHour(weather_object);
                    hour++;
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void proceedWeatherWeeklyForecastResponse(JSONObject response){}

    public WeatherHour[] getHourlyForecast() {
        return hourlyForecast;
    }

    public WeatherDay[] getDailyForecast() {
        return dailyForecast;
    }
}
