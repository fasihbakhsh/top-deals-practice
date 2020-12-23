package com.codinghomies.topdeals.Model;

public class Items {

    private String item_id, title, currentPrice, url, thumbnail;    //instance variables

    public Items(){
    }

    public Items(String item_id, String title, String currentPrice, String url, String thumbnail)
    {
        this.item_id = item_id;
        this.title = title;
        this.currentPrice = currentPrice;
        this.url = url;
        this.thumbnail = thumbnail;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
