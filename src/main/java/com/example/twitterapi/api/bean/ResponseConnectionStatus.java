package com.example.twitterapi.api.bean;

public class ResponseConnectionStatus {

    public ResponseConnectionStatus() {
    }

    private Boolean backendConnection;
    // TODO: implementar conexion BD
//    private Boolean dataBaseConnection;
    private String backendVersion;

    public String getBackendVersion() {
        return backendVersion;
    }

    public void setBackendVersion(String backendVersion) {
        this.backendVersion = backendVersion;
    }

    public Boolean getBackendConnection() {
        return backendConnection;
    }

    public void setBackendConnection(Boolean status) {
        this.backendConnection = status;
    }
}
