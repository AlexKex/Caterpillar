package module.widget.WeatherWidget;



import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import module.iface.widgetInterface;
import module.service.Weather.WeatherHour;
import module.widget.Widget;

import java.io.IOException;

/**
 * Created by apryakhin on 28.10.2015.
 */
public class WeatherWidgetController extends Widget implements widgetInterface {
    protected Scene scene;
    protected WeatherWidgetModel model;

    protected FXMLLoader loader;
    protected GridPane widget_pane;
    protected GridPane hourly_forecast_pane;
    protected GridPane daily_forecast_pane;

    // info lines
    protected Label weather_label;
    protected ImageView icon;
    protected Label time_label;

    public WeatherWidgetController() throws IOException {
        String searchCity = "Moscow"; // TODO change to user's selection
        this.model = new WeatherWidgetModel();

        this.is_reloadable = true;
        this.reload_interval = 600;

        this.createDesktopModule();
    }

    public void render() {

    }

    public void destroy() {

    }

    public void expand() {

    }

    @Override
    public void createDesktopModule() throws IOException {
        this.model.prepareData();

        this.loader = new FXMLLoader();
        this.loader.setLocation(getClass().getResource("/fxml/WeatherWidgetView.fxml"));
    }

    @Override
    public Pane getWidget() throws IOException {
        this.widget_pane = this.loader.load();
        this.widget_pane.getStyleClass().addAll("grid");
        this.widget_pane.getStylesheets().add("/css/WeatherWidgetStyle.css");

        try{
            this.assembleMainInformationBlock();
            this.assembleHourlyForecastBlock();
            this.assembleDailyForecastBlock();
        }
        catch(NoSuchFieldException e){
            System.out.println(e.getMessage());
        }

        return this.widget_pane;
    }

    public Scene getScene() throws IOException {
        this.prepareWidget();

        return this.scene;
    }

    public FXMLLoader getLoader() throws IOException {
        this.prepareWidget();

        return this.loader;
    }

    /**
     * renew widget
     * @throws IOException
     */
    public void renewWidget() throws IOException {
        try {
            this.model.refreshData();

            String[] labels = this.getWeatherLabelText();

            this.time_label.setText(labels[0]);
            this.weather_label.setText(labels[1]);
        }
        catch (Exception e){
            System.out.println("Exception in " + e.getClass() + " : " + e.getMessage());
        }
    }

    public WeatherWidgetModel getModel(){
        return this.model;
    }

    /**
     * prepare widget to main application screen
     */
    protected void prepareWidget(){
        System.out.println("Preparing widget");
    }

    private String[] getWeatherLabelText() throws NoSuchFieldException {
        String[] weather_labels = new String[3];

        try {
            weather_labels[0] = "Last update on " + this.model.getData("date");

            Double celsius = (Double) this.model.getData("temperature_celsius");
            Double fahrenheit = (Double) this.model.getData("temperature_fahrenheit");
            weather_labels[1] = Integer.toString(celsius.intValue()) + " C | " + Integer.toString(fahrenheit.intValue()) + " F";
        }
        catch (NoSuchFieldException e){
            System.out.println("NoSuchFieldException at " + e.getClass() + " : " + e.getMessage());
        }

        return weather_labels;
    }

    /**
     * Collect info about current weather to widget
     * @throws NoSuchFieldException
     */
    private void assembleMainInformationBlock() throws NoSuchFieldException{
        String[] labels = this.getWeatherLabelText();

        this.time_label = new Label(labels[0]);
        this.widget_pane.add(this.time_label, 0, 0);

        this.weather_label = new Label(labels[1]);
        this.widget_pane.add(this.weather_label, 0, 1);

        this.icon = new ImageView(new Image(this.model.getData("icon").toString()));
        this.widget_pane.add(this.icon, 0, 3);
    }

    /**
     * Collect info about hourly forecast to widget
     */
    private void assembleHourlyForecastBlock() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/WeatherHourlyForecastView.fxml"));

        this.hourly_forecast_pane = loader.load();

        WeatherHour[] forecast = this.model.getMyServiceModule().getHourlyForecast();

        for(int i = 0; i < forecast.length; i++){
            FXMLLoader item_loader = new FXMLLoader();
            item_loader.setLocation(getClass().getResource("/fxml/WeatherForecastItemView.fxml"));
            GridPane item_pane = item_loader.load();

            Label time_label = new Label(forecast[i].getItemLabel());
            item_pane.add(time_label, 0, 0);

            Double celsius = forecast[i].getTemperatureCelsius();
            Double fahrenheit = forecast[i].getTemperatureFahrenheit();
            Label weather_label = new Label(
                    celsius.intValue() + " C | " + fahrenheit.intValue() + " F"
            );
            item_pane.add(weather_label, 0, 1);

            ImageView icon = new ImageView(new Image(forecast[i].getIconRef()));
            item_pane.add(icon, 0, 3);

            this.hourly_forecast_pane.add(item_pane, i, 0);
        }

        this.widget_pane.add(this.hourly_forecast_pane, 0, 4);
    }

    /**
     * Collect info about daily forecast to widget
     */
    private void assembleDailyForecastBlock(){

    }
}
