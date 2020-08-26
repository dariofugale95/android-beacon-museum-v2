package com.castagnolofugale.smartmuseum;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import cz.msebera.android.httpclient.Header;

public class ArtworkDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("ArtworkUrl");
        String urlImage = getIntent().getStringExtra("Image");
        setContentView(R.layout.activity_artowork_details_result);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Details");
    }

        if (url != null) {
            System.out.println(url);
            getMedia(url,urlImage);
        }
    }

    private void getMedia(String url, final String urlImage) {

        final View progressBar2 = findViewById(R.id.progressLoading);
        new AsyncHttpClient().get(url, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println(statusCode + " BAD");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                setContentView(R.layout.activity_artwork_details);
                final LinearLayout dynamicContent = findViewById(R.id.videoList);
                progressBar2.setVisibility(View.GONE);
                dynamicContent.removeAllViews();

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
                        System.out.println("Found uri " + myuri);

                        View newListView = getLayoutInflater().inflate(R.layout.card_video, dynamicContent, false);
                        TextView infoVideoView = (TextView) newListView.findViewById(R.id.title);
                        ImageView infoImage=(ImageView) newListView.findViewById(R.id.PreView);

                        if (myuri.contains("youtube")) {
                            Picasso.get().load(R.drawable.icon_movie).fit()
                                    .centerInside().error(R.mipmap.no_image).into(infoImage);
                            PlayVideo(myuri, dynamicContent, newListView, infoVideoView);
                        }

                        if (myuri.contains("sketchfab")) {
                            Picasso.get().load(R.drawable.icon_3d).fit()
                                                               .centerInside().error(R.mipmap.no_image).into(infoImage);
                            System.out.println("3d");
                            Play3DModel(myuri, dynamicContent, newListView, infoVideoView);
                        }
                    }
                }

                String audioToFind = "data-audio-url";
                int indexAudio = 0;
                int indexTitle = 0;
                while (indexAudio >= 0) {
                    indexAudio = responseString.indexOf(audioToFind, indexAudio + 1);
                    System.out.println("Index " + indexAudio);

                    if (indexAudio > 0) {
                        indexAudio += 16;
                        int lastIndexAudio = responseString.indexOf('\"', indexAudio) > 0 ? responseString.indexOf('\"', indexAudio) : responseString.indexOf('\'', indexAudio);
                        String myAudioUri = responseString.substring(indexAudio, lastIndexAudio);
                        indexTitle = responseString.indexOf("data-audio-title=", lastIndexAudio + 1);
                        indexTitle = indexTitle + 18;
                        int lastIndexTitle = responseString.indexOf('\"', indexTitle) > 0 ? responseString.indexOf('\"', indexTitle) : responseString.indexOf('\'', indexTitle);
                        System.out.println("Found uri Audio: " + myAudioUri);
                        String myTitle = responseString.substring(indexTitle, lastIndexTitle);
                        System.out.println("Found uri TITLE " + myTitle);
                        View newListView = getLayoutInflater().inflate(R.layout.card_video, dynamicContent, false);
                        TextView infoVideoView = (TextView) newListView.findViewById(R.id.title);
                        ImageView imageInfo=(ImageView) newListView.findViewById(R.id.PreView);

                        if (myAudioUri.contains(".mp3")) {
                            System.out.println("MP3");
                            Picasso.get().load(R.drawable.icon).fit()
                                    .centerInside().error(R.mipmap.no_image).into(imageInfo);

                            myAudioUri = "https://www.moma.org" + myAudioUri;
                            PlayAudio(myAudioUri, dynamicContent, newListView, infoVideoView, myTitle,urlImage);
                        }
                    }
                }
            }
        });
    }

    private void Play3DModel(final String threeDModel, LinearLayout dynamicContent, View newListView, TextView infoVideoView) {
        System.out.println("Dentro play3d");
        infoVideoView.setText("3D Model");

        newListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(threeDModel);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(threeDModel));
                startActivity(browserIntent);
            }
        });
        dynamicContent.addView(newListView);
    }

    private void PlayAudio(final String audioUrl, LinearLayout dynamicContent, final View newListView, TextView infoVideoView, String title, final String urlImage) {
        System.out.println("Dentro play audio");

        if (title != null) {

            if (Build.VERSION.SDK_INT >= 24) {
                title = String.valueOf(Html.fromHtml(title.toString(), Html.FROM_HTML_MODE_LEGACY));

            } else {
                title = String.valueOf(Html.fromHtml(title.toString()));
            }
            title = title.replaceAll("\\<[^>]*>", "");
            infoVideoView.setText(title);

        } else {
            infoVideoView.setText("Play Audio");
        }

        System.out.println(title);

        final String audioTitle = title;
        newListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passiamo l'url all'activity che si occuperà di riprodurre l'audio
                System.out.println(audioUrl);
                Intent audioIntent=new Intent(ArtworkDetailsActivity.this,AudioActivity.class);
                audioIntent.putExtra("AudioUrl", audioUrl);
                audioIntent.putExtra("AudioTitle", audioTitle);
                if(urlImage!=null){
                audioIntent.putExtra("Image",urlImage);
                System.out.println("Img ok");}

                startActivity(audioIntent);
            }


        });

        dynamicContent.addView(newListView);
    }

    private void PlayVideo(final String urlMedia, final LinearLayout dynamicContent, final View newListView, final TextView infoVideoView) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}