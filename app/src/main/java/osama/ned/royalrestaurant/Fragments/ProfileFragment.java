package osama.ned.royalrestaurant.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import osama.ned.royalrestaurant.Others.SQLiteHandler;
import osama.ned.royalrestaurant.Others.User;
import osama.ned.royalrestaurant.R;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, null);

        TextView profileFragmentNameTextView = (TextView) rootView.findViewById(R.id.profileFragmentNameTextView);
        TextView profileFragmentEmailTextView = (TextView) rootView.findViewById(R.id.profileFragmentEmailTextView);
        TextView profileFragmentAddressTextView = (TextView) rootView.findViewById(R.id.profileFragmentAddressTextView);
        TextView profileFragmentPhoneTextView = (TextView) rootView.findViewById(R.id.profileFragmentPhoneTextView);

        SQLiteHandler db = new SQLiteHandler(getContext());

        List<User> loggedInUsers = db.getUserDetails();

        User currentUser = loggedInUsers.get(0);

        profileFragmentNameTextView.setText(currentUser.getName());
        profileFragmentEmailTextView.setText(currentUser.getEmail());
        profileFragmentAddressTextView.setText(currentUser.getAddress());
        profileFragmentPhoneTextView.setText(currentUser.getPhone());

        return rootView;
    }

}
