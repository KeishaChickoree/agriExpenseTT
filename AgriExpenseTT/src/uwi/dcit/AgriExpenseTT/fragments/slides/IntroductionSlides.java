package uwi.dcit.AgriExpenseTT.fragments.slides;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import uwi.dcit.AgriExpenseTT.Main;
import uwi.dcit.AgriExpenseTT.R;

public class IntroductionSlides extends SlideKey {

    private static final int Num_pages = 3;
    int[] mResources = {
            R.drawable.introslide1,
            R.drawable.introslide2,
            R.drawable.introslide3,
            R.drawable.introslide4,
            R.drawable.introslide5,
            R.drawable.introslide6,
            R.drawable.cycledefinition,
    };
    //  private ViewPager mPager;

    //  @Override
    //  protected void onCreate(Bundle savedInstanceState) {
    //      super.onCreate(savedInstanceState);
    //      setContentView(R.layout.fragment_introtest);
    //      mPager = (ViewPager) findViewById(R.id.pager);
    //      PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(this);
    //      mPager.setAdapter(mPagerAdapter);
    //  }

    //   @Override
    //   public void onBackPressed(){
    //       if(mPager.getCurrentItem()==0){
    //           super.onBackPressed();
    //       }else{
    //          mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    //      }
    //  }


    //   @Override
    //   public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    //  getMenuInflater().inflate(R.menu.menu_introtest, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

}