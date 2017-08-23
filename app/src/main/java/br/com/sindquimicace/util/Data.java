package br.com.sindquimicace.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.sindquimicace.ws.Evento;
import br.com.sindquimicace.ws.Grupo;
import br.com.sindquimicace.ws.Mensagem;
import br.com.sindquimicace.ws.Usuario;


/**
 * Created by fred on 20/10/16.
 */

public class Data {

    public static void insertEvento(SharedPreferences mPrefs, Evento evento){

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gSon.toJson(evento);
        prefsEditor.putString("evento", json);
        prefsEditor.commit();

    }


    public static Evento getEvento(SharedPreferences mPrefs){

        Evento user = null;

        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        String json = mPrefs.getString("evento", "0");

        if(json != null && !json.equals("0"))
            user = gSon.fromJson(json, Evento.class);

        return user;
    }


    public static void insertUsuario(SharedPreferences mPrefs, Usuario usuario){

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gSon.toJson(usuario);
        prefsEditor.putString("usuario", json);
        prefsEditor.commit();

    }


    public static Usuario getUsuario(SharedPreferences mPrefs){

        Usuario user = null;

        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        String json = mPrefs.getString("usuario", "0");

        if(json != null && !json.equals("0"))
        user = gSon.fromJson(json, Usuario.class);

        return user;
    }

    public static void loggoutUsuario(SharedPreferences mPrefs ){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove("usuario");
        editor.apply();
    }


    public static void saveToken(SharedPreferences shared, String token){

        SharedPreferences.Editor editor = shared.edit();

        // Save to SharedPreferences
        editor.putString("token_app", token);
        editor.apply();

    }

    public static String getToken(SharedPreferences shared){

        return shared.getString("token_app", null);
    }


    public static void insertMensagem(SharedPreferences mPrefs, Mensagem msg){

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gSon.toJson(msg);
        prefsEditor.putString("msg", json);
        prefsEditor.commit();

    }


    public static Mensagem getMensagem(SharedPreferences mPrefs){

        Mensagem msg = null;

        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        String json = mPrefs.getString("msg", "0");

        if(json != null && !json.equals("0"))
            msg = gSon.fromJson(json, Mensagem.class);

        return msg;
    }

    public static void limpaMensagem(SharedPreferences mPrefs ){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove("msg");
        editor.apply();
    }

    public static void insertGrupo(SharedPreferences mPrefs, Grupo grupo){

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gSon.toJson(grupo);
        prefsEditor.putString("grupo", json);
        prefsEditor.commit();

    }


    public static Grupo getGrupo(SharedPreferences mPrefs){

        Grupo user = null;

        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        String json = mPrefs.getString("grupo", "0");

        if(json != null && !json.equals("0"))
            user = gSon.fromJson(json, Grupo.class);

        return user;
    }

    public static void limpaGrupo(SharedPreferences mPrefs ){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove("grupo");
        editor.apply();
    }


}
