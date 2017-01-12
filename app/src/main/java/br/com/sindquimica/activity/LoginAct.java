package br.com.sindquimica.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import br.com.martinlabs.commons.android.MLActivity;
import br.com.martinlabs.commons.android.OpResponse;
import br.com.martinlabs.commons.android.SecurityUtils;
import br.com.martinlabs.commons.android.Validator;
import br.com.sindquimica.R;
import br.com.sindquimica.model.Usuario;
import br.com.sindquimica.process.ProcessServices;

public class LoginAct extends MLActivity {

    int MY_REQUEST_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerInteraction();

         if (ContextCompat.checkSelfPermission(LoginAct.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    MY_REQUEST_CODE);
        }
    }

    private void login() {

        String email = ((EditText) f(R.id.editTextUsuario)).getText().toString();
        String senha = ((EditText) f(R.id.editTextSenha)).getText().toString();

        if (email == null || email.isEmpty()) {
            uiToast("Usuário inválido",false);
            return;
        }

        if (senha == null || senha.isEmpty()) {
            uiToast("A senha obrigatória",false);
            return;
        }

        OpResponse<Usuario> resp = ProcessServices.getSessionInstance().login(senha,email, this);

        if (resp.isSuccess() && (resp.getData() != null && resp.getData().getNome() != null)) {

            if(resp.getData().getStatus()) {

                uiToast("Login realizado com sucesso!", true);
                NavHome();
            }else{
                uiToast("Usuário Bloqueado.Fale com o Administrador!", false);

            }

        } else {
            uiToast("Login inválido!",true);
        }

    }


    private void NavHome() {
        startActivityClearTask(HomeAct.class);
    }

    private void NavRegistrar() {
        startActivityClearTask(UsuarioAct.class);
    }

    private void navEsqueciSenha()
    {
        startActivity(EsqueciSenhaAct.class);
    }

    private void registerInteraction() {


        f(R.id.btnLoginEntrar).setOnClickListener(v -> {
            queueLoading(R.string.carregando, () -> login());
        });

        f(R.id.registrarUsuarioBtn).setOnClickListener(v -> {
            NavRegistrar();
        });

    }

}


