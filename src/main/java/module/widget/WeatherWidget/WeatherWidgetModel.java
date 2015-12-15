package module.widget.WeatherWidget;

import module.service.Weather.WeatherService;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by apryakhin on 05.11.2015.
 */
public class WeatherWidgetModel {
    protected WeatherService myServiceModule;
    protected HashMap<String, Object> data;

    public WeatherWidgetModel(){
        // TODO собирать данные от пользователя
        this.myServiceModule = new WeatherService("Moscow");
        this.data = new HashMap<>();
    }

    /**
     * collect all needed data
     * @throws IOException
     */
    public void prepareData() throws IOException {
        try {
            this.myServiceModule.requestWeather("weather");

            this.data.put("city", this.myServiceModule.getWeatherCity());
            this.data.put("lat", this.myServiceModule.getLatitude());
            this.data.put("lon", this.myServiceModule.getLongitude());
            this.data.put("temperature_celsius", this.myServiceModule.getTemperatureCelsius());
            this.data.put("temperature_fahrenheit", this.myServiceModule.getTemperatureFahrenheit());
            this.data.put("icon", this.myServiceModule.getIconRef());
            this.data.put("pressure", this.myServiceModule.getPressure());
            this.data.put("humidity", this.myServiceModule.getHumidity());
            this.data.put("date", this.myServiceModule.requestDate);

            this.myServiceModule.requestWeather("forecast");

            //this.myServiceModule.getForecast();
        } catch (Exception e) {
            System.out.println("Undefined exception " + e.getMessage());
        }
    }

    public void refreshData() throws IOException {
        try {
            this.prepareData();
        }
        catch(IOException e){
            System.out.println("IOException " + e.getMessage());
        }
        catch(Exception e){
            System.out.println("Exception " + e.getMessage());
        }
    }

    public Object getData(String key) throws NoSuchFieldException {
        Object value;

        if(this.data.containsKey(key)){
            value = this.data.get(key);
        }
        else{
            throw new NoSuchFieldException("No such key in data array");
        }

        return value;
    }

    public WeatherService getMyServiceModule(){
        return this.myServiceModule;
    }
}
