package service;

import module.service.Weather.WeatherService;
import module.widget.WeatherWidget.WeatherWidgetController;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by apryakhin on 03.12.2015.
 */
public class weatherWidgetControllerTest {
    private WeatherWidgetController controller;
    private WeatherService service;

    @Before
    public void prepareTestData() throws IOException {
        this.controller = new WeatherWidgetController();
        this.controller.setReloadInterval(5);
    }

    @Test
    public void testRenewWidget() {

    }
}
