/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author PedroD
 */
public class Controller {

    private ArrayList<Mensaje> msnList;
    private final EncriptadorRC4 rc4 = new EncriptadorRC4();
    private static final String ssap = "SeguInfo";
    private ArrayList<Mensaje> msnencryp;
    private DAObasedatos database;
    

    /**
     *
     */
    public Controller() {
        this.database = new DAObasedatos();
        this.msnencryp = new ArrayList<>();
        this.msnList = new ArrayList<>();
    }

    public ArrayList<Mensaje> getMsnencryp() {
        return msnencryp;
    }

    public void setMsnencryp(ArrayList<Mensaje> msnencryp) {
        this.msnencryp = msnencryp;
    }

    public int loguear(Usuario user) throws Exception {
        user.setUsername(new sun.misc.BASE64Encoder().encode(this.rc4.encrypt(user.getUsername(), ssap)));
        user.setPassword(new sun.misc.BASE64Encoder().encode(this.rc4.encrypt(user.getPassword(), ssap)));
        return this.database.logueoUsuario(user, ssap, rc4);
    }

    public void enviarMSN(Usuario user, String msn) throws Exception {
        msn = new sun.misc.BASE64Encoder().encode(this.rc4.encrypt(msn, ssap));
        this.database.enviarMSN(user, msn);
    }

    public ArrayList<Mensaje> recibirMSN() throws Exception {
        ArrayList<Mensaje> msns = this.database.recibirMSN();
        this.msnencryp = new ArrayList<>();
        for (Mensaje msn : msns) {
            this.msnencryp.add(new Mensaje(msn.getUsuario(), msn.getMensaje()));
            msn.setMensaje(this.rc4.decrypt(new sun.misc.BASE64Decoder().decodeBuffer(msn.getMensaje()), ssap));
            msn.setUsuario(this.rc4.decrypt(new sun.misc.BASE64Decoder().decodeBuffer(msn.getUsuario()), ssap));
        }
        return msns;
    }

    public int salir(Usuario user) {
        return this.database.salirUsuario(user);
    }

    public int registarUsuario(Usuario user) throws Exception {
        Usuario aux = new Usuario(user);
        aux.setNombre(new sun.misc.BASE64Encoder().encode(this.rc4.encrypt(aux.getNombre(), ssap)));
        aux.setUsername(new sun.misc.BASE64Encoder().encode(this.rc4.encrypt(aux.getUsername(), ssap)));
        aux.setEmail(new sun.misc.BASE64Encoder().encode(this.rc4.encrypt(aux.getEmail(), ssap)));
        aux.setPassword(new sun.misc.BASE64Encoder().encode(this.rc4.encrypt(aux.getPassword(), ssap)));
        return this.database.registrarUsuario(aux);
    }

    public ArrayList<Usuario> conectados() throws Exception {
        ArrayList<Usuario> aux = this.database.usuarios();
        for (Usuario user : aux) {
            user.setNombre(this.rc4.decrypt(new sun.misc.BASE64Decoder().decodeBuffer(user.getNombre()), ssap));
        }
        return aux;
    }

    public boolean verificaInternet() throws IOException {
        try {
            try {
                URL url = new URL("http://www.asturianaltda.tk");
                System.out.println(url.getHost());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();
                if (con.getResponseCode() == 200) {
                    System.out.println("Connection established!!");
                    return true;
                }
            } catch (Exception exception) {
                System.out.println("No Connection");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
