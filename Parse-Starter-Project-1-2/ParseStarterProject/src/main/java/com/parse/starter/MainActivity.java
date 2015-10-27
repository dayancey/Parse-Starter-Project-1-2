/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
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
  ListView lv;
  List<ParseObject> ob;
  ArrayAdapter<String> adapter;
  ProgressDialog mProgressDialog;
  ParseQuery<ParseObject> query;
  ArrayList<String> list;
  TextView txt;
  ProgressDialog pd;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    un = (TextView) findViewById(R.id.mainUserId);
    un.setText(getIntent().getSerializableExtra("userName").toString().trim());

    list = new ArrayList<>();
    query = new ParseQuery<>("sampleObject");
    //query = ParseQuery.getQuery("Post");

    txt = (TextView) findViewById(R.id.textView3);
    //arrayAdapter = new ArrayAdapter<Device>(this, android.R.layout.simple_list_item_1, deviceList);
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

    lv = (ListView) findViewById(R.id.listView);

    pd = new ProgressDialog(MainActivity.this);

    ParseUser user = new ParseUser();

    Post post = new Post();
    post.setOwner(user.getCurrentUser());
    post.setDisplayName(user.getCurrentUser().getUsername());
    post.setVote1(2);



    /**
    ParseObject upload = new ParseObject("sampleObject");

    upload.put("postTest", post);
    upload.put("num", 4);

    upload.saveInBackground();
     **/



    //post.saveInBackground();





    /**
      query.whereExists("displayName");


      query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {
          if (e == null) {
            //int size = objects.size();
            Log.d("Here", "here");

            for (int i = 0; i < objects.size(); i++) {
              Log.d("Adding to the list", "next");
              if (list.add(objects.get(i).toString())) {
                Log.d("Adding to the list", "success");
                //txt.setText(objects.get(i).toString());
                //getPost(objects.get(i).get("post2").toString());

                Log.d("Setting Text", "Text Hopefully Set");
                //Post post1 = (Post) objects.get(i);
                //txt.setText(post1.getVoteForimg1() + "");

              } else {
                Log.d("Adding to the object", "false");
              }
            }
          }
        }
      });

    **/

    ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("sampleObject");
    //query1.whereEqualTo("voteImage1", 2);
    query1.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {

        if (e == null) {
          Log.d("Query Search", "Successful");
          for (int i = 0; i < objects.size(); i++){
            list.add(objects.get(i).toString());
            Log.d("adding to list", objects.get(i).toString());

          }


          adapter.clear();
          if (list.size() == 0){
            Log.d("List size is not ", "zero");
          }

          

          lv.setAdapter(adapter);

        } else {
          Log.d("Query search", "Unsuccessful");
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
