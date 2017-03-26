package br.com.sindquimica.delegate;


import java.util.List;

import br.developersd3.sindquimica.ws.EmpresaAssociada;
import br.developersd3.sindquimica.ws.Usuario;

/**
 * Created by fred on 20/10/16.
 */


public interface ListaEmpresaDelegate {

    void listou(List<EmpresaAssociada> lista);
    void carregaDialog();
}
