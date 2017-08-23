package br.com.sindquimicace.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sindquimicace.R;
import br.com.sindquimicace.delegate.EditarUsuarioDelegate;
import br.com.sindquimicace.task.EditarUsuarioTask;
import br.com.sindquimicace.util.Data;
import br.com.sindquimicace.util.Mask;
import br.com.sindquimicace.ws.Usuario;


public class EditUsuarioAct extends AppCompatActivity implements EditarUsuarioDelegate {


    ProgressDialog ringProgressDialog;

    private Usuario usuario;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    ImageView fotoUsuarioView;

    int PICK_PHOTO_FOR_AVATAR = 985;

    ImageView btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_usuario);


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        usuario = Data.getUsuario(PreferenceManager.getDefaultSharedPreferences(this));

        instanceObjects();

        registerInteraction();
    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void editarUsuario(Usuario usuario) {
        ringProgressDialog.dismiss();
        if(usuario != null){

            Data.insertUsuario(PreferenceManager.getDefaultSharedPreferences(this),usuario);

            AlertDialog.Builder builder = new AlertDialog.Builder(EditUsuarioAct.this);

            builder.setMessage("Dados Cadastrais alterados com sucesso!")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent i = new Intent(EditUsuarioAct.this, HomeAct.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);

                                    return;

                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void instanceObjects(){

        EditText nome = ((EditText) findViewById(R.id.editNome));
        EditText editEmail = ((EditText) findViewById(R.id.editEmail));
        EditText editTelefone = ((EditText) findViewById(R.id.editTelefone));
        EditText editCelular = ((EditText) findViewById(R.id.editCelular));
        EditText editLogradouro = ((EditText) findViewById(R.id.editLogradouro));
        EditText editNumero = ((EditText) findViewById(R.id.editNumero));
        EditText editComplemento = ((EditText) findViewById(R.id.editComplemento));
        EditText editBairro = ((EditText) findViewById(R.id.editBairro));
        EditText editCidade = ((EditText) findViewById(R.id.editCidade));
        EditText dataNascimento = (EditText) findViewById(R.id.editDtNascimento);
        EditText login = (EditText) findViewById(R.id.editLogin);
        EditText senha = (EditText) findViewById(R.id.editSenha);
        EditText confirmeSenha = (EditText) findViewById(R.id.editConfirmeASenha);

        dataNascimento.addTextChangedListener(Mask.insert("##/##/####", dataNascimento));

        editTelefone.addTextChangedListener(Mask.insert("(##)####-####", editTelefone));
        editCelular.addTextChangedListener(Mask.insert("(##)#####-####", editCelular));

        nome.setText(usuario.getNome());
        editEmail.setText(usuario.getEmail());
        editTelefone.setText(usuario.getTelefones().split(";")[0]);
        editLogradouro.setText(usuario.getEndereco().getLogradouro());
        editNumero.setText(usuario.getEndereco().getNumero());
        editComplemento.setText(usuario.getEndereco().getComplemento());
        editBairro.setText(usuario.getEndereco().getBairro());
        editCidade.setText(usuario.getEndereco().getCidade());
        dataNascimento.setText(format.format(usuario.getDtNascimento()));
        login.setText(usuario.getLogin());
        senha.setText(usuario.getPassword());
        confirmeSenha.setText(usuario.getPassword());
        editCelular.setText(usuario.getTelefones().split(";")[1]);

        fotoUsuarioView = (ImageView) findViewById(R.id.fotoUsuario);

        if(usuario != null && usuario.getImagem() !=null) {

            Bitmap bmp = BitmapFactory.decodeByteArray(usuario.getImagem(), 0, usuario.getImagem().length);

            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp, 70, 70, true);

            fotoUsuarioView.setImageBitmap(bMapScaled);
        }

    }

    private void registerInteraction() {



        fotoUsuarioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickImage();

            }
        });

        btnRegistrar = (ImageView) findViewById(R.id.btnPersistUsuario);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarUsuario();
            }
        });

    }

    private void alterarUsuario() {

        String nome = ((EditText) findViewById(R.id.editNome)).getText().toString();
        String editEmail = ((EditText) findViewById(R.id.editEmail)).getText().toString();
        String editTelefone = ((EditText) findViewById(R.id.editTelefone)).getText().toString();
        String editCelular = ((EditText) findViewById(R.id.editCelular)).getText().toString();
        String editLogradouro = ((EditText) findViewById(R.id.editLogradouro)).getText().toString();
        String editNumero = ((EditText) findViewById(R.id.editNumero)).getText().toString();
        String editComplemento = ((EditText) findViewById(R.id.editComplemento)).getText().toString();
        String editBairro = ((EditText) findViewById(R.id.editBairro)).getText().toString();
        String editCidade = ((EditText) findViewById(R.id.editCidade)).getText().toString();
        String editDtNascimento = ((EditText) findViewById(R.id.editDtNascimento)).getText().toString();
        String editLogin = ((EditText) findViewById(R.id.editLogin)).getText().toString();
        String editSenha = ((EditText) findViewById(R.id.editSenha)).getText().toString();
        String editConfirme = ((EditText) findViewById(R.id.editConfirmeASenha)).getText().toString();

        if (editSenha == null || editSenha.isEmpty()) {
            ((EditText) findViewById(R.id.editSenha)).setError("Campo Obrigatório!");
            return;
        }

        if (editConfirme == null || editConfirme.isEmpty()) {
            ((EditText) findViewById(R.id.editConfirmeASenha)).setError("Campo Obrigatório!");
            return;
        }

        if (editLogin == null || editLogin.isEmpty()) {
            ((EditText) findViewById(R.id.editNome)).setError("Campo Obrigatório!");
            return;
        }

        if (!editConfirme.equals(editSenha)) {
            Toast.makeText(this, "Confirmação de senha não confere com senha.", Toast.LENGTH_LONG).show();
            return;
        }

        if (editLogin == null || editLogin.isEmpty()) {
            ((EditText) findViewById(R.id.editNome)).setError("Campo Obrigatório!");
            return;
        }

        if (nome == null || nome.isEmpty()) {
            ((EditText) findViewById(R.id.editNome)).setError("Campo Obrigatório!");
            return;
        }

        if (editDtNascimento == null || editDtNascimento.isEmpty()) {
            ((EditText) findViewById(R.id.editDtNascimento)).setError("Campo Obrigatório!");
            return;
        }

        if (editEmail == null || editEmail.isEmpty()) {
            ((EditText) findViewById(R.id.editEmail)).setError("Campo Obrigatório!");
            return;
        }

        if(!isValidEmail(editEmail.trim())){
            ((EditText) findViewById(R.id.editEmail)).setError("Email inválido!");
            Toast.makeText(this,"Email inválido!",Toast.LENGTH_LONG).show();
            return;
        }

        if (editTelefone == null || editTelefone.isEmpty()) {
            editTelefone = " ";
        }

        if (editCelular == null || editCelular.isEmpty()) {
            ((EditText) findViewById(R.id.editCelular)).setError("Campo Obrigatório!");
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
            usuario.setDtNascimento(format.parse(editDtNascimento));
        }catch(ParseException e) {
            e.printStackTrace();
        }


        usuario.setPassword(editSenha);
        usuario.setLogin(editLogin);
        usuario.setEmail(editEmail);
        usuario.setTelefones(editTelefone+";"+editCelular);
        usuario.getEndereco().setLogradouro(editLogradouro);
        usuario.getEndereco().setNumero(editNumero);
        usuario.getEndereco().setBairro(editBairro);
        usuario.getEndereco().setCidade(editCidade);
        usuario.getEndereco().setComplemento(editComplemento);

        usuario.setStatus(false);

        EditarUsuarioTask task = new EditarUsuarioTask(this);

        task.execute(new Usuario[]{usuario});
    }

    private boolean isValidEmail(String emailInput) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailInput);
        return matcher.matches();
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            navToHome();
        }

        return super.onOptionsItemSelected(item);
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


