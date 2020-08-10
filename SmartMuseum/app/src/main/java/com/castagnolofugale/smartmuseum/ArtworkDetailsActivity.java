package com.castagnolofugale.smartmuseum;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ArtworkDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);
        String url = getIntent().getStringExtra("ArtworkUrl");
        //Bundle extras = getIntent().getExtras();
        if (url != null) {

            System.out.println(url);
            getMedia(url);
        }
    }


    private void getMedia(String url) {
        new AsyncHttpClient().get(url, new TextHttpResponseHandler() {
            //ArrayList<String> uriYouTube = new ArrayList();
            //ArrayList<String> threeDModels = new ArrayList();
            ArrayList<String> uri = new ArrayList();
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(statusCode + " BAD");

            }




            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                String toFind = "<iframe";
                int index = 0;

                while (index >= 0) {
                    index = responseString.indexOf(toFind, index + 1);
                    if (index > 0) {
                        index = responseString.indexOf("src=", index);
                        index += 5;
                        String myuri = responseString.substring(index, responseString.indexOf(">", index));
                        int lastIndex = myuri.indexOf('\"') > 0 ? myuri.indexOf('\"') : myuri.indexOf('\'');
                        myuri = myuri.substring(0, lastIndex);
                        //in uri conservo gli uri di tutti i video
                        //uri.add(myuri);
                        System.out.println("Found uri " + myuri);
                        if (myuri.contains("youtube")) {
                            GetMedia(myuri);
                           // uriYouTube.add(myuri);
                        }
                        if (myuri.contains("sketchfab")) {
                            System.out.println("3d");
                        };
                    }

                }

            }
        });
    }

    private void Play3DModel(ArrayList<String> threeDModels) {
        for (String a : threeDModels) {
            System.out.println("3DModel uri found:" + a);
        }
    }



    private void GetMedia(final String urlMedia) {
        final LinearLayout dynamicContent = findViewById(R.id.videoList);
        dynamicContent.removeAllViews();
        new AsyncHttpClient().get(urlMedia, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(statusCode + " BAD");
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                String toFind = "<title>";
                int index_start = 0;
                int index_stop = 0;
                index_start = responseString.indexOf(toFind, 0);
                index_stop = responseString.indexOf("</title>", index_start + 1);
                String title = responseString.substring(index_start + 7, index_stop);
                if (Build.VERSION.SDK_INT >= 24) {
                    title = String.valueOf(Html.fromHtml(title.toString(), Html.FROM_HTML_MODE_LEGACY));

                } else {
                    title = String.valueOf(Html.fromHtml(title.toString()));
                }
                View newListView = getLayoutInflater().inflate(R.layout.card_video, dynamicContent, false);
                TextView infoVideoView = (TextView) newListView.findViewById(R.id.title_video);
                infoVideoView.setText(title);

                newListView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(urlMedia);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(urlMedia));
                        startActivity(browserIntent);
                    }
                });
                dynamicContent.addView(newListView);
            }
        });
    }


}