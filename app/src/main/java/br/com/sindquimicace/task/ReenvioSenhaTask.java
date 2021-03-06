package br.com.sindquimicace.task;

import android.os.AsyncTask;

import br.com.sindquimicace.delegate.ReenvioSenhaDelegate;
import br.com.sindquimicace.ws.Usuario;
import br.com.sindquimicace.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class ReenvioSenhaTask extends AsyncTask< String, Usuario, Void> {

    private ReenvioSenhaDelegate delegate;

    public ReenvioSenhaTask(ReenvioSenhaDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected Void doInBackground(String... params) {

        WsDao ws = new WsDao();

        try {

            ws.reenvioDeSenha(params[0]);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        this.delegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(Void v) {

        this.delegate.reenvioOK();
    }
}
