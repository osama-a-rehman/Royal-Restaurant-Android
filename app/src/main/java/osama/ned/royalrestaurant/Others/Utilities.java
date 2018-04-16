package osama.ned.royalrestaurant.Others;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import osama.ned.royalrestaurant.R;

public final class Utilities {
    public static final String SHARED_PREFERENCES_FIRST_TIME = "first_time";

    public static final String MENU_CATEGORY_INDEX = "menu_category_index";

    public static final int BBQ_CATEGORY = 0;
    public static final int FAST_FOOD_CATEGORY = 1;
    public static final int CHINESE_CATEGORY = 2;
    public static final int SHAWARMA_CATEGORY = 3;
    public static final int FRENCH_FRIES_CATEGORY = 4;
    public static final int DRINKS_CATEGORY = 5;
    public static final int SOUPS_CATEGORY = 6;

    public static final int BBQ_CATEGORY_ID_START = 1;
    public static final int BBQ_CATEGORY_ID_END = 5;

    public static final int FAST_FOOD_CATEGORY_ID_START = 100;
    public static final int FAST_FOOD_CATEGORY_ID_END = 105;

    public static final int CHINESE_CATEGORY_ID_START = 200;
    public static final int CHINESE_CATEGORY_ID_END = 205;

    public static final int SHAWARMA_CATEGORY_ID_START = 300;
    public static final int SHAWARMA_CATEGORY_ID_END = 301;

    public static final int FRENCH_FRIES_CATEGORY_ID_START = 400;
    public static final int FRENCH_FRIES_CATEGORY_ID_END = 402;

    public static final int DRINKS_CATEGORY_ID_START = 500;
    public static final int DRINKS_CATEGORY_ID_END = 505;

    public static final int SOUP_CATEGORY_ID_START = 600;
    public static final int SOUP_CATEGORY_ID_END = 601;

    public static final int[][] idsList = new int[][]{
            {   1, 2, 3, 4, 5   },
            {   100, 101, 102, 103, 104, 105 },
            {   200, 201, 202, 203, 204, 205 },
            {   300, 301 },
            {   400, 401, 402 },
            {   500, 501, 502, 503, 504, 505 },
            {   600, 601 }
    };

    public static final int[][] thumbnailsList = new int[][]{
            {   R.drawable.bbq_category_chicken_tikka, R.drawable.bbq_category_beef_bihari_boti, R.drawable.bbq_category_seekh_kabab,
                    R.drawable.bbq_category_beef_roll, R.drawable.bbq_category_chicken_boti },

            {   R.drawable.fast_food_category_beef_burger, R.drawable.fast_food_category_beef_cheese_burger, R.drawable.fast_food_category_chicken_burger, R.drawable.fast_food_category_chicken_cheese_burger,
                    R.drawable.fast_food_category_chicken_sandwich, R.drawable.fast_food_category_chicken_broast},

            {   R.drawable.chinese_category_chicken_grilled_shashlik, R.drawable.chinese_category_chicken_ginger, R.drawable.chinese_category_chicken_jalfrezi,
                    R.drawable.chinese_category_chicken_manchurian, R.drawable.chinese_category_plain_rice, R.drawable.chinese_category_fish_and_chips },

            {   R.drawable.shawarma_category_roll, R.drawable.shawarma_category_plater},

            {   R.drawable.fries_category_small, R.drawable.fries_category_medium, R.drawable.fries_category_large},

            {   R.drawable.drinks_category_seven_up, R.drawable.drinks_category_coca_cola, R.drawable.drinks_category_dew,
                    R.drawable.drinks_category_fanta, R.drawable.drinks_category_pepsi, R.drawable.drinks_category_sprite },

            {   R.drawable.soup_category_chicken_corn, R.drawable.soup_category_hot_and_sour}
    };

    public static final int[][] namesList = new int[][]{
            {R.string.bbq_chicken_tikka, R.string.bbq_beef_bihari_boti, R.string.bbq_seekh_kabab,
                    R.string.bbq_beef_roll, R.string.bbq_chicken_boti},

            {R.string.fast_food_beef_burger, R.string.fast_food_beef_cheese_burger, R.string.fast_food_chicken_burger,
                    R.string.fast_food_chicken_cheese_burger, R.string.fast_food_chicken_sandwich, R.string.fast_food_chicken_broast },

            {R.string.chinese_chicken_grilled_shashlik, R.string.chinese_chicken_ginger, R.string.chinese_chicken_jalfrezi,
                    R.string.chinese_chicken_manchurian, R.string.chinese_plain_rice ,R.string.chinese_fish_and_chips },

            {R.string.shawarma_roll, R.string.shawarma_plater},

            {R.string.fries_small, R.string.fries_medium, R.string.fries_large},

            {R.string.drinks_seven_up, R.string.drinks_coca_cola, R.string.drinks_dew,
                    R.string.drinks_fanta, R.string.drinks_pepsi ,R.string.drinks_sprite },

            {R.string.soup_chicken_corn, R.string.soup_hot_and_sour}
    };

    public static final float[][] pricesList = new float[][]{
            {12.05f, 15.25f, 20.55f, 15.15f, 35.15f},

            {12.05f, 15.25f, 20.55f, 15.15f, 35.15f, 10.25f, 28.75f},

            {12.05f, 15.25f, 20.55f, 15.15f, 35.15f, 10.25f, 28.75f},

            {20.05f, 22.55f},

            {10.25f, 17.25f, 30.25f},

            {5.55f, 5.55f, 5.55f, 5.55f, 5.55f, 5.55f},

            {10.25f, 22.55f}
    };

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
