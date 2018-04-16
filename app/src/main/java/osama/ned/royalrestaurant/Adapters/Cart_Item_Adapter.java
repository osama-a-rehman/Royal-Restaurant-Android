package osama.ned.royalrestaurant.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import osama.ned.royalrestaurant.Activities.CartActivity;
import osama.ned.royalrestaurant.Others.Cart_Item;
import osama.ned.royalrestaurant.R;

public class Cart_Item_Adapter extends ArrayAdapter<Cart_Item> {

    public Cart_Item_Adapter(Context context, List<Cart_Item> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if(currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.cart_list_item, parent, false);
        }

        final Cart_Item cart_item = getItem(position);

        ImageView cartItemImageView = (ImageView) currentView.findViewById(R.id.cartItemImageView);

        int imageResource = cart_item.getImageResource();

        Glide.with(getContext()).load(imageResource).override(480, 320).into(cartItemImageView);

        TextView cartItemTitle = (TextView) currentView.findViewById(R.id.cartItemTitle);
        cartItemTitle.setText(cart_item.getName());

        TextView cartItemPrice = (TextView) currentView.findViewById(R.id.cartItemPrice);
        cartItemPrice.setText("$" + String.valueOf(cart_item.getPrice()));

        final TextView numberOfCartItems = (TextView) currentView.findViewById(R.id.numberOfItem);

        numberOfCartItems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CartActivity.totalCartItems.setText(CartActivity.itemsString);
                CartActivity.totalCartPrice.setText("$" + CartActivity.priceString);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final Button[] btnIncreaseNumber = {(Button) currentView.findViewById(R.id.increaseNumberofItem)};
        Button btnDecreaseNumber = (Button) currentView.findViewById(R.id.decreaseNumberofItem);

        final int[] number = {1};

        btnIncreaseNumber[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActivity.itemsString = String.valueOf(Integer.parseInt(CartActivity.itemsString) + 1);

                float newPrice = Float.parseFloat(CartActivity.priceString) + cart_item.getPrice();

                CartActivity.priceString = String.valueOf(((int)(newPrice*100.0f))/100.0f);

                numberOfCartItems.setText(String.valueOf(++number[0]));
            }
        });

        btnDecreaseNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number[0] > 1){
                    CartActivity.itemsString = String.valueOf(Integer.parseInt(CartActivity.itemsString) - 1);

                    float newPrice = Float.parseFloat(CartActivity.priceString) - cart_item.getPrice();

                    CartActivity.priceString = String.valueOf(((int)(newPrice*100.0f))/100.0f);

                    numberOfCartItems.setText(String.valueOf(--number[0]));
                }
            }
        });

        return currentView;
    }
}
