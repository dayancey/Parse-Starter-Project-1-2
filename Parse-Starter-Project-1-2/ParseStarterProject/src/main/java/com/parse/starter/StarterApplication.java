/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    //Parse.enableLocalDatastore(this);

    ParseObject.registerSubclass(Post.class);
    // Add your initialization code here
    Parse.initialize(this, "Svhc6XbmgzKhhvpnaxF8B7YuPYoyAebYpyJF5Hwu", "ijC4XaCIsehsuZ35npHNuKhm4TBlh9MVZmxMQpU8");

  ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
