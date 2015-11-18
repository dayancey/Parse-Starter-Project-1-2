package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devinyancey on 10/30/15.
 */
class CustomAdapter extends ArrayAdapter<Post> {
    String[] posted;
    //ParseObject post = new ParseObject("");
    Bitmap bitmap, bitmap2;
    ByteArrayOutputStream stream;
    BitmapFactory.Options o;
    TextView userName;
    TextView description;
    ImageView userProfPic;
    ParseFile file;
    byte[] arr;



    CustomAdapter(Context context, Post[] posts){
        super(context, R.layout.the_post_layouts, posts);
        //query = new ParseQuery<ParseObject>("Post");
        stream = new ByteArrayOutputStream();
        Log.d("in costructor", "true");
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Log.d("In getView", "true");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.post_description_layout, parent, false);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");;
        userName = (TextView) customView.findViewById(R.id.userNameInfo);
        description = (TextView) customView.findViewById(R.id.usersDescription);
        userProfPic = (ImageView) customView.findViewById(R.id.postUserPic);

        //userProfPic = (ImageView) customView.findViewById(R.id.userProfPic);

        //get reference to everything

        final Post post = getItem(position);
        String descriptions = post.getString("comment");
        if (post == null){
            Log.d("Post is null", "true");

        }
        if (post != null){
            if (post.get("profilePicture") != null) {
                file = (ParseFile) post.get("profilePicture");
                try {
                    arr = file.getData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                stream = new ByteArrayOutputStream();

                bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                userProfPic.setImageBitmap(bitmap);
                bitmap = null;
            }
            //userName.setText("Username: This will be the usernames");
            //userName.setText("username: "+post.getString("displayName"));
            userName.setText(post.getString("displayName"));
            if (!descriptions.isEmpty()) {
                description.setText(descriptions);
            }else{
                description.setText("No Description");
            }


        Log.d("DisplayName:", post.getString("displayName"));

        //userName.setText(postItem.getUserName() + "");
        //Log.d("Display Name", postItem.getUserName()+"");
        //set the custom view
        //image1.setImageResource((Integer) postItem.get("Image1"));

        //byte[] a = postItem.getBytes("Image1");
        //Bitmap bitmap = BitmapFactory.decodeByteArray(a, 0, a.length);
        //image1.setImageBitmap(getItem(position).getImg1());

        //image1.setImageResource();
        //image2.setImageResource(R.mipmap.steph);
        //userImage.setImageResource(R.mipmap.steph);

        }

        return customView;
    }



}
