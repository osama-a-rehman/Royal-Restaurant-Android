package osama.ned.royalrestaurant.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import osama.ned.royalrestaurant.Adapters.Cart_Item_Adapter;
import osama.ned.royalrestaurant.Adapters.Menu_Item_Adapter;
import osama.ned.royalrestaurant.Others.Cart_Item;
import osama.ned.royalrestaurant.Others.SQLiteHandler;
import osama.ned.royalrestaurant.R;

public class CartActivity extends AppCompatActivity {

    private SQLiteHandler db;
    public static String itemsString;
    public static String priceString;

    public static TextView totalCartItems;
    public static TextView totalCartPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Your Cart");

        db = new SQLiteHandler(this);

        List<Cart_Item> cart_itemList = db.getAllCartItems();

        //Toast.makeText(this, "Cart Items: " + cart_itemList.size(), Toast.LENGTH_LONG).show();

        /*for (Cart_Item cart_item : cart_itemList){
            Log.i("CART-ACTIVITY", cart_item.toString());
        }*/

        ListView cartListView = (ListView) findViewById(R.id.cartListView);

        Cart_Item_Adapter adapter = new Cart_Item_Adapter(this, cart_itemList);

        cartListView.setAdapter(adapter);

        totalCartItems = (TextView) findViewById(R.id.totalNumberOfItemsInCart);
        totalCartPrice = (TextView) findViewById(R.id.totalCartPrice);

        itemsString = String.valueOf(cart_itemList.size());

        totalCartItems.setText(itemsString);

        float price = 0f;

        for(Cart_Item cart_item : cart_itemList){
            price += cart_item.getPrice();
        }

        priceString = String.valueOf(((int)(price*100.0f))/100.0f);
        totalCartPrice.setText("$" + priceString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_cart_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_order:
                Toast.makeText(this, "Your request has been received and is under process", Toast.LENGTH_SHORT).show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
