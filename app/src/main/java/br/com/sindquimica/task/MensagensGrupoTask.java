package br.com.sindquimica.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimica.delegate.MensagensDelegate;
import br.com.sindquimica.delegate.MensagensGrupoDelegate;
import br.developersd3.sindquimica.ws.Grupo;
import br.developersd3.sindquimica.ws.Mensagem;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class MensagensGrupoTask extends AsyncTask<Grupo, Grupo, List<Mensagem>> {

    private MensagensDelegate delegate;

    public MensagensGrupoTask(MensagensDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected List<Mensagem> doInBackground(Grupo... params) {

        WsDao ws = new WsDao();

        List<Mensagem> lista = null;

        try {

            lista = ws.listaMensagensGrupo(params[0]);

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
    protected void onPostExecute(List<Mensagem> lista) {

        this.delegate.listou(lista);
    }
}
