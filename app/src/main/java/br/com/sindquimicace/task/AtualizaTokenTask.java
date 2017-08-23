package br.com.sindquimicace.task;

import android.os.AsyncTask;

import br.com.sindquimicace.delegate.AtualizaTokenDelegate;
import br.com.sindquimicace.ws.Usuario;
import br.com.sindquimicace.ws.WsDao;




/**
 * Created by fred on 20/10/16.
 */

public class AtualizaTokenTask extends AsyncTask<Usuario, Boolean, Boolean> {

    private AtualizaTokenDelegate delegate;

    public AtualizaTokenTask(AtualizaTokenDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected Boolean doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        Boolean retorno = false;

        try {

        ws.atualizaTokenDeUsuario(params[0].getId(),params[0].getToken());

        return true;

        }catch(Exception e){
            e.printStackTrace();
            retorno = false;
        }

        return retorno;

    }

    @Override
    protected void onPreExecute() {
        this.delegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(Boolean retorno) {

        this.delegate.tokenAtualizado(retorno);
    }
}
