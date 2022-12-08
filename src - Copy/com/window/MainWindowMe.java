package com.window;

import com.media.Audio;
import com.media.Gambar;
import com.users.Users;
import com.window.dialogs.ConfirmLogout;
import com.window.panels.DataBarang;
import com.window.panels.DataPembeli;
import com.window.panels.DataSupplier;
import com.window.panels.LaporanBeli;
import com.window.panels.LaporanJual;
import com.window.panels.TransaksiBeli;
import com.window.panels.TransaksiJual;
import com.window.panels.Dashboard;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Achmad Baihaqi
 */
public class MainWindowMe extends javax.swing.JFrame {


    private final Users user = new Users();
    
    private final Dashboard dashboard = new Dashboard();
    
    private JLabel activated;
    
    private JLabel[] btns;
    
    public MainWindowMe() throws ParseException {
        initComponents();
        
        this.setTitle("Dashboard");
        this.setIconImage(Gambar.getWindowIcon());
//        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.lblNamaUser.setText(this.user.getCurrentLogin().toUpperCase());
        this.btns = new JLabel[]{
            this.btnDashboard, this.btnSupplier, this.btnBarang,
            this.btnTrJual, this.btnTrBeli, this.btnLpJual, this.btnLpBeli, this.btnLogout
        };
        this.activated = this.btnDashboard;
        
        // reset window
        this.pnlMenu.removeAll();
        this.pnlMenu.repaint();
        this.pnlMenu.revalidate();
        // menampilkan ulang
        this.pnlMenu.add(new com.window.panels.Dashboard());
        this.pnlMenu.repaint();
        this.pnlMenu.revalidate();
        
        this.setActivatedButton(btnDashboard);
        this.hoverButton(btns);
        this.setResizable(false);
    }
    
    private void setActivatedButton(JLabel activated){
        this.activated = activated;
        // set menjadi activated
//        activated.setOpaque(true);
//       activated.setOpaque(true);
//       activated.setBackground(new Color(166,203,227));
//       activated.setForeground(new Color(0,0,0));
//       activated.setIcon("");
        // mereset warna button/label
//        for(JLabel btn : btns){
//            if(btn != this.activated){
//                btn.setOpaque(false);
//                btn.setOpaque(false);
//                btn.setBackground(new Color(0,0,0,0));
//                btn.setForeground(new Color(255,255,255));
//                if(Gambar.isDarkIcon(btn.getIcon().toString())){
//                    btn.setIcon(Gambar.getWhiteIcon(btn.getIcon().toString()));                    
//                }
//            }
//            
//        }
    }
    
    private void hoverButton(JLabel[] btns){
        
        for(JLabel btn : btns){
            btn.addMouseListener(new java.awt.event.MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mousePressed(MouseEvent e) {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent e) {
//                    if(btn != activated){
//                        btn.setOpaque(true);
//                        btn.setForeground(new Color(0,0,0));
//                        btn.setBackground(new Color(96,167,231));
//                        btn.setIcon(Gambar.getDarkIcon(btn.getIcon().toString()));
//                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
//                    if(btn != activated){
//                        btn.setOpaque(false);
//                        btn.setForeground(new Color(255,255,255));
//                        btn.setBackground(new Color(0,0,0,0));
//                        btn.setIcon(Gambar.getWhiteIcon(btn.getIcon().toString()));
//                    }
                }
            });
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pnlMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        lblNamaUser = new javax.swing.JLabel();
        btnBarang = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JLabel();
        btnPembeli = new javax.swing.JLabel();
        btnSupplier = new javax.swing.JLabel();
        btnTrBeli = new javax.swing.JLabel();
        btnTrJual = new javax.swing.JLabel();
        btnLpJual = new javax.swing.JLabel();
        btnLpBeli = new javax.swing.JLabel();
        btnLogout = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMenu.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlMain.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 990, -1));

        lblNamaUser.setForeground(new java.awt.Color(0, 0, 0));
        lblNamaUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlMain.add(lblNamaUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 50, 50, 16));

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-dataBarang-075.png"))); // NOI18N
        btnBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBarangMouseClicked(evt);
            }
        });
        pnlMain.add(btnBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 170, -1, -1));

        btnDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Dashboard-075.png"))); // NOI18N
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardMouseClicked(evt);
            }
        });
        pnlMain.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 105, -1, -1));

        btnPembeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-dataPembeli-075.png"))); // NOI18N
        btnPembeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPembeliMouseClicked(evt);
            }
        });
        pnlMain.add(btnPembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 235, -1, -1));

        btnSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-dataSupplier-075.png"))); // NOI18N
        btnSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupplierMouseClicked(evt);
            }
        });
        pnlMain.add(btnSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 300, -1, -1));

        btnTrBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Transaksi_beli-075.png"))); // NOI18N
        btnTrBeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrBeliMouseClicked(evt);
            }
        });
        pnlMain.add(btnTrBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 365, -1, -1));

        btnTrJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Transaksi_jual-075.png"))); // NOI18N
        btnTrJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrJualMouseClicked(evt);
            }
        });
        pnlMain.add(btnTrJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 430, -1, -1));

        btnLpJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Laporan_pemasukan-075.png"))); // NOI18N
        btnLpJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLpJualMouseClicked(evt);
            }
        });
        pnlMain.add(btnLpJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 495, -1, -1));

        btnLpBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Laporan_pengeluaran-075.png"))); // NOI18N
        btnLpBeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLpBeliMouseClicked(evt);
            }
        });
        pnlMain.add(btnLpBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 570, -1, -1));

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-logout-075.png"))); // NOI18N
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });
        pnlMain.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 700, 80, 30));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-mainWindow-075.png"))); // NOI18N
        pnlMain.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, -1));

        jScrollPane1.setViewportView(pnlMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBarangMouseClicked
        //        this.lblMenuName.setText("Data Barang");
        this.setTitle("Data Barang");
//        this.setActivatedButton(this.btnBarang);

        // menghapus panel lama
        pnlMenu.removeAll();
        pnlMenu.repaint();
        pnlMenu.revalidate();

        // menambahkan panel baru
        pnlMenu.add(new DataBarang());
        pnlMenu.repaint();
        pnlMenu.revalidate();
    }//GEN-LAST:event_btnBarangMouseClicked

    private void btnDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseClicked
        try {
            //        this.lblMenuName.setText("Dashboard");
            this.setTitle("Dashboard");
            this.setActivatedButton(this.btnDashboard);
            
            // menghaspus panel lama
            pnlMenu.removeAll();
            pnlMenu.repaint();
            pnlMenu.revalidate();
            
            // menambahkan panel baru
            pnlMenu.add(new com.window.panels.Dashboard());
            pnlMenu.repaint();
            pnlMenu.revalidate();
        } catch (ParseException ex) {
            Logger.getLogger(MainWindowMe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDashboardMouseClicked

    private void btnPembeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPembeliMouseClicked
        //        this.lblMenuName.setText("Data Pembeli");
        this.setTitle("Data Pembeli");
        this.setActivatedButton(this.btnPembeli);

        // menghapus panel lama
        pnlMenu.removeAll();
        pnlMenu.repaint();
        pnlMenu.revalidate();

        // menambahkan panel baru
        pnlMenu.add(new DataPembeli());
        pnlMenu.repaint();
        pnlMenu.revalidate();
    }//GEN-LAST:event_btnPembeliMouseClicked

    private void btnSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupplierMouseClicked
        //        this.lblMenuName.setText("Data Supplier");
        this.setTitle("Data Supplier");
        this.setActivatedButton(this.btnSupplier);

        // menghapus panel lama
        pnlMenu.removeAll();
        pnlMenu.repaint();
        pnlMenu.revalidate();

        // menambahkan panel baru
        pnlMenu.add(new DataSupplier());
        pnlMenu.repaint();
        pnlMenu.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSupplierMouseClicked

    private void btnTrBeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrBeliMouseClicked
        //        this.lblMenuName.setText("Transaksi Beli");
        this.setTitle("Transaksi Beli");
        this.setActivatedButton(this.btnTrBeli);

        // menghapus panel lama
        pnlMenu.removeAll();
        pnlMenu.repaint();
        pnlMenu.revalidate();

        // menambahkan panel baru
        pnlMenu.add(new TransaksiBeli());
        pnlMenu.repaint();
        pnlMenu.revalidate();
    }//GEN-LAST:event_btnTrBeliMouseClicked

    private void btnTrJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrJualMouseClicked
        //        this.lblMenuName.setText("Transaksi Jual");
        this.setTitle("Transaksi Jual");
        this.setActivatedButton(this.btnTrJual);

        // menghapus panel lama
        pnlMenu.removeAll();
        pnlMenu.repaint();
        pnlMenu.revalidate();

        // menambahkan panel baru
        pnlMenu.add(new TransaksiJual());
        pnlMenu.repaint();
        pnlMenu.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTrJualMouseClicked

    private void btnLpJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLpJualMouseClicked
        //        this.lblMenuName.setText("Laporan Jual");
        this.setTitle("Laporan Jual");
        this.setActivatedButton(this.btnLpJual);

        // menghapus panel lama
        pnlMenu.removeAll();
        pnlMenu.repaint();
        pnlMenu.revalidate();

        try {
            // menambahkan panel baru
            pnlMenu.add(new LaporanJual());
        } catch (ParseException ex) {
            Logger.getLogger(MainWindowMe.class.getName()).log(Level.SEVERE, null, ex);
        }
        pnlMenu.repaint();
        pnlMenu.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLpJualMouseClicked

    private void btnLpBeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLpBeliMouseClicked
        //        this.lblMenuName.setText("Laporan Beli");
        this.setTitle("Laporan Beli");
        this.setActivatedButton(this.btnLpBeli);

        // menghapus panel lama
        pnlMenu.removeAll();
        pnlMenu.repaint();
        pnlMenu.revalidate();

        try {
            // menambahkan panel baru
            pnlMenu.add(new LaporanBeli());
        } catch (ParseException ex) {
            Logger.getLogger(MainWindowMe.class.getName()).log(Level.SEVERE, null, ex);
        }
        pnlMenu.repaint();
        pnlMenu.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLpBeliMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        Audio.play(Audio.SOUND_WARNING);
        new ConfirmLogout(this, true).setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindowMe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainWindowMe().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(MainWindowMe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel btnBarang;
    private javax.swing.JLabel btnDashboard;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnLpBeli;
    private javax.swing.JLabel btnLpJual;
    private javax.swing.JLabel btnPembeli;
    private javax.swing.JLabel btnSupplier;
    private javax.swing.JLabel btnTrBeli;
    private javax.swing.JLabel btnTrJual;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMenu;
    // End of variables declaration//GEN-END:variables
}
