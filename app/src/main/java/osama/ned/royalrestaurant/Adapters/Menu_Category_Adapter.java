package osama.ned.royalrestaurant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import osama.ned.royalrestaurant.Activities.MenuActivity;
import osama.ned.royalrestaurant.Others.Menu_Category;
import osama.ned.royalrestaurant.R;
import osama.ned.royalrestaurant.Others.Utilities;


public class Menu_Category_Adapter extends RecyclerView.Adapter<Menu_Category_Adapter.MyViewHolder> {
    private Context mContext;
    public static List<Menu_Category> categoryList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.menuCategoryTitle);
            thumbnail = (ImageView) view.findViewById(R.id.menuCategoryThumbnail);
        }
    }

    public Menu_Category_Adapter(Context mContext, List<Menu_Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_category_card_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Menu_Category menuCategory = categoryList.get(position);

        holder.title.setText(menuCategory.getName());

        Glide.with(mContext).load(menuCategory.getThumbnail()).override(600, 200).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MenuActivity.class);
                intent.putExtra(Utilities.MENU_CATEGORY_INDEX, position);

                mContext.startActivity(intent);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MenuActivity.class);
                intent.putExtra(Utilities.MENU_CATEGORY_INDEX, position);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
