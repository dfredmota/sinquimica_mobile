package br.com.sindquimica.task;

import android.os.AsyncTask;

import java.util.List;

import br.com.sindquimica.delegate.ListaEmpresaDelegate;
import br.com.sindquimica.delegate.LoginDelegate;
import br.developersd3.sindquimica.ws.EmpresaAssociada;
import br.developersd3.sindquimica.ws.Usuario;
import br.developersd3.sindquimica.ws.WsDao;


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
