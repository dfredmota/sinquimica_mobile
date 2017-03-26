package br.com.sindquimica.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimica.delegate.GruposDelegate;
import br.com.sindquimica.delegate.GruposMsgDelegate;
import br.developersd3.sindquimica.ws.Grupo;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class GruposMsgTask extends AsyncTask<Usuario, Usuario, List<Grupo>> {

    private GruposMsgDelegate delegate;

    public GruposMsgTask(GruposMsgDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected List<Grupo> doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        List<Grupo> lista = null;

        try {

            lista = ws.listaGrupos();

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
