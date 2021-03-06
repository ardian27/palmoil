/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sawit;

import java.sql.Connection;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Ardi
 */
public class Normalisasi extends javax.swing.JFrame {

    /**
     * Creates new form Normalisasi
     */
    public Normalisasi() {
        initComponents();
        getData();
    }

    private void getData(){
    try {
        Connection conn =(Connection)Koneksi.koneksiDB();
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet sql = stm.executeQuery("select * from normalisasi");
        tbl_normalisasi.setModel(DbUtils.resultSetToTableModel(sql));
        stm.close();
    }
    catch (Exception e) {
        System.out.print("Gagal Get Tabel Normalisasi Data Sawit");
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

        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_normalisasi = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        combo_pembagian_data = new javax.swing.JComboBox<>();
        btn_normalisasi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sawit.SawitApp.class).getContext().getResourceMap(Normalisasi.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tbl_normalisasi.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        tbl_normalisasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID SAWIT ", "H", "S", "V", "TARGET"
            }
        ));
        tbl_normalisasi.setName("tbl_normalisasi"); // NOI18N
        jScrollPane3.setViewportView(tbl_normalisasi);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        combo_pembagian_data.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "70:30", "80:20", "90:10" }));
        combo_pembagian_data.setName("combo_pembagian_data"); // NOI18N

        btn_normalisasi.setText(resourceMap.getString("btn_normalisasi.text")); // NOI18N
        btn_normalisasi.setName("btn_normalisasi"); // NOI18N
        btn_normalisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_normalisasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(combo_pembagian_data, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(328, 328, 328)
                .addComponent(btn_normalisasi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combo_pembagian_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_normalisasi))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_normalisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_normalisasiActionPerformed
        // TODO add your handling code here:
        
        FunctionSawit fc = new FunctionSawit();
        
        String itemText = (String)combo_pembagian_data.getSelectedItem( );
        
        if (itemText=="70:30"){
            fc.data70Normalisasi();
            getData();
        }else if (itemText=="80:20"){
            fc.data80Normalisasi();
            getData();
        }else{
            fc.data90Normalisasi();
            getData();
            
        }
    }//GEN-LAST:event_btn_normalisasiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Normalisasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Normalisasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Normalisasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Normalisasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Normalisasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_normalisasi;
    private javax.swing.JComboBox<String> combo_pembagian_data;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbl_normalisasi;
    // End of variables declaration//GEN-END:variables
}
