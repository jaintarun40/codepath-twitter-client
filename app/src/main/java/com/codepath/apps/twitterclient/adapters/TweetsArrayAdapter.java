package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
            viewHolder.tvTweetFullName = (TextView) convertView.findViewById(R.id.tvTweetFullName);
            viewHolder.tvTweetUsername = (TextView) convertView.findViewById(R.id.tvTweetUsername);
            viewHolder.tvTweetAge = (TextView) convertView.findViewById(R.id.tvTweetAge);
            viewHolder.tvTweetBody = (TextView) convertView.findViewById(R.id.tvTweetBody);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tweet tweet = getItem(position);
        viewHolder.tvTweetAge.setText(tweet.getCreatedAt());
        viewHolder.tvTweetBody.setText(tweet.getBody());

        User user = tweet.getUser();
        Picasso.with(getContext()).load(Uri.parse(user.getUserProfileImage())).into(viewHolder.ivAvatar);
        viewHolder.tvTweetFullName.setText(user.getUserName().toString());
        viewHolder.tvTweetUsername.setText(user.getUserName().toString());

        return convertView;

    }

    private static class ViewHolder {
        ImageView ivAvatar;
        TextView tvTweetFullName;
        TextView tvTweetUsername;
        TextView tvTweetAge;
        TextView tvTweetBody;
    }
}
