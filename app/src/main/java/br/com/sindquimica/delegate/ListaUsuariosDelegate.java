package br.com.sindquimica.delegate;


import java.util.List;

import br.developersd3.sindquimica.ws.Usuario;

/**
 * Created by fred on 20/10/16.
 */


public interface ListaUsuariosDelegate {

    void listaUsuarios(List<Usuario> lista);
    void carregaDialog();
}
