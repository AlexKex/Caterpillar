package caterpillar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import module.widget.WeatherWidget.WeatherWidgetController;
import module.widget.Widget;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Caterpillar extends Application {
    private Stage primaryStage;
    private GridPane rootLayout;
    private Map<String, Widget> components;

    public static void main(String[] args) {
        try {
            launch(args);
        }
        catch (Exception e){
            System.out.println("Main load exception : " + e.getMessage());
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
            this.components = new HashMap<>();
            this.components.put("WeatherWidget", new WeatherWidgetController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderComponents() {
        try {
            if(this.components.size() > 0){
                Iterator<Map.Entry<String, Widget>> iterator = this.components.entrySet().iterator();

                while(iterator.hasNext()){
                    Map.Entry<String, Widget> pair = iterator.next();

                    Widget widget = pair.getValue();
                    if(widget.isReloadable()){
                        widget.setTimer();
                    }

                    Pane widget_pane = pair.getValue().getWidget();

                    // Set person overview into the center of root layout.
                    this.rootLayout.add(widget_pane, 0, 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
