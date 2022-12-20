package com.window;

import com.data.app.Log;
import com.media.Audio;
import com.media.Gambar;
import com.users.Users;
import com.window.dialogs.ConfirmLogout;
import com.window.panels.DataBarang;
import com.window.panels.DataSupplier;
import com.window.panels.LaporanBeli;
import com.window.panels.LaporanJual;
import com.window.panels.TransaksiBeli;
import com.window.panels.TransaksiJual;
import com.window.test.Dashboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Gemastik Lightning
 */
public class MainWindow extends javax.swing.JFrame {
    
    private final Users user = new Users();
    
    private final Dashboard dashboard = new Dashboard();
    
    private JLabel activated;
    
    private JLabel[] btns;
    
    public MainWindow() throws ParseException {
        initComponents();
        
        this.setTitle("Dashboard");
        this.setIconImage(Gambar.getWindowIcon());
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        this.lblNamaUser.setText(this.user.getCurrentLoginName());
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
        btnLogout = new javax.swing.JLabel();
        btnTrBeli = new javax.swing.JLabel();
        btnTrJual = new javax.swing.JLabel();
        btnLpBeli = new javax.swing.JLabel();
        btnSupplier = new javax.swing.JLabel();
        btnLpJual = new javax.swing.JLabel();
        btnBarang = new javax.swing.JLabel();
        lblNamaUser = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        pnlMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-logout-075.png"))); // NOI18N
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });
        pnlMain.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, 80, 30));

        btnTrBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Transaksi_beli-075.png"))); // NOI18N
        btnTrBeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrBeliMouseClicked(evt);
            }
        });
        pnlMain.add(btnTrBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 363, -1, -1));

        btnTrJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Transaksi_jual-075.png"))); // NOI18N
        btnTrJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrJualMouseClicked(evt);
            }
        });
        pnlMain.add(btnTrJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 428, -1, -1));

        btnLpBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Laporan_pengeluaran-075.png"))); // NOI18N
        btnLpBeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLpBeliMouseClicked(evt);
            }
        });
        pnlMain.add(btnLpBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 570, -1, -1));

        btnSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-dataSupplier-075.png"))); // NOI18N
        btnSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupplierMouseClicked(evt);
            }
        });
        pnlMain.add(btnSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 299, -1, -1));

        btnLpJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Laporan_pemasukan-075.png"))); // NOI18N
        btnLpJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLpJualMouseClicked(evt);
            }
        });
        pnlMain.add(btnLpJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 492, -1, -1));

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-dataBarang-075.png"))); // NOI18N
        btnBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBarangMouseClicked(evt);
            }
        });
        pnlMain.add(btnBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 170, -1, -1));
        pnlMain.add(lblNamaUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 50, 60, 20));

        btnDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar_icon/sidebar-Dashboard-075.png"))); // NOI18N
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardMouseClicked(evt);
            }
        });
        pnlMain.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 105, -1, -1));
        pnlMain.add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 0, 973, 769));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/gambar/app-mainWindow-075.png"))); // NOI18N
        pnlMain.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jScrollPane1.setViewportView(pnlMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

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
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDashboardMouseClicked

    private void btnBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBarangMouseClicked
        //        this.lblMenuName.setText("Data Barang");
        this.setTitle("Data Barang");
        this.setActivatedButton(this.btnBarang);

        // menghapus panel lama
        pnlMenu.removeAll();
        pnlMenu.repaint();
        pnlMenu.revalidate();

        // menambahkan panel baru
        pnlMenu.add(new DataBarang());
        pnlMenu.repaint();
        pnlMenu.revalidate();
    }//GEN-LAST:event_btnBarangMouseClicked

    private void btnLpJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLpJualMouseClicked
        try {
            //        this.lblMenuName.setText("Laporan Jual");
            this.setTitle("Laporan Jual");
            this.setActivatedButton(this.btnLpJual);
            
            // menghapus panel lama
            pnlMenu.removeAll();
            pnlMenu.repaint();
            pnlMenu.revalidate();
            
            // menambahkan panel baru
            pnlMenu.add(new LaporanJual());
            pnlMenu.repaint();
            pnlMenu.revalidate();
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLpJualMouseClicked

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
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        pnlMenu.repaint();
        pnlMenu.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLpBeliMouseClicked

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

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        Audio.play(Audio.SOUND_WARNING);
        new ConfirmLogout(this, true).setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutMouseClicked

    public static void main(String args[]) {
        Log.createLog();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainWindow().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel btnSupplier;
    private javax.swing.JLabel btnTrBeli;
    private javax.swing.JLabel btnTrJual;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMenu;
    // End of variables declaration//GEN-END:variables
}

class RoundedPanel extends JPanel{
    
    private Color backgroundColor;
    private int cornerRadius = 15;
    
    public RoundedPanel(LayoutManager layout, int radius){
        super(layout);
        cornerRadius = radius;
    }
    
    public RoundedPanel(LayoutManager layout, int radius, Color bgColor){
        super(layout);
        cornerRadius = radius;
        backgroundColor = bgColor;
    }
    
    public RoundedPanel(int radius){
        super();
        cornerRadius = radius;
    }
    
    public RoundedPanel(int radius, Color bgColor){
        super();
        cornerRadius = radius;
        this.backgroundColor = bgColor;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // mengambar rounded panel with borders
        if(backgroundColor != null){
            graphics.setColor(getBackground());
        }else{
            graphics.setColor(getBackground());
        }
        
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
    }
    
}
