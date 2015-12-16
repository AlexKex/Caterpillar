package module;

import module.iface.moduleInterface;

/**
 * Created by apryakhin on 28.10.2015.
 */
public abstract class Module implements moduleInterface {
    protected boolean isLicensed = false;
    protected String myLicenseKey;

    public boolean checkLicense(){
        return true;
    }


}
