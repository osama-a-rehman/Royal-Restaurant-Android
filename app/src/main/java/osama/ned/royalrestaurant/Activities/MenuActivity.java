package osama.ned.royalrestaurant.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import osama.ned.royalrestaurant.Adapters.Menu_Category_Adapter;
import osama.ned.royalrestaurant.Adapters.Menu_Item_Adapter;
import osama.ned.royalrestaurant.Others.Cart_Item;
import osama.ned.royalrestaurant.Others.Menu_Item;
import osama.ned.royalrestaurant.Others.SQLiteHandler;
import osama.ned.royalrestaurant.R;
import osama.ned.royalrestaurant.Others.Utilities;

public class MenuActivity extends AppCompatActivity {

    private List<Menu_Item> menu_itemList;
    private List<Cart_Item> cart_itemList;
    private SQLiteHandler db;
    private int userId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        position = intent.getIntExtra(Utilities.MENU_CATEGORY_INDEX, -1);

        if(position != -1){
            setTitle(Menu_Category_Adapter.categoryList.get(position).getName());

            db = new SQLiteHandler(this);

            if(db.getUserDetails().size() > 0){
                userId = db.getUserDetails().get(0).getId();
            }

            //Toast.makeText(this, "User Id:" + userId, Toast.LENGTH_SHORT).show();

            ListView menuListView = (ListView) findViewById(R.id.menuListView);

            menu_itemList = new ArrayList<>();
            cart_itemList = db.getAllCartItems();

            int[] inCartItemIds = new int[6];

            int dex = 0;

            switch (position){
                case Utilities.BBQ_CATEGORY:
                    for (Cart_Item cart_item : cart_itemList){
                        if(cart_item.getId() >= Utilities.BBQ_CATEGORY_ID_START && cart_item.getId() <= Utilities.BBQ_CATEGORY_ID_END){
                            inCartItemIds[dex++] = cart_item.getId();
                        }
                    }

                    break;

                case Utilities.FAST_FOOD_CATEGORY:
                    for (Cart_Item cart_item : cart_itemList){
                        if(cart_item.getId() >= Utilities.FAST_FOOD_CATEGORY_ID_START && cart_item.getId() <= Utilities.FAST_FOOD_CATEGORY_ID_END){
                            inCartItemIds[dex++] = cart_item.getId();
                        }
                    }

                    break;

                case Utilities.CHINESE_CATEGORY:
                    for (Cart_Item cart_item : cart_itemList){
                        if(cart_item.getId() >= Utilities.CHINESE_CATEGORY_ID_START && cart_item.getId() <= Utilities.CHINESE_CATEGORY_ID_END){
                            inCartItemIds[dex++] = cart_item.getId();
                        }
                    }

                    break;

                case Utilities.SHAWARMA_CATEGORY:
                    for (Cart_Item cart_item : cart_itemList){
                        if(cart_item.getId() >= Utilities.SHAWARMA_CATEGORY_ID_START && cart_item.getId() <= Utilities.SHAWARMA_CATEGORY_ID_END){
                            inCartItemIds[dex++] = cart_item.getId();
                        }
                    }

                    break;

                case Utilities.FRENCH_FRIES_CATEGORY:
                    for (Cart_Item cart_item : cart_itemList){
                        if(cart_item.getId() >= Utilities.FRENCH_FRIES_CATEGORY_ID_START && cart_item.getId() <= Utilities.FRENCH_FRIES_CATEGORY_ID_END){
                            inCartItemIds[dex++] = cart_item.getId();
                        }
                    }

                    break;

                case Utilities.DRINKS_CATEGORY:
                    for (Cart_Item cart_item : cart_itemList){
                        if(cart_item.getId() >= Utilities.DRINKS_CATEGORY_ID_START && cart_item.getId() <= Utilities.DRINKS_CATEGORY_ID_END){
                            inCartItemIds[dex++] = cart_item.getId();
                        }
                    }

                    break;

                case Utilities.SOUPS_CATEGORY:
                    for (Cart_Item cart_item : cart_itemList){
                        if(cart_item.getId() >= Utilities.SOUP_CATEGORY_ID_START && cart_item.getId() <= Utilities.SOUP_CATEGORY_ID_END){
                            inCartItemIds[dex++] = cart_item.getId();
                        }
                    }

                    break;
            }

            dex = 0;
            boolean isInCart = false, isFavourite = false;

            int[] selectedMenuListIds = Utilities.idsList[position];
            int[] selectedMenuListThumbnails = Utilities.thumbnailsList[position];
            int[] selectedMenuListNames = Utilities.namesList[position];
            float[] selectedMenuListPrices = Utilities.pricesList[position];

            if(selectedMenuListNames.length < 1){
                Toast.makeText(this, "No Items here", Toast.LENGTH_SHORT).show();
            }else{
                for(int i=0; i<selectedMenuListNames.length; i++){

                    if(selectedMenuListIds.length > 0){
                        if(selectedMenuListIds[i] == inCartItemIds[dex]){
                            isInCart = true;
                            dex++;
                        }
                    }

                    menu_itemList.add(new Menu_Item(selectedMenuListIds[i], selectedMenuListThumbnails[i], getString(selectedMenuListNames[i]), selectedMenuListPrices[i], isInCart, false));
                    isInCart = false;
                }

                Menu_Item_Adapter adapter = new Menu_Item_Adapter(this, menu_itemList);

                menuListView.setAdapter(adapter);

                Toast.makeText(this, "Touch on Cart or Heart to add to cart or favourites", Toast.LENGTH_SHORT).show();
            }
        }else{
            onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        modifyDatabase();
    }

    public void modifyDatabase(){
        Log.i("POSITION NNNN", String.valueOf(position));

        db.removeWholeCart(position);

        for(Menu_Item menu_item : menu_itemList){
            if(menu_item.isInCart()){
                db.addToCart(new Cart_Item(menu_item, userId));

                Log.i("MENU", menu_item.toString());
            }
        }
    }
}
