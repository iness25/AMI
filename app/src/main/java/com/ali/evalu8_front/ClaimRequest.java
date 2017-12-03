package com.ali.evalu8_front;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 03/12/2017.
 */

public class ClaimRequest extends StringRequest {
    private static final String CLAIM_REQUEST_URL = "https://busfinder.000webhostapp.com/ajouterReclamation.php";
    private Map<String, String> params;

    public ClaimRequest(String type, String objet, String session,Response.Listener<String> listener){
        super(Method.POST, CLAIM_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("type",type);
        params.put("objet",objet);
        params.put("session",session);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
