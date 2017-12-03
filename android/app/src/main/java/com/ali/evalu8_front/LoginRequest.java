package com.ali.evalu8_front;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 03/12/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://busfinder.000webhostapp.com/login.php";
    private Map<String, String> params;

    public LoginRequest(Integer numtel,String mp,Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("n_tel",numtel+"");
        params.put("mp",mp);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
