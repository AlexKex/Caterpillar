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

/**
 * Created by apryakhin on 28.10.2015.
 */
public class WeatherWidgetController extends Widget implements widgetInterface {
    protected Scene scene;
    protected WeatherWidgetModel model;

    protected FXMLLoader loader;

    public WeatherWidgetController() throws IOException {
        String searchCity = "Moscow"; // TODO change to user's selection
        this.model = new WeatherWidgetModel();

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
        Pane panel = this.loader.load();

        try{
            ImageView weather_icon = new ImageView(new Image(this.model.getData("icon").toString()));
            panel.getChildren().add(weather_icon);

            Double celsius = (Double) this.model.getData("temperature_celsius");
            Label weather_celsius = new Label(Integer.toString(celsius.intValue()) + " C");
            panel.getChildren().add(weather_celsius);

            Double fahrenheit = (Double) this.model.getData("temperature_fahrenheit");
            Label weather_fahrenheit = new Label(Integer.toString(fahrenheit.intValue()) + " F");
            panel.getChildren().add(weather_fahrenheit);
        }
        catch(NoSuchFieldException e){
            System.out.println(e.getMessage());
        }


        return panel;
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
     * prepare widget to main application screen
     */
    protected void prepareWidget(){
        System.out.println("Preparing widget");

    }
}
