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
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
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
  AbsListView lv;

  List<ParseObject> ob;
  ArrayAdapter<String> adapter;
  ListAdapter listAdapter;
  ProgressDialog mProgressDialog;
  ParseQuery<ParseObject> query;
  ArrayList<String> list;
  TextView txt;
  ProgressDialog pd;

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    un = (TextView) findViewById(R.id.mainUserId);
    un.setText(getIntent().getSerializableExtra("userName").toString().trim());

    list = new ArrayList<>();
    query = new ParseQuery<>("Post");
    //query = ParseQuery.getQuery("Post");
    Post test = new Post();
    Post test1 = new Post();
    Post[] arrayOfPost = {test, test1};
    txt = (TextView) findViewById(R.id.textView3);
    //arrayAdapter = new ArrayAdapter<Device>(this, android.R.layout.simple_list_item_1, deviceList);
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

    listAdapter = new CustomAdapter(this, arrayOfPost);
    lv = (AbsListView) findViewById(R.id.postListView);
    lv.setAdapter(listAdapter);
    pd = new ProgressDialog(MainActivity.this);

    ParseUser user = new ParseUser();

    Post post = new Post();
    post.setOwner(user.getCurrentUser());
    post.setDisplayName(user.getCurrentUser().getUsername());
    post.setVote1(2);
    ParseACL acl = new ParseACL();
    acl.setPublicReadAccess(true);
    acl.setPublicWriteAccess(true);

    post.setACL(acl);
    //post.saveInBackground();
    


    /**
    ParseObject upload = new ParseObject("sampleObject");

    upload.put("postTest", post);
    upload.put("num", 4);

    upload.saveInBackground();
     **/



    //post.saveInBackground();






      //query.whereExists("voteImage1");
      query.whereEqualTo("objectId", "OSDWgkbkVy");


      query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {
          if (e == null) {

            Log.d("object size:", objects.size() + "");
            for (int i = 0; i < objects.size(); i++) {
              Log.d("Object:", objects.get(i).toString());
              String s = (String) objects.get(i).getString("voteImage1");
              txt.setText(s);
            }
          } else {

          }

        }


      });






              // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_main );

              //lv.setAdapter(arrayAdapter);

              ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void addToListArray(List<ParseObject> lst){
    list.clear();
    for (int i = 0; i < lst.size(); i++){

      list.add(lst.get(i).toString());
      Log.d("Adding to Array list", "Added " + i +" "+lst.get(i).getString("objectId"));
    }
    txt.setText("Here");
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
