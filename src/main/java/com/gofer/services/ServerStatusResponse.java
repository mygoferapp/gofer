package com.gofer.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by rjaraja on 8/17/16.
 */
@XmlRootElement(name = "helloresponse", namespace = "com.isc.servlet")
@XmlAccessorType(XmlAccessType.NONE)
public class ServerStatusResponse implements Serializable{

    @XmlElement(name = "uberUrl")
    private String uberUrl;

    @XmlElement(name = "uberRedirectUrl")
    private String uberRedirectUrl;

    @XmlElement(name = "status")
    private String status;



    @Override
    public String toString() {
        return "ServerStatusResponse{" +
                "uber='" + uberUrl + '\'' +
                ", uberRedirectUrl='" + uberRedirectUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerStatusResponse that = (ServerStatusResponse) o;

        if (uberUrl != null ? !uberUrl.equals(that.uberUrl) : that.uberUrl != null) return false;
        if (uberRedirectUrl != null ? !uberRedirectUrl.equals(that.uberRedirectUrl) : that.uberRedirectUrl != null)
            return false;
        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override
    public int hashCode() {
        int result = uberUrl != null ? uberUrl.hashCode() : 0;
        result = 31 * result + (uberRedirectUrl != null ? uberRedirectUrl.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public String getUberUrl() {
        return uberUrl;
    }

    public void setUberUrl(String uberUrl) {
        this.uberUrl = uberUrl;
    }

    public String getUberRedirectUrl() {
        return uberRedirectUrl;
    }

    public void setUberRedirectUrl(String uberRedirectUrl) {
        this.uberRedirectUrl = uberRedirectUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
