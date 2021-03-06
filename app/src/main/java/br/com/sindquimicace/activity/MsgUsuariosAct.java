package br.com.sindquimicace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sindquimicace.R;
import br.com.sindquimicace.adapter.UsuarioAdapter;
import br.com.sindquimicace.delegate.ListaUsuariosDelegate;
import br.com.sindquimicace.delegate.SendMessageDelegate;
import br.com.sindquimicace.task.ListaUsuariosTask;
import br.com.sindquimicace.task.SendMessageTask;
import br.com.sindquimicace.util.Data;
import br.com.sindquimicace.ws.Grupo;
import br.com.sindquimicace.ws.Mensagem;
import br.com.sindquimicace.ws.Usuario;


public class MsgUsuariosAct extends AppCompatActivity implements ListaUsuariosDelegate,
        SendMessageDelegate {

    private ListView listViewUsuarios;

    private List<Usuario> listaUsuarios;

    ProgressDialog ringProgressDialog;

    ImageView btnEnviar;

    Mensagem mensagem;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_usuarios);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Selecione os Usuários");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        usuario = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        mensagem = Data.getMensagem(PreferenceManager.getDefaultSharedPreferences(this));

        mensagem.setUsuarios(new ArrayList<Usuario>());
        mensagem.setGrupos(new ArrayList<Grupo>());

        listViewUsuarios = (ListView) findViewById(R.id.listView1);

        ListUtils.setDynamicHeight(listViewUsuarios);

        btnEnviar = (ImageView) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(Usuario user : listaUsuarios){

                    if(user.getIsChecked() != null && user.getIsChecked()){
                        mensagem.getUsuarios().add(user);
                    }

                }


                postMessage();

            }
        });

        ListaUsuariosTask taskUser = new ListaUsuariosTask(this);

        taskUser.execute(usuario);
    }


    @Override
    public void onBackPressed()
    {

        navToHome();
    }

    private void navToHome(){

        Intent i = new Intent(this, HomeAct.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        i.addCategory(Intent.CATEGORY_HOME);

        this.startActivity(i);

    }



    private void postMessage(){

        if(mensagem.getGrupos().isEmpty() && mensagem.getUsuarios().isEmpty()){

            Toast.makeText(this, "Selecione ao menos um destinatário!", Toast.LENGTH_LONG).show();
            return;

        }

        // nesse ponto eu envio uma mensagem para mim mesmo pra visualiza-la na linha do tempo

        Usuario usuario = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        mensagem.getUsuarios().add(usuario);

        SendMessageTask task = new SendMessageTask(this);

        mensagem.setUsuario(usuario);

        task.execute(mensagem);
    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void listaUsuarios(List<Usuario> lista) {
        ringProgressDialog.dismiss();

        if(lista != null && !lista.isEmpty()){

            listaUsuarios = lista;

            UsuarioAdapter adapter = new UsuarioAdapter(this,listaUsuarios);

            listViewUsuarios.setAdapter(adapter);

        }

    }

    @Override
    public void sendMessageOK(Mensagem msg) {

        if(msg != null && msg.getId() != null ){

            Toast.makeText(this, "Mensagem Enviada com Sucesso!", Toast.LENGTH_LONG).show();
            navToHome();

        }else{
            Toast.makeText(this, "Erro ao Enviar a Mensagem. Tente Mais Tarde!", Toast.LENGTH_LONG).show();
            navToHome();
        }

    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }




}

