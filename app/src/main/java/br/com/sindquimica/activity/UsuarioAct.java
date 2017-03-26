package br.com.sindquimica.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.com.sindquimica.R;
import br.com.sindquimica.adapter.SpinnerAdapterEmpresaAssociada;
import br.com.sindquimica.delegate.ListaEmpresaDelegate;
import br.com.sindquimica.delegate.RegistraUsuarioDelegate;
import br.com.sindquimica.task.ListaEmpresaTask;
import br.com.sindquimica.task.RegistraUsuarioTask;
import br.com.sindquimica.util.Data;
import br.com.sindquimica.util.Mask;
import br.developersd3.sindquimica.ws.EmpresaAssociada;
import br.developersd3.sindquimica.ws.Endereco;
import br.developersd3.sindquimica.ws.Perfil;
import br.developersd3.sindquimica.ws.Usuario;

public class UsuarioAct extends AppCompatActivity implements RegistraUsuarioDelegate,ListaEmpresaDelegate {

    private List<EmpresaAssociada> listaEmpresasAssociadas;

    SpinnerAdapterEmpresaAssociada adapterEmpresaAssociada;

    Spinner spinnerEmpresa;

    private Usuario usuario;

    DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    ImageView fotoUsuarioView;

    int PICK_PHOTO_FOR_AVATAR = 985;

    EditText dataNascimento;
    EditText telefone;
    EditText celular;
    EditText cep;

    ProgressDialog ringProgressDialog;

    ImageView btnPersistUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);


        usuario = new Usuario();

        fotoUsuarioView = (ImageView) findViewById(R.id.fotoUsuario);

        registerInteraction();

        instanceObjects();

        carregarDados();
    }

    @Override
    public void listou(List<EmpresaAssociada> lista) {

        ringProgressDialog.dismiss();

        if(lista != null){
            listaEmpresasAssociadas = lista;
            populateEmpresasAssociadas();
        }
    }

    private void carregarDados()
    {

        ListaEmpresaTask task = new ListaEmpresaTask(this);

        task.execute();

    }

    private void populateEmpresasAssociadas(){

        adapterEmpresaAssociada = new SpinnerAdapterEmpresaAssociada(this,android.R.layout.simple_spinner_item,
                listaEmpresasAssociadas);

        spinnerEmpresa = (Spinner) findViewById(R.id.spinnerEmpresa);

        spinnerEmpresa.setAdapter(adapterEmpresaAssociada);
    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void registraUsuario(Usuario usuario) {

        ringProgressDialog.dismiss();

        if(usuario != null) {

            Data.insertUsuario(PreferenceManager.getDefaultSharedPreferences(this), usuario);

            AlertDialog.Builder builder = new AlertDialog.Builder(UsuarioAct.this);

            builder.setMessage("Usuário cadastrado com sucesso. Aguarde desbloqueio pelo adminstrador!")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent i = new Intent(UsuarioAct.this, LoginAct.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);

                                    return;

                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(UsuarioAct.this);

            builder.setMessage("Erro ao cadastrar usuário. tente mais tarde!")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    return;

                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
        }


    }

    private void registrarUsuario() {

        String nome = ((EditText) findViewById(R.id.editNome)).getText().toString();
        String dtNascimento = ((EditText) findViewById(R.id.editDtNascimento)).getText().toString();
        String editEmail = ((EditText) findViewById(R.id.editEmail)).getText().toString();
        String editTelefone = ((EditText) findViewById(R.id.editTelefone)).getText().toString();
        String editCelular = ((EditText) findViewById(R.id.editCelular)).getText().toString();
        String editLogin = ((EditText) findViewById(R.id.editLogin)).getText().toString();
        String editSenha = ((EditText) findViewById(R.id.editSenha)).getText().toString();
        String editConfirmeASenha = ((EditText) findViewById(R.id.editConfirmeASenha)).getText().toString();
        String editLogradouro = ((EditText) findViewById(R.id.editLogradouro)).getText().toString();
        String editNumero = ((EditText) findViewById(R.id.editNumero)).getText().toString();
        String editComplemento = ((EditText) findViewById(R.id.editComplemento)).getText().toString();
        String editBairro = ((EditText) findViewById(R.id.editBairro)).getText().toString();
        String editCidade = ((EditText) findViewById(R.id.editCidade)).getText().toString();
        String editCep = ((EditText) findViewById(R.id.editCep)).getText().toString();


        if (nome == null || nome.isEmpty()) {
            Toast.makeText(this, "Nome obrigatório", Toast.LENGTH_LONG).show();
            return;
        }

        if (dtNascimento == null || dtNascimento.isEmpty()) {
            Toast.makeText(this, "Data de Nascimento", Toast.LENGTH_LONG).show();
            return;
        }

        if (editEmail == null || editEmail.isEmpty()) {
            Toast.makeText(this, "Email obrigatório", Toast.LENGTH_LONG).show();
            return;
        }

        if (editTelefone == null || editTelefone.isEmpty()) {
            editTelefone = " ";
        }

        if (editCelular == null || editCelular.isEmpty()) {
            Toast.makeText(this, "Celular obrigatório", Toast.LENGTH_LONG).show();
            return;
        }

        if (editLogin == null || editLogin.isEmpty()) {
            Toast.makeText(this, "Login obrigatório", Toast.LENGTH_LONG).show();
            return;
        }

        if (editSenha == null || editSenha.isEmpty()) {
            Toast.makeText(this, "Senha obrigatória", Toast.LENGTH_LONG).show();
            return;
        }

        if (editConfirmeASenha == null || editConfirmeASenha.isEmpty()) {
            Toast.makeText(this, "Confirmação de senha obrigatória", Toast.LENGTH_LONG).show();
            return;
        }

        if (!editConfirmeASenha.equals(editSenha)) {
            Toast.makeText(this, "Confirmação de senha não confere com senha.", Toast.LENGTH_LONG).show();
            return;
        }

        if (editLogradouro == null || editLogradouro.isEmpty()) {
            editLogradouro = " ";
        }

        if (editNumero == null || editNumero.isEmpty()) {
            editNumero = " ";
        }

        if (editComplemento == null || editComplemento.isEmpty()) {
            editComplemento = " ";
         }

        if (editBairro == null || editBairro.isEmpty()) {
            editBairro = " ";
        }

        if (editCidade == null || editCidade.isEmpty()) {
            editCidade = " ";
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
        perfil.setId(3);
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

        RegistraUsuarioTask task = new RegistraUsuarioTask(this);

        task.execute(usuario);

   }

    private void instanceObjects(){

        telefone = (EditText) findViewById(R.id.editTelefone);

        telefone.addTextChangedListener(Mask.insert("(##)####-####", telefone));

        celular = (EditText) findViewById(R.id.editCelular);

        celular.addTextChangedListener(Mask.insert("(##)#####-####", celular));

        cep = (EditText) findViewById(R.id.editCep);

        cep.addTextChangedListener(Mask.insert("##.###-###", cep));

        dataNascimento = (EditText) findViewById(R.id.editDtNascimento);
        dataNascimento.addTextChangedListener(Mask.insert("##/##/####", dataNascimento));


    }

    private void registerInteraction() {


        btnPersistUsuario = (ImageView) findViewById(R.id.btnPersistUsuario);

        btnPersistUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarUsuario();

            }
        });

        fotoUsuarioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickImage();

            }
        });

    }


    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {

                Uri selectedImage = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                fotoUsuarioView.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                usuario.setImagem(byteArray);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
