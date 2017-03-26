package br.com.sindquimica.delegate;


import br.developersd3.sindquimica.ws.Usuario;

/**
 * Created by fred on 20/10/16.
 */

public interface RegistraUsuarioDelegate {

    void registraUsuario(Usuario usuario);
    void carregaDialog();
}
