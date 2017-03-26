package br.com.sindquimica.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.sindquimica.R;
import br.com.sindquimica.delegate.LoginDelegate;
import br.com.sindquimica.task.LoginTask;
import br.developersd3.sindquimica.ws.Usuario;
import br.com.sindquimica.util.Data;

public class LoginAct extends AppCompatActivity implements LoginDelegate{

    int MY_REQUEST_CODE = 999;

    Button btnLoginEntrar;

    TextView registrarUsuarioBtn;

    ProgressDialog ringProgressDialog;

    TextView esqueciASenhaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


         if (ContextCompat.checkSelfPermission(LoginAct.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    MY_REQUEST_CODE);
        }

        registrarInteracao();
    }

    @Override
    public void carregaDialog() {
        ringProgressDialog= ProgressDialog.show(this,"Aguarde...","");
        ringProgressDialog.show();
    }

    @Override
    public void login(Usuario usuario) {
        ringProgressDialog.dismiss();

        if(usuario != null) {

            Data.insertUsuario(PreferenceManager.getDefaultSharedPreferences(this),usuario);

            if (usuario.getStatus()) {
                Toast.makeText(this, "Login Realizado com sucesso!", Toast.LENGTH_LONG).show();
                navToHome();

            }else{
                Toast.makeText(this, "Usuário Bloqueado!Aguarde o desbloqueio pelo Administrador.", Toast.LENGTH_LONG).show();
                return;
            }

        }else{
            Toast.makeText(this, "Login Inválido!", Toast.LENGTH_LONG).show();
        }
    }

    private void login() {

        String token = Data.getToken(PreferenceManager.getDefaultSharedPreferences(this));

        String email = ((EditText) findViewById(R.id.editTextUsuario)).getText().toString();
        String senha = ((EditText) findViewById(R.id.editTextSenha)).getText().toString();

        if (email == null || email.isEmpty()) {
            Toast.makeText(this,"Usuário inválido",Toast.LENGTH_LONG).show();
            return;
        }

        if (senha == null || senha.isEmpty()) {
            Toast.makeText(this,"A senha obrigatória",Toast.LENGTH_LONG).show();
            return;
        }

        Usuario user = new Usuario();

        user.setLogin(email);
        user.setPassword(senha);
        user.setToken(token);

        LoginTask task = new LoginTask(this);

        task.execute(user);

    }

    private void registrarInteracao(){

        btnLoginEntrar = (Button) findViewById(R.id.btnLoginEntrar);

        registrarUsuarioBtn = (TextView) findViewById(R.id.registrarUsuarioBtn);

        btnLoginEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();

            }
        });


        registrarUsuarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navToRegistro();

            }
        });

        esqueciASenhaBtn = (TextView) findViewById(R.id.esqueciASenhaBtn);

        esqueciASenhaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToEsqueciASenha();
            }
        });

    }

    private void navToEsqueciASenha(){

        Intent i = new Intent(this, EsqueciSenhaAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }


    private void navToRegistro(){

        Intent i = new Intent(this, UsuarioAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }

    private void navToHome(){

        Intent i = new Intent(this, HomeAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);
    }


}


