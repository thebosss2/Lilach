package org.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Controller{

    protected StoreSkeleton globalSkeleton;

    public void setSkeleton(StoreSkeleton skeleton){
        this.globalSkeleton = skeleton;
    } //set skeleton

    public StoreSkeleton getSkeleton() {
        return this.globalSkeleton;
    } //returns skeleton information
}
