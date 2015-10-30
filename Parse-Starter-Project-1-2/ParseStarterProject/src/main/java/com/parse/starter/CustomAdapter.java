package com.parse.starter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by devinyancey on 10/30/15.
 */
class CustomAdapter extends ArrayAdapter<Post> {

    CustomAdapter(Context context, Post[] posts){
        super(context, R.layout.the_post_layouts, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.the_post_layouts, parent, false);

        //get reference to everything
        Post postItem = getItem(position);

        TextView userName = (TextView) customView.findViewById(R.id.userName2);
        TextView userName3 = (TextView) customView.findViewById(R.id.userName3);
        TextView userDescription = (TextView) customView.findViewById(R.id.userDescription);
        TextView vote1 = (TextView) customView.findViewById(R.id.voteNumber1);
        TextView vote2 = (TextView) customView.findViewById(R.id.voteNumber2);

        ImageView userImage = (ImageView) customView.findViewById(R.id.userPicture);

        ImageView image1 = (ImageView) customView.findViewById(R.id.imageVote1);
        ImageView image2 = (ImageView) customView.findViewById(R.id.imageVote2);

        //set the custom view

        image1.setImageResource(R.mipmap.steph);
        image2.setImageResource(R.mipmap.steph);
        userImage.setImageResource(R.mipmap.steph);
        return customView;
    }
}
