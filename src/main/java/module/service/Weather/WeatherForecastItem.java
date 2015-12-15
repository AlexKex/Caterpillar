package module.service.Weather;

/**
 * Created by apryakhin on 09.12.2015.
 */
public class WeatherForecastItem {
    protected String item_time;
    protected int item_timestamp;
    protected String item_label;
    protected double temperature_celsius;
    protected double temperature_fahrenheit;
    protected String icon_ref;

    public String getItemTime(){
        return this.item_time;
    }

    public String getItemLabel(){
        return this.item_label;
    }

    public double getTemperatureCelsius(){
        return this.temperature_celsius;
    }

    public double getTemperatureFahrenheit(){
        return this.temperature_fahrenheit;
    }

    public String getIconRef(){
        return this.icon_ref;
    }
}
