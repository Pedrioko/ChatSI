/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import static java.lang.Thread.yield;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import logica.Controller;
import logica.Mensaje;
import logica.Usuario;

/**
 *
 * @author PedroD
 */
public class JFChat extends javax.swing.JFrame {

    private Controller control = new Controller();
    private static ArrayList<Mensaje> msnlist = new ArrayList<>();
    private JFBasedatos jfbd = new JFBasedatos();
    private final Icon icono = new ImageIcon(getClass().getResource("/resources/icono_informacion.png"));
    private final Icon online = new ImageIcon(getClass().getResource("/resources/online.png"));
    private final Icon offline = new ImageIcon(getClass().getResource("/resources/offline.png"));

    private Usuario user;

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     *
     * @throws Exception
     */
    public JFChat() throws Exception {
        initComponents();

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                close();
            }
        });

    }

    /**
     *
     */
    public void actualizarPantalla() {
        SwingUtilities.updateComponentTreeUI(this);
        this.validateTree();
    }

    public void start() {
        contactos();
        jfbd.colocarDatos(control.getMsnencryp());
        new Thread() {
            @Override
            public synchronized void run() {
                try {
                    msnlist = control.recibirMSN();
                } catch (Exception ex) {
                    Logger.getLogger(JFChat.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Mensaje mensaje : msnlist) {
                    JTAchattexto.setText(JTAchattexto.getText() + mensaje.getUsuario() + ": " + mensaje.getMensaje() + "\n");
                    yield();
                }
                for (;;) {

                    try {
                        Thread.sleep(5000);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(JFChat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Mensaje> msnlist1 = null;
                    try {
                        msnlist1 = control.recibirMSN();
                    } catch (Exception ex) {
                        Logger.getLogger(JFChat.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    JTAchattexto.setText("");
                    for (Mensaje mensaje : msnlist1) {
                        JTAchattexto.setText(JTAchattexto.getText() + mensaje.getUsuario() + ": " + mensaje.getMensaje() + "\n");
                        yield();
                    }
                    jfbd.colocarDatos(control.getMsnencryp());
                    contactos();

                    JTAchattexto.setCaretPosition(JTAchattexto.getDocument().getLength());

                }
            }
        }.start();
        this.validate();
    }

    private void close() {
        if (JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente salir del chat?", "Salir del sistema", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icono) == JOptionPane.YES_OPTION) {
            control.salir(user);
            System.exit(0);
        }
    }

    private void contactos() {
        ArrayList<Usuario> users;
        try {
            users = control.conectados();
            Map<Object, Icon> icons = new HashMap<Object, Icon>();
            DefaultListModel listModel;
            listModel = new DefaultListModel();
            for (Usuario user1 : users) {

                if (user1.getEstado() == 1) {
                    System.out.println("user1 -- " + user1);
                    icons.put(user1.getNombre(), online);
                } else {
                    System.out.println("user1  -----" + user1);
                    icons.put(user1.getNombre(), offline);
                }

                listModel.addElement(user1.getNombre());
            }

            JLTUsarios.setModel(listModel);
            JLTUsarios.setCellRenderer(new IconListRenderer(icons));
            JLTUsarios.setFocusable(false);

        } catch (Exception ex) {
            Logger.getLogger(JFChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        JTAchattexto = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTAtextoenviar = new javax.swing.JTextArea();
        JBenviar = new javax.swing.JButton();
        JLnickname = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        JLTUsarios = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Seguro");
        setResizable(false);

        JTAchattexto.setEditable(false);
        JTAchattexto.setColumns(20);
        JTAchattexto.setRows(5);
        JTAchattexto.setFocusable(false);
        jScrollPane1.setViewportView(JTAchattexto);

        JTAtextoenviar.setColumns(20);
        JTAtextoenviar.setRows(2);
        jScrollPane2.setViewportView(JTAtextoenviar);

        JBenviar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        JBenviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_letter.png"))); // NOI18N
        JBenviar.setText("Enviar");
        JBenviar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JBenviar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        JBenviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBenviarActionPerformed(evt);
            }
        });

        JLnickname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JLnickname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/man-42934_640.png"))); // NOI18N
        JLnickname.setText("Nick Name");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Chat.png"))); // NOI18N
        jLabel1.setText("Chat");

        jButton1.setText("Ver base de datos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        JLTUsarios.setFocusable(false);
        jScrollPane3.setViewportView(JLTUsarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JBenviar, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JLnickname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JLnickname)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JBenviar, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JBenviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBenviarActionPerformed
        if (!JTAtextoenviar.getText().isEmpty() && !JTAtextoenviar.getText().trim().isEmpty()) {

            String msn;
            msn = JTAtextoenviar.getText();

            try {
                control.enviarMSN(user, msn);
            } catch (Exception ex) {
                Logger.getLogger(JFChat.class.getName()).log(Level.SEVERE, null, ex);
            }
            JTAtextoenviar.setText("");
            JTAtextoenviar.requestFocus();
        } else {
            JTAtextoenviar.setText("");
            JTAtextoenviar.requestFocus();
        }

    }//GEN-LAST:event_JBenviarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jfbd.setVisible(!jfbd.isVisible());
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     *
     * @param nickname
     */
    public void nickName(String nickname) {
        JLnickname.setText(nickname);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBenviar;
    private javax.swing.JList JLTUsarios;
    private javax.swing.JLabel JLnickname;
    private javax.swing.JTextArea JTAchattexto;
    private javax.swing.JTextArea JTAtextoenviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
