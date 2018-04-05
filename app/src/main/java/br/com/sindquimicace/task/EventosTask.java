package br.com.sindquimicace.task;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.sindquimicace.delegate.EventosDelegate;
import br.com.sindquimicace.delegate.MensagensDelegate;
import br.com.sindquimicace.ws.Evento;
import br.com.sindquimicace.ws.Grupo;
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

        List<Evento> lista = new ArrayList<Evento>();

        try {

            List<Evento> listaUsuario = ws.listaEventosDeUsuario(params[0]);

            // recupera os ids de grupo desse usuario
            List<Grupo> grupos = ws.listaGruposEmpresaAssociada(params[0]);

            List<Evento> listaGrupos = new ArrayList<Evento>();

            if(grupos != null && !grupos.isEmpty()){

                listaGrupos = ws.listaEventosDeGrupo(grupos);

            }


            if(listaUsuario != null && !listaUsuario.isEmpty()){

                for(int x = 0; x < listaUsuario.size(); x++){

                    lista.add(listaUsuario.get(x));

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
    protected void onPostExecute(List<Evento> lista) {

        this.delegate.listou(lista);
    }
}
