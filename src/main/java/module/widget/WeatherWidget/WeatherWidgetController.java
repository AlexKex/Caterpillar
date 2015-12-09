package module.widget.WeatherWidget;



import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import module.iface.widgetInterface;
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
            String[] labels = this.getWeatherLabelText();

            this.time_label = new Label(labels[0]);
            this.widget_pane.add(this.time_label, 0, 0);

            this.weather_label = new Label(labels[1]);
            this.widget_pane.add(this.weather_label, 0, 1);

            this.icon = new ImageView(new Image(this.model.getData("icon").toString()));
            this.widget_pane.add(this.icon, 0, 3);
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
}
