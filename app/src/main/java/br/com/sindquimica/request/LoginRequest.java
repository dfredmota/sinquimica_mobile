package br.com.sindquimica.request;

/**
 * Created by developer on 6/30/16.
 */
public class LoginRequest {
    private String account, password,token;


    public LoginRequest(String senhaSha1, String email,String token) {
        this.account = email;
        this.password = senhaSha1;
        this.token    = token;
    }
}
