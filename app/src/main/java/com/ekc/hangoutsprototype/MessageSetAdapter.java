package com.ekc.hangoutsprototype;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private Context mContext;
    private Resources mResources;
    private float mAvatarWidth;


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
        mContext = viewGroup.getContext();
        mResources = mContext.getResources();
        mAvatarWidth = mResources.getDimension(R.dimen.avatarWidth);
        View v = LayoutInflater.from(mContext).inflate(R.layout.message_set,
                viewGroup, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        String date = DATES[i];
        MessageSnippetAdapter messageSnippetAdapter = new MessageSnippetAdapter(MAP.get(date));
        viewHolder.mDateTextView.setText(date);
        viewHolder.mMessageRecyclerView.setLayoutManager(mLayoutManager);
        viewHolder.mMessageRecyclerView.setAdapter(messageSnippetAdapter);
        viewHolder.mMessageRecyclerView.setHasFixedSize(true);

        // Add row separators
        final Paint paint = new Paint();
        paint.setColor(mResources.getColor(R.color.divider));
        viewHolder.mMessageRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                int childCount = parent.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    float y = child.getHeight() + child.getY();
                    c.drawLine(child.getX() + mAvatarWidth,
                            y,
                            child.getWidth(),
                            y,
                            paint);
                }
            }
        });
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
