package it.unical.mat.progettoweb2022.model;

public class Auction {
    private Integer id;
    private String endTime;
    private Integer currentPrice;
    private String winner;
    private Integer ad_id;
    private Integer numOfferte;

    public Integer getNumOfferte() {
        return numOfferte;
    }

    public void setNumOfferte(Integer numOfferte) {
        this.numOfferte = numOfferte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Integer currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Integer getAd_id() {
        return ad_id;
    }

    public void setAd_id(Integer ad_id) {
        this.ad_id = ad_id;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", endTime='" + endTime + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", winner='" + winner + '\'' +
                ", ad_id='" + ad_id + '\'' +
                '}';
    }
}
