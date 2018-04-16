package osama.ned.royalrestaurant.Others;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import osama.ned.royalrestaurant.Activities.SignInActivity;

public class Login_Register_AsynTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;
    private String type;
    private final String loginURL = "http://10.0.2.2:8080/RoyalRestaurant/login.php";
    private final String registerURL = "http://10.0.2.2:8080/RoyalRestaurant/register.php";

    //private final String loginURL = "https://royal-restaurant.000webhostapp.com/login.php";
    //private final String registerURL = "https://royal-restaurant.000webhostapp.com/register.php";


    public Login_Register_AsynTask(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage((type.equals(Database_Utilities.TYPE_LOGIN)) ? "Logging you in, " : "Signing you up, " + "Please Wait!");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        if (type.equals(Database_Utilities.TYPE_LOGIN)) {
            String userEmail = strings[0];
            String userPassword = strings[1];

            try {
                URL url = new URL(loginURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Database_Utilities.UTF_8));

                String postData = URLEncoder.encode(Database_Utilities.KEY_EMAIL, Database_Utilities.UTF_8) + "=" + URLEncoder.encode(userEmail, Database_Utilities.UTF_8) + "&" +
                        URLEncoder.encode(Database_Utilities.KEY_PASSWORD, Database_Utilities.UTF_8) + "=" + URLEncoder.encode(userPassword, Database_Utilities.UTF_8);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Database_Utilities.POST_DATA_TYPE));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals(Database_Utilities.TYPE_REGISTER)){
            String userName = strings[0];
            String userEmail = strings[1];
            String userPassword = strings[2];
            String userAddress = strings[3];
            String userPhone = strings[4];

            try {
                URL url = new URL(registerURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Database_Utilities.UTF_8));

                String postData = URLEncoder.encode(Database_Utilities.KEY_NAME, Database_Utilities.UTF_8) + "=" + URLEncoder.encode(userName, Database_Utilities.UTF_8) + "&" +
                        URLEncoder.encode(Database_Utilities.KEY_EMAIL, Database_Utilities.UTF_8) + "=" + URLEncoder.encode(userEmail, Database_Utilities.UTF_8) + "&" +
                        URLEncoder.encode(Database_Utilities.KEY_PASSWORD, Database_Utilities.UTF_8) + "=" + URLEncoder.encode(userPassword, Database_Utilities.UTF_8) + "&" +
                        URLEncoder.encode(Database_Utilities.KEY_ADDRESS, Database_Utilities.UTF_8) + "=" + URLEncoder.encode(userAddress, Database_Utilities.UTF_8) + "&" +
                        URLEncoder.encode(Database_Utilities.KEY_PHONE, Database_Utilities.UTF_8) + "=" + URLEncoder.encode(userPhone, Database_Utilities.UTF_8);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Database_Utilities.POST_DATA_TYPE));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        progressDialog.dismiss();

        if(type.equals(Database_Utilities.TYPE_LOGIN)){
            if(result.equals(Database_Utilities.LOGIN_ERROR_1) || result.equals(Database_Utilities.LOGIN_ERROR_2)){
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }else{

            }
        }


    }
}
