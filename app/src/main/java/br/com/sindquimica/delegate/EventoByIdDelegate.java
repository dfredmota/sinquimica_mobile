package br.com.sindquimica.delegate;


import java.util.List;

import br.developersd3.sindquimica.ws.Evento;

/**
 * Created by fred on 20/10/16.
 */


public interface EventoByIdDelegate {

    void listou(Evento evento);
    void carregaDialog();
}
