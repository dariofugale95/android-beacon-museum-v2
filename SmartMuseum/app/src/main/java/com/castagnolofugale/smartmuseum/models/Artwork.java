package com.castagnolofugale.smartmuseum.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Artwork {
    private ObjectId _id;
    private String Title;
    private ArrayList<String> ConstituentID;
    private ArrayList<String> ArtistBio;
    private ArrayList<String> Nationality;
    private ArrayList<String> BeginDate;
    private ArrayList<String> EndDate;
    private ArrayList<String> Gender;
    private int Date; // only year
    private String Medium;
    private String Dimensions;
    private String CreditLine;
    private String AccessionNumber;
    private String Classification;
    private String Department;
    private String DateAcquired;
    private String Cataloged;
    private int ObjectID;
    private String URL;
    private String ThumbnailURL;
    private float Height;
    private float Width;

    public Artwork(ObjectId _id,
                   String title,
                   ArrayList<String> constituentID,
                   ArrayList<String> artistBio,
                   ArrayList<String> nationality,
                   ArrayList<String> beginDate,
                   ArrayList<String> endDate,
                   ArrayList<String> gender,
                   int date, String medium,
                   String dimensions,
                   String creditLine,
                   String accessionNumber,
                   String classification,
                   String department,
                   String dateAcquired,
                   String cataloged,
                   int objectID,
                   String URL,
                   String thumbnailURL,
                   float height,
                   float width,
                   long beacon_id) {
        this._id = _id;
        Title = title;
        ConstituentID = constituentID;
        ArtistBio = artistBio;
        Nationality = nationality;
        BeginDate = beginDate;
        EndDate = endDate;
        Gender = gender;
        Date = date;
        Medium = medium;
        Dimensions = dimensions;
        CreditLine = creditLine;
        AccessionNumber = accessionNumber;
        Classification = classification;
        Department = department;
        DateAcquired = dateAcquired;
        Cataloged = cataloged;
        ObjectID = objectID;
        this.URL = URL;
        ThumbnailURL = thumbnailURL;
        Height = height;
        Width = width;
        Beacon_id = beacon_id;
    }

    public Artwork() {
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "_id=" + _id +
                ", Title='" + Title + '\'' +
                ", ConstituentID=" + ConstituentID +
                ", ArtistBio=" + ArtistBio +
                ", Nationality=" + Nationality +
                ", BeginDate=" + BeginDate +
                ", EndDate=" + EndDate +
                ", Gender=" + Gender +
                ", Date=" + Date +
                ", Medium='" + Medium + '\'' +
                ", Dimensions='" + Dimensions + '\'' +
                ", CreditLine='" + CreditLine + '\'' +
                ", AccessionNumber='" + AccessionNumber + '\'' +
                ", Classification='" + Classification + '\'' +
                ", Department='" + Department + '\'' +
                ", DateAcquired='" + DateAcquired + '\'' +
                ", Cataloged='" + Cataloged + '\'' +
                ", ObjectID=" + ObjectID +
                ", URL='" + URL + '\'' +
                ", ThumbnailURL='" + ThumbnailURL + '\'' +
                ", Height=" + Height +
                ", Width=" + Width +
                ", Beacon_id=" + Beacon_id +
                '}';
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<String> getConstituentID() {
        return ConstituentID;
    }

    public void setConstituentID(ArrayList<String> constituentID) {
        ConstituentID = constituentID;
    }

    public ArrayList<String> getArtistBio() {
        return ArtistBio;
    }

    public void setArtistBio(ArrayList<String> artistBio) {
        ArtistBio = artistBio;
    }

    public ArrayList<String> getNationality() {
        return Nationality;
    }

    public void setNationality(ArrayList<String> nationality) {
        Nationality = nationality;
    }

    public ArrayList<String> getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(ArrayList<String> beginDate) {
        BeginDate = beginDate;
    }

    public ArrayList<String> getEndDate() {
        return EndDate;
    }

    public void setEndDate(ArrayList<String> endDate) {
        EndDate = endDate;
    }

    public ArrayList<String> getGender() {
        return Gender;
    }

    public void setGender(ArrayList<String> gender) {
        Gender = gender;
    }

    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        Date = date;
    }

    public String getMedium() {
        return Medium;
    }

    public void setMedium(String medium) {
        Medium = medium;
    }

    public String getDimensions() {
        return Dimensions;
    }

    public void setDimensions(String dimensions) {
        Dimensions = dimensions;
    }

    public String getCreditLine() {
        return CreditLine;
    }

    public void setCreditLine(String creditLine) {
        CreditLine = creditLine;
    }

    public String getAccessionNumber() {
        return AccessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        AccessionNumber = accessionNumber;
    }

    public String getClassification() {
        return Classification;
    }

    public void setClassification(String classification) {
        Classification = classification;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getDateAcquired() {
        return DateAcquired;
    }

    public void setDateAcquired(String dateAcquired) {
        DateAcquired = dateAcquired;
    }

    public String getCataloged() {
        return Cataloged;
    }

    public void setCataloged(String cataloged) {
        Cataloged = cataloged;
    }

    public int getObjectID() {
        return ObjectID;
    }

    public void setObjectID(int objectID) {
        ObjectID = objectID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getThumbnailURL() {
        return ThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        ThumbnailURL = thumbnailURL;
    }

    public float getHeight() {
        return Height;
    }

    public void setHeight(float height) {
        Height = height;
    }

    public float getWidth() {
        return Width;
    }

    public void setWidth(float width) {
        Width = width;
    }

    public long getBeacon_id() {
        return Beacon_id;
    }

    public void setBeacon_id(long beacon_id) {
        Beacon_id = beacon_id;
    }

    private long Beacon_id;

}
