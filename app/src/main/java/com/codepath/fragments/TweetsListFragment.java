package com.codepath.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.adapters.TweetsArrayAdapter;
import com.codepath.apps.twitterclient.helpers.EndlessScrollListener;
import com.codepath.apps.twitterclient.models.Tweet;

import java.util.ArrayList;

public abstract class TweetsListFragment extends Fragment {

    private TweetsArrayAdapter tweetAdapter;
    private SwipeRefreshLayout swipeContainer;


    public TweetsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(isNetworkAvailable()) {
            populateTimeline(null);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void populateTimeline(String sinceId);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);

        ListView lvTimeline = (ListView) v.findViewById(R.id.lvTimeline);
        tweetAdapter = new TweetsArrayAdapter(getActivity(), new ArrayList<Tweet>());

        lvTimeline.setAdapter(tweetAdapter);

        lvTimeline.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if(tweetAdapter.getCount() > 0) {
                    Tweet newestTweet = tweetAdapter.getItem(0);
                    populateTimeline(newestTweet.getId());
                } else {
                    populateTimeline(null);
                }
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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

    protected void stopRefreshing() {
        swipeContainer.setRefreshing(false);
    }

}
