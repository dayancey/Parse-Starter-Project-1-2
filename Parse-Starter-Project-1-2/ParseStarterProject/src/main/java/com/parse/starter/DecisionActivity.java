package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class DecisionActivity extends AppCompatActivity {
    TextView userName;
    TextView vote1Num, vote2Num;

    Post post;
    String objectId;
    ParseQuery<ParseObject> query;
    ImageView img1, img2, iconImg;
    String description;
    int vote1, vote2;
    Bitmap bitmap, bitmap2;
    ByteArrayOutputStream stream;
    byte[] arr, arr2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_post_layouts);
        //get the views for all the widgets
        userName = (TextView) findViewById(R.id.userName2);
        vote1Num = (TextView) findViewById(R.id.voteNumber1);
        vote2Num = (TextView) findViewById(R.id.voteNumber2);
        img1 = (ImageView) findViewById(R.id.imageVote1);
        img2 = (ImageView) findViewById(R.id.imageVote2);

        //initalize other fields
        query = new ParseQuery<ParseObject>("Post");


        //get the objectId that was passed intent
        Intent intent = getIntent();
        objectId = intent.getExtras().getString("objectId");
        //userName.setText(objectId);


        //query for the object using objectId
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("Failed get object:", "True");
                    Log.d("objectId ", objectId);
                } else {
                    post = (Post) object;
                    userName.setText(post.getDisplayName() + "");
                    vote1Num.setText(post.get("voteImage1")+"");
                    //Log.d("username", post.getUserName());
                    //arr = (byte[]) post.get("imageTest");
                    //arr = post.getImg2();
                    ParseFile file = (ParseFile) post.get("Image");

                    try {
                        arr2 = file.getData();

                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    if (arr2 != null) {
                        Log.d("arr2", arr2 + "");
                        stream = new ByteArrayOutputStream();
                        //bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
                        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        //img1.setImageBitmap(bitmap);
                        //bitmap = null;

                        bitmap2 = BitmapFactory.decodeByteArray(arr2, 0, arr2.length);
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        img2.setImageBitmap(bitmap2);
                        bitmap = null;
                    }
                }
            }
        });

        //userName.setText(post.getDisplayName() + "");

    }

    public void voteForOne(View view) throws ParseException {
        vote1 = (Integer) post.get("voteImage1");
        post.setVote1(vote1+1);
        try {
            post.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vote1Num.setText(vote1+1 +"");

    }

    public void voteForTwo(View view){
        vote2 = (Integer) post.get("voteImage2");
        post.setVote1(vote2+1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_decision, menu);
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
