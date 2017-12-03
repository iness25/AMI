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

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText prenom = (EditText) findViewById(R.id.prenom);
        final EditText num_tel = (EditText) findViewById(R.id.num_tel);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText mot_pass = (EditText) findViewById(R.id.mot_pass);
        final EditText confmot_pass = (EditText) findViewById(R.id.confmot_pass);
        final Button regis_btn = (Button) findViewById(R.id.regis_btn);
        final ProgressDialog progressDialog = new ProgressDialog(Register.this);

        regis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nom = prenom.getText().toString();
                final int numtel = Integer.parseInt(num_tel.getText().toString());
                final String mail = email.getText().toString();
                final String mp = mot_pass.getText().toString();
                final String confmp = confmot_pass.getText().toString();

                if(isFieldEmpty(nom)){AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                    builder.setMessage("Veuillez saisir votre nom et prénom.").setNegativeButton("Ok",null).create().show();}
                else{
                    if (isFieldEmpty(num_tel.getText().toString())){AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                        builder.setMessage("Veuillez saisir votre numéro de téléphone.").setNegativeButton("Ok",null).create().show();}
                    else{
                        if (isFieldEmpty(mail)){AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                            builder.setMessage("Veuillez saisir votre adresse e-mail.").setNegativeButton("Ok",null).create().show();}
                        else{
                            if (isFieldEmpty(mp)){AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                                builder.setMessage("Veuillez saisir un mot de passe.").setNegativeButton("Ok",null).create().show();}
                            else{
                                if (isFieldEmpty(confmp)){AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                                    builder.setMessage("Veuillez confirmer votre mot de passe.").setNegativeButton("Ok",null).create().show();}
                                else{
                                    if(!isPhoneNbr(num_tel.getText().toString())){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                                        builder.setMessage("Votre numéro de téléphone doit être composé de 8 chiffres.").setNegativeButton("Ok",null).create().show();}
                                    else {
                                        if(!isEmail(mail)){
                                            AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                                            builder.setMessage("Votre adresse e-mail est incorrecte.").setNegativeButton("Ok",null).create().show();}
                                        else {
                                            if (!PasswordsMatch(mp, confmp)) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                                builder.setMessage("Les mots de passe ne correspondent pas.").setNegativeButton("Ok", null).create().show();}
                                            else {
                                                progressDialog.setMessage("Connexion..");
                                                progressDialog.show();

                                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject jsonResponse = new JSONObject(response);
                                                            boolean success = jsonResponse.getBoolean("success");
                                                            if (success) {
                                                                Toast.makeText(Register.this,"Vous êtes inscrit.",Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(Register.this, Login.class);
                                                                Register.this.startActivity(intent);
                                                            } else {
                                                                progressDialog.dismiss();
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                                                builder.setMessage("Numéro de téléphone déjà utilisé.").setNegativeButton("Réessayer", null).create().show();
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };

                                                RegisterRequest registerRequest = new RegisterRequest(nom, numtel, mail, mp, responseListener);
                                                RequestQueue queue = Volley.newRequestQueue(Register.this);
                                                queue.add(registerRequest);}
                                        }
                                    }
                                }
                            }
                        }}
                }}
        });
    }
    boolean isFieldEmpty(String a) {
        return a.equals("");
    }
    boolean isPhoneNbr(String a){
        return a.trim().length()==8;
    }
    boolean isEmail(String a) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(a).matches();
    }
    boolean PasswordsMatch(String a,String b) {
        return a.equals(b);
    }
}
