package com.example.burak.imdbviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Filmleri sqlite db üzerinde işlemlerinin halledilmesini sağlayan sınıf.
 * Created by Burak on 10.05.2016.
 */
public class Db extends SQLiteOpenHelper {

    SQLiteDatabase db;
    Context ctx;

    public Db(Context context, String name) {
        super(context, context.getDatabasePath(name).getAbsolutePath(), null, 3);
        ctx = context;
        db = getWritableDatabase();
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tabloQ = "create table IF NOT EXISTS filmler (" +
                "id integer primary key AUTOINCREMENT," +
                "adi text," +
                "yil text," +
                "tur text," +
                "yonetmen text," +
                "rating text," +
                "vote text," +
                "poster text," +
                "sure text," +
                "plot text)";

        db.execSQL(tabloQ);
    }


    /**
     * Notlar tablosundaki bütün kayıtları siler.
     */
    public void deleteAllMovies()
    {
        db = getWritableDatabase();
        String q = "delete from filmler";
        db.execSQL(q);
        db.close();
    }

    /**
     * Verilen kayıt id sine göre database den kaydı siler.
     * @param id
     */
    public void deleteMovie(int id)
    {
        db = getWritableDatabase();
        String q = "delete from filmler where id ="+id;
        db.execSQL(q);
        db.close();
    }

    /**
     * Verilen id ile kayıtlı notu günceller.
     * @param id
     * @param Not bilgileri(baslik,tarih,icerik)
     */
    public void updateMovie(int id, Movie m)
    {
        String adi = m.getAdi(),
                yil = m.getYil(),
                tur = m.getTur(),
                yonetmen = m.getYonetmen(),
                rating = m.getRating(),
                vote = m.getVote(),
                poster = m.getPoster(),
                sure = m.getSure(),
                plot = m.getPlot();

        db = getWritableDatabase();
        Toast.makeText(ctx, "UPDATE MOVIE :"+id+" "+adi+" "+yil+" "+yonetmen, Toast.LENGTH_SHORT).show();
        String q = "update filmler set adi = '%s'," +
                                        " yil = '%s'," +
                                        " tur = '%s' ," +
                                        " yonetmen = '%s' ," +
                                        " rating = '%s' ," +
                                        " vote = '%s' ," +
                                        " poster = '%s' ," +
                                        " sure = '%s'" +
                                        " plot = '%s'" +
                                        " where id="+id+";";

        db.execSQL(String.format(q,adi, yil, tur, yonetmen, rating, vote, poster, sure,plot));
        db.close();
    }

    /**
     * Notlar tablosuna yeni veri ekler.
     * @param v (baslik, tarih, icerik)
     */
    public void addMovie(Movie m)
    {
        if(m == null)
            return;

        String adi = m.getAdi(),
                yil = m.getYil(),
                tur = m.getTur(),
                yonetmen = m.getYonetmen(),
                rating = m.getRating(),
                vote = m.getVote(),
                poster = m.getPoster(),
                sure = m.getSure(),
                plot = m.getPlot();

        db = getWritableDatabase();
        String q = "insert into filmler(adi,yil,tur,yonetmen,rating,vote,poster,sure,plot) values('%s', '%s' ,'%s' ,'%s','%s','%s','%s','%s','%s' )";
        db.execSQL(String.format(q,adi, yil, tur.replaceAll(",","."), yonetmen, rating, vote.replaceAll(",","."), poster, sure,plot.replaceAll(","," ").replaceAll("'"," ")));
        db.close();
    }

    /**
     * Notlar tablosunda kayıtlı bütün verileri döndürür.
     * @return ArrayList<ContentValues(id,baslik,tarih,icerik)>
     */
    public ArrayList<Movie> getMovies()
    {

        ArrayList<Movie> movieList = new ArrayList<Movie>();
        String q = "select * from filmler";
        db = getWritableDatabase();
        Cursor c = db.rawQuery(q,null);

        while(c.moveToNext())
        {
            Movie m = new Movie(c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getString(9));

            movieList.add(m);
        }

        db.close();
        return movieList;
    }

    public Movie getMovie(String title)
    {
        db = getWritableDatabase();
        String q = "select * from filmler where adi='"+title+"' COLLATE NOCASE";
        Cursor c = db.rawQuery(q,null);

        if(c.getCount() == 0)
            return null;

        c.moveToNext();
        Movie m = new Movie(c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getString(9));
        db.close();
        return m;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
