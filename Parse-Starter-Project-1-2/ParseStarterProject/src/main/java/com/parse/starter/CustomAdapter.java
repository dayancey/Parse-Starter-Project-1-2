package com.parse.starter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devinyancey on 10/30/15.
 */
class CustomAdapter extends ArrayAdapter<String> {
    String[] posted;



    CustomAdapter(Context context, String[] posts){
        super(context, R.layout.the_post_layouts, posts);
        //query = new ParseQuery<ParseObject>("Post");
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.the_post_layouts, parent, false);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");;
        //get reference to everything
        String post = getItem(position);

        final TextView userName = (TextView) customView.findViewById(R.id.userName2);
        TextView userName3 = (TextView) customView.findViewById(R.id.userName3);
        TextView userDescription = (TextView) customView.findViewById(R.id.userDescription);
        TextView vote1 = (TextView) customView.findViewById(R.id.voteNumber1);
        TextView vote2 = (TextView) customView.findViewById(R.id.voteNumber2);

        ImageView userImage = (ImageView) customView.findViewById(R.id.userPicture);

        final ImageView image1 = (ImageView) customView.findViewById(R.id.imageVote1);
        final ImageView image2 = (ImageView) customView.findViewById(R.id.imageVote2);
//




        query.whereEqualTo("objectId", post);
        query.getFirstInBackground(new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject object, ParseException e) {

                userName.setText(((Post) object).getDisplayName());
                ParseFile parseFile = (ParseFile) object.get("Image1");
                parseFile.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                        image1.setImageBitmap(bitmap);
                        //test.setImages(bitmap, bitmap);

                        //img = (ImageView) findViewById(R.id.testView);
                        //img.setImageBitmap(test.getImg1());
                    }
                });
            }
        });





        //userName.setText(postItem.getUserName() + "");
        //Log.d("Display Name", postItem.getUserName()+"");
        //set the custom view
        //image1.setImageResource((Integer) postItem.get("Image1"));

        //byte[] a = postItem.getBytes("Image1");
        //Bitmap bitmap = BitmapFactory.decodeByteArray(a, 0, a.length);
        //image1.setImageBitmap(getItem(position).getImg1());

        //image1.setImageResource();
        //image2.setImageResource(R.mipmap.steph);
        //userImage.setImageResource(R.mipmap.steph);
        return customView;
    }


}
