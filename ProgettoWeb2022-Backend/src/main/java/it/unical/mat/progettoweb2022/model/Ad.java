package it.unical.mat.progettoweb2022.model;

public class Ad {


    Integer id;
    String title;
    String description;
    User user;
    Integer property;
    Double price;
    Double mq;
    String status;
    String city;
    String auction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getProperty() {
        return property;
    }

    public void setProperty(Integer property) {
        this.property = property;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMq() {
        return mq;
    }

    public void setMq(Double mq) {
        this.mq = mq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAuction() {
        return auction;
    }

    public void setAuction(String auction) {
        this.auction = auction;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", property=" + property +
                ", price=" + price +
                ", mq=" + mq +
                ", status='" + status + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
