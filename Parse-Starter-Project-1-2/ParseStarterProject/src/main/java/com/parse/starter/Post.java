package com.parse.starter;

import android.graphics.drawable.Drawable;
import android.media.Image;

/**
 * Created by devinyancey on 10/6/15.
 */
public class Post {
    Image img1, img2;
    int voteForimg1, voteForimg2;
    String userName;


    public void post(Image img1, Image img2, int voteForimg1,
                     int voteForimg2, String userName){
        this.img1 = img1;
        this.img2 = img2;
        this.voteForimg1 = voteForimg1;
        this.voteForimg2 = voteForimg2;
        this.userName = userName;
    }

    public void setImages(Image image, Image image2){
        img1 = image;
        img2 = image2;
    }

    public void updateVoteForimg1(){
        voteForimg1 += 1;
    }

    public Image getImg1() {
        return img1;
    }

    public Image getImg2() {
        return img2;
    }

    public int getVoteForimg1() {
        return voteForimg1;
    }

    public String getUserName() {
        return userName;
    }
}
