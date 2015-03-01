package com.codepath.apps.twitterclient.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

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
    User user = new User();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        user.setId(pref.getString("id", null));
        user.setUserProfileImage(pref.getString("avatarURL", null));
        user.setUserName(pref.getString("name", null));
        user.setUserScreenName(pref.getString("username", null));

    }

    public void populateTimeline(String sinceId) {

        client.getUserTimeline(user.getUserScreenName(), sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response);
                ArrayList<Tweet> tweets = Tweet.fromJson(response, user);
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
