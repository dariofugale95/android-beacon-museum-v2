package com.castagnolofugale.smartmuseum;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BeaconResultsActivity extends AppCompatActivity {

    // Ip address - server nodejs
    private static String url = "http://192.168.1.149:4000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_results);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getIntent().getStringExtra("BeaconName"));
        }

        final View progressBar2 = findViewById(R.id.progressBar2);
        String beaconHash = getIntent().getStringExtra("BeaconHash");
        new AsyncHttpClient().get(url+beaconHash, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray responseBody) {
                progressBar2.setVisibility(View.GONE);
                showResults(responseBody);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressBar2.setVisibility(View.GONE);
                Toast.makeText(BeaconResultsActivity.this, "Error to load info", Toast.LENGTH_LONG).show();
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    /** Recupera le informazioni dal server **/
    private void showResults(JSONArray results)
    {
        Toast.makeText(BeaconResultsActivity.this, results.length() + " results", Toast.LENGTH_LONG).show();
        if(results.length() <= 0) return;

        LinearLayout dynamicContent = findViewById(R.id.dynamic_artwork);
        dynamicContent.removeAllViews();

        for (int i = 0; i < results.length(); i++)
        {
            try
            {
               final JSONObject artwork = results.getJSONObject(i);
                View newArtworkView = getLayoutInflater().inflate(R.layout.item_artwork, dynamicContent, false);

                //associazione onclick alla view del singolo item
                 newArtworkView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                          try {
                              Intent artworkDetailsIntent=new Intent(BeaconResultsActivity.this,ArtworkDetailsActivity.class);
                              artworkDetailsIntent.putExtra("ArtworkUrl", artwork.getString("URL"));
                              artworkDetailsIntent.putExtra("Image", artwork.getString("ThumbnailURL"));
                              startActivity(artworkDetailsIntent);
                         } catch (JSONException e) {
                             e.printStackTrace();
                             System.out.println("problema onclick");
                         }
                     }
                 });

                ((TextView)newArtworkView.findViewById(R.id.title)).setText(artwork.getString("Title"));

                JSONArray artist = artwork.getJSONArray("Artist");
                String a = "";
                for (int j = 0; j < artist.length(); j++) a += artist.getString(j) + ((j == artist.length()-1) ? "" : ", ");
                ((TextView)newArtworkView.findViewById(R.id.artist_artwork)).setText(a);

                if(URLUtil.isValidUrl(artwork.getString("ThumbnailURL")))
                    Picasso.get().load(artwork.getString("ThumbnailURL")).fit()
                        .centerCrop().error(R.mipmap.no_image).into((ImageView) newArtworkView.findViewById(R.id.PreView));

                ((TextView)newArtworkView.findViewById(R.id.data_artwork)).setText(artwork.getString("Date"));

                ((TextView)newArtworkView.findViewById(R.id.id_artwork)).setText(artwork.getString("ObjectID"));

                dynamicContent.addView(newArtworkView);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
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
