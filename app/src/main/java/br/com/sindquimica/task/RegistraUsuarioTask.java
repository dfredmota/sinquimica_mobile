package br.com.sindquimica.task;

import android.os.AsyncTask;

import br.com.sindquimica.delegate.RegistraUsuarioDelegate;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class RegistraUsuarioTask extends AsyncTask<Usuario, Usuario, Usuario> {

    private RegistraUsuarioDelegate registraDelegate;

    public RegistraUsuarioTask(RegistraUsuarioDelegate activity){

        this.registraDelegate = activity;
    }

    @Override
    protected Usuario doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        Usuario usuario = null;

        try {

        usuario = ws.insertUsuario("",params[0]);

        }catch(Exception e){
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    protected void onPreExecute() {
        this.registraDelegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(Usuario usuario) {

        this.registraDelegate.registraUsuario(usuario);

    }
}
