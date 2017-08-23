package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimicace.delegate.EventosDelegate;
import br.com.sindquimicace.delegate.MensagensDelegate;
import br.com.sindquimicace.ws.Evento;
import br.com.sindquimicace.ws.Usuario;
import br.com.sindquimicace.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class EventosTask extends AsyncTask<Usuario, Usuario, List<Evento>> {

    private EventosDelegate delegate;

    public EventosTask(EventosDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected List<Evento> doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        List<Evento> lista = null;

        try {

            lista = ws.listaEventosDeUsuario(params[0]);

        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    protected void onPreExecute() {
        this.delegate.carregaDialog();
    }

    @Override
    protected void onPostExecute(List<Evento> lista) {

        this.delegate.listou(lista);
    }
}
