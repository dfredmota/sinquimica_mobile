package br.com.sindquimica.task;

import android.os.AsyncTask;

import br.com.sindquimica.delegate.LoginDelegate;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class LoginTask extends AsyncTask<Usuario, Usuario, Usuario> {

    private LoginDelegate loginDelegate;

    public LoginTask(LoginDelegate activity){

        this.loginDelegate = activity;
    }

    @Override
    protected Usuario doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        Usuario usuario = null;

        try {

           usuario = ws.loginApp("",params[0].getLogin(),params[0].getPassword(),params[0].getToken());

        }catch(Exception e){
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    protected void onPreExecute() {
        this.loginDelegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(Usuario usuario) {

        this.loginDelegate.login(usuario);
    }
}
