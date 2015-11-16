package module.widget;

/**
 * Created by apryakhin on 05.11.2015.
 */

import module.Module;
import module.iface.widgetInterface;

import java.io.IOException;

/**
 * Created by apryakhin on 29.10.2015.
 */
public abstract class Widget extends Module implements widgetInterface {
    protected Module myServiceModule;
    protected Object Data;

    protected int standard_height   = 240;
    protected int standard_width    = 240;

    @Override
    public void render() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void expand() {

    }

    public Object getData(){
        return Data;
    }

    public void setData(){

    }

    protected void prepareData() throws IOException {

    }
}

