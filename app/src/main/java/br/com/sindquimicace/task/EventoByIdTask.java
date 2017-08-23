package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimicace.delegate.EventoByIdDelegate;
import br.com.sindquimicace.delegate.EventosDelegate;
import br.com.sindquimicace.ws.Evento;
import br.com.sindquimicace.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class EventoByIdTask extends AsyncTask<Integer[], Integer[], Evento> {

    private EventoByIdDelegate delegate;

    public EventoByIdTask(EventoByIdDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected Evento doInBackground(Integer[]... params) {

        WsDao ws = new WsDao();

        Evento evento = null;

        try {

            evento = ws.listaEventoUsuario(params[0]);

        }catch(Exception e){
            e.printStackTrace();
        }
        return evento;
    }

    @Override
    protected void onPreExecute() {
        this.delegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(Evento lista) {

        this.delegate.listou(lista);
    }
}
