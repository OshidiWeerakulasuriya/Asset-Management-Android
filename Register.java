package com.example.hp.akura;



import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextFullname , editTextUsername, editTextPassword;
    private Button buttonRegister;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        if(SharedPrefManager.getInstance(this).isLoggedIn()){
//            finish();
//            startActivity(new Intent(this, Home.class));
//            return;
//        }

        editTextFullname = (EditText) findViewById(R.id.editTextFullname);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);

    }

    private void registerUser() {
        final String fullname = editTextFullname.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                getString(R.string.register),
                new Response.Listener <String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        // String array=response.substring(13);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//response.toString()
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", fullname);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

//        RequestQueue requestQueue=Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister)
            registerUser();
        //    if(view == textViewLogin)
        //  startActivity(new Intent(this, LoginActivity.class));
    }
}