/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
  TextView un;
  ListView lv;

  List<ParseObject> ob;
  ArrayAdapter<String> adapter;
  ListAdapter listAdapter;
  ProgressDialog mProgressDialog;
  ParseQuery<ParseObject> query;
  ArrayList<Post> list;
  TextView txt;
  ProgressDialog pd;
  ParseFile file;

  ImageView img;

  String[] objectIds;

  Post[] objectPosts;

  byte[] image;
  byte[] arr;

  Bitmap bitmap;
  ByteArrayOutputStream stream;


  int arraySize;
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    un = (TextView) findViewById(R.id.mainUserId);
    un.setText(getIntent().getSerializableExtra("userName").toString().trim());
    objectIds = null;
    objectPosts = null;

    query = new ParseQuery<>("Post");
    list = new ArrayList<>();
    img = (ImageView) findViewById(R.id.userIcon);
    ParseFile fild = (ParseFile) ParseUser.getCurrentUser().get("profilePicture");
    try {
      arr = fild.getData();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    if (arr != null){
      stream = new ByteArrayOutputStream();

      bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);

      img.setImageBitmap(bitmap);
    }
    //query = ParseQuery.getQuery("Post");

    Post test1 = new Post();



    //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

    ParseUser user = new ParseUser();
    //makePost();
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap
            .steph);


    //Convert it to byte
    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    //compress the image
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

    byte[] image2 = stream.toByteArray();
    image = image2;

    Post post = new Post();
    post.setOwner(user.getCurrentUser());
    post.setUserName(user.getCurrentUser().toString());
    post.setDisplayName(user.getCurrentUser().getUsername());
    //post.setVote1(2);
    //post.setImg1(image);
    //post.setImg2(image);
    post.setImg3(image);
    //post.put("Image1", file);
    ParseACL acl = new ParseACL();
    acl.setPublicReadAccess(true);
    acl.setPublicWriteAccess(true);

    ParseFile file = new ParseFile("image", image2);
    file.saveInBackground();
    post.putImage(file);
    post.setACL(acl);
    Log.d("post: ", post.getUserName() + "");
    //post.saveInBackground();

    lv = (ListView) findViewById(R.id.postListView);

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Post post1 = lv.getItemAtPosition(position);
        Intent intent = new Intent(MainActivity.this, DecisionActivity.class);
        intent.putExtra("PostObject",(Post) lv.getItemAtPosition(position));
        intent.putExtra("objectId", ((Post) lv.getItemAtPosition(position)).getObjectId().toString());
        Toast.makeText(MainActivity.this, ((Post) lv.getItemAtPosition(position)).getObjectId().toString(),Toast.LENGTH_SHORT).show();
        Log.d("object being Passed:", lv.getItemAtPosition(position).toString());
        startActivity(intent);
      }
    });

    pd = new ProgressDialog(MainActivity.this);



    //query.whereEqualTo("displayName", "test");





    /**
    post.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null){
          Log.d("saved: ", "true");
        }
      }
    });
     **/
    queryPosts();

              ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void queryPosts(){
    query.setLimit(20);
    //query.whereEqualTo("displayName", "day");
    query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {
            if (e == null) {
              Log.d("number of objects", objects.size() + "");
              objectPosts = new Post[objects.size()];
              //Post[] pst = new Post[objects.size()];
              for (int i = 0; i < objects.size(); i++) {
                Log.d("ParseObject:", objects.get(i).toString());
                //pst[i] = (Post) objects.get(i);
                objectPosts[i] = (Post) objects.get(i);
              }
              sendToAdpater(objectPosts);
              lv.setAdapter(listAdapter);
            }
          }
        });



  }

  public void sendToAdpater(Post[] array){

    pd = ProgressDialog.show(this, "dialog title",
            "dialog message", true);
    for (int i = 0; i < array.length; i++){
      Log.d("Object:", array[i].toString());
    }
    listAdapter = new CustomAdapter(this, array);
    pd.dismiss();
  }


  public void makePost(){
    //THis is to locate the image but it will be replaced by going into the
    //gallery/taking a picture
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap
            .steph);

    //Convert it to byte
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    BitmapFactory.Options o = new BitmapFactory.Options();
    //compress the image
    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
    byte[] image2 = stream.toByteArray();
    image = image2;
    //create the parse file
    //file = new ParseFile("postImage.png", image);
    //file.saveInBackground();
  }



  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //Determine what action bar item user selected and perform action
    if(id == R.id.action_new_post){
      Intent intent2 = new Intent(MainActivity.this, CreatePost.class);

      //Retrieve username to pass to next activity
      String userName = un.getText().toString();
      intent2.putExtra("userName", userName);

      //Start CreatePost activity
      startActivity(intent2);

      finish();
      return true;
    } else if(id == R.id.action_refresh){
      finish();
      startActivity(getIntent());
      return true;
    } else if(id == R.id.action_logout){
      ParseUser.logOut();
      Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
      startActivity(intent);
      finish();
      return true;
    } else if (id == R.id.action_settings) {
      Intent intent = new Intent(MainActivity.this, Settings.class);
      startActivity(intent);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void goToUserPage(View view){

    //ParseUser currentUser = ParseUser.getCurrentUser();
    Intent intent = new Intent(MainActivity.this, UserPage.class);
    // intent.putExtra("userName", currentUser.getUsername().toString().trim());
    intent.putExtra("userName", un.getText());
    startActivity(intent);
  }


  @Override
  public void onDestroy(){
    super.onDestroy();
   un = null;
    lv = null;
  }
}
