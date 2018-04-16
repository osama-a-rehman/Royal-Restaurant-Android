package osama.ned.royalrestaurant.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import osama.ned.royalrestaurant.Adapters.Sliding_Image_Adapter;
import osama.ned.royalrestaurant.R;
import osama.ned.royalrestaurant.Others.Utilities;

public class StartActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 3;
    private static final Integer[] IMAGES = {R.drawable.blurred_burger, R.drawable.blurred_burger1, R.drawable.blurred_burger2};

    private ArrayList<Integer> imagesArray = new ArrayList<>();
    private ArrayList<String> imagesTitlesArray = new ArrayList<>();
    private ArrayList<String> imagesTextArray = new ArrayList<>();
    private Button btnSkip, btnNext;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnSkip = (Button) findViewById(R.id.startActivityBtnSkip);
        btnNext = (Button) findViewById(R.id.startActivityBtnNext);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        if(preferences.getBoolean(Utilities.SHARED_PREFERENCES_FIRST_TIME, false)){
            Intent intent = new Intent(StartActivity.this, SelectActivity.class);
            startActivity(intent);

            StartActivity.this.finish();
        }else{
            init();
        }
    }

    private void init() {
        for(int i=0;i<IMAGES.length;i++){
            imagesArray.add(IMAGES[i]);
        }

        imagesTextArray.add(getString(R.string.start_text_1));
        imagesTextArray.add(getString(R.string.start_text_2));
        imagesTextArray.add(getString(R.string.start_text_3));

        imagesTitlesArray.add(getString(R.string.start_title_1));
        imagesTitlesArray.add(getString(R.string.start_title_2));
        imagesTitlesArray.add(getString(R.string.start_title_3));

        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setAdapter(new Sliding_Image_Adapter(StartActivity.this,imagesArray, imagesTextArray, imagesTitlesArray));

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean(Utilities.SHARED_PREFERENCES_FIRST_TIME, true);
                editor.apply();

                Intent intent = new Intent(StartActivity.this, SelectActivity.class);
                startActivity(intent);

                StartActivity.this.finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnNext.getText().toString().equals(getString(R.string.got_it))){
                    editor.putBoolean(Utilities.SHARED_PREFERENCES_FIRST_TIME, true);
                    editor.apply();

                    Intent intent = new Intent(StartActivity.this, SelectActivity.class);
                    startActivity(intent);

                    StartActivity.this.finish();
                }

                if(currentPage < NUM_PAGES-1){
                    currentPage++;
                    mPager.setCurrentItem(currentPage, true);

                    //Log.i("Page", String.valueOf(currentPage));
                }

                changeLookForLastPage(currentPage);
            }
        });

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

                changeLookForLastPage(currentPage);

                //Log.i("Page", String.valueOf(currentPage));
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void changeLookForLastPage(int currentPage){
        if(currentPage == NUM_PAGES - 1){
            btnSkip.setVisibility(View.GONE);
            btnNext.setText(getString(R.string.got_it));
        }else{
            btnSkip.setVisibility(View.VISIBLE);
            btnNext.setText(getString(R.string.button_next));
        }

    }
}
