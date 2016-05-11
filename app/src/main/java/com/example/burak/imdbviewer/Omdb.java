package com.example.burak.imdbviewer;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Omdb api kullanarak film bilgilerini Movie sınıfı yardımı ile aktaran sınıf.
 * Created by Burak on 11.05.2016.
 */
public class Omdb {

    private final static String omdbUrl = "http://www.omdbapi.com/";
    public static Movie getMovie(String title)
    {
        Movie m = null;

        try {

            Document doc =  Jsoup.connect(omdbUrl)
                            .userAgent("Mozilla")
                            .followRedirects(true)
                            .ignoreContentType(true)
                            .timeout(30000)
                            .data("t",title)
                            .data("y","")
                            .data("plot","full")
                            .data("r","xml")
                            .get();
            Log.e("Omdb : ",doc.select("root").html());
            if(doc.select("root").attr("response").equalsIgnoreCase("false"))
                return null;

            Element elem = doc.select("movie").get(0);

            String adi = elem.attr("title").toString(),
                    yil = elem.attr("year").toString(),
                    tur = elem.attr("genre").toString(),
                    yonetmen = elem.attr("director").toString(),
                    rating = elem.attr("imdbRating").toString(),
                    vote = elem.attr("imdbVotes").toString(),
                    poster = elem.attr("poster").toString(),
                    sure = elem.attr("runtime").toString(),
                    plot = elem.attr("plot").toString();

            m = new Movie(adi,yil,tur,yonetmen,rating,vote,poster,sure,plot);

        }catch (Exception e){
            e.printStackTrace();
        }


        return m;
    }
}
