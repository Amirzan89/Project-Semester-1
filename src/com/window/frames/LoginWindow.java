package com.window.frames;

import com.data.app.Application;
import com.data.app.Log;
import com.error.AuthenticationException;
import com.manage.Message;
import com.media.Audio;
import com.media.Gambar;
import com.users.UserLevels;
import com.users.Users;
import com.window.MainWindowMe;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Digunakan untuk login bagi admin, petugas dan siswa.
 * 
 * @author Amirzan fikri 
 * @since 2020-11-22
 */
public class LoginWindow extends javax.swing.JFrame {

    private final Users user = new Users();
    private String idUser, password;
    private int x, y;
    
    public LoginWindow() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        this.setIconImage(Gambar.getWindowIcon());
//        this.lblKembali.setVisible(false);
//        this.lblNamaTeam.setText(Application.getAuthor());
//        this.lblLogoApp.setIcon(Gambar.scaleImage(new java.io.File("src\\resources\\image\\icons\\app-logo.png"), 160, 160));
//        this.btnLogin.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        inpUsername = new javax.swing.JTextField();
        lblMinimaze = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        inpPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("umkm\n");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inpUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        inpUsername.setOpaque(false);
        inpUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpUsernameActionPerformed(evt);
            }
        });
        pnlMain.add(inpUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 250, 30));

        lblMinimaze.setBackground(new java.awt.Color(50, 50, 55));
        lblMinimaze.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblMinimaze.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinimaze.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-login-minimaze.png"))); // NOI18N
        lblMinimaze.setOpaque(true);
        lblMinimaze.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimazeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMinimazeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMinimazeMouseExited(evt);
            }
        });
        pnlMain.add(lblMinimaze, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, 30, 28));

        lblClose.setBackground(new java.awt.Color(50, 51, 55));
        lblClose.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-login-close.png"))); // NOI18N
        lblClose.setOpaque(true);
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCloseMouseExited(evt);
            }
        });
        pnlMain.add(lblClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 31, 28));

        inpPassword.setBorder(null);
        inpPassword.setOpaque(false);
        inpPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpPasswordActionPerformed(evt);
            }
        });
        pnlMain.add(inpPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 250, 30));

        btnLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });
        pnlMain.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 363, 212, 25));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-login-050.png"))); // NOI18N
        pnlMain.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(pnlMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Log.addLog("Menutup Window " + getClass().getName());
        user.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Log.addLog("Menutup Window " + getClass().getName());
        user.closeConnection();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Log.addLog("Membuka Window " + getClass().getName());
    }//GEN-LAST:event_formWindowOpened

    private void inpUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpUsernameActionPerformed

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        Application.closeApplication();
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered
        this.lblClose.setIcon(Gambar.getIcon("ic-login-close-entered.png"));
    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited
        this.lblClose.setIcon(Gambar.getIcon("ic-login-close.png"));
    }//GEN-LAST:event_lblCloseMouseExited

    private void lblMinimazeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimazeMouseClicked
        this.setExtendedState(javax.swing.JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinimazeMouseClicked

    private void lblMinimazeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimazeMouseEntered
        this.lblMinimaze.setIcon(Gambar.getIcon("ic-login-minimaze-entered.png"));
    }//GEN-LAST:event_lblMinimazeMouseEntered

    private void lblMinimazeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimazeMouseExited
        this.lblMinimaze.setIcon(Gambar.getIcon("ic-login-minimaze.png"));
    }//GEN-LAST:event_lblMinimazeMouseExited

    private void inpPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpPasswordActionPerformed

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        try{
            idUser = this.inpUsername.getText();
            password = this.inpPassword.getText();
            boolean login = user.login(idUser,password);
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if(login){
                Audio.play(Audio.SOUND_INFO);
                JOptionPane.showMessageDialog(this, "Login Berhasil!\n\nSelamat datang "+ user.getData(UserLevels.KARYAWAN.name(),"nama_karyawan","WHERE id_karyawan = '" + this.idUser + "'"));
                // membuka window dashboard
                java.awt.EventQueue.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        try {
                            new MainWindowMe().setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                // menutup koneksi dan window
                user.closeConnection();
                this.dispose();
            }else{
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                // mereset textfield jika login gagal
                this.inpUsername.setText("");
                this.inpPassword.setText("");
            }
        }catch(IOException | AuthenticationException | SQLException ex){
            this.inpUsername.setText("");
            this.inpPassword.setText("");
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            // menampilkan pesan error
            Message.showException(this, ex, true);
        } catch (Exception ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLoginMouseClicked

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel btnLogin;
    private javax.swing.JPasswordField inpPassword;
    private javax.swing.JTextField inpUsername;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblMinimaze;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
