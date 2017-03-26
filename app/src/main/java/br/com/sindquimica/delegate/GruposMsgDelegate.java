package br.com.sindquimica.delegate;


import java.util.List;

import br.developersd3.sindquimica.ws.Grupo;

/**
 * Created by fred on 20/10/16.
 */


public interface GruposMsgDelegate {

    void listouGrupos(List<Grupo> lista);
    void carregaDialog();
}
