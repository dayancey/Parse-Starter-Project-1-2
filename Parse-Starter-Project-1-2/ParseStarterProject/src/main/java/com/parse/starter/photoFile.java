package com.parse.starter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zachary on 11/6/2015.
 */

@ParseClassName("photoFile")
public class photoFile extends ParseObject {

    Bitmap img1, img2, userImage;
    byte[]  bytePic;
    int voteForimg1, voteForimg2;
    // String userName, title;
    String userComment;
    ParseFile pic;
    Post post;
    //ParseQuery<ParseObject> query;
    photoFile photo;
    ParseUser currUser = ParseUser.getCurrentUser();

    // photoFile userPhoto = new photoFile();
    ParseQuery<ParseObject> query = ParseQuery.getQuery("photoFile");

    public photoFile(){

    }
/**
     public photoFile(Bitmap image, String title) {
     super();
     this.userImage = image;
     //this.title = title;
     }
**/

    public photoFile( ParseFile file, ParseUser user, Bitmap userimg, byte[] arr){// Bitmap img1, Bitmap img2,
        bytePic = arr;
        setUserPicBmp(bytePic);
        currUser = user;
        pic = file;
        userImage = userimg;
        // title = txt;

    }



    public void setImages(Bitmap image, Bitmap image2){
        img1 = image;
        img2 = image2;
    }

    /**
     public void setImg1(byte[] image){
     put("imageTest", image);
     img1 = image;
     }
     public void updateVoteForimg1(){
     voteForimg1 += 1;
     }
     **/
    public Bitmap getImg1() {
        return img1;
    }

    public Bitmap getImg2() {
        return img2;
    }


    /**
     public int getVoteForimg1() {
     return voteForimg1;
     }
     **/
    /**
     public String getUserName() {
     return userName;
     }
     public void setUserName(String userName){
     this.userName = userName;
     }
     **/




    public void setDisplayName(String displayName) {
        put("displayName", displayName);
    }

    public String getDisplayName() {

        return currUser.getUsername();
    }


    public void setOwner(ParseUser user) {
        put("owner", user);
    }

    public ParseUser getOwner() {

        return getParseUser("owner");
    }


    public void setUserPicture(ParseFile photo){
        put("profilePicture", photo);
        currUser.put("profilePicture", photo);

    }

    public ParseFile getUserPicture(){

        ParseFile photo = (ParseFile) currUser.get("profilePicture");

        return photo;

    }


    public Bitmap getUserPicBmp(){
        return userImage;
    }


    /*
       newest try to find the curr user's profile pic and convert it back to bitmap
       and set as image view
       */
    public void setUserPicBmp(byte[] pic) {
        bytePic = pic;

        BitmapFactory.Options options = new BitmapFactory.Options();
        userImage = BitmapFactory.decodeByteArray(bytePic, 0, bytePic.length, options); //Convert bytearray to bitmap
        bytePic = null;


    }



    public Bitmap callProfilePic() {
        photo = new photoFile();
        ParseFile file = photo.getUserPicture();
        if (file != null) {
            byte[] userPic = new byte[0];
            try {
                userPic = file.getData();
                photo.setUserPicBmp(userPic);
                userImage = photo.getUserPicBmp();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return userImage;
    }



}