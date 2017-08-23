package br.com.sindquimicace.delegate;


import java.util.List;

import br.com.sindquimicace.ws.Evento;


/**
 * Created by fred on 20/10/16.
 */


public interface EventoByIdDelegate {

    void listou(Evento evento);
    void carregaDialog();
}
