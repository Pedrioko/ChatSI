/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author PedroD
 */
public class Start {

    private static JFlogin nn;

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        nn = new JFlogin();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/1430109762_Orange forum.png"));

                nn.setIconImage(image);
                nn.setVisible(true);

            }
        });
    }

    /**
     *
     * @throws Exception
     */
    public Start() throws Exception {

    }

    public static JFlogin getNn() {
        return nn;
    }

    public static void setNn(JFlogin nn) {
        Start.nn = nn;
    }

    
    
}
