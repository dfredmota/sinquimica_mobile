package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.ArrayList;
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

        List<Grupo> lista = new ArrayList<Grupo>();

        try {

            List<Grupo> listaUsuarios = ws.listaGruposUsuario(params[0]);

            List<Grupo> listaGrupos = ws.listaGruposEmpresaAssociada(params[0]);

            if(listaUsuarios != null && !listaUsuarios.isEmpty()){

                for(int x = 0; x < listaUsuarios.size(); x++){

                    lista.add(listaUsuarios.get(x));

                }

            }

            if(listaGrupos != null && !listaGrupos.isEmpty()){

                for(int x = 0; x < listaGrupos.size(); x++){

                    lista.add(listaGrupos.get(x));

                }

            }


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
