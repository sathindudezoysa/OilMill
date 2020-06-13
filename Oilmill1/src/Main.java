
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


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
    
    ArrayList<String> list= new ArrayList<String>();
    JFrame f;     
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
                model.addRow(new Object[]{rs.getString(3),jLabel2.getText(),rs.getString(4),rs.getString(5)});
            }
                
                        
        }catch(Exception e){
            System.out.println(e.getMessage());
            
        }
    }
     
      public static String getCount(String title) {
        JPanel panel = new JPanel();
        final JTextField text = new JTextField(10);
        panel.add(new JLabel("Count"));
        panel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(text);
        JOptionPane pane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION) {
            @Override
            public void selectInitialValue() {
                text.requestFocusInWindow();
                
            }
        };
        pane.createDialog(null, title).setVisible(true);
        return text.getText().length() == 0 ? null : new String(text.getText());
    }
     
      public void num1(int num){
         if(num <= list.size() && num > 0 ){
              q = true;
                          
          }else{
              JOptionPane.showMessageDialog(null,"Wrong id");
              q = false; 
              jTextField1.setText("");
          }
          
      }
     
     
    public void writedata(){
        
        DateFormat df = new SimpleDateFormat("yy/MM/dd ");
        Date dateobj = new Date();
        

        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        
        for(int i = 0; i<model.getRowCount();i++){  
            int x = this.getlast();
            x ++;
                              
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO `sales` (`id`, `Date`, `ProId`,`quntity`, `prize`) VALUES ('"+x+"', '"+df.format(dateobj)+"', '"+model.getValueAt(i,0)+"','"+model.getValueAt(i,1)+"', '"+model.getValueAt(i,2)+"');";
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
          
             
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
        
    }
     
    public void search(String id){
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
    public Integer getlast(){
        int last = 0;
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "select id from sales";
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
               last = rs.getInt(1);
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return last ;
    }
    
    public void prize(int x,double quntity){              
        int y = x - 1;            
            double round = Math.round(quntity * 100.0) / 100.0;
            int value = Integer.parseInt(list.get(y));
            double totval = round * value;
            double roundOff = Math.round(totval * 100.0) / 100.0;
            DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{String.valueOf(x),String.valueOf(quntity),String.valueOf(roundOff)});
            total();
            
        
    }
    
    public void chashIn(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        int b = 0;
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "select * from CashIn";
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
               b = rs.getInt(1);
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(b);
        b++;
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO `CashIn` (`id`,`Date`, `Value`) VALUES ('"+b+"','"+df.format(dateobj)+"','"+cashIn +"');";
            st.executeUpdate(insert);    
            System.out.println("inserted");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    public void chashout(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        int b = 0;
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "select * from CashOut";
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
               b = rs.getInt(1);
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(b);
        b++;
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO `CashOut` (`id`,`Date`, `Value`) VALUES ('"+b+"','"+df.format(dateobj)+"','"+cashOut +"');";
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
            sum += Float.valueOf(model.getValueAt(i,2).toString());
        }
        jLabel7.setText(String.valueOf(sum));
       
   }    
   
   public void clear(){
       
       DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
       model.setRowCount(0);
       
   }
   public void clean(){
       jTextField1.setText("");
       jTextField2.setText("");
       jLabel2.setText("");
       jLabel8.setText("");
       jLabel7.setText("");
   }
    
    

    /**
     * Creates new form main
     */
    public Main() {
       
        initComponents(); 
        
        clean();
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
              // printIt("Released", keyEvent);
            }
            
            @Override
            public void keyTyped(KeyEvent keyEvent) {
               // printIt("Typed", keyEvent);
            }
            
        
            private void printIt(String title, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                String keyText = KeyEvent.getKeyText(keyCode);
                
                //JOptionPane.showMessageDialog(null, keyCode);
                System.out.println(title + " : " + keyText + " / " + keyEvent.getKeyChar());
                if(keyEvent.getKeyCode()== KeyEvent.VK_ADD){
                    Float count = Float.valueOf(Main.getCount(title));
                    jLabel8.setText(String.valueOf(count));
                    JLabel label = new JLabel("Balance is: "+(count - sum));
                    label.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null,label,"Balance",JOptionPane.WARNING_MESSAGE);
                    //JOptionPane.showMessageDialog(null,"Balance is: "+(count - sum));
                    writedata();
                    clean();
                    sum = 0;
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_DIVIDE){
                    cashIn = Integer.parseInt(Main.getCount(title));
                    chashIn();
                    clean();
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_MULTIPLY){
                    cashOut = Integer.parseInt(Main.getCount(title));
                    chashout();
                    clean();
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_V){                      
                    getdata();
                   
                    clean();
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_C){                    
                    DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);
                    clean();
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_S){                    
                    new summery().setVisible(true);
                    clean();                
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_DOWN){
                    jTextField2.requestFocusInWindow(); 
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_UP){
                    jTextField1.requestFocusInWindow(); 
                }else if (keyEvent.getKeyCode()== KeyEvent.VK_C){
                    clear();
                    clean();                   
                }else if (keyEvent.getKeyCode()== KeyEvent.VK_RIGHT){
                    jButton1.requestFocusInWindow();
                    
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
        if(jTextField1.getDocument().getLength() != 0){
        num1(Integer.parseInt(jTextField1.getText()));
        if(q){
            search(jTextField1.getText());       
            jTextField2.requestFocusInWindow();       //clean();
        }
        }else if(jLabel7.getPreferredSize().getWidth() != 0){
            Float count = Float.valueOf(Main.getCount("Count is "));
                    jLabel8.setText(String.valueOf(count));
                    JLabel label = new JLabel("Balance is: "+(count - sum));
                    label.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null,label,"Balance",JOptionPane.WARNING_MESSAGE);
                    //JOptionPane.showMessageDialog(null,"Balance is: "+(count - sum));
                    writedata();
                    clean();
                    sum = 0;
       // JOptionPane.showMessageDialog(null,"Enter Id");
       
    }else{
            JOptionPane.showMessageDialog(null,"Enter Id");
        }
            
    }
    }); 
    jTextField2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(jTextField2.getDocument().getLength() != 0 ){
                prize(Integer.parseInt(jTextField1.getText()),Float.valueOf(jTextField2.getText()));
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField1.requestFocusInWindow(); 
            }else {
            JOptionPane.showMessageDialog(null,"Enter value");
       
            }
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
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(154, 154, 154));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("New WellaMadda Oil Mill");

        jTable1.setAutoCreateColumnsFromModel(false);
        jTable1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "quntity", "price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel5.setText("Quntiti : ");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 55)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel9.setText("Given Amount : ");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("jLabel8");

        jTextField2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setText("0");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel6.setText("ID");

        jButton1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jButton1.setText("Shutdown");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel10.setText("Total :-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(117, 117, 117)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(140, 140, 140)
                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel5))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(260, 260, 260)
                                                .addComponent(jLabel9))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(430, 430, 430)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jButton1)))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel2)))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel3))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jButton1))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec("shutdown -h now");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.exit(0);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
