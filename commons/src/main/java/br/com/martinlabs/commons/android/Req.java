package br.com.martinlabs.commons.android;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by gil on 3/7/16.
 */
public class Req {

    public static String post(String url, Object param){
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            String paramStr = null;

            if (param != null) {
                paramStr = gson.toJson(param);
            }

            HttpRequest req = HttpRequest.post(url);

            if (paramStr != null) {
                paramStr = URLEncoder.encode(paramStr, "UTF-8");
                req.send(paramStr);
            }

            if (req.ok()) {
                String respStr = req.body();
                return respStr;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String get(String url, Object... params){
        try {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Date) {
                    params[i] = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format((Date) params[i]);
                }
            }

            HttpRequest req = HttpRequest.get(url, true, params);

            if (req.ok()) {
                String respStr = req.body();
                return respStr;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T post(String url, Object param, Class<T> classOfT) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String str = post(url, param);

        if (str == null) {
            return null;
        }

        return gson.fromJson(str, classOfT);
    }

    public static <T> T post(String url, Object param, Type typeOfT) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String str = post(url, param);

        if (str == null) {
            return null;
        }

        return gson.fromJson(str, typeOfT);
    }

    public static <T> T post(String url, Type typeOfT, Object... params) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        if (params.length % 2 != 0) {
            return null;
        }

        HashMap<String, Object> param = new HashMap<>();

        for (int i = 0; i < params.length -1; i += 2) {
            param.put((String)params[i], params[i+1]);
        }

        String str = post(url, param);

        if (str == null) {
            return null;
        }

        return gson.fromJson(str, typeOfT);
    }

    public static <T> T get(String url, Class<T> classOfT, Object... params) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String str = get(url, params);

        if (str == null) {
            return null;
        }

        return gson.fromJson(str, classOfT);
    }

    public static <T> T get(String url, Type typeOfT, Object... params) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String str = get(url, params);

        if (str == null) {
            return null;
        }

        return gson.fromJson(str, typeOfT);
    }

    public static int test() {
        return HttpRequest.get("http://google.com").code();
    }

}
