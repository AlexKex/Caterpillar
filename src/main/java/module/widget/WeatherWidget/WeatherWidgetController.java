package module.widget.WeatherWidget;



import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
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
    protected WeatherService myServiceModule;
    protected HashMap<String, Object> data;
    protected Scene scene;
    protected WeatherWidgetModel model;

    protected FXMLLoader loader;

    public WeatherWidgetController() throws IOException {
        String searchCity = "Moscow"; // TODO change to user's selection
        this.myServiceModule = new WeatherService(searchCity);
        this.model = new WeatherWidgetModel();

        this.createDesktopModule();
        this.data = new HashMap<>();
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

        Label label = new Label("Weather widget");
        panel.getChildren().add(label);

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
