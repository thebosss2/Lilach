package org.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Controller{

    protected StoreSkeleton globalSkeleton;
    //protected Client client;
    public void setSkeleton(StoreSkeleton skeleton){
        this.globalSkeleton = skeleton;
    }

    public StoreSkeleton getSkeleton() {
        return this.globalSkeleton;
    }

    /*public void setClient(Client client) {
        this.client = client;
    }

    public void jbkfnb(){
        try {
            ArrayList<Object> msg = new ArrayList<Object>();
            msg.add("#PULLCATALOG");
            msg.add(this);
            client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
