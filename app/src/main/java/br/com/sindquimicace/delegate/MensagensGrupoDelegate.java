package br.com.sindquimicace.delegate;


import java.util.List;

import br.com.sindquimicace.ws.Mensagem;


/**
 * Created by fred on 20/10/16.
 */


public interface MensagensGrupoDelegate {

    void listou(List<Mensagem> lista);
    void carregaDialog();
}
