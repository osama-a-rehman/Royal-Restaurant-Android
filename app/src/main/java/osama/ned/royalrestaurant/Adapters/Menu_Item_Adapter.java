package osama.ned.royalrestaurant.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;
import osama.ned.royalrestaurant.Others.Menu_Item;
import osama.ned.royalrestaurant.R;

public class Menu_Item_Adapter extends ArrayAdapter<Menu_Item> {

    public Menu_Item_Adapter(Context context, List<Menu_Item> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if(currentView == null){
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.menu_list_item, parent, false);
        }

        final Menu_Item menu_item = getItem(position);

        ImageView menuItemImageView = (ImageView) currentView.findViewById(R.id.menuItemImageView);

        int imageResource = menu_item.getImageResource();

        Glide.with(getContext()).load(imageResource).override(480, 320).into(menuItemImageView);

        TextView menuItemTitle = (TextView) currentView.findViewById(R.id.menuItemTitle);
        menuItemTitle.setText(menu_item.getName());

        TextView menuItemPrice = (TextView) currentView.findViewById(R.id.menuItemPrice);
        menuItemPrice.setText("$" + String.valueOf(menu_item.getPrice()));

        final ImageView menuItemHeartImageView = (ImageView) currentView.findViewById(R.id.menuItemHeartImageView);

        if(menu_item.isFavourite()){
            Glide.with(getContext()).load(R.drawable.ic_favorite_black_48dp).into(menuItemHeartImageView);
        }else{
            Glide.with(getContext()).load(R.drawable.ic_favorite_border_black_48dp).into(menuItemHeartImageView);
        }

        menuItemHeartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_item.setFavourite(!menu_item.isFavourite());

                if(menu_item.isFavourite()){
                    Glide.with(getContext()).load(R.drawable.ic_favorite_black_48dp).into(menuItemHeartImageView);
                }else{
                    Glide.with(getContext()).load(R.drawable.ic_favorite_border_black_48dp).into(menuItemHeartImageView);
                }
            }
        });

        final ImageView menuItemCartImageView = (ImageView) currentView.findViewById(R.id.menuItemCartImageView);

        if(menu_item.isInCart()){
            Glide.with(getContext()).load(R.drawable.ic_remove_shopping_cart_black_48dp).into(menuItemCartImageView);
        }else{
            Glide.with(getContext()).load(R.drawable.ic_add_shopping_cart_black_48dp).into(menuItemCartImageView);
        }

        menuItemCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                menu_item.setInCart(!menu_item.isInCart());

                if(menu_item.isInCart()){
                    Glide.with(getContext()).load(R.drawable.ic_remove_shopping_cart_black_48dp).into(menuItemCartImageView);
                }else{
                    Glide.with(getContext()).load(R.drawable.ic_add_shopping_cart_black_48dp).into(menuItemCartImageView);
                }
            }
        });

        return currentView;
    }
}
