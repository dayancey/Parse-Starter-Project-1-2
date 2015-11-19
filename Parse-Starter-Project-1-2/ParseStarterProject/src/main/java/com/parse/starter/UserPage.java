package com.parse.starter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseUser;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class UserPage extends ActionBarActivity {
    ParseUser currentUser = ParseUser.getCurrentUser();
    TextView un, txt, gallery;
    ImageView userProfilePic, miniUserPic, userImages;
    // Uri img;
    ListView lv;
    List<ParseObject> ob;
    ArrayAdapter<String> adapter;
    ListAdapter listAdapter;
    ParseQuery<ParseObject> query, picQuery;
    ArrayList<Post> list;
    ProgressDialog pd;
    //  ParseFile file;
    //  photoFile photoFile = new photoFile(file);
    //ParseObject photoFile = new ParseObject("photoFile");
    byte[] userPic, userimg, image;

    private GridView gridView;
    Bitmap currPic;
    photoFile photo;
    Post post;

    //   ParseUser owner = photo.getOwner();

    //  private PhotoGallery gridAdapter;

    String[] objectIds;

    Post[] objectPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);
        un = (TextView) findViewById(R.id.profileUserId);
        un.setText(getIntent().getStringExtra("userName"));

        //  gallery = (TextView) findViewById(R.id.photoGallery);
        lv = (ListView) findViewById(R.id.userListView);
        pd = new ProgressDialog(UserPage.this);

        objectIds = null;
        objectPosts = null;


        picQuery = new ParseQuery<>("photoFile");
        query = new ParseQuery<>("Post");
        list = new ArrayList<>();


        //       un.setText(getIntent().getSerializableExtra("userName").toString());

        userProfilePic = (ImageView) findViewById(R.id.userProfilePic);
        miniUserPic = (ImageView) findViewById(R.id.miniUserPic);



        callProfilePic();
        userProfilePic.setImageBitmap(currPic);
        miniUserPic.setImageBitmap(currPic);



        //set onclick listener for userProfile pic
        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  System.out.print("clicked image");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);

            }

        });


        un.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(UserPage.this, MainActivity.class);
                intent.putExtra("userName", currentUser.getUsername().trim());
                startActivity(intent);
            }
        });

        miniUserPic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(UserPage.this, MainActivity.class);
                intent.putExtra("userName", currentUser.getUsername().trim()); //send the userName to MAinActivity
                // intent.putExtra("miniUserPic", );                //send the userProfilePic to MainActivity
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Post post1 = lv.getItemAtPosition(position);
                Intent intent = new Intent(UserPage.this, DecisionActivity.class);
                intent.putExtra("PostObject", (Post) lv.getItemAtPosition(position));
                intent.putExtra("objectId", ((Post) lv.getItemAtPosition(position)).getObjectId().toString());

                Log.d("object being Passed:", lv.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(UserPage.this)
                        .setTitle("Delete Your Post!!")
                        .setMessage("Are You Sure You Want To Delete Your Post?")
                        .setCancelable(true)
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        })
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Post) lv.getItemAtPosition(position)).deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null){
                                            Toast.makeText(UserPage.this,
                                                    "Successful Delete",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(getIntent());
                                        }else{
                                            Toast.makeText(UserPage.this,
                                                    "Cannot Delete",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).create().show();
                return true;
            }
        });
/**
 gallery.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
startActivity(new Intent(UserPage.this, Gallery.class));
// setContentView(R.layout.photo_gallery);
}
});
 **/
/**
 query.whereEqualTo("displayName", currentUser.getUsername());
 query.findInBackground(new FindCallback<ParseObject>() {
@Override
public void done(List<ParseObject> objects, ParseException e) {
if (e == null) {
Log.d("number of objects", objects.size() + "");
Post[] pst = new Post[objects.size()];
for (int i = 0; i < objects.size(); i++) {
Log.d("ParseObject:", objects.get(i).toString());
pst[i] = (Post) objects.get(i);
}
sendToAdpater(pst);
lv.setAdapter(listAdapter);
}
}
});
 **/
        queryPosts();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }



    public void queryPosts(){
        query.setLimit(20);
        query.whereEqualTo("displayName", currentUser.getUsername());
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
                    sendToAdapter(objectPosts);
                    lv.setAdapter(listAdapter);
                }
            }
        });



    }


    /* Result activity method for the User Profile pic
        this gets the data for the image stored on the user's device if
        the user DOES choose one and
        sets the chosen image
     */
    public void onActivityResult(int request,int result, Intent data){
        //check if change has been made
        if(result == RESULT_OK) {
            if (request == 1)
                userProfilePic.setImageURI(data.getData());
            miniUserPic.setImageURI(data.getData());

            makePhoto(userProfilePic);
        }

    }

    // This is the section where the images is converted, saved, and uploaded.
    public void makePhoto(ImageView userProfilePic) {

        photo = new photoFile();                            //create PhotoFile object
        //    post = new Post();
        // ParseUser currentUser = ParseUser.getCurrentUser();     //get the user
        photo.setOwner(currentUser.getCurrentUser());       //set this photo's owner as current user
        photo.setDisplayName(currentUser.getUsername());    //set this photo's display name

        //Bitmap the ImageView userProfilePic
        Bitmap bitmap = ((BitmapDrawable) userProfilePic.getDrawable()).getBitmap();


        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] userPic = stream.toByteArray();


        // Create the ParseFile passing the byte[] and the name of this specific image file
        ParseFile file = new ParseFile("UserPicture", userPic);

        //set the pic
        photo.setUserPicture(file);
        //  post.setUserPicture(file);


        //may need to check here for duplicates before saving again
        photo.saveInBackground();
        file.saveInBackground();
        currentUser.saveInBackground();
    }


    public Bitmap callProfilePic() {
        photo = new photoFile();
        ParseFile file = photo.getUserPicture();
        if(file != null) {
            byte[] userPic = new byte[0];
            try {
                userPic = file.getData();
                photo.setUserPicBmp(userPic);
                currPic = photo.getUserPicBmp();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
                   /*
                    Intent intent = new Intent(this, .class);
                    intent.putExtra("profilePicture", data);
                    startActivity(intent);
                   */


        return currPic;
    }



    public void sendToAdapter(Post[] array){

        pd = ProgressDialog.show(this, "dialog title",
                "dialog message", true);
        for (int i = 0; i < array.length; i++){
            Log.d("Object:", array[i].toString());
        }
        listAdapter = new CustomAdapter(this, array);
        pd.dismiss();
    }

    /**
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
     Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_icon);
     //Convert it to byte
     ByteArrayOutputStream stream = new ByteArrayOutputStream();
     //compress the image
     bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
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
     **/


/*
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new PhotoGallery(this, R.layout.photo_gallery_layout, getPic());
        gridView.setAdapter(gridAdapter);
        //appImageView = (ImageView) findViewById(R.id.imageView);
    }
    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getPic(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }
    @Override
    View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
    */


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent intent = new Intent(UserPage.this, MainActivity.class);
            intent.putExtra("userName", currentUser.getUsername().toString().trim());
            startActivity(intent);
            return true;
        }
        return false;
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

        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_refresh:
                finish();
                startActivity(getIntent());
                return true;

            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

}