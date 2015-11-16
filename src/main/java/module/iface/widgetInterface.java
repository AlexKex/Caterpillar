package module.iface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import module.widget.WeatherWidget.WeatherWidgetController;
import module.widget.Widget;

import java.io.IOException;

/**
 * Created by apryakhin on 28.10.2015.
 */
public interface widgetInterface {
    void render();
    void destroy();
    void expand();
    void setData();

    void createDesktopModule() throws IOException;
    Pane getWidget() throws IOException;
    Scene getScene() throws IOException;
    FXMLLoader getLoader() throws IOException;
}