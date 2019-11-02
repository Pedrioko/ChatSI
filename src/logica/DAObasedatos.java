/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author PedroD
 */
public class DAObasedatos {

    private static final String url_new_msn = "http://asturianaltda.tk/SI/envia_mensajes.php";
    private static final String url_all_msn = "http://asturianaltda.tk/SI/recibir_mensajes.php";
    private static final String url_registar = "http://asturianaltda.tk/SI/registrar.php";
    private static final String url_loguear = "http://asturianaltda.tk/SI/loguear.php";
    private static final String url_salir = "http://asturianaltda.tk/SI/salir.php";
    private static final String url_users = "http://asturianaltda.tk/SI/users.php";
    private JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER = "usuario";
    private static final String TAG_MSN = "mensaje";
    private static final String TAG_MSNS = "mensajes";
    private JSONArray MSMS = null;
    private ArrayList<Mensaje> msnList = new ArrayList<>();
    private EncriptadorRC4 rc4;

    public DAObasedatos() {
    }

    public void enviarMSN(Usuario user, String msn) throws Exception {

        // Building Parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("usuario", String.valueOf(user.getId())));
        params.add(new BasicNameValuePair("mensaje", msn));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_new_msn, "POST", params);

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // successfully created product
                System.out.println("YOLO");

            } else {
                System.err.println("#YOLO");
            }
        } catch (JSONException e) {
        }
    }

    public ArrayList<Mensaje> recibirMSN() throws Exception {
        msnList = new ArrayList<>();
        // Building Parameters
        List<NameValuePair> params = new ArrayList<>();
        // getting JSON string from URL
        JSONObject json = jsonParser.makeHttpRequest(url_all_msn, "GET", params);

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // MSMS found
                // Getting Array of Products
                MSMS = json.getJSONArray(TAG_MSNS);

                // looping through All Products
                for (int i = 0; i < MSMS.length(); i++) {
                    JSONObject c = MSMS.getJSONObject(i);

                    // Storing each json item in variable
                    String usuario = c.getString(TAG_USER);
                    String mensaje = c.getString(TAG_MSN);

                    System.out.println(mensaje);
                    Mensaje newMSN = new Mensaje(usuario, mensaje);

                    // adding to ArrayList
                    msnList.add(newMSN);
                }
            } else {
                // no MSMS found

            }
        } catch (JSONException e) {
        }
        return msnList;
    }

    public int registrarUsuario(Usuario user) {

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("email", user.getEmail()));
        params.add(new BasicNameValuePair("nombre", user.getNombre()));
        params.add(new BasicNameValuePair("username", user.getUsername()));
        params.add(new BasicNameValuePair("password", user.getPassword()));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_registar, "POST", params);

        // check for success tag
        int success = -1;
        try {
            success = json.getInt(TAG_SUCCESS);

        } catch (JSONException e) {
        }

        return success;
    }

    public int logueoUsuario(Usuario user, String ssap, EncriptadorRC4 rc4) {
        this.rc4 = rc4;
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", user.getUsername()));
        params.add(new BasicNameValuePair("password", user.getPassword()));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_loguear, "GET", params);

        // check for success tag
        int success = -11;
        try {
            success = json.getInt(TAG_SUCCESS);

        } catch (JSONException e) {
        }

        if (success == 1) {
            try {
                JSONArray userobj = json.getJSONArray("user");
                JSONObject userbd = userobj.getJSONObject(0);
                user.setId(Integer.parseInt(userbd.getString("id")));
                user.setEmail(userbd.getString("email"));
                user.setNombre(userbd.getString("nombre"));
                user.setNombre(this.rc4.decrypt(new sun.misc.BASE64Decoder().decodeBuffer(user.getNombre()), ssap));
            } catch (Exception e) {

            }

        }
        return success;
    }

    public int salirUsuario(Usuario user) {

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", user.getUsername()));
        params.add(new BasicNameValuePair("password", user.getPassword()));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_salir, "POST", params);

        // check for success tag
        int success = -11;
        try {
            success = json.getInt(TAG_SUCCESS);

        } catch (JSONException e) {
        }

        return success;
    }

    public ArrayList<Usuario> usuarios() {
        System.out.println("sigo vivo ");
        ArrayList<Usuario> users = new ArrayList<>();
        JSONArray jsonusers = new JSONArray();
        // Building Parameters
        List<NameValuePair> params = new ArrayList<>();
        // getting JSON string from URL
        JSONObject json = jsonParser.makeHttpRequest(url_users, "GET", params);
        System.out.println("sigo vivo 1");
        try {
            System.out.println("sigo vivo 2");
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);
            System.out.println("sigo vivo 3");
            if (success == 1) {
                // MSMS found
                // Getting Array of Products
                jsonusers = json.getJSONArray("usuarios");

                // looping through All Products
                for (int i = 0; i < jsonusers.length(); i++) {
                    JSONObject c = jsonusers.getJSONObject(i);

                    // Storing each json item in variable
                    String usuario = c.getString("nombre");
                    String conectado = c.getString("conectado");

                    Usuario user = new Usuario();
                    user.setNombre(usuario);
                    user.setEstado(Integer.parseInt(conectado));

                    // adding to ArrayList
                    users.add(user);
                }

            } else {
                // no MSMS found

            }
            System.out.println("sigo vivo ");
        } catch (JSONException e) {
        }

        System.out.println("user " + users);
        return users;
    }
}
