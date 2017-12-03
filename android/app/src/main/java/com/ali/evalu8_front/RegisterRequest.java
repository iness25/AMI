package com.ali.evalu8_front;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 03/12/2017.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://busfinder.000webhostapp.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String nom,Integer numtel,String mail,String mp, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nom",nom);
        params.put("email",mail);
        params.put("n_tel",numtel+"");
        params.put("mp",mp);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
