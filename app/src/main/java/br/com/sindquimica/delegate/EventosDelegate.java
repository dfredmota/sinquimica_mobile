package br.com.sindquimica.delegate;


import java.util.List;

import br.developersd3.sindquimica.ws.Evento;
import br.developersd3.sindquimica.ws.Mensagem;

/**
 * Created by fred on 20/10/16.
 */


public interface EventosDelegate {

    void listou(List<Evento> lista);
    void carregaDialog();
}
