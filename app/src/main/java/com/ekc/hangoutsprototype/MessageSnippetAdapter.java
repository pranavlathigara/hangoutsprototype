package com.ekc.hangoutsprototype;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter for MessageSnippets, which are items for the RecyclerView inside MessageSets.
 * MessageSnippets are 3 row items which feature a Sender (From) and 2 line message snippet.
 * Trigger a transition onClick to open conversation window.
 */
public class MessageSnippetAdapter extends RecyclerView.Adapter<MessageSnippetAdapter.ViewHolder> {

    private static final String TAG = MessageSnippetAdapter.class.getSimpleName();
    private ArrayList<Message> mMessageArrayList;
    private LayoutInflater mLayoutInflater;
    private ActionBarActivity mContext;
    private FragmentManager mFragmentManager;

    public MessageSnippetAdapter(ArrayList<Message> messageArrayList) {
        mMessageArrayList = messageArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = (ActionBarActivity) viewGroup.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
//        Fragment f = mContext.getSupportFragmentManager()
//                .findFragmentByTag("messageSetFragment");
//        mFragmentManager = f.getChildFragmentManager();
        mFragmentManager = mContext.getSupportFragmentManager();
        View v = mLayoutInflater.inflate(R.layout.message_snippet,
                viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ViewHolder vh = viewHolder;
        final int pos = i;
        final Message message = mMessageArrayList.get(i);
        viewHolder.mFrom.setText(message.mFrom);
        viewHolder.mTimestamp.setText(message.mTimestamp);
        if (message.mText != null) {
            viewHolder.mText.setText(message.mText);
        }

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                vh.mText.setText(pos + "clicked");

                // pass in Y coordinate from view (for animation purposes)
                int[] coord = new int[2];
                v.getLocationOnScreen(coord);

                // launch chat window and add to back stack
                mFragmentManager.beginTransaction()
                        .replace(R.id.inner_container,
                                MessageFragment.newInstance(message, coord[1]))
                        .addToBackStack(null)
                        .commit();

            }
        });
        viewHolder.mImageView.setImageResource(message.mPicResourceId);
        Log.i(TAG, "bind view height " + viewHolder.mView.getLayoutParams().height);
    }

    @Override
    public int getItemCount() {
        return mMessageArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFrom;
        public TextView mText;
        public TextView mTimestamp;
        public View mView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mFrom = (TextView) v.findViewById(R.id.from);
            mTimestamp = (TextView) v.findViewById(R.id.timestamp);
            mText = (TextView) v.findViewById(R.id.text);
            mImageView = (ImageView) v.findViewById(R.id.person_image);
        }
    }
}
