package caterpillar;

import iface.Plugable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.Module;
import utils.plugin.PluginFactory;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Caterpillar extends Application {
    private Stage primaryStage;
    private GridPane rootLayout;
    private ArrayList<String> components = new ArrayList<>();

    public static void main(String[] args) {
        try {
            launch(args);
        }
        catch (Exception e){
            System.out.println("Main load exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Caterpillar");

        this.initRootLayout();
        this.prepareComponents();
        this.renderComponents();

        Scene scene = new Scene(this.rootLayout, 800, 480);
        this.primaryStage.setScene(scene);
        this.primaryStage.getScene().getStylesheets().add("/css/main.css");
        this.primaryStage.show();
    }

    private void initRootLayout(){
        try{
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scheme.fxml"));
            this.rootLayout = loader.load();
            this.rootLayout.setAlignment(Pos.CENTER);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void prepareComponents() {
        // TODO собирать модули с сервера на основании ключа
        try {
            //this.components.put("WeatherWidget", new WeatherWidgetController());
            this.components.add("WeatherWidget");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderComponents() {
        try {
                    ArrayList<Plugable> plugins = PluginFactory.getPlugins();

                    for (Plugable p : plugins) {
                        Plugable widget = p.run();

                        if (widget.isReloadable()) {
                            widget.setTimer();
                        }

                        Pane widget_pane = widget.getWidget();

                        this.rootLayout.add(widget_pane, 0, 0);
                    }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

}
