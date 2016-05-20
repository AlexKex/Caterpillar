package iface;

import javafx.scene.layout.Pane;
import utils.Module;

/**
 * Created by apryakhin on 19.05.2016.
 */
public interface Plugable {
    public Plugable run();
    public boolean isReloadable();
    public void setTimer();
    public Pane getWidget();
}

