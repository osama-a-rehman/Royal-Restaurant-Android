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
import osama.ned.royalrestaurant.Others.SessionManager;
import osama.ned.royalrestaurant.R;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private EditText nameEditText, emaiLEditText, passwordEditText, addressEditText, phoneEditText;
    private String signUpName, signUpEmail, signUpPassword, signUpAddress, signUpPhone;
    private Button btnSignUp;

    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = (EditText) findViewById(R.id.signUpNameEditText);
        emaiLEditText = (EditText) findViewById(R.id.signUpEmailEditText);
        passwordEditText = (EditText) findViewById(R.id.signUpPasswordEditText);
        addressEditText = (EditText) findViewById(R.id.signUpAddressEditText);
        phoneEditText = (EditText) findViewById(R.id.signUpPhoneEditText);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(this);

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnSignUp = (Button) findViewById(R.id.btnProcessSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSuccessfulValidation = validateInformation();

                if (isSuccessfulValidation){
                    if(checkInternetConnection()){
                        signUpUser();
                    }else {
                        Toast.makeText(SignUpActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private boolean validateInformation() {
        signUpName = nameEditText.getText().toString();
        signUpEmail = emaiLEditText.getText().toString();
        signUpPassword = passwordEditText.getText().toString();
        signUpAddress = addressEditText.getText().toString();
        signUpPhone = phoneEditText.getText().toString();

        if (signUpName.isEmpty()) {
            Toast.makeText(SignUpActivity.this, getString(R.string.no_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (signUpEmail.isEmpty()) {
            Toast.makeText(SignUpActivity.this, getString(R.string.no_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (signUpPassword.isEmpty()) {
            Toast.makeText(SignUpActivity.this, getString(R.string.no_password), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (signUpAddress.isEmpty()) {
            Toast.makeText(SignUpActivity.this, getString(R.string.no_address), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (signUpPhone.isEmpty()) {
            Toast.makeText(SignUpActivity.this, getString(R.string.no_phone), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void signUpUser() {

        // Tag used to cancel the request
        String tag_string_req = "request_register";

        pDialog.setMessage("Signing you up, Please wait...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Database_Utilities.REGISTER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        hideDialog();
                        Toast.makeText(getApplicationContext(), "User successfully registered. Try signing in now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        hideDialog();
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    hideDialog();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();

                params.put("name", signUpName);
                params.put("email", signUpEmail);
                params.put("password", signUpPassword);
                params.put("address", signUpAddress);
                params.put("phone", signUpPhone);

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
