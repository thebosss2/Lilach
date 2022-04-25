package org.client;

public abstract class Controller {

    protected StoreSkeleton globalSkeleton;
    protected Client client;
    public void setSkeleton(StoreSkeleton skeleton){
        this.globalSkeleton = skeleton;
    }

    public StoreSkeleton getSkeleton() {
        return this.globalSkeleton;
    }
}
