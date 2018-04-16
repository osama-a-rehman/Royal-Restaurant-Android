package osama.ned.royalrestaurant.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import osama.ned.royalrestaurant.Fragments.FavouritesFragment;
import osama.ned.royalrestaurant.Fragments.HomeFragment;
import osama.ned.royalrestaurant.Fragments.MenuFragment;
import osama.ned.royalrestaurant.Fragments.OrdersFragment;
import osama.ned.royalrestaurant.Fragments.ProfileFragment;
import osama.ned.royalrestaurant.Others.BadgeDrawable;
import osama.ned.royalrestaurant.Others.Cart_Item;
import osama.ned.royalrestaurant.Others.SQLiteHandler;
import osama.ned.royalrestaurant.Others.SessionManager;
import osama.ned.royalrestaurant.Others.User;
import osama.ned.royalrestaurant.Others.Utilities;
import osama.ned.royalrestaurant.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteHandler db;
    private SessionManager session;
    private int noOfItemsInCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Home");

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        List<User> loggedInUsers = db.getUserDetails();

        noOfItemsInCart = db.getAllCartItems().size();

        for(User loggedInUser : loggedInUsers){
            Log.i("Logged In User", loggedInUser.toString());
        }

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.mainFragmentsContainer, homeFragment);
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View navView = getLayoutInflater().inflate(R.layout.nav_header_main, null);

        ImageView profileImage = (ImageView) navView.findViewById(R.id.navProfileImage);

        Glide.with(profileImage.getContext()).load(R.drawable.demo_user).into(profileImage);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        noOfItemsInCart = db.getAllCartItems().size();

        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, String.valueOf(noOfItemsInCart));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle(getString(R.string.home_fragment));

            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.mainFragmentsContainer, homeFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_menu) {

            setTitle(getString(R.string.menu_fragment));

            MenuFragment menuFragment = new MenuFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.mainFragmentsContainer, menuFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_orders) {

            setTitle(getString(R.string.orders_fragment));

            OrdersFragment ordersFragment = new OrdersFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.mainFragmentsContainer, ordersFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_favourites) {

            setTitle(getString(R.string.favourites_fragment));

            FavouritesFragment favouritesFragment = new FavouritesFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.mainFragmentsContainer, favouritesFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_profile) {

            setTitle(getString(R.string.profile_fragment));

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.mainFragmentsContainer, profileFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_logout) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Sign Out!");
            alertDialogBuilder.setMessage("Do you really want to sign out ?");

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    logoutUser();
                }
            });

            alertDialogBuilder.setNegativeButton("No", null);

            alertDialogBuilder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser(){
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, SelectActivity.class);
        startActivity(intent);
        finish();
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
}
