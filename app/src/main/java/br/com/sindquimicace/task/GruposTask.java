package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimicace.delegate.GruposDelegate;
import br.com.sindquimicace.delegate.MensagensDelegate;
import br.com.sindquimicace.ws.Grupo;
import br.com.sindquimicace.ws.Usuario;
import br.com.sindquimicace.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class GruposTask extends AsyncTask<Usuario, Usuario, List<Grupo>> {

    private GruposDelegate delegate;

    public GruposTask(GruposDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected List<Grupo> doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        List<Grupo> lista = null;

        try {

            lista = ws.listaGruposUsuario(params[0]);

            System.out.print("Size de Grupos: "+lista.size());

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
    protected void onPostExecute(List<Grupo> lista) {

        this.delegate.listouGrupos(lista);
    }
}
