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
        //userProfPic = (ImageView) customView.findViewById(R.id.userProfPic);

        //get reference to everything

        final Post post = getItem(position);
        String descriptions = post.getString("comment");
        if (post == null){
            Log.d("Post is null", "true");

        }
        if (post != null){

            //userName.setText("Username: This will be the usernames");
            //userName.setText("username: "+post.getString("displayName"));
            userName.setText(post.getString("displayName"));
            if (!descriptions.isEmpty()) {
                description.setText("Description: " + descriptions);
            }else{
                description.setText("Description: No Description");
            }



            /**
             *
             *
             *
             *
             final TextView userName = (TextView) customView.findViewById(R.id.userName2);
             TextView userName3 = (TextView) customView.findViewById(R.id.userName3);
             TextView userDescription = (TextView) customView.findViewById(R.id.userDescription);
             TextView vote1 = (TextView) customView.findViewById(R.id.voteNumber1);
             TextView vote2 = (TextView) customView.findViewById(R.id.voteNumber2);

             ImageView userImage = (ImageView) customView.findViewById(R.id.userPicture);

             final ImageView image1 = (ImageView) customView.findViewById(R.id.imageVote1);
             final ImageView image2 = (ImageView) customView.findViewById(R.id.imageVote2);

             userName.setText(post.getString("displayName"));

            byte[] arr = (byte[]) post.get("imageTest");
            if (arr != null) {
                bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
                bitmap.compress(Bitmap.CompressFormat.PNG, 25, stream);
                image1.setImageBitmap(bitmap);
                bitmap = null;
            }
            byte[] arr2 = (byte[]) post.get("imageTest2");
            if (arr2 != null){
                bitmap2 = BitmapFactory.decodeByteArray(arr2,0,arr2.length);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                BitmapFactory.Options o = new BitmapFactory.Options();
                //compress the image
                bitmap2.compress(Bitmap.CompressFormat.PNG, 25, stream);
                image2.setImageBitmap(bitmap2);
                bitmap2 = null;
            }
             **/
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
        /**
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked",Toast.LENGTH_SHORT).show();
                ParseUser currentUser = ParseUser.getCurrentUser();
                // Start an intent for the dispatch activity
                Post post1 = getItem(position);
                Intent intent = new Intent(getContext(), DecisionActivity.class);
                //intent.putExtra("postObject", (Parcelable) post1);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                //getContext().startActivity(intent);
            }
        });
         **/
        return customView;
    }



}
