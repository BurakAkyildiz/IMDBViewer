package com.example.burak.imdbviewer;

import java.io.Serializable;

/**
 * Filme ait bilgileri saglayan ve sağlayan sınıf.
 * Created by Burak on 11.05.2016.
 */
public class Movie implements Serializable{
    public static final long SerializableID = 2349829384091283L;

    private int id;
    private String adi, yil,  tur,  yonetmen,  rating,  vote,  poster,  sure, plot;


    public Movie( String adi, String yil, String tur, String yonetmen, String rating, String vote, String poster, String sure, String plot)
    {
        this.adi = adi;
        this.yil = yil;
        this.tur = tur;
        this.yonetmen = yonetmen;
        this.rating = rating;
        this.vote = vote;
        this.poster = poster;
        this.sure = sure;
        this.plot = plot;
    }


    public Movie(int id, String adi, String yil, String tur, String yonetmen, String rating, String vote, String poster, String sure, String plot)
    {
        this.id = id;
        this.adi = adi;
        this.yil = yil;
        this.tur = tur;
        this.yonetmen = yonetmen;
        this.rating = rating;
        this.vote = vote;
        this.poster = poster;
        this.sure = sure;
        this.plot = plot;
    }


    @Override
    public String toString() {
        return id+" "+adi+" "+yil+" "+tur+" "+yonetmen+" "+rating+" "+vote+" "+poster+" "+sure;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getYil() {
        return yil;
    }

    public void setYil(String yil) {
        this.yil = yil;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getYonetmen() {
        return yonetmen;
    }

    public void setYonetmen(String yonetmen) {
        this.yonetmen = yonetmen;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }
}
