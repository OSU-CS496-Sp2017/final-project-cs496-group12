package com.cs496.clh.lowpolyfinalproject.utils;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hessro on 5/10/17.
 */

public class LFutils {

    private final static String LF_BASE_URL = "http://loremflickr.com";
    private final static String LF_S = "/";

    public static String buildLFURL(int width, int height, String query) {
        return LF_BASE_URL + LF_S + Integer.toString(width) + LF_S + Integer.toString(height) + LF_S + query;
    }
}
