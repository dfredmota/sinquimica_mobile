package br.com.sindquimicace.delegate;


import java.util.List;

import br.com.sindquimicace.ws.Evento;


/**
 * Created by fred on 20/10/16.
 */


public interface EventosDelegate {

    void listou(List<Evento> lista);
    void carregaDialog();
}
