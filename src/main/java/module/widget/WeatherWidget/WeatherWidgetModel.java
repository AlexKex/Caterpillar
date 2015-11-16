package module.widget.WeatherWidget;

import module.service.WeatherService;

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
    }

    /**
     * collect all needed data
     * @throws IOException
     */
    public void prepareData() throws IOException {
        try {
            this.myServiceModule.requestWeather();
            //this.setData("weatherCity", this.myServiceModule);
        } catch (IOException e) {
            // TODO выводить человекопонятную инфу
            e.printStackTrace();
        }
    }

    /**
     * add smth do data container
     * @param key
     * @param value
     */
    public void setData(String key, Object value){
        this.data.put(key, value);
    }
}
