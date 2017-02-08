package com.gbrf;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
@SuppressLint("NewApi")
public class SplashActivity extends Activity {
	/** Called when the activity is first created. */
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	   // getActionBar().hide();
        setContentView(R.layout.splash);
        startAnimating();
       // startService();
    }
   /**
    * Helper method to start the animation on the splash screen
    */
   private void startAnimating() {
       // Fade in top title
   /*    TextView logo1 = (TextView) findViewById(R.id.titlename);
       Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
       logo1.startAnimation(fade1);*/

       // Fade in bottom title after a built-in delay.
      TextView logo2 = (TextView) findViewById(R.id.titlenametwo);
       Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
       logo2.startAnimation(fade2);

       // Transition to Main Menu when bottom title finishes animating
       fade2.setAnimationListener(new AnimationListener() {

           public void onAnimationEnd(Animation animation) {
               // The animation has ended, transition to the Main Menu screen
               startActivity(new Intent(SplashActivity.this, LoginActivity.class));
               SplashActivity.this.finish();
               
           }

           public void onAnimationRepeat(Animation animation) {
           }

           public void onAnimationStart(Animation animation) {
           }
       });
   /*    ImageView image = (ImageView)findViewById(R.id.ImageView2_Left);
       Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise);
       image.startAnimation(animation);*/
       // Load animations for all views within the TableLayout
       Animation spinin = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
       LayoutAnimationController controller = new LayoutAnimationController(spinin);

       TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);
       for (int i = 0; i < table.getChildCount(); i++) {
           TableRow row = (TableRow) table.getChildAt(i);
           row.setLayoutAnimation(controller);
       }

   }

    }

    /**
     * Helper method to start the animation on the splash screen
     */


  