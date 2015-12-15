package module.service.Weather;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by apryakhin on 09.12.2015.
 */
public class WeatherForecastItem {
    protected String item_time;
    protected String item_label;
    protected double temperature_celsius;
    protected double temperature_fahrenheit;
    protected String icon_ref;

    public static GridPane getForecastPane(FXMLLoader item_loader, WeatherForecastItem forecast_data) throws IOException {
        GridPane item_pane = item_loader.load();

        Label time_label = new Label(forecast_data.getItemLabel());
        item_pane.add(time_label, 0, 0);

        Double celsius = forecast_data.getTemperatureCelsius();
        Double fahrenheit = forecast_data.getTemperatureFahrenheit();
        Label weather_label = new Label(
                celsius.intValue() + " C | " + fahrenheit.intValue() + " F"
        );
        item_pane.add(weather_label, 0, 1);

        ImageView icon = new ImageView(new Image(forecast_data.getIconRef()));
        item_pane.add(icon, 0, 3);

        return item_pane;
    }

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
