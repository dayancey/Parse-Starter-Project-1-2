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
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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

  public Post test;
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

    //query = ParseQuery.getQuery("Post");
    test = new Post();
    Post test1 = new Post();

    txt = (TextView) findViewById(R.id.textView3);

    //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

    lv = (ListView) findViewById(R.id.postListView);

    pd = new ProgressDialog(MainActivity.this);

/**
    ///////////////////////////////////////////

    query.whereEqualTo("displayName", "test");
    query.findInBackground(new FindCallback<ParseObject>() {

      @Override
      public void done(final List<ParseObject> objects, ParseException e) {
        Log.d("antone", "test");
        Log.d("Testing", "123");
        objectIds = new String[objects.size()];
        String[] ids = new String[objects.size()];
        if (e == null) {

          Log.d("object size:", objects.size() + "");

          for (int i = 0; i < objects.size(); i++) {
            Log.d("Object:", objects.get(i).toString());


            objectIds[i] = objects.get(i).getObjectId().toString();
            ids[i] = objects.get(i).getObjectId().toString();

            ParseObject parseObject = objects.get(i);
            test.setUserName(objects.get(i) + "");
            txt.setText(test.getUserName());
          }
          sendToAdpater(ids);

          lv.setAdapter(listAdapter);
        } else {

        }
      }
    });

    //listAdapter = new CustomAdapter(this, test);
    ////////////////////////////////////////////
**/

    //query.whereEqualTo("displayName", "test");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if (e == null){
          Log.d("number of objects", objects.size() +"");
          Post[] pst = new Post[objects.size()];
          for (int i = 0; i < objects.size(); i++){
            Log.d("ParseObject:", objects.get(i).toString());
            pst[i] = (Post) objects.get(i);
          }
          sendToAdpater(pst);
          lv.setAdapter(listAdapter);
        }
      }
    });





    ParseUser user = new ParseUser();


    makePost();
    Post post = new Post();
    post.setOwner(user.getCurrentUser());
    post.setUserName(user.getCurrentUser().toString());
    post.setDisplayName(user.getCurrentUser().getUsername());
    post.setVote1(2);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable
            .user_icon);

    post.setImg1(image);
    //post.put("Image1", file);
    ParseACL acl = new ParseACL();
    acl.setPublicReadAccess(true);
    acl.setPublicWriteAccess(true);

    post.setACL(acl);
    //post.saveInBackground();


              ParseAnalytics.trackAppOpenedInBackground(getIntent());
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

  public void addToListArray(List<ParseObject> lst){
    list.clear();
    for (int i = 0; i < lst.size(); i++){

      list.add((Post) lst.get(i));
      Log.d("Adding to Array list", "Added " + i +" "+((Post) lst.get(i)).getUserName());
    }
    txt.setText("Here");
  }

  public void makePost(){
    //THis is to locate the image but it will be replaced by going into the
    //gallery/taking a picture
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable
            .user_icon);

    //Convert it to byte
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    //compress the image
    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
    byte[] image2 = stream.toByteArray();
    image = image2;
    //create the parse file
    //file = new ParseFile("postImage.png", image);
    //file.saveInBackground();
  }


  public void getPosts(){
    adapter.clear();

    Log.d("Clearing Adapter", "Success");
    for (int i = 0; i < list.size(); i++) {
      Log.d("Adding to adapter", "success");
      adapter.add(list.get(i).toString());

    }
    adapter.notifyDataSetChanged();

    lv.setAdapter(adapter);

    //pd.dismiss();
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

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
