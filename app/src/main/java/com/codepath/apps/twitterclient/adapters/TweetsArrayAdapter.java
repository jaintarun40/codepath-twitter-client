package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.activities.ProfileActivity;
import com.codepath.apps.twitterclient.activities.TimelineActivity;
import com.codepath.apps.twitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Random r = new Random();
        int trust = r.nextInt(6) + 4;

        int score = 0;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
            viewHolder.tvTweetFullName = (TextView) convertView.findViewById(R.id.tvTweetFullName);
            viewHolder.tvTweetUsername = (TextView) convertView.findViewById(R.id.tvTweetUsername);
            viewHolder.tvTweetAge = (TextView) convertView.findViewById(R.id.tvTweetAge);
            viewHolder.tvTweetBody = (TextView) convertView.findViewById(R.id.tvTweetBody);
            viewHolder.tvRetweets = (TextView) convertView.findViewById(R.id.tvRetweetCount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Tweet tweet = getItem(position);

        if(tweet.getRetweetCount() > 500) {
            score += 5;
        }

        if((tweet.getBody()).length() > 80) {
            score += 2;
        }


        viewHolder.tvTweetAge.setText(Integer.toString((score/7)*10));
        viewHolder.tvTweetBody.setText(tweet.getBody());
        viewHolder.tvRetweets.setText(String.valueOf(tweet.getRetweetCount()));

        User user = tweet.getUser();

        Picasso.with(getContext()).load(Uri.parse(user.getUserProfileImage())).into(viewHolder.ivAvatar);
        viewHolder.tvTweetFullName.setText(user.getUserName().toString());
        viewHolder.tvTweetUsername.setText("@" + user.getUserScreenName().toString());

        viewHolder.ivAvatar.setTag(user.getUserScreenName().toString());
        viewHolder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("username", v.getTag().toString());
                getContext().startActivity(i);
            }
        });

        return convertView;

    }

    private static class ViewHolder {
        ImageView ivAvatar;
        TextView tvTweetFullName;
        TextView tvTweetUsername;
        TextView tvTweetAge;
        TextView tvTweetBody;
        TextView tvRetweets;
    }
}
