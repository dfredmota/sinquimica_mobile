package br.com.sindquimica.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimica.delegate.ListaUsuariosDelegate;
import br.com.sindquimica.delegate.LoginDelegate;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


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

            usuarios = ws.listaUsuarios();

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
