package module.widget;

/**
 * Created by apryakhin on 05.11.2015.
 */

import javafx.application.Platform;
import module.Module;
import module.iface.widgetInterface;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apryakhin on 29.10.2015.
 */
public abstract class Widget extends Module implements widgetInterface {
    protected int standard_height   = 240;
    protected int standard_width    = 240;

    protected boolean is_reloadable = false;
    protected int reload_interval   = 0;
    protected Timer widget_timer;
    protected TimerTask widget_timer_task;

    @Override
    public void render() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void expand() {

    }

    public void setData(){

    }

    protected void prepareData() throws IOException {

    }

    @Override
    public void setTimer() {
        this.widget_timer = new Timer();
        this.widget_timer_task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            renewWidget();
                        } catch (IOException e) {
                            System.out.println("IOException in Platform runner " + e.getClass() + " : " + e.getMessage());
                        }
                    }
                });
            }
        };

        this.widget_timer.scheduleAtFixedRate(this.widget_timer_task, this.reload_interval * 1000, this.reload_interval * 1000);
    }

    @Override
    public Timer getTimer() {
        return this.widget_timer;
    }

    @Override
    public boolean isReloadable() {
        return this.is_reloadable;
    }

    @Override
    public int getReloadInterval() {
        return this.reload_interval;
    }

    @Override
    public void setReloadInterval(int interval) {
        this.reload_interval = interval;
    }

    public void renewWidget() throws IOException {}
}

