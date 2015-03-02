package com.codepath.apps.twitterclient.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.activities.ProfileActivity;
import com.codepath.apps.twitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitterclient.helpers.EndlessScrollListener;
import com.codepath.apps.twitterclient.models.Tweet;

import java.util.ArrayList;

public abstract class TweetsListFragment extends Fragment {

    TweetsArrayAdapter tweetAdapter;
    SwipeRefreshLayout swipeContainer;
    ListView lvTimeline;

    public TweetsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isNetworkAvailable()) {
            populateTimeline(null);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void populateTimeline(String maxId);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);

        lvTimeline = (ListView) v.findViewById(R.id.lvTimeline);
        tweetAdapter = new TweetsArrayAdapter(getActivity(), new ArrayList<Tweet>());

        lvTimeline.setAdapter(tweetAdapter);

        lvTimeline.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                System.out.println("tweet adapter: " + tweetAdapter.getCount());
                if (tweetAdapter.getCount() > 0) {
                    Tweet oldest = tweetAdapter.getItem(tweetAdapter.getCount()-1);
                    populateTimeline(oldest.getId());
                } else {
                    populateTimeline(null);
                }
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tweetAdapter.clear();
                tweetAdapter.notifyDataSetChanged();
                populateTimeline(null);
            }
        });

        return v;
    }

    protected Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void add(Tweet tweet) {
        tweetAdapter.insert(tweet, 0);
        tweetAdapter.notifyDataSetChanged();
    }

    protected void addAll(ArrayList<Tweet> tweets) {
        tweetAdapter.addAll(tweets);
        tweetAdapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);
    }

}
