package br.com.sindquimica.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimica.delegate.ConfirmaPresencaDelegate;
import br.com.sindquimica.delegate.EventosDelegate;
import br.developersd3.sindquimica.ws.Evento;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


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
