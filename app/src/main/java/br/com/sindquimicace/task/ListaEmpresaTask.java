package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimicace.delegate.ListaEmpresaDelegate;
import br.com.sindquimicace.delegate.LoginDelegate;
import br.com.sindquimicace.ws.EmpresaAssociada;
import br.com.sindquimicace.ws.Usuario;
import br.com.sindquimicace.ws.WsDao;


/**
 * Created by fred on 20/10/16.
 */

public class ListaEmpresaTask extends AsyncTask<Usuario, List<EmpresaAssociada>, List<EmpresaAssociada>> {

    private ListaEmpresaDelegate delegate;

    public ListaEmpresaTask(ListaEmpresaDelegate activity){

        this.delegate = activity;
    }

    @Override
    protected List<EmpresaAssociada> doInBackground(Usuario... params) {

        WsDao ws = new WsDao();

        List<EmpresaAssociada> lista = null;

        try {

            lista = ws.listaEmpresasAssociadas();

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
    protected void onPostExecute(List<EmpresaAssociada> lista) {

        this.delegate.listou(lista);
    }
}
