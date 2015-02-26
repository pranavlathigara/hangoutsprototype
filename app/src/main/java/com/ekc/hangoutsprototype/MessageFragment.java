package com.ekc.hangoutsprototype;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * MessageFragment hosts the individual conversation window after clicking on a snippet.
 * Ideally this should host a ViewPager such that user can swipe left/right to switch between
 * conversations.
 * TODO: couple MessageFragment with a ViewPager
 */
public class MessageFragment extends Fragment implements Animation.AnimationListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private static final String TAG = MessageFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PIC_RESOURCE_ID = "person_image";
    private static final String ARG_FROM = "from";
    private static final String ARG_TIMESTAMP = "timestamp";
    private static final String ARG_TEXT = "text";
    private static final String ARG_Y = "y_coord";

    // TODO: Rename and change types of parameters
    private int mPicResourceId;
    private String mFrom;
    private String mTimestamp;
    private String mText;

    private Bundle mArguments;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(Message message, int y) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PIC_RESOURCE_ID, message.mPicResourceId);
        args.putString(ARG_FROM, message.mFrom);
        args.putString(ARG_TEXT, message.mText);
        args.putString(ARG_TIMESTAMP, message.mTimestamp);
        args.putInt(ARG_Y, y);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArguments = getArguments();
        if (mArguments != null) {
            mPicResourceId = mArguments.getInt(ARG_PIC_RESOURCE_ID);
            mFrom = mArguments.getString(ARG_FROM);
            mTimestamp = mArguments.getString(ARG_TIMESTAMP);
            mText = mArguments.getString(ARG_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
//
////        List view
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.message_container);
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
        View rootView = inflater.inflate(R.layout.activity_message, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        FrameLayout root = (FrameLayout) view;
        Context context = view.getContext();
        // display the snippet by itself first when view is created
        View item = LayoutInflater.from(context).inflate(R.layout.message_snippet, root, false);

        int[] coord = new int[2];
        root.getLocationOnScreen(coord);
        bind(item);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) item.getLayoutParams();
        if (mArguments != null) {
            // get Y coordinate of view - Y coordinate of FrameLayout to get actual position on screen
            params.topMargin = mArguments.getInt(ARG_Y) - coord[1];
        }
        root.addView(item);
    }

    public void bind(View item) {
        ViewHolder vh = new ViewHolder(item);
        vh.mImageView.setImageResource(mPicResourceId);
        vh.mFrom.setText(mFrom);
        vh.mTimestamp.setText(mTimestamp);
        if (mText != null) {
            vh.mText.setText(mText);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(),
                enter ? android.R.anim.fade_in : android.R.anim.fade_out);

        animation.setDuration(50);

        if (animation != null) {
            animation.setAnimationListener(this);
        }

        return animation;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // don't need to do anything during start animation
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO: add conversation list demo w/ speech bubbles
        // TODO: add emoji / send button next to EditText
        // start transition on animation end
        final Scene scene = Scene.getSceneForLayout((ViewGroup) getView(),
                R.layout.message_snippet_transition,
                getActivity());
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(150);
        TransitionManager.go(scene, autoTransition);
        bind(scene.getSceneRoot());
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public static class ViewHolder {
        private ImageView mImageView;
        private TextView mFrom;
        private TextView mTimestamp;
        private TextView mText;

        public ViewHolder(View v) {
            mImageView = (ImageView) v.findViewById(R.id.person_image);
            mFrom = (TextView) v.findViewById(R.id.from);
            mTimestamp = (TextView) v.findViewById(R.id.timestamp);
            mText = (TextView) v.findViewById(R.id.text);
        }
    }
}
