package osama.ned.royalrestaurant.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import osama.ned.royalrestaurant.App.AppController;
import osama.ned.royalrestaurant.Others.Database_Utilities;
import osama.ned.royalrestaurant.Others.Login_Register_AsynTask;
import osama.ned.royalrestaurant.Others.SQLiteHandler;
import osama.ned.royalrestaurant.Others.SessionManager;
import osama.ned.royalrestaurant.Others.User;
import osama.ned.royalrestaurant.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = SignInActivity.class.getSimpleName();
    private EditText emaiLEditText, passwordEditText;
    private String signInEmail, signInPassword;
    private SessionManager sessionManager;
    private ProgressDialog pDialog;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emaiLEditText = (EditText) findViewById(R.id.loginUserNameEditText);
        passwordEditText= (EditText) findViewById(R.id.loginPasswordEditText);

        Button btnSignIn = (Button) findViewById(R.id.btnProcessSignIn);
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(this);
        sessionManager = new SessionManager(this);

        // Check if user is already logged in or not
        if (sessionManager.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProcessSignIn:
                boolean isSuccessful = validateInformation();

                if(isSuccessful){
                    if(checkInternetConnection()){
                        signInUser();
                    }else {
                        Toast.makeText(SignInActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }

                }

                break;

            case R.id.btnSignUp:
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
                break;
        }
    }

    private boolean validateInformation() {
        signInEmail = emaiLEditText.getText().toString();
        signInPassword = passwordEditText.getText().toString();

        if (signInEmail.isEmpty()) {
            Toast.makeText(SignInActivity.this, getString(R.string.no_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (signInPassword.isEmpty()) {
            Toast.makeText(SignInActivity.this, getString(R.string.no_password), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void signInUser() {

        // Tag used to cancel the request
        String tag_string_req = "request_login";

        pDialog.setMessage("Logging you in, Please wait...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Database_Utilities.LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        sessionManager.setLogin(true);

                        // Now store the user in SQLite
                        String userId = jObj.getString(Database_Utilities.KEY_ID);

                        JSONObject user = jObj.getJSONObject("user");
                        String userName = user.getString(Database_Utilities.KEY_NAME);
                        String userEmail = user.getString(Database_Utilities.KEY_EMAIL);
                        String userPassword = user.getString(Database_Utilities.KEY_PASSWORD);
                        String userAddress = user.getString(Database_Utilities.KEY_ADDRESS);
                        String userPhone = user.getString(Database_Utilities.KEY_PHONE);

                        // Inserting row in users table
                        User loggedInUser = new User(Integer.parseInt(userId), userName, userEmail, userPassword, userAddress, userPhone);

                        db.addUser(loggedInUser);
                        hideDialog();

                        // Launch main activity
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);

                        startActivity(intent);

                        finish();
                    } else {
                        // Error in login. Get the error message
                        hideDialog();
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    hideDialog();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put(Database_Utilities.KEY_EMAIL, signInEmail);
                params.put(Database_Utilities.KEY_PASSWORD, signInPassword);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
        finish();
    }
}
