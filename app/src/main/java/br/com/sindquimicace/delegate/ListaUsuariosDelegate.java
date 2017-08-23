package br.com.sindquimicace.delegate;


import java.util.List;

import br.com.sindquimicace.ws.Usuario;


/**
 * Created by fred on 20/10/16.
 */


public interface ListaUsuariosDelegate {

    void listaUsuarios(List<Usuario> lista);
    void carregaDialog();
}
