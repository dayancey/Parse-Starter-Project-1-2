package com.parse.starter;

import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.ParseFile;
import com.parse.ParseACL;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class CreatePost extends ActionBarActivity{

    ParseUser user = new ParseUser();
    Bitmap currPic;
    ImageView postUserPic;

    TextView userPost;
    Boolean leftClicked = false;
    Boolean rightClicked = false;
    Post newPost;
    ParseFile leftFile, rightFile;

    //Define variables to handle comments
    EditText text;
    String comment;

    boolean description = false;

    //Define variables to handle buttons
    //   Button leftImage;
    //   Button rightImage;
    Button submit;

    //Define variables to handle retrieving username from intent
    Bundle extras;
    String userName;

      byte[] lImage;
      byte[] rImage;

    //Define variables to handle picture selection
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private String filemanagerstring;
    String imagePicked;
    String leftImg, rightImg;
    ImageView leftImageView;
    ImageView rightImageView;

    //ParseFile rightFile;
    //ParseFile leftFile;

    //Monitors if both images have been uploaded
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post2);
        postUserPic = (ImageView) findViewById(R.id.userPicture);
        //  callProfilePic();
        //  postUserPic.setImageBitmap(currPic);
        userPost = (TextView) findViewById(R.id.userName2);
        userPost.setText(getIntent().getStringExtra("userName"));
        newPost = new Post();
        //Accept intent
        extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString(userName);
        }

        text = (EditText) findViewById(R.id.userDescription);
        leftImageView = (ImageView) findViewById(R.id.imageVote1);
        rightImageView = (ImageView) findViewById(R.id.imageVote2);
        submit = (Button) findViewById(R.id.submitBtn);



        //    leftImageView = (ImageView) findViewById(R.id.imageVote1);
        //    rightImageView = (ImageView) findViewById(R.id.imageVote2);


        //set onclick listener for userProfile pic
        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i++;

                //User selects image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                leftClicked = true;
/*
Below everything is null because it doesn't wait for image choice
 */
                /**
                 //Bitmap selected image, hide button, and display image in imageView
                 leftImg = imagePicked;
                 File imageFile = new File(leftImg);
                 Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                 leftImageView.setImageBitmap(bitmap);
                 leftImageView.setVisibility(View.GONE);
                 //Compress image and put into byte array
                 ByteArrayOutputStream stream = new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                 lImage = stream.toByteArray();
                 **/
            }

        });


        //set onclick listener for userProfile pic
        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;

                //User selects image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                rightClicked = true;

/*
Below everything is null because it doesn't wait for image choice
 */
                /**
                 //Bitmap selected image, hide button, and display image in imageView
                 rightImg = imagePicked;
                 File imageFile = new File(rightImg);
                 Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                 rightImageView.setImageBitmap(bitmap);
                 rightImageView.setVisibility(View.GONE);
                 //Compress image and put into byte array
                 //      ByteArrayOutputStream stream = new ByteArrayOutputStream();
                 //     bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                 //    rImage = stream.toByteArray();
                 **/
            }


        });


        //set onclick listener for userProfile pic
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Both images have been uploaded, submit post and return to main activity
                if (i == 2) {

                    creatingPost();
                    /**
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
                     Intent intent = new Intent(CreatePost.this, MainActivity.class);
                     startActivity(intent);
                     **/
                } else {  //Notify users that two images have to be uploaded to submit post
                    Toast.makeText(getApplicationContext(), "Please Upload Two Images",
                            Toast.LENGTH_LONG).show();

                }

            }

        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent intent = new Intent(CreatePost.this, MainActivity.class);
            intent.putExtra("userName", currentUser.getUsername().toString().trim());
            startActivity(intent);
            return true;
        }
        return false;
    }

    public boolean descriptionIsThere() {

        return description;
    }

        //set onCLick listeners for each button
        //  leftImageView.setOnClickListener(handler1);
        //   rightImageView.setOnClickListener(handler2);
        //   submit.setOnClickListener(handler3);


/**
 //onClick listener for upload left image button
 leftImageView.setOnClickListener(new View.OnClickListener() {
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
 leftImageView.setVisibility(View.GONE);
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
 rightImageView.setVisibility(View.GONE);
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
 **/





    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {


                if(leftClicked == true) {

                    Uri selectedImageUri = data.getData();


                    //OI FILE Manager
                    filemanagerstring = selectedImageUri.getPath();

                    //MEDIA GALLERY
                    selectedImagePath = getPath(selectedImageUri);

                    //NOW WE HAVE OUR WANTED STRING
                    if (selectedImagePath != null)
                        imagePicked = selectedImagePath;
                    else
                        imagePicked = filemanagerstring;


                    leftImageView.setImageURI(selectedImageUri);


                    Bitmap bitmap = ((BitmapDrawable) leftImageView.getDrawable()).getBitmap();


                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] arr = stream.toByteArray();
                     lImage = arr;


                    // Create the ParseFile passing the byte[] and the name of this specific image file
                    leftFile = new ParseFile("LeftPostPic", lImage);
                    leftFile.saveInBackground();
                    //leftPic(leftFile);

                    //set the pic
                    //  photo.setUserPicture(file);
                    //  post.setUserPicture(file);



                }


                else if(rightClicked == true) {

                    Uri selectedImageUri = data.getData();


                    //OI FILE Manager
                    filemanagerstring = selectedImageUri.getPath();

                    //MEDIA GALLERY
                    selectedImagePath = getPath(selectedImageUri);

                    //NOW WE HAVE OUR WANTED STRING
                    if (selectedImagePath != null)
                        imagePicked = selectedImagePath;
                    else
                        imagePicked = filemanagerstring;


                    rightImageView.setImageURI(selectedImageUri);


                    Bitmap bitmap = ((BitmapDrawable) rightImageView.getDrawable()).getBitmap();


                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] arr2 = stream.toByteArray();
                    rImage = arr2;



                    // Create the ParseFile passing the byte[] and the name of this specific image file


                    rightFile = new ParseFile("RightPostPic", rImage);

                    rightFile.saveInBackground();
                    //rightPic(rightFile);

                    //set the pic
                    //  photo.setUserPicture(file);
                    // post.setUserPicture(file);



                }

                leftClicked = false;
                rightClicked = false;
            }
        }
    }


    public ParseFile leftPic(ParseFile file){

        leftFile = file;
        return leftFile;
    }
    public ParseFile rightPic(ParseFile file){
        rightFile = file;
        return rightFile;
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


    public void creatingPost(){

        //Retrieve comment about post
        comment = text.getText().toString();
        Log.d("comment", comment);

        //Save post to Parse
        final ParseUser currentUser = ParseUser.getCurrentUser();
        newPost.setOwner(user.getCurrentUser());
        newPost.setDisplayName(user.getCurrentUser().getUsername());
        newPost.setImg1(leftFile);
        newPost.setImg2(rightFile);
        newPost.setVote1(0);
        newPost.setVote2(0);
        //newPost.putImage(leftFile);
        newPost.setComment(comment);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        newPost.setACL(acl);
        Log.d("left image: ", leftFile.toString());
        Log.d("right image: ", rightFile.toString());
        Log.d("userparsess :", user.getCurrentUser().toString());
        //newPost.saveInBackground();
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Intent intent = new Intent(CreatePost.this, MainActivity.class);
                    intent.putExtra("userName", currentUser.getUsername().toString().trim());
                    startActivity(intent);
                }
            }
        });
        //user.saveInBackground();


        //Return to MainActivity
          //Intent intent = new Intent(CreatePost.this, MainActivity.class);
          //startActivity(intent);

    }

/**
    public Bitmap callProfilePic() {
        //photo = new photoFile();
        //ParseFile file = photo.getUserPicture();
        ParseFile file = new ParseFile();
        if(file != null) {
            byte[] userPic = new byte[0];
            try {
                userPic = file.getData();
                //photo.setUserPicBmp(userPic);
                //currPic = photo.getUserPicBmp();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

                    //Intent intent = new Intent(this, .class);
                    //intent.putExtra("profilePicture", data);
                    //startActivity(intent);



        return currPic;
    }
**/

}