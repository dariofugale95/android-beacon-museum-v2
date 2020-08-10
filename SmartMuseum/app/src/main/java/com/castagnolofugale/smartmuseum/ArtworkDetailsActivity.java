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
import java.util.List;

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
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(statusCode + " BAD");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                String toFind = "<iframe";
                int index = 0;
                ArrayList<String> uriYouTube = new ArrayList();
                ArrayList<String> threeDModels = new ArrayList();
                ArrayList<String> uri = new ArrayList();
                while (index >= 0) {
                    index = responseString.indexOf(toFind, index + 1);
                    if (index > 0) {
                        index = responseString.indexOf("src=", index);
                        index += 5;
                        String myuri = responseString.substring(index, responseString.indexOf(">", index));
                        int lastIndex = myuri.indexOf('\"') > 0 ? myuri.indexOf('\"') : myuri.indexOf('\'');
                        myuri = myuri.substring(0, lastIndex);
                        //in uri conservo gli uri di tutti i video
                        uri.add(myuri);
                        System.out.println("Found uri " + myuri);
                        if (myuri.contains("youtube")) uriYouTube.add(myuri);
                        else if (myuri.contains("sketchfab")) threeDModels.add(myuri);
                    }

                }
                if (!uriYouTube.isEmpty()) {
                    PlayVideo(uriYouTube);
                }

                if (!threeDModels.isEmpty()) {
                    Play3DModel(threeDModels);
                }

            }
        });
    }

    private void Play3DModel(ArrayList<String> threeDModels) {
        for (String a : threeDModels) {
            System.out.println("3DModel uri found:" + a);
        }
    }

    private void PlayVideo(List<String> videoUrls) {
        LinearLayout dynamicContent = findViewById(R.id.videoList);
        dynamicContent.removeAllViews();

        for (final String video : videoUrls) {

            View newListView = getLayoutInflater().inflate(R.layout.card_video, dynamicContent, false);
            TextView infoVideoView = (TextView) newListView.findViewById(R.id.title_video);
            //get info from youtube url
            String title = GetMediaInfo(video);
            System.out.println("ciaooooooo" + title);
            if(title!=null){
                System.out.println(title);
            infoVideoView.setText(title.toString());}
            else{
                infoVideoView.setText("Video");
            }
            // MediaController mediaController= new MediaController(this);
            //mediaController.setAnchorView(videoView);
            //videoView.setMediaController(mediaController);
            newListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(video);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(video));
                    startActivity(browserIntent);
                }
            });
                    /*
            videoView.setVideoURI(Uri.parse(a));
            //videoView.requestFocus();
            //videoView.start(); */
            dynamicContent.addView(newListView);

        }

    }

    private String GetMediaInfo(String urlMedia) {

        final String[] info = new String[1];
        if (urlMedia != null) {


            new AsyncHttpClient().get(urlMedia, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    System.out.println(statusCode + " BAD");
                }


                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    String toFind = "<title>";
                    int index_start=0;
                    int index_stop=0;
                    index_start = responseString.indexOf(toFind, 0);
                    index_stop=responseString.indexOf("</title>", index_start + 1);
                    String title=responseString.substring(index_start+7,index_stop);
                    if (Build.VERSION.SDK_INT >= 24) {
                        title= String.valueOf(Html.fromHtml(title.toString(), Html.FROM_HTML_MODE_LEGACY));

                    } else {
                        title= String.valueOf(Html.fromHtml(title.toString()));
                    }
                    info[0] =title;
                    System.out.println(info[0]);


                }
            });
            return info[0];
        }
        return info[0];

    }


}
