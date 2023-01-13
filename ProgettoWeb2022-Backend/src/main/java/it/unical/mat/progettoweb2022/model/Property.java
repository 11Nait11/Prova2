package it.unical.mat.progettoweb2022.model;

public class Property {

    public Property(){
    }
    Integer id;
    String type;
    Double mq;
    String latitude;
    String longitude;
    String user;

    Integer ad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMq() {
        return mq;
    }

    public void setMq(Double mq) {
        this.mq = mq;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getAd() {
        return ad;
    }

    public void setAd(Integer ad) {
        this.ad = ad;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", mq=" + mq +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", user='" + user + '\'' +
                ", ad=" + ad +
                '}';
    }
}
