package com.ali.evalu8_front;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Claim extends AppCompatActivity {

        private static final int DATE_DIALOG_ID = 1;
        private int year;
        private int month;
        private int day;
        private String currentDate;
        EditText editTextDate;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_claim);

            Intent intent = getIntent();
            final MaterialBetterSpinner _type = (MaterialBetterSpinner) findViewById(R.id.type);
            final MaterialBetterSpinner _objet = (MaterialBetterSpinner) findViewById(R.id.object);
            final MaterialBetterSpinner _session = (MaterialBetterSpinner) findViewById(R.id.session);


            final ProgressDialog progressDialog = new ProgressDialog(Claim.this);

            //_________________________________TYPE____________________________________________
            ArrayAdapter<CharSequence> Tadapter = ArrayAdapter.createFromResource(Claim.this,
                    R.array._types, android.R.layout.simple_spinner_item);
            Tadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            _type.setAdapter(Tadapter);

            //_______________________________

            //________________________
            //_________________________________SESSION____________________________________________
            ArrayAdapter<CharSequence> Sadapter = ArrayAdapter.createFromResource(Claim.this,
                    R.array._sessions, android.R.layout.simple_spinner_item);
            Sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            _session.setAdapter(Sadapter);

            //____________________________OBJET__SELON__TYPE____________________________________
            _type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch(position){
                        case 0: ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(Claim.this,
                                R.array._type0, android.R.layout.simple_spinner_item);
                            adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            _objet.setAdapter(adapter0);
                            break;
                        case 1: ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(Claim.this,
                                R.array._type1, android.R.layout.simple_spinner_item);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            _objet.setAdapter(adapter1);
                            break;
                        case 2: ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(Claim.this,
                                R.array._type2, android.R.layout.simple_spinner_item);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            _objet.setAdapter(adapter2);
                            break;
                        case 3: ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(Claim.this,
                                R.array._type3, android.R.layout.simple_spinner_item);
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            _objet.setAdapter(adapter3);
                            break;
                        case 4: ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(Claim.this,
                                R.array._type4, android.R.layout.simple_spinner_item);
                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            _objet.setAdapter(adapter4);
                            break;
                        case 5: ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(Claim.this,
                                R.array._type5, android.R.layout.simple_spinner_item);
                            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            _objet.setAdapter(adapter5);
                            break;
                    }
                }
            });

            final Button add = (Button) findViewById(R.id.btn_add);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String type = _type.getText().toString();
                    final String objet = _objet.getText().toString();
                    final String session = _session.getText().toString();

                    if (!isFieldEmpty(type)) {
                        if (!isFieldEmpty(objet)) {
                            if (!isFieldEmpty(session)) {
                                        progressDialog.setMessage("Connexion..");
                                        progressDialog.show();
                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonResponse = new JSONObject(response);
                                                    boolean success = jsonResponse.getBoolean("success");
                                                    if (success) {
                                                        Toast.makeText(Claim.this,"Votre réclamation a été enregistré.",Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(Claim.this, Claim.class);
                                                        Claim.this.startActivity(intent);
                                                        Toast.makeText(Claim.this,"Ajouter une autre réclamation!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        progressDialog.dismiss();
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(Claim.this);
                                                        builder.setMessage("Veuillez vérifier votre connexion Internet.").setNegativeButton("Réessayer", null).create().show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Claim.this);
                                                    builder.setMessage(e+"").setNegativeButton("Ok", null).create().show();
                                                }
                                            }
                                        };
                                        ClaimRequest request = new ClaimRequest(type,objet,session,responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(Claim.this);
                                        queue.add(request);
                                    }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Claim.this);
                                builder.setMessage("Veuillez sélectionner une session avant de continuer.").setNegativeButton("Ok", null).create().show();
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Claim.this);
                            builder.setMessage("Veuillez sélectionner l'objet de la réclamation avant de continuer.").setNegativeButton("Ok", null).create().show();
                        }
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Claim.this);
                        builder.setMessage("Veuillez sélectionner le type de réclamation avant de continuer.").setNegativeButton("Ok", null).create().show();
                    }
                }
            });
        }
        boolean isFieldEmpty(String a) {
            return(a.equals(""));
        }
        //_________________________________________________________________________________________
    }
