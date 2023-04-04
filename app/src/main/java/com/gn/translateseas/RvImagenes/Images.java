package com.gn.translateseas.RvImagenes;

public class Images {
    private String url;
    private String place;

    public Images(String url, String place) {
        this.url = url;
        this.place = place;
    }

    public String getUrl() {
        return "https://www.biosur365.com/Signslate/data/" + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
