package br.com.sindquimicace.delegate;


import br.com.sindquimicace.ws.Usuario;

/**
 * Created by fred on 20/10/16.
 */

public interface RegistraUsuarioDelegate {

    void registraUsuario(Usuario usuario);
    void carregaDialog();
}
