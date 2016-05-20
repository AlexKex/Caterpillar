package utils;

import iface.Plugable;
import javafx.scene.layout.Pane;

/**
 * Created by apryakhin on 28.10.2015.
 */
public abstract class Module implements Plugable {
    protected boolean isLicensed = false;
    protected String myLicenseKey;

    public boolean checkLicense(){
        return true;
    }

    public boolean isReloadable() { return false; }

    public Pane getWidget() { return null; }

    public void setTimer() {}

    public Plugable run() {
        return null;
    }
}
