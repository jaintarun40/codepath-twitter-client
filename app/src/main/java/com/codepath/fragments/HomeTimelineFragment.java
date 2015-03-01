package com.codepath.fragments;

import android.os.Bundle;
import android.widget.Toast;

import com.codepath.apps.twitterclient.TwitterApplication;
import com.codepath.apps.twitterclient.helpers.TwitterClient;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class HomeTimelineFragment extends TweetsList {

    private TwitterClient client = TwitterApplication.getRestClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isNetworkAvailable()) {
            populateTimeline(null   );
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT);
        }
    }

    public void populateTimeline(String sinceId) {
        client.getHomeTimeline(null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<Tweet> tweets = Tweet.fromJson(response);
                addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
                super.onFailure(statusCode, headers, throwable, errorResponse);
                stopRefreshing();
            }
        });
    }
}
