package br.com.sindquimica.process;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.martinlabs.commons.android.OpResponse;
import br.com.martinlabs.commons.android.Req;
import br.com.sindquimica.model.EmpresaAssociada;
import br.com.sindquimica.model.Grupo;
import br.com.sindquimica.model.Mensagem;
import br.com.sindquimica.model.Usuario;
import br.com.sindquimica.request.LoginRequest;
import br.com.sindquimica.services.MensagemSistema;
import br.com.sindquimica.services.URL;


/**
 * Created by developer on 6/10/2016.
 */
public class Session {

    public OpResponse<Usuario> login(String senha, String email, Context ctx) {

        OpResponse<Usuario> resp = Req.post(URL.login(),
                new LoginRequest(senha, email),
                new TypeToken<OpResponse<Usuario>>() {
                }.getType());

        if (resp == null) {
            resp = new OpResponse(false, MensagemSistema.semRede());
        }
        else
        {
            if(resp.isSuccess())
            {
                Usuario usuario = resp.getData();
                ProcessServices.setUsuarioLogado(usuario, ctx);
            }
        }

        return resp;
    }

    public OpResponse<List<EmpresaAssociada>> carregaListaEmpresaAssociadas(Context ctx) {

        OpResponse<List<EmpresaAssociada>> resp = Req.get(URL.carregaEmpresasAssociadas(),
                new TypeToken<OpResponse<List<EmpresaAssociada>>>() {}.getType());

        if (resp == null) {
            resp = new OpResponse(false, MensagemSistema.semRede());
        }

        return resp;
    }

    public OpResponse<Usuario> registrarUsuario(Context ctx,Usuario usuario){

        OpResponse<Usuario> resp = Req.post(URL.registrarUsuario(),
                new TypeToken<OpResponse<Usuario>>() {}.getType(),
                "usuario", usuario);

        if (resp == null) {
            resp = new OpResponse(false, MensagemSistema.semRede());
        }

        return resp;

    }

    public OpResponse<List<Mensagem>> carregaMensagensUsuario(Context ctx,Usuario usuario){

        OpResponse<List<Mensagem>> resp = Req.post(URL.carregaMensagensUsuario(),
                new TypeToken<OpResponse<List<Mensagem>>>() {}.getType(),"usuario", usuario);

        if (resp == null) {
            resp = new OpResponse(false, MensagemSistema.semRede());
        }

        return resp;

    }

    public OpResponse<List<Grupo>> carregaGruposUsuario(Context ctx, Usuario usuario){

        OpResponse<List<Grupo>> resp = Req.post(URL.carregaGruposUsuario(),
                new TypeToken<OpResponse<List<Grupo>>>() {}.getType(),"usuario", usuario);

        if (resp == null) {
            resp = new OpResponse(false, MensagemSistema.semRede());
        }

        return resp;

    }

}
