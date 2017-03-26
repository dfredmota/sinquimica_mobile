package br.com.sindquimica.delegate;


import br.developersd3.sindquimica.ws.Usuario;

/**
 * Created by fred on 20/10/16.
 */

public interface EditarUsuarioDelegate {

    void editarUsuario(Usuario usuario);
    void carregaDialog();
}
