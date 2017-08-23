package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimicace.delegate.ConfirmaPresencaDelegate;
import br.com.sindquimicace.ws.Evento;
import br.com.sindquimicace.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class ConfirmarPresencaTask extends AsyncTask<Evento, Evento, Boolean> {

    private ConfirmaPresencaDelegate delegate;

    public ConfirmarPresencaTask(ConfirmaPresencaDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected Boolean doInBackground(Evento... params) {

        WsDao ws = new WsDao();

        Boolean confirmou = true;

        try {

            ws.confirmaPresencaEventoUsuario(params[0]);

        }catch(Exception e){
            confirmou = false;
            e.printStackTrace();
        }
        return confirmou;
    }

    @Override
    protected void onPreExecute() {
        this.delegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(Boolean confirmacao) {

        this.delegate.confirmou(confirmacao);
    }
}
