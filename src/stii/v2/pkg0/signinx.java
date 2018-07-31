/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stii.v2.pkg0;

import dbconnection.dbconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Shawn
 */
public class signinx extends javax.swing.JFrame {

   
    
    Connection conn=null;
        PreparedStatement pst=null;
    public signinx() {
        initComponents();
        
        conn = dbconnect.dbcon();
        this.setLocationRelativeTo(null); //form to center
    }
    
    public boolean confrim_pass(String password,String Confirm_pass)
    {
        boolean x=false;
        if(password.equals(Confirm_pass))
        {
            x=true;
            erro_pass.setText("");
        }
        else
        {
            erro_pass.setText("Passwords Does Not Match");
        }
        return x;
        
    }
    public boolean check_email(String email){
        
        int ati=email.indexOf('@');
        int doti=email.lastIndexOf('.');
        if(ati>0 && doti>ati)
        {
            email_erro.setText("");
            return true;
        }
        else
        {
            email_erro.setText("This not a Valid Email");

            return false;
        }
        
    }
    
    public boolean check_nic(String nic){
        
        boolean y=false;
        
        if(nic.length()==10 && nic.endsWith("V"))
        {
            for(int i=0;i<9;i++)
            {
                if(Character.isDigit(nic.charAt(i)))
                {
                    y=true;
                }
                else
                {
                    nic_erro.setText("This is not a Valid NIC");
                    break;   
                }      
            }
        }
        else
        {
            nic_erro.setText("This is not a Valid NIC");    
        }
        return y;
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        namebox = new javax.swing.JTextField();
        nicbox = new javax.swing.JTextField();
        emailbox = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        nic_erro = new javax.swing.JLabel();
        email_erro = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        passwordbox = new javax.swing.JPasswordField();
        confrim_passbox = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        erro_pass = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        namebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameboxActionPerformed(evt);
            }
        });
        getContentPane().add(namebox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 180, 30));

        nicbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nicboxActionPerformed(evt);
            }
        });
        getContentPane().add(nicbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 180, 30));

        emailbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailboxActionPerformed(evt);
            }
        });
        getContentPane().add(emailbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 180, 30));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Name");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NIC");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Email");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Confirm Password");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jButton1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 310, 90, -1));

        jButton2.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton2.setText("Log In");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 320, 90, -1));

        nic_erro.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(nic_erro, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 290, 20));

        email_erro.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(email_erro, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, 280, 20));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Already Have an Account?");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, 240, 30));
        getContentPane().add(passwordbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 180, 30));

        confrim_passbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confrim_passboxActionPerformed(evt);
            }
        });
        getContentPane().add(confrim_passbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 180, 30));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Password");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        erro_pass.setForeground(new java.awt.Color(255, 0, 0));
        getContentPane().add(erro_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 280, 290, 20));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sign Up with the System");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 350, 40));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stii/v2/pkg0/um.jpg"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 370));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nameboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameboxActionPerformed

    private void nicboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nicboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nicboxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String name=namebox.getText();
        String nic=nicbox.getText();
        String email=emailbox.getText();
        String password=passwordbox.getText();
        String confrim_pass=confrim_passbox.getText();
        
        boolean email_ok=this.check_email(email);
        boolean nic_ok=this.check_nic(nic);
        boolean pass_ok=confrim_pass(password,confrim_pass);
        
        if(namebox.getText().equals("")||nicbox.getText().equals("")||emailbox.getText().equals("")||passwordbox.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Fill in all the Forms");}
       
        else 
        {
            if(email_ok==true && nic_ok==true && pass_ok==true)
            {
                try{
                    String q = "INSERT INTO customers (name,email,password,nic) values ('"+name+"','"+email+"','"+password+"','"+nic+"')";
                    pst=conn.prepareStatement(q);
                    pst.execute();
                    login s=new login();
                    s.setVisible(true);
                    this.dispose();
                    }

                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        login s=new login();
         s.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void emailboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailboxActionPerformed

    private void confrim_passboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confrim_passboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confrim_passboxActionPerformed

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
            java.util.logging.Logger.getLogger(signinx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(signinx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(signinx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(signinx.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new signinx().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField confrim_passbox;
    private javax.swing.JLabel email_erro;
    private javax.swing.JTextField emailbox;
    private javax.swing.JLabel erro_pass;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField namebox;
    private javax.swing.JLabel nic_erro;
    private javax.swing.JTextField nicbox;
    private javax.swing.JPasswordField passwordbox;
    // End of variables declaration//GEN-END:variables
}