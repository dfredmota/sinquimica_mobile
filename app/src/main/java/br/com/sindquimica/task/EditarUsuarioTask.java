package br.com.sindquimica.task;

import android.os.AsyncTask;

import br.com.sindquimica.delegate.EditarUsuarioDelegate;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class EditarUsuarioTask extends AsyncTask<Usuario, Usuario, Usuario> {

    private EditarUsuarioDelegate editarDelegate;

    public EditarUsuarioTask(EditarUsuarioDelegate activity){

        this.editarDelegate = activity;
    }

    @Override
    protected Usuario doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        Usuario usuario = null;

        try {

            if(params[0].getEndereco()  != null){

                params[0].getEndereco().setEmpresaSistema(params[0].getEmpresaSistema());

            }

           usuario = ws.alterarUsuario("",params[0]);

        }catch(Exception e){
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    protected void onPreExecute() {
        this.editarDelegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(Usuario usuario) {

        this.editarDelegate.editarUsuario(usuario);

    }
}
