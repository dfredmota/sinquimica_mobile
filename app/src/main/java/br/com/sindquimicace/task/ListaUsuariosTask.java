package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimicace.delegate.ListaUsuariosDelegate;
import br.com.sindquimicace.delegate.LoginDelegate;
import br.com.sindquimicace.ws.Usuario;
import br.com.sindquimicace.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class ListaUsuariosTask extends AsyncTask<Usuario, Usuario, List<Usuario>> {

    private ListaUsuariosDelegate delegate;

    public ListaUsuariosTask(ListaUsuariosDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected List<Usuario> doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        List<Usuario> usuarios = null;

        try {

            usuarios = ws.listaUsuarios(params[0].getEmpresaSistema());

        }catch(Exception e){
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    protected void onPreExecute() {
        this.delegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(List<Usuario> lista) {

        this.delegate.listaUsuarios(lista);
    }
}
