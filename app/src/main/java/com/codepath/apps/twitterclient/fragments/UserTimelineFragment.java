package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;

import com.codepath.apps.twitterclient.TwitterApplication;
import com.codepath.apps.twitterclient.helpers.TwitterClient;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserTimelineFragment extends TweetsListFragment {

    TwitterClient client = TwitterApplication.getRestClient();
    String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments().getString("user");
    }

    public static UserTimelineFragment newInstance(String username) {
        UserTimelineFragment f = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("user", username);
        f.setArguments(args);
        return f;
    }

    public void populateTimeline(String sinceId) {

        client.getUserTimeline(username, sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response);
                ArrayList<Tweet> tweets = Tweet.fromJson(response);
                addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("user timeline failed");
                System.out.println(errorResponse);
                stopRefreshing();
            }
        });
    }
}
