package osama.ned.royalrestaurant.Others;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 14;

    // Database Name
    private static final String DATABASE_NAME = "royal_restaurant";

    // Login table name
    private static final String TABLE_USER = "users";
    private static final String TABLE_CART = "cart";

    // Login Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";

    public static final String KEY_CART_ORDER_ID = "id";
    public static final String KEY_CART_ORDER_IMAGE = "image";
    public static final String KEY_CART_ORDER_NAME = "name";
    public static final String KEY_CART_ORDER_PRICE = "price";
    public static final String KEY_USER_ID = "userId";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT,"
                + KEY_ADDRESS + " TEXT, " + KEY_PHONE + " TEXT)";
        db.execSQL(CREATE_LOGIN_TABLE);

        CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + KEY_CART_ORDER_ID + " INTEGER," + KEY_CART_ORDER_IMAGE + " TEXT,"
                + KEY_CART_ORDER_NAME + " TEXT," + KEY_CART_ORDER_PRICE + " TEXT,"
                + KEY_USER_ID + " INTEGER)";

        db.execSQL(CREATE_LOGIN_TABLE);

        //Log.i(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getId());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_ADDRESS, user.getAddress());
        values.put(KEY_PHONE, user.getPhone());

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

//        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public List<User> getUserDetails() {
        //HashMap<String, String> user = new HashMap<String, String>();
        List<User> usersList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            usersList.add(new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + usersList.toString());

        return usersList;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

//        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void addToCart(Cart_Item cart_item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CART_ORDER_ID, cart_item.getId());
        values.put(KEY_CART_ORDER_IMAGE, cart_item.getImageResource());
        values.put(KEY_CART_ORDER_NAME, cart_item.getName());
        values.put(KEY_CART_ORDER_PRICE, cart_item.getPrice());
        values.put(KEY_USER_ID, cart_item.getUserId());

        // Inserting Row
        long id = db.insert(TABLE_CART, null, values);
        db.close(); // Closing database connection

//        Log.d(TAG, "New Cart Item inserted into Sqlite: " + id);
    }

    public void removeWholeCart(int category){
        String userId = String.valueOf(getUserDetails().get(0).getId());

        SQLiteDatabase db = this.getWritableDatabase();

        String query;

        switch (category){
            case Utilities.BBQ_CATEGORY:
                query = "DELETE FROM " + TABLE_CART + " WHERE " + KEY_CART_ORDER_ID + ">=" + Utilities.BBQ_CATEGORY_ID_START + " AND " + KEY_CART_ORDER_ID + "<=" + Utilities.BBQ_CATEGORY_ID_END +
                                " AND " + KEY_USER_ID + "=" + userId;

                db.execSQL(query);

                break;

            case Utilities.FAST_FOOD_CATEGORY:
                query = "DELETE FROM " + TABLE_CART + " WHERE " + KEY_CART_ORDER_ID + ">=" + Utilities.FAST_FOOD_CATEGORY_ID_START + " AND " + KEY_CART_ORDER_ID + "<=" + Utilities.FAST_FOOD_CATEGORY_ID_END +
                        " AND " + KEY_USER_ID + "=" + userId;

                db.execSQL(query);

                break;

            case Utilities.CHINESE_CATEGORY:
                query = "DELETE FROM " + TABLE_CART + " WHERE " + KEY_CART_ORDER_ID + ">=" + Utilities.CHINESE_CATEGORY_ID_START + " AND " + KEY_CART_ORDER_ID + "<=" + Utilities.CHINESE_CATEGORY_ID_END +
                        " AND " + KEY_USER_ID + "=" + userId;

                db.execSQL(query);

                break;

            case Utilities.SHAWARMA_CATEGORY:
                query = "DELETE FROM " + TABLE_CART + " WHERE " + KEY_CART_ORDER_ID + ">=" + Utilities.SHAWARMA_CATEGORY_ID_START + " AND " + KEY_CART_ORDER_ID + "<=" + Utilities.SHAWARMA_CATEGORY_ID_END +
                        " AND " + KEY_USER_ID + "=" + userId;

                db.execSQL(query);

                break;

            case Utilities.FRENCH_FRIES_CATEGORY:
                query = "DELETE FROM " + TABLE_CART + " WHERE " + KEY_CART_ORDER_ID + ">=" + Utilities.FRENCH_FRIES_CATEGORY_ID_START + " AND " + KEY_CART_ORDER_ID + "<=" + Utilities.FRENCH_FRIES_CATEGORY_ID_END +
                        " AND " + KEY_USER_ID + "=" + userId;

                db.execSQL(query);

                break;

            case Utilities.DRINKS_CATEGORY:
                query = "DELETE FROM " + TABLE_CART + " WHERE " + KEY_CART_ORDER_ID + ">=" + Utilities.DRINKS_CATEGORY_ID_START + " AND " + KEY_CART_ORDER_ID + "<=" + Utilities.DRINKS_CATEGORY_ID_END +
                        " AND " + KEY_USER_ID + "=" + userId;

                db.execSQL(query);

                break;

            case Utilities.SOUPS_CATEGORY:
                query = "DELETE FROM " + TABLE_CART + " WHERE " + KEY_CART_ORDER_ID + ">=" + Utilities.SOUP_CATEGORY_ID_START + " AND " + KEY_CART_ORDER_ID + "<=" + Utilities.SOUP_CATEGORY_ID_END +
                        " AND " + KEY_USER_ID + "=" + userId;

                db.execSQL(query);

                break;
        }

        db.close();

//        Log.d(TAG, "Deleted all cart items from sqlite");
    }

    public List<Cart_Item> getAllCartItems() {
        List<Cart_Item> cartList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CART + " WHERE " + KEY_USER_ID + " = " + getUserDetails().get(0).getId();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{
                Menu_Item menu_item = new Menu_Item(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),cursor.getString(2),Float.parseFloat(cursor.getString(3)), true, false);
                cartList.add(new Cart_Item(menu_item, Integer.parseInt(cursor.getString(4))));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching cart items from Sqlite: " + cartList.toString());

        return cartList;
    }
}
