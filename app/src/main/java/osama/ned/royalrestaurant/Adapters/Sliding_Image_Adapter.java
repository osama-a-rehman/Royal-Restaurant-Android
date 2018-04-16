package osama.ned.royalrestaurant.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;
import osama.ned.royalrestaurant.R;


public class Sliding_Image_Adapter extends PagerAdapter {

    private ArrayList<Integer> slidingImages;
    private ArrayList<String> imageTexts;
    private ArrayList<String> imageTitles;
    private LayoutInflater layoutInflater;
    private Context context;

    public Sliding_Image_Adapter(Context context, ArrayList<Integer> slidingImages, ArrayList<String> imageTexts, ArrayList<String> imageTitles) {
        this.context = context;
        this.slidingImages=slidingImages;
        layoutInflater = LayoutInflater.from(context);
        this.imageTexts = imageTexts;
        this.imageTitles = imageTitles;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return slidingImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = layoutInflater.inflate(R.layout.start_activity_sliding_image_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.startActivitySlidingImageView);
        final TextView titletextView = (TextView) imageLayout.findViewById(R.id.startActivitySlidingTitleTextView);
        final TextView textView = (TextView) imageLayout.findViewById(R.id.startActivitySlidingTextView);

        /*imageView.post(new Runnable() {
            @Override
            public void run() {
                Blurry.with(context)
                        .radius(25)
                        .sampling(6)
                        .async()
                        .animate(500)
                        .onto(relativeLayout);
            }
        });*/

        Glide.with(context).load(slidingImages.get(position)).into(imageView);

        titletextView.setText(imageTitles.get(position));

        textView.setText(imageTexts.get(position));

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
