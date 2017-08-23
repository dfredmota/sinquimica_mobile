package br.com.sindquimicace.delegate;


import java.util.List;

import br.com.sindquimicace.ws.Grupo;


/**
 * Created by fred on 20/10/16.
 */


public interface GruposMsgDelegate {

    void listouGrupos(List<Grupo> lista);
    void carregaDialog();
}
