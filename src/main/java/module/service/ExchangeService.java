package module.service;

import module.Module;
import module.iface.serviceModuleInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by apryakhin on 05.11.2015.
 */
public class ExchangeService extends Module implements serviceModuleInterface {
    protected String target;
    protected URL url;
    protected HttpURLConnection url_connection;

    public void setTarget(String target){
        this.target = target;
    }

    public void createRequest() {
        try{
            this.url = new URL(this.target);
        }
        catch (Exception e){
            System.out.println("Can't create URL object with given string " + this.target);
        }
    }

    public String getContent() throws IOException {
        String answer = null;
        String line;

        url_connection = (HttpURLConnection) url.openConnection();
        url_connection.setRequestMethod("GET");
        url_connection.connect();

        java.io.InputStream in = url_connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        while ((line = reader.readLine()) != null)
        {
            if(line!=null){
                answer = line;
            }
            else{
                answer += line;
            }

        }

        return answer;
    }

    public URL getExchangeObject() {
        return this.url;
    }
}

