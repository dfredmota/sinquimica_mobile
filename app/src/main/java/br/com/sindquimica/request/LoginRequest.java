package br.com.sindquimica.request;

/**
 * Created by developer on 6/30/16.
 */
public class LoginRequest {
    private String account, password;


    public LoginRequest(String senhaSha1, String email) {
        this.account = email;
        this.password = senhaSha1;
    }
}
