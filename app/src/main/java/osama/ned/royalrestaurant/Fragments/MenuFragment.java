package osama.ned.royalrestaurant.Fragments;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import osama.ned.royalrestaurant.Adapters.Menu_Category_Adapter;
import osama.ned.royalrestaurant.Others.Menu_Category;
import osama.ned.royalrestaurant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private Menu_Category_Adapter adapter;
    private List<Menu_Category> categoryList;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_menu, null);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.menuCategoryRecyclerView);

        categoryList = new ArrayList<>();
        adapter = new Menu_Category_Adapter(getContext(), categoryList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareCategoryList();

        return rootView;
    }

    private void prepareCategoryList() {
        int numOfCategories = 7;

        int[] thumbnails = new int[]{
                R.drawable.menu_category_bbq,
                R.drawable.menu_category_fast_food,
                R.drawable.menu_category_chinese,
                R.drawable.menu_category_shawarma,
                R.drawable.fries,
                R.drawable.menu_category_drinks,
                R.drawable.menu_category_soup};

        String[] titles = new String[]{getString(R.string.menu_category_bbq), getString(R.string.menu_category_fast_food), getString(R.string.menu_category_chinese),
                getString(R.string.menu_category_shawarma), getString(R.string.menu_category_fries), getString(R.string.menu_category_drinks),
                getString(R.string.menu_category_soup)};

        for(int i=0; i<numOfCategories; i++){
            categoryList.add(new Menu_Category(titles[i], thumbnails[i]));
        }

        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
