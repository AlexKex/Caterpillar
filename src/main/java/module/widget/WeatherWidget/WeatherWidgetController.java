package module.widget.WeatherWidget;



import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import module.iface.widgetInterface;
import module.service.WeatherService;
import module.widget.Widget;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apryakhin on 28.10.2015.
 */
public class WeatherWidgetController extends Widget implements widgetInterface {
    protected Scene scene;
    protected WeatherWidgetModel model;

    protected FXMLLoader loader;
    protected Pane widget_pane;

    // info lines
    protected Label weather_label;
    protected ImageView icon;

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
        this.widget_pane.getStylesheets().add("/css/WeatherWidgetStyle.css");

        try{
            this.icon = new ImageView(new Image(this.model.getData("icon").toString()));
            this.widget_pane.getChildren().add(this.icon);

            this.weather_label = new Label(this.getWeatherLabelText());
            this.widget_pane.getChildren().add(this.weather_label);
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

    public void renewWidget() throws IOException {
        try {
            this.model.refreshData();
            this.weather_label.setText(this.getWeatherLabelText());
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

    private String getWeatherLabelText() throws NoSuchFieldException {
        String weather_label_text = null;

        try {
            Double celsius = (Double) this.model.getData("temperature_celsius");
            Double fahrenheit = (Double) this.model.getData("temperature_fahrenheit");
            weather_label_text = Integer.toString(celsius.intValue()) + " C | " + Integer.toString(fahrenheit.intValue()) + " F" + " | " + this.model.getData("date");
        }
        catch (NoSuchFieldException e){
            System.out.println("NoSuchFieldException at " + e.getClass() + " : " + e.getMessage());
        }

        return weather_label_text;
    }
}
