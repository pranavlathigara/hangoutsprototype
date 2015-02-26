package com.ekc.hangoutsprototype;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapter class for MessageSet RecyclerViews. Each MessageSet also hosts another set of
 * RecyclerViews (I heard you like to recycle while you recycle...). Custom LinearLayoutManager
 * needed because nested RecyclerViews will collapse the child views (similar to nested ListViews).
 */
public class MessageSetAdapter extends RecyclerView.Adapter<MessageSetAdapter.ViewHolder> {
    private static final String TAG = MessageSetAdapter.class.getSimpleName();
    private static final String[] DATES = Message.DATES;
    private static final HashMap<String, ArrayList<Message>> MAP = new HashMap<>();

    private RecyclerView.LayoutManager mLayoutManager;

    public MessageSetAdapter() {
        for (String d : DATES) {
            MAP.put(d, new ArrayList<Message>());
        }

        for (Message m : Message.MESSAGES) {
            MAP.get(m.mDate).add(m);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        mLayoutManager = new LinearLayoutManager(viewGroup.getContext());

        mLayoutManager = new ChildLinearLayoutManager(viewGroup.getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_set,
                viewGroup, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String date = DATES[i];
        MessageSnippetAdapter messageSnippetAdapter = new MessageSnippetAdapter(MAP.get(date));
        viewHolder.mDateTextView.setText(date);
        viewHolder.mMessageRecyclerView.setLayoutManager(mLayoutManager);
        viewHolder.mMessageRecyclerView.setAdapter(messageSnippetAdapter);
    }

    @Override
    public int getItemCount() {
        return DATES.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mDateTextView;
        public RecyclerView mMessageRecyclerView;
        public CardView mCardView;
        public ViewHolder(View v) {
            super(v);
            mDateTextView = (TextView) v.findViewById(R.id.date_text_view);
            mMessageRecyclerView = (RecyclerView) v.findViewById(R.id.message_container);
            mCardView = (CardView) v.findViewById(R.id.card_container);
        }
    }
}
