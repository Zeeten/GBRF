package com.gbrf;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Admin on 14-08-2015.
 */
@SuppressLint("NewApi")
public class Fragment1 extends Fragment {


    private Animator mCurrentAnimator;

    /**
     * The system "short" animation time duration, in milliseconds. This duration is ideal for
     * subtle animations or animations that occur very frequently.
     */
    private int mShortAnimationDuration;
    
    String image,thumbnailimage;

    private ImageView expandedImageView;
    private  RelativeLayout linearLayout;
    ImageLoader imageLoader = new ImageLoader(getActivity());
  //  private ImageView KissmatInternational;
    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if(container==null){
            return null;
        }
       /* Typeface Tahoma = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Tahoma.ttf");
        FirstTimeWorld=(TextView)findViewById(R.id.FirstTimeWorld);
        FirstTimeWorld.setTypeface(Tahoma);*/
         /*
        *
       TextView txt = (TextView) v.findViewById(R.id.Zipcode);
       Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/customfont.ttf");
       txt.setTypeface(font);
       return v;
        * */
        /*return (LinearLayout) inflater.inflate(R.layout.fragment1_layout,container,false);*/
        View v = (RelativeLayout) inflater.inflate(R.layout.fragment1_layout, container, false);
         expandedImageView = (ImageView)v.findViewById(R.id.expanded_image);
      //   KissmatInternational = (ImageView)v.findViewById(R.id.KissmatInternational);
         linearLayout=(RelativeLayout)v.findViewById(R.id.container);
        TextView txt1 = (TextView) v.findViewById(R.id.FirstTimeWorld);
      //  Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Lt.ttf");
     //   txt1.setTypeface(font1);

        TextView txt2 = (TextView) v.findViewById(R.id.UniqueInvitation);
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt2.setTypeface(font2);

        TextView txt3 = (TextView) v.findViewById(R.id.CordiallyInvited);
        Typeface font3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Lt.ttf");
        txt3.setTypeface(font3);

        TextView txt4 = (TextView) v.findViewById(R.id.GuestHonour);
        Typeface font4 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Segoe_Script_light.ttf");
        txt4.setTypeface(font4);

        TextView txt5 = (TextView) v.findViewById(R.id.ReleaseBook);
        Typeface font5 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Lt.ttf");
        txt5.setTypeface(font5);
        Intent i = getActivity().getIntent();
       // image = preferences.getString("thumbnailimage", "Test");
       // thumbnailimage = preferences.getString("thumbnailimage", "Test");
        image = i.getStringExtra("thumbnailimage");
        thumbnailimage = i.getStringExtra("thumbnailimage");
        ImageView txtimage = (ImageView) v.findViewById(R.id.imageView);
        imageLoader.DisplayImage(image, txtimage);
     //   imageLoader.DisplayImage(thumbnailimage, expandedImageView);

        //TextView txt6 = (TextView) v.findViewById(R.id.By);
       // Typeface font6 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Lt.ttf");
       // txt6.setTypeface(font6);

      /*  TextView txt7 = (TextView) v.findViewById(R.id.KissmatInternational);
        Typeface font7 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Aller_Lt.ttf");
        txt7.setTypeface(font7);*/
        
     /*   KissmatInternational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kissmatinternational.com/"));
            	startActivity(browserIntent);
            }
        });*/
        final View thumb2View = v.findViewById(R.id.imageView);
        thumb2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb2View, thumbnailimage);
            }
        });
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);


        return v;
    }
    private void zoomImageFromThumb(final View thumbView, String imageResId) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        // Load the high-resolution "zoomed-in" image.

       // expandedImageView.setImageResource(imageResId);
        imageLoader.DisplayImage(thumbnailimage, expandedImageView);
        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        linearLayout.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel, back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}
