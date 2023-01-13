package it.unical.mat.progettoweb2022.model;


import java.util.Arrays;

public class Image {
    Integer id;
    byte[] data;
    Integer ad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Integer getAd() {
        return ad;
    }

    public void setAd(Integer ad) {
        this.ad = ad;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", data=" + Arrays.toString(data) +
                ", ad=" + ad +
                '}';
    }
}
