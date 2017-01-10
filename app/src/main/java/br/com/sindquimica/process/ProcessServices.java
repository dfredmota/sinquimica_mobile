package br.com.sindquimica.process;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import br.com.martinlabs.commons.android.OpResponse;
import br.com.martinlabs.commons.android.SnappyWrapper;
import br.com.sindquimica.model.Usuario;

/**
 * Created by fred mota on 6/10/2016.
 */
public class ProcessServices {

    public static final String KEY_TREINADOR_LOGADO = "usuarioLogado";
    public static final String KEY_TOKEN_TREINADOR_LOGADO = "tokenUsuarioLogado";

    private static SindQuimicaProcess instanceSindQuimicaProcess;
    private static Session instanceSession;
    private static Usuario usuarioLogado;


   public static Session getSessionInstance() {
        if (instanceSession == null) {
            instanceSession = new Session();
        }
        return instanceSession;
    }

    public static SindQuimicaProcess getInstanceUsuarioProcess(){
        if(instanceSindQuimicaProcess == null){
            instanceSindQuimicaProcess = new SindQuimicaProcess();
        }
        return instanceSindQuimicaProcess;
    }


    public static Usuario getUsuarioLogado(Context ctx) {
        if(usuarioLogado == null){
            SnappyWrapper.open(ctx, snappy -> {
                usuarioLogado = snappy.getObject(KEY_TREINADOR_LOGADO, Usuario.class);
                return  null;
            });
        }
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado, Context ctx) {
        ProcessServices.usuarioLogado = usuarioLogado;
        salvarUsuarioLogado(usuarioLogado, ctx);
    }

    public static void setTokenUsuarioLogado(String token, Context ctx) {
        salvarUsuarioLogado(usuarioLogado, ctx);
    }

    private static void salvarUsuarioLogado(Usuario usuarioLogado, Context ctx){
        SnappyWrapper.open(ctx, snappy -> {
            snappy.put(KEY_TREINADOR_LOGADO, usuarioLogado);
            return null;
        });
    }

    public static void salvarUsuarioLogado(Context ctx){
        SnappyWrapper.open(ctx, snappy -> {
            snappy.put(KEY_TREINADOR_LOGADO, usuarioLogado);
            return null;
        });
    }


}
