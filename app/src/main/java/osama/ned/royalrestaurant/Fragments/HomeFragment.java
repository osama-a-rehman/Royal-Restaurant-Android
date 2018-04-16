package osama.ned.royalrestaurant.Fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import osama.ned.royalrestaurant.R;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, null);

        ImageView mainImage = (ImageView) rootView.findViewById(R.id.homeFragmentMainImage);

        Glide.with(getContext()).load(R.drawable.burger1).into(mainImage);

        return rootView;
    }

}
