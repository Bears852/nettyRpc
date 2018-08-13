package com.recklessMo.registry.config;

public enum RegistryType {
    clientNode(0), serverNode(1);

    private int status;

    RegistryType(int index){
        this.status = index;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
