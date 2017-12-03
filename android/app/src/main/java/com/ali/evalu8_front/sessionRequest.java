package com.ali.evalu8_front;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 03/12/2017.
 */

public class sessionRequest extends StringRequest {
    private static final String LINE_REQUEST_URL = "***";
    private Map<String, String> params;

    public sessionRequest(Response.Listener<String> listener){
        super(Method.POST, LINE_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {return params;}
}