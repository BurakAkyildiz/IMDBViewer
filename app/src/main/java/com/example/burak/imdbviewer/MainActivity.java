package com.example.burak.imdbviewer;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Db db = null;
    public static Movie currentMovie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Db(this,"MovieCash");//db oluşturulur.
    }


    // Film aranması
    public void onClick_Ara(View v)
    {
        String title = ((EditText)findViewById(R.id.edt_movie_title)).getText().toString(); //Kullanıcının girdiği arama cümlesi
        new BackgroundSearch(this,db).execute(title); // Arama işleminin başlatılması

    }
}

/**
 * Asenktron bir şekilde film bilgilerini önce db den yoksa omdb apiden alan sınıf.
 */
class BackgroundSearch extends AsyncTask<String,Void,Movie>
{

    MainActivity root = null;
    Context ctx = null;
    Db db = null;
    public BackgroundSearch(MainActivity root, Db db)
    {
        this.root = root;
        this.ctx = root.getBaseContext();
        this.db = db;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(ctx, "Searching Movie...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Movie doInBackground(String... args) {// Arka planda film bilgilerinin alınası
        Movie m = null;
        String title = args[0];

        m = db.getMovie(title);// Eğer film database de mevcutsa döndürülür.
        if(m != null)
            return m;

        m = Omdb.getMovie(title); // eğer db de bulunamadıysa omdb apiden alınır.

        db.addMovie(m);// alınan film database e eklenir.
        Log.e("MOVIE INFO : : : : : ",(m == null) ? "Null":m.toString());
        return m;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);

        root.currentMovie = movie;
        if(movie != null) // eğer film bilgilerine ulaşıldıysa ekranda gösterilir.
        {
            Toast.makeText(ctx, "Movie Found !", Toast.LENGTH_SHORT).show();
            ((TextView)root.findViewById(R.id.txt_Adi)).setText(movie.getAdi());
            ((TextView)root.findViewById(R.id.txt_Rat)).setText(movie.getRating());
            ((TextView)root.findViewById(R.id.txt_Sure)).setText(movie.getSure());
            ((TextView)root.findViewById(R.id.txt_Tur)).setText(movie.getTur());
            ((TextView)root.findViewById(R.id.txt_Vot)).setText(movie.getVote());
            ((TextView)root.findViewById(R.id.txt_Yil)).setText(movie.getYil());
            ((TextView)root.findViewById(R.id.txt_Yonetmen)).setText(movie.getYonetmen());
            ((TextView)root.findViewById(R.id.txt_Plot)).setText(movie.getPlot());

            ImageView iv =(ImageView)root.findViewById(R.id.img_poster);

            Picasso.with(root)
                    .load(movie.getPoster())
                    .placeholder(R.drawable.loading_poster)
                    .error(R.drawable.no_poster)
                    .into(iv);
        }else
        {
            Toast.makeText(ctx, "Moive not found...", Toast.LENGTH_SHORT).show();
        }



    }
}