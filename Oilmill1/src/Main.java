
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sathindu
 */
public class Main extends javax.swing.JFrame {
   
    int num = 0;
    int x,len,las;
    ArrayList<String> list= new ArrayList<String>();
    JFrame f; 
    String id,last;
    float amount;
    float sum = 0;
    int cashIn = 0;
    int cashOut = 0;
    boolean q;
    
    
  
    
    
    public Connection getconnection() throws ClassNotFoundException{
        Connection con = null;   
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1","admin","sathindu");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
     return con;       
    }
     public void getdata(){
        
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from sales");
            while(rs.next()){
                DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
                model.addRow(new Object[]{rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)});
            }
                
                        
        }catch(Exception e){
            System.out.println(e.getMessage());
            
        }
    }
     
      public static String getCount(String title) {
        JPanel panel = new JPanel();
        final JTextField text = new JTextField(10);
        panel.add(new JLabel("Count"));
        panel.add(text);
        JOptionPane pane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
            @Override
            public void selectInitialValue() {
                text.requestFocusInWindow();
            }
        };
        pane.createDialog(null, title).setVisible(true);
        return text.getText().length() == 0 ? null : new String(text.getText());
    }
      
      public void num1(){
          int num = Integer.parseInt(jTextField1.getText());
          if(num <= list.size() && num > 0){
              q = true;
                          
          }else{
              JOptionPane.showMessageDialog(null,"Wrong id");
              q = false;
              jTextField1.setText("");
          }
          
      }
     
     
    public void writedata(){
        
        
        System.out.println("work");
        DateFormat df = new SimpleDateFormat("yy/MM/dd");
        Date dateobj = new Date();
        

        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        
        for(int i = 0; i<model.getRowCount();i++){            
        las ++;                       
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO `sales` (`id`, `Date`, `ProId`, `Name`, `quntity`, `prize`) VALUES ('"+las+"', '"+df.format(dateobj)+"', '"+model.getValueAt(i,0)+"', '"+model.getValueAt(i,1)+"', '"+model.getValueAt(i,2)+"', '"+model.getValueAt(i,3)+"');";
            st.executeUpdate(insert);    
            System.out.println("inserted");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        }
        model.setRowCount(0);
    }
     public void getprize(){
         try{
             Connection con = getconnection();
             Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from prize");
            
            while (rs.next()) {
            list.add(rs.getString(2));
            }
            
          //  len = list.size();
             
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
        
    }
     
    public void search(){
        id = jTextField1.getText();
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM product WHERE ProId = "+id;
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
             jLabel2.setText(rs.getString(2));
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void getlast(){
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "select id from sales";
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
               last = rs.getString(1);            
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        las = Integer.parseInt(last);
        System.out.println(las);
    }
    
    public void prize(){
        x = Integer.parseInt(jTextField1.getText());
        
        int y = x - 1;
            double quntity = Float.valueOf(jTextField2.getText());
            double round = Math.round(quntity * 100.0) / 100.0;
            int value = Integer.parseInt(list.get(y));
            double totval = round * value;
            double roundOff = Math.round(totval * 100.0) / 100.0;
            DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{id,jLabel2.getText(),String.valueOf(round),String.valueOf(roundOff)});
            System.out.println(round);
            total();
            
        
    }
    
    public void chashIn(){
        DateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        Date dateobj = new Date();
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO `CashIn` (`date`, `CashIn`) VALUES ('"+df.format(dateobj)+"','"+cashIn +" ');";
            st.executeUpdate(insert);    
            System.out.println("inserted");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    public void chashout(){
        DateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        Date dateobj = new Date();
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO `CashOut` (`date`, `CashOut`) VALUES ('"+df.format(dateobj)+"','"+cashOut +" ');";
            st.executeUpdate(insert);    
            System.out.println("inserted");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }

    public void total(){
        sum = 0;        
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        for(int i= 0; i< model.getRowCount();i++){
            sum += Float.valueOf(model.getValueAt(i,3).toString());
        }
        jLabel7.setText(String.valueOf(sum));
       
   }    
   
   public void clear(){
       
       DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
       model.setRowCount(0);
       
   }
    
    

    /**
     * Creates new form main
     */
    public Main() {
        num = 0;
        initComponents(); 
        jTextField1.requestFocusInWindow();
       
        getprize();
        getlast();
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
            
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                printIt("Typed", keyEvent);
            }
            
        
            private void printIt(String title, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                String keyText = KeyEvent.getKeyText(keyCode);
                
                //JOptionPane.showMessageDialog(null, keyCode);
                System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
                if(keyEvent.getKeyCode()== KeyEvent.VK_ESCAPE){
                    Float count = Float.valueOf(Main.getCount(title));
                    
                    JOptionPane.showMessageDialog(null,"Balance is: "+(count - sum));
                    writedata();
                    jLabel2.setText("");
                    jLabel7.setText("Total");
                    sum = 0;
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_I){
                    cashIn = Integer.parseInt(Main.getCount(title));
                    chashIn();
                    jTextField1.setText("");
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_O){
                    cashOut = Integer.parseInt(Main.getCount(title));
                    chashout();
                    jTextField1.setText("");
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_V){
                    
                    getdata();
                    total();
                    jTextField1.setText("");
                    jTextField2.setText("");
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_C){
                    DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);
                    jLabel7.setText("Total");
                    jTextField1.setText("");
                    jTextField2.setText("");
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_S){
                    new summery().setVisible(true);
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_DOWN){
                    jTextField2.requestFocusInWindow(); 
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_UP){
                    jTextField1.requestFocusInWindow(); 
                }else if (keyEvent.getKeyCode()== KeyEvent.VK_C){
                    clear();
                    jLabel2.setText("");
                }
                
            }
        };
        jTextField1.addKeyListener(keyListener);        
        jTextField2.addKeyListener(keyListener);
           
       
        
       // writedata();
        //getdata();
       // 
      
    jTextField1.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) { 
        num1();
        if(q){
            search();   
        
      jTextField2.requestFocusInWindow(); 
        }
    }
    }); 
    jTextField2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                prize();
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField1.requestFocusInWindow(); 
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("New WellaMadda Oil Mill");

        jTable1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Item", "quntity", "price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(176, 176, 176));
        jTable1.setSelectionForeground(new java.awt.Color(60, 60, 60));
        jScrollPane1.setViewportView(jTable1);

        jTextField1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText("00");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel4.setText("Product Name : ");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel5.setText("Quntiti : ");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Total");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel9.setText("Given Amount : ");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("jLabel8");

        jTextField2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setText("0");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel6.setText("ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(jTextField2))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(278, 278, 278)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
                
                
            }          
            
            
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
