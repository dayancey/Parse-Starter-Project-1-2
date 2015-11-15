package com.parse.starter;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseUser;
import com.parse.ParseFile;
import com.parse.ParseACL;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by nabain on 11/13/15.
 */
public class CreatePost extends ActionBarActivity{

    ParseUser user = new ParseUser();

    //Define variables to handle comments
    EditText text;
    String comment;

    //Define variables to handle buttons
    Button leftImage;
    Button rightImage;
    Button submit;

    //Define variables to handle retrieving username from intent
    Bundle extras;
    String userName;

    byte[] lImage;
    byte[] rImage;

    ParseFile leftImaged;
    ParseFile rightImaged;

    //Define variables to handle picture selection
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private String filemanagerstring;
    String imagePicked;
    String leftImg, rightImg;
    ImageView leftImageView;
    ImageView rightImageView;

    //Monitors if both images have been uploaded
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post2);

        //Accept intent
        extras = getIntent().getExtras();
        if(extras!= null){
            userName = extras.getString(userName);
        }

        leftImage = (Button) findViewById(R.id.leftImageBtn);
        rightImage = (Button) findViewById(R.id.rightImageBtn);
        submit = (Button) findViewById(R.id.submitBtn);

        //set onCLick listeners for each button
        leftImage.setOnClickListener(handler1);
        rightImage.setOnClickListener(handler2);
        submit.setOnClickListener(handler3);

        text = (EditText) findViewById(R.id.editText);
        leftImageView = (ImageView) findViewById(R.id.leftImageView);
        rightImageView = (ImageView) findViewById(R.id.rightImageView);
    }

    //onClick listener for upload left image button
    View.OnClickListener handler1 = new View.OnClickListener(){
        public void onClick(View v){
            i++;

            //User selects image
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            //Bitmap selected image, hide button, and display image in imageView
            leftImg = imagePicked;
            File imageFile = new File(leftImg);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            leftImageView.setImageBitmap(bitmap);
            leftImage.setVisibility(View.GONE);

            //Compress image and put into byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            lImage = stream.toByteArray();

        }
    };

    //onClick listener for upload right image button
    View.OnClickListener handler2 = new View.OnClickListener(){
        public void onClick(View v){
            i++;

            //User selects image
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            //Bitmap selected image, hide button, and display image in imageView
            rightImg = imagePicked;
            File imageFile = new File(rightImg);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            rightImageView.setImageBitmap(bitmap);
            rightImage.setVisibility(View.GONE);

            //Compress image and put into byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            rImage = stream.toByteArray();
        }
    };

    //onClick listener for submit button
    View.OnClickListener handler3 = new View.OnClickListener(){
        public void onClick(View v){

            //Both images have been uploaded, submit post and return to main activity
            if(i == 2){

                //Retrieve comment about post
                comment = text.getText().toString();

                //Save post to Parse
                Post post = new Post();
                post.setOwner(user.getCurrentUser());
                post.setDisplayName(user.getCurrentUser().getUsername());
                post.setImg1(lImage);
                post.setImg2(rImage);
                post.setComment(comment);
                ParseACL acl = new ParseACL();
                acl.setPublicReadAccess(true);
                acl.setPublicWriteAccess(true);
                post.setACL(acl);
                post.saveInBackground();

                //Return to MainActivity
                Intent intent = new Intent(CreatePost.this,MainActivity.class);
                startActivity(intent);

            } else {  //Notify users that two images have to be uploaded to submit post
                Toast.makeText(getApplicationContext(), "Please Upload Two Images",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                //OI FILE Manager
                filemanagerstring = selectedImageUri.getPath();

                //MEDIA GALLERY
                selectedImagePath = getPath(selectedImageUri);

                //NOW WE HAVE OUR WANTED STRING
                if(selectedImagePath!=null)
                    imagePicked = selectedImagePath;
                else
                    imagePicked = filemanagerstring;
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null)
        {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }

}
