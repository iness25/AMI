package com.ali.evalu8_front;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mot_pass = (EditText) findViewById(R.id.mot_pass);
        final EditText num_tel = (EditText) findViewById(R.id.num_tel);
        final Button login_btn = (Button) findViewById(R.id.login_btn);
        final Button regis_btn = (Button) findViewById(R.id.regis_btn);

        regis_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                Login.this.startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final int numtel = Integer.parseInt(num_tel.getText().toString());
                final String mp = mot_pass.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(Login.this);

                if (!isFieldEmpty(num_tel.getText().toString())) {
                    if (!isFieldEmpty(mp)) {
                        if (!isPhoneNbr(num_tel.getText().toString())) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setMessage("Vérifiez votre numéro de téléphone.").setNegativeButton("Ok", null).create().show();
                        } else {
                            progressDialog.setMessage("Connexion..");
                            progressDialog.show();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        if (!success) {
                                            progressDialog.dismiss();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                            builder.setMessage("Numéro de téléphone ou mot de passe incorrect.").setNegativeButton("Réessayer", null).create().show();
                                        } else {
                                            Intent intent = new Intent(Login.this,Claim.class);
                                            Login.this.startActivity(intent);
                                            String nom = jsonResponse.getString("nom");
                                            Toast.makeText(Login.this, "Bienvenue " + nom + "!", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }
                                }
                            };

                            LoginRequest loginRequest = new LoginRequest(numtel, mp, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(Login.this);
                            queue.add(loginRequest);
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        builder.setMessage("Veuillez saisir votre mot de passe.").setNegativeButton("Ok", null).create().show();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage("Veuillez saisir votre numéro de téléphone.").setNegativeButton("Ok", null).create().show();
                }
            }
        });
    }
    boolean isFieldEmpty(String a) {
        return(a.equals(""));
    }
    boolean isPhoneNbr(String a){
        return a.trim().length()==8;
    }
}