package br.com.sindquimica.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.com.martinlabs.commons.android.MLActivity;
import br.com.martinlabs.commons.android.OpResponse;
import br.com.sindquimica.R;
import br.com.sindquimica.adapter.SpinnerAdapterEmpresaAssociada;
import br.com.sindquimica.model.EmpresaAssociada;
import br.com.sindquimica.model.Endereco;
import br.com.sindquimica.model.Perfil;
import br.com.sindquimica.model.Usuario;
import br.com.sindquimica.process.ProcessServices;

public class UsuarioAct extends MLActivity {

    private List<EmpresaAssociada> listaEmpresasAssociadas;

    SpinnerAdapterEmpresaAssociada adapterEmpresaAssociada;

    Spinner spinnerEmpresa;

    private Usuario usuario;

    DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");

    byte[] fotoUsuario;

    ImageView fotoUsuarioView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        //queueLoading(R.string.carregando, () -> carregarDados());



        fotoUsuarioView = (ImageView) findViewById(R.id.fotoUsuario);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            fotoUsuario = extras.getByteArray("fotoParceiro");

            if(fotoUsuario != null){

                Bitmap bmp = BitmapFactory.decodeByteArray(fotoUsuario, 0, fotoUsuario.length);

                Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp, 70, 70, true);

                fotoUsuarioView.setImageBitmap(bMapScaled);
            }

        }

        registerInteraction();
    }

    private void carregarDados()
    {

        OpResponse<List<EmpresaAssociada>> resp = ProcessServices.getSessionInstance().carregaListaEmpresaAssociadas(this);

        if (resp.isSuccess())
        {
            listaEmpresasAssociadas = resp.getData();

            ui(() -> populateEmpresasAssociadas());
        }
        else
        {
            uiToast(resp);
        }


    }

    private void populateEmpresasAssociadas(){

        adapterEmpresaAssociada = new SpinnerAdapterEmpresaAssociada(this,android.R.layout.simple_spinner_item,
                listaEmpresasAssociadas);

        spinnerEmpresa = (Spinner) findViewById(R.id.spinnerEmpresa);

        spinnerEmpresa.setAdapter(adapterEmpresaAssociada);
    }


    private void registrarUsuario() {

        usuario = new Usuario();

        String nome = ((EditText) f(R.id.editNome)).getText().toString();
        String dtNascimento = ((EditText) f(R.id.editDtNascimento)).getText().toString();
        String editEmail = ((EditText) f(R.id.editEmail)).getText().toString();
        String editTelefone = ((EditText) f(R.id.editTelefone)).getText().toString();
        String editCelular = ((EditText) f(R.id.editCelular)).getText().toString();
        String editLogin = ((EditText) f(R.id.editLogin)).getText().toString();
        String editSenha = ((EditText) f(R.id.editSenha)).getText().toString();
        String editConfirmeASenha = ((EditText) f(R.id.editConfirmeASenha)).getText().toString();
        String editLogradouro = ((EditText) f(R.id.editLogradouro)).getText().toString();
        String editNumero = ((EditText) f(R.id.editNumero)).getText().toString();
        String editComplemento = ((EditText) f(R.id.editComplemento)).getText().toString();
        String editBairro = ((EditText) f(R.id.editBairro)).getText().toString();
        String editCidade = ((EditText) f(R.id.editCidade)).getText().toString();
        String editCep = ((EditText) f(R.id.editCep)).getText().toString();


        if (nome == null || nome.isEmpty()) {
            uiToast("Nome obrigatório",false);
            return;
        }

        if (dtNascimento == null || dtNascimento.isEmpty()) {
            uiToast("Data de Nascimento",false);
            return;
        }

        if (editEmail == null || editEmail.isEmpty()) {
            uiToast("Email obrigatório",false);
            return;
        }

        if (editTelefone == null || editTelefone.isEmpty()) {
            uiToast("Telefone obrigatório",false);
            return;
        }

        if (editCelular == null || editCelular.isEmpty()) {
            uiToast("Celular obrigatório",false);
            return;
        }

        if (editLogin == null || editLogin.isEmpty()) {
            uiToast("Login obrigatório",false);
            return;
        }

        if (editSenha == null || editSenha.isEmpty()) {
            uiToast("Senha obrigatória",false);
            return;
        }

        if (editConfirmeASenha == null || editConfirmeASenha.isEmpty()) {
            uiToast("Confirmação de senha obrigatória",false);
            return;
        }

        if (!editConfirmeASenha.equals(editSenha)) {
            uiToast("Confirmação de senha não confere com senha.",false);
            return;
        }

        if (editLogradouro == null || editLogradouro.isEmpty()) {
            uiToast("Logradouro obrigatório",false);
            return;
        }

        if (editNumero == null || editNumero.isEmpty()) {
            uiToast("Numero obrigatório",false);
            return;
        }

        if (editComplemento == null || editComplemento.isEmpty()) {
            uiToast("Complemento obrigatório",false);
            return;
        }

        if (editBairro == null || editBairro.isEmpty()) {
            uiToast("Bairro obrigatório",false);
            return;
        }

        if (editCidade == null || editCidade.isEmpty()) {
            uiToast("Cidade obrigatório",false);
            return;
        }

        usuario.setNome(nome);

        try {
            usuario.setDtNascimento(format.parse(dtNascimento));
        }catch(ParseException e){
            e.printStackTrace();
        }

        usuario.setEmail(editEmail);
        usuario.setTelefones(editTelefone+";"+editCelular);
        usuario.setEmpresa((EmpresaAssociada) spinnerEmpresa.getSelectedItem());
        usuario.setLogin(editLogin);
        usuario.setPassword(editSenha);

        Perfil perfil = new Perfil();
        perfil.setId(2);
        usuario.setPerfil(perfil);
        usuario.setStatus(false);

        Endereco endereco = new Endereco();

        endereco.setLogradouro(editLogradouro);
        endereco.setBairro(editBairro);
        endereco.setCidade(editCidade);
        endereco.setComplemento(editComplemento);
        endereco.setCep(editCep);
        endereco.setNumero(editNumero);
        endereco.setEmpresaSistema(usuario.getEmpresa().getEmpresaSistema());

        usuario.setEndereco(endereco);

        usuario.setImagem(fotoUsuario);

        OpResponse<Usuario> resp = ProcessServices.getSessionInstance().registrarUsuario(this,usuario);

        if (resp.isSuccess() && (resp.getData() != null && resp.getData().getId() != null)) {

            uiToast("Usuário cadastrado com sucesso. Aguarde desbloqueio pelo adminstrador!",true);
            NavLogin();

        } else {
            uiToast("Erro ao cadastrar usuário, Tente novamente mais tarde!",false);
        }

    }

    private void registerInteraction() {


        f(R.id.btnPersistParceiro).setOnClickListener(v -> {
            queueLoading(R.string.carregando, () -> registrarUsuario());
        });

        fotoUsuarioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsuarioAct.this, ValidarBiometria.class);

                startActivity(intent);

            }
        });



    }

    private void NavLogin() {
        startActivityClearTask(LoginAct.class);
    }
}
