package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by devinyancey on 10/6/15.
 */

@ParseClassName("Post")

public class Post extends ParseObject{
    Bitmap userImage, img2;
    Bitmap img1;
    int voteForimg1, voteForimg2;
    String userName;
    String userComment;

    public Post(){

    }

    public Post(Bitmap user, Bitmap img1, Bitmap img2, int voteForimg1,
                     int voteForimg2, String userName, String userComment){
        this.userImage = user;
        this.img1 = img1;
        this.img2 = img2;
        this.voteForimg1 = voteForimg1;
        this.voteForimg2 = voteForimg2;
        this.userName = userName;
        this.userComment = userComment;

    }

    public void setImages(Bitmap image, Bitmap image2){
        img1 = image;
        img2 = image2;
    }

    public void updateVoteForimg1(){
        voteForimg1 += 1;
    }

    public Bitmap getImg1() {
        return img1;
    }

    public Bitmap getImg2() {
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

    public void putImage(Image img){
        put("Image", img);
    }

    public int getVote(){
        return 1;
    }




}
