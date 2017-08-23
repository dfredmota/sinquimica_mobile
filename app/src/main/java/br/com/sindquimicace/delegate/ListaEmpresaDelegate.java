package br.com.sindquimicace.delegate;


import java.util.List;

import br.com.sindquimicace.ws.EmpresaAssociada;


/**
 * Created by fred on 20/10/16.
 */


public interface ListaEmpresaDelegate {

    void listou(List<EmpresaAssociada> lista);
    void carregaDialog();
}
