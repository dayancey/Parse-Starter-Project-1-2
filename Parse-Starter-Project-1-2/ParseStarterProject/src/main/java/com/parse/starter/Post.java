package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

/**
 * Created by devinyancey on 10/6/15.
 */

@ParseClassName("Post")

public class Post extends ParseObject implements Serializable{
    Bitmap userImage;
    byte[] img1, img2;
    int voteForimg1, voteForimg2;
    String userName;
    String userComment;

    public Post(){

    }

    public Post(Bitmap user, Bitmap img1, Bitmap img2, int voteForimg1,
                     int voteForimg2, String userName, String userComment){
        this.userImage = user;


        this.voteForimg1 = voteForimg1;
        this.voteForimg2 = voteForimg2;
        this.userName = userName;
        this.userComment = userComment;

    }



    public void setImg1(byte[] image){
        put("imageTest", image);
        img1 = image;
    }

    public void setImg2(byte[] image){
        put("imageTest2", image);
    }

    public void setImg3(byte[] image){
        img2 = image;
    }

    public void setComment(String comment){
        put("comment",comment);
    }


    public void updateVoteForimg1(){
        voteForimg1 += 1;
    }

    public byte[] getImg1() {
        return img1;
    }

    public byte[] getImg2() {
        return img2;
    }

    public int getVoteForimg1() {
        return voteForimg1;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }


    public String getDisplayName() {
        return getString("displayName");
    }
    public void setDisplayName(String displayName) {
        put("displayName", displayName);
    }

    public ParseUser getOwner() {
        return getParseUser("owner");
    }
    public void setOwner(ParseUser user) {
        put("owner", user);
    }


    public void setVote1(int voteForimg1) {
        put("voteImage1", voteForimg1);
    }

    public void setVote2(int voteForimg2){
        put("voteImage1", voteForimg2);
    }

    public void putImage(ParseFile file){
        put("Image", file);
    }

    public int getVote(){
        return 1;
    }

}
