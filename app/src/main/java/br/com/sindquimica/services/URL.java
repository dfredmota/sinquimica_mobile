package br.com.sindquimica.services;


/**
 * Created by gil on 3/7/16.
 */
public class URL {

    //private static String ws = "http://192.168.56.1:8080/DNACoachServer/";
    private static String ws = "http://192.168.25.162:8080/sindquimica";
   //private static String ws = "http://192.175.112.170:7825/sindquimica-1/";


    private static String concat(String str) {
        return ws + str;
    }
    public static String login() {return concat("/Login");}
    public static String registrarUsuario() {return concat("/RegistrarUsuario");}
    public static String carregaEmpresasAssociadas() {return concat("/ListaEmpresaAssociadas");}
    public static String carregaMensagensUsuario() {return concat("/Mensagens");}
    public static String carregaGruposUsuario() {return concat("/Grupos");}
}
