/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.Serializable;
import java.text.ParseException;


public class MainActivity extends ActionBarActivity {
  TextView un;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    un = (TextView) findViewById(R.id.mainUserId);
    un.setText(getIntent().getSerializableExtra("userName").toString().trim());


    Post post = new Post();
    post.userName = "dave";
    ParseObject upload = new ParseObject("uploadTest");
    //upload.put("post1", post);

    //upload.saveInBackground();
    //ParseFile file = new ParseFile();
    /**
    check for which user is logged in?
    based off who is logged in talior the system preferences to that user?

    this is where we want to display the main activity
    what is it going to look like?

     show the a list of the users "choices" to be selected upon?

     Better yet show options to go to the users "choices" and go to "choices"
     that they can "pick" on
        1st option: starts UserChoicesActivity
        2nd option: starts PickActivity
    **/

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
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
