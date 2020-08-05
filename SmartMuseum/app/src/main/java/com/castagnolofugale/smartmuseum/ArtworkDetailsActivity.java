package com.castagnolofugale.smartmuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.castagnolofugale.smartmuseum.models.Artwork;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class ArtworkDetailsActivity extends AppCompatActivity {
/*
    private static String connString = "mongodb://192.168.1.149:27017";
    private static String databaseName = "beacon-museum";
    private static String collectionName = "artworks";

    MongoClientURI mongoClientURI = new MongoClientURI(connString);
    MongoClient mongoClient = new MongoClient(mongoClientURI);

    MongoDatabase db = mongoClient.getDatabase(databaseName);
    MongoCollection coll = db.getCollection(collectionName);
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ObjectID = extras.getString("ObjectId");
            //System.out.println(getArtwork(Integer.parseInt(ObjectID)));
        }
    }
/*
    private Artwork getArtwork(int ObjectId){
        Artwork artwork = (Artwork) coll.find(eq("ObjectID", ObjectId)).first();
        return artwork;
    }
*/
}