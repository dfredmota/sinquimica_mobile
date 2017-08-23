package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimicace.delegate.LoginDelegate;
import br.com.sindquimicace.delegate.MensagensDelegate;
import br.com.sindquimicace.ws.Mensagem;
import br.com.sindquimicace.ws.Usuario;
import br.com.sindquimicace.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class MensagensTask extends AsyncTask<Usuario, Usuario, List<Mensagem>> {

    private MensagensDelegate delegate;

    public MensagensTask(MensagensDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected List<Mensagem> doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        List<Mensagem> lista = null;

        try {

            lista = ws.listaMensagensUsuario("",params[0]);

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
