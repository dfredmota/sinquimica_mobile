package br.com.sindquimica.delegate;


import java.util.List;

import br.developersd3.sindquimica.ws.Mensagem;

/**
 * Created by fred on 20/10/16.
 */


public interface MensagensGrupoDelegate {

    void listou(List<Mensagem> lista);
    void carregaDialog();
}
