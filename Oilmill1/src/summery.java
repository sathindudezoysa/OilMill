
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sathindu
 */
public class summery extends javax.swing.JFrame {
    boolean x = false;

    // private Float total;
    public Connection getconnection() throws ClassNotFoundException {
        Connection con = null;        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1", "admin", "sathindu");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;        
    }
    public Float balance(){
        float last = 0;
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "select * from DayBalance";
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
               last = Float.valueOf(rs.getString(2));            
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        System.out.println(last);
        return last;
    }
    
    public void daybalance(){
        System.out.println("work");
        DateFormat df = new SimpleDateFormat("yy/MM/dd");
        Date dateobj = new Date();

        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO `DayBalance` (`date`,`balance`) VALUES ('"+df.format(dateobj)+"', '"+this.total()+"');";
            st.executeUpdate(insert);    
            System.out.println("inserted");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public Float searchlit() {
        float liter = 0;
        //  id = jTextField1.getText();
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM sales WHERE ProId = " + 1;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                liter += Float.valueOf(rs.getString(5));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return liter;
    }

    public Float searchlitPrize() {
        float litprize = 0;
        //  id = jTextField1.getText();
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM sales WHERE ProId = " + 1;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                litprize += Float.valueOf(rs.getString(6));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return litprize;
    }
    
    public Float searchbot() {
        float bottle = 0;
        //  id = jTextField1.getText();
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM sales WHERE ProId = " + 2;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                bottle += Float.valueOf(rs.getString(5));             
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return bottle;
    }

    public Float searchbotprize() {
        //  id = jTextField1.getText();
        float bottleprize = 0;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM sales WHERE ProId = " + 2;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                bottleprize += Float.valueOf(rs.getString(6));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return bottleprize;
    }
    
    public Float searchKilo() {
        //  id = jTextField1.getText();
        float kilo = 0;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM sales WHERE ProId = " + 3;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                kilo += Float.valueOf(rs.getString(5));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return kilo;
    }

    public Float searchKiloPrize() {
        //  id = jTextField1.getText();
        float kiloprize = 0;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM sales WHERE ProId = " + 3;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                kiloprize += Float.valueOf(rs.getString(6));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return kiloprize;
    }
    
    public Float searchPunak() {
        //  id = jTextField1.getText();
        float punak = 0;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM sales WHERE ProId = " + 4;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                punak += Float.valueOf(rs.getString(5));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return punak;
    }

    public Float searchPunakPrize() {
        float punakprize = 0;
        //  id = jTextField1.getText();
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM sales WHERE ProId = " + 4;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                punakprize += Float.valueOf(rs.getString(6));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return punakprize;
    }
    
    public Float getCashIn() {        
        float cashin = 0;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from CashIn");
            while (rs.next()) {                
                cashin += Float.valueOf(rs.getString(2));                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        
        return cashin;
    }

    public Float getCashOut() {
        float cashout = 0;        
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from CashOut");
            while (rs.next()) {                
                cashout += Float.valueOf(rs.getString(2));
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        
        return cashout;
    }
    
    public Float total(){
        float sum = 0;
        sum = (this.balance()+this.getCashIn()+this.searchlitPrize()+this.searchbotprize()+this.searchKiloPrize()+this.searchPunakPrize())- this.getCashOut();
        
        return sum;
    }
    
    Float totcash(){
        float subsum = 0;
        subsum = this.balance()+this.getCashIn()+this.searchlitPrize()+this.searchbotprize()+this.searchKiloPrize()+this.searchPunakPrize();
        return subsum;
    }

    /**
     * Creates new form summery
     */
    public summery() {
        initComponents();
        jLabel12.setText(String.valueOf(this.balance()));
        jLabel13.setText(String.valueOf(this.getCashIn()));    
        jLabel19.setText(String.valueOf(this.getCashOut()));
        jLabel21.setText(String.valueOf(this.searchlit()));
        jLabel22.setText(String.valueOf(this.searchbot()));
        jLabel23.setText(String.valueOf(this.searchKilo()));
        jLabel24.setText(String.valueOf(this.searchPunak()));
        jLabel14.setText(String.valueOf(this.searchlitPrize()));
        jLabel15.setText(String.valueOf(this.searchbotprize()));
        jLabel16.setText(String.valueOf(this.searchKiloPrize()));
        jLabel17.setText(String.valueOf(this.searchPunakPrize()));
        jLabel18.setText(String.valueOf(this.totcash()));
        jLabel20.setText(String.valueOf(this.total()));
            
        KeyListener keyListener;
        keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                printIt("Pressed", keyEvent);
                
            }
            
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                printIt("Released", keyEvent);
            }
            
            public void keyTyped(KeyEvent keyEvent) {
                printIt("Typed", keyEvent);
            }

            private void printIt(String title, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                String keyText = KeyEvent.getKeyText(keyCode);

                //JOptionPane.showMessageDialog(null, keyCode);
                System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
                if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                    jButton2.requestFocusInWindow();
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                    jButton1.requestFocusInWindow();
                }
                
            }
            
        };
        jButton1.addKeyListener(keyListener);
        jButton2.addKeyListener(keyListener);
        
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
               daybalance();
            }
            
        }); 
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(460, 625));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Day Summery");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel2.setText("Previous day balance :");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel3.setText("Cash In :");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel4.setText("Sales : -");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel5.setText("Liters : ");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel6.setText("Bottles :");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel7.setText("Kilo : ");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel8.setText("Punak : ");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel9.setText("total cash In :");

        jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel10.setText("Chash Out  : ");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel11.setText("Day Balance : ");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("0");

        jLabel13.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("0");

        jLabel14.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("0");

        jLabel15.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("0");

        jLabel16.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("0");

        jLabel17.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("0");

        jLabel18.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("0");

        jLabel19.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("0");

        jLabel20.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("0");

        jButton1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jButton2.setText("Print");

        jLabel21.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("0");

        jLabel22.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel22.setText("0");

        jLabel23.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("0");

        jLabel24.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel14)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel15)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel16)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel17)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(summery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(summery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(summery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(summery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new summery().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
