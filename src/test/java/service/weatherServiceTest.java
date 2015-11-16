package service;

import module.service.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;


/**
 * Created by apryakhin on 13.11.2015.
 */

/**
 * @Test
 */
public class weatherServiceTest {
    protected WeatherService service;

    @Before
    public void prepareTestData() throws IOException {
        this.service = new WeatherService("Moscow");
        this.service.requestWeather();
    }

    @Test
    public void testNotEmptyWeatherResponse() throws IOException {
        // check if response was not empty
        Assert.assertNotEquals(null, this.service.getWeatherCity());
    }


    @Test
    public void testRequestWeather() throws IOException {
        // check if response was correct
        Assert.assertEquals("Moscow", this.service.getWeatherCity());
    }
}
