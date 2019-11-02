/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author PedroD
 */
public class Mensaje {

    private String usuario;
    private String mensaje;

    /**
     *
     * @param usuario
     * @param mensaje
     */
    public Mensaje(String usuario, String mensaje) {
        this.usuario = usuario;
        this.mensaje = mensaje;
    }

    /**
     *
     * @return
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     *
     * @param mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "usuario=" + usuario + ", mensaje=" + mensaje + '}';
    }

}
