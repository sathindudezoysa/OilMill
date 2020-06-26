
import java.awt.Color;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sathindu
 */
public class Main1 extends javax.swing.JFrame {
    
    //ArrayList<String> list= new ArrayList<String>();
    JFrame f;     
    float amount;
    int cashIn = 0;
    int cashOut = 0;
    boolean q;
    printer pf = new printer();
    
    
    
  
    
    
    public Connection getconnection() throws ClassNotFoundException{
        Connection con = null;   
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1","user","sathindu");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
     return con;       
    }
    
     public void getdata(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();
        float cashin = 0;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"'";
            ResultSet rs = st.executeQuery(q);            
            while (rs.next()) {
                DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
                model.addRow(new Object[]{rs.getString(3),rs.getString(4),rs.getString(5)});
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
     }         
      public static String getCount(String title) {
        JPanel panel = new JPanel();
        final JTextField text = new JTextField(10);
        panel.add(new JLabel("Given Amount"));
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
     
      public void num1(String num){
         if(search(num)==""){
              JOptionPane.showMessageDialog(null,"Wrong id");
              q = false; 
              jTextField1.setText("");                                       
          }else{
              q = true; 
          }
          
      }
    public String print(){
        
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        DecimalFormat decim = new DecimalFormat("0.00");
        String bill= "";
        for(int i = 0; i<model.getRowCount();i++){  
                        
            String body =search((String) model.getValueAt(i,0))+"\t"+model.getValueAt(i,1)+"\t"+getprize(Integer.parseInt((String) model.getValueAt(i, 0)))+"\t"+model.getValueAt(i,2)+"\n"; 
            bill +=body;
            }
       return bill;
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
            String insert = "INSERT INTO `sales` (`id`, `Date`, `ProId`,`quntity`, `prize`) VALUES ('"+x+"','"+df.format(dateobj)+"', '"+model.getValueAt(i,0)+"','"+model.getValueAt(i,1)+"', '"+model.getValueAt(i,2)+"');";
            st.executeUpdate(insert);    
            System.out.println("inserted");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        }
        model.setRowCount(0);
    }
    
     public String getprize(int id){
         DecimalFormat decim = new DecimalFormat("0.00");
         double prize = 0;
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM prize WHERE ProId = "+id;
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
                prize = Float.valueOf(rs.getString(2));
             
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }        
        return decim.format(prize);
        
    }
     
    public String search(String id){
        String name = "";
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM product WHERE ProId = "+id;
            ResultSet rs = st.executeQuery(q);
            
            while(rs.next()){
                name = rs.getString(2);
             
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }        
        return name;
    }
    public void changecolour(String id){
        if(Integer.parseInt(id) == 1){
            jPanel2.setBackground(Color.red);
        }else if(Integer.parseInt(id) == 2){
            jPanel2.setBackground(Color.BLUE);
        }else if(Integer.parseInt(id) == 3){
            jPanel2.setBackground(Color.GREEN);
        }else if(Integer.parseInt(id) == 4){
            jPanel2.setBackground(Color.YELLOW);
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
        DecimalFormat decim = new DecimalFormat("0.00");
                
        double totval = 0;
            totval= Float.valueOf(decim.format(quntity)) * Float.valueOf(getprize(x));
            DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{String.valueOf(x),String.valueOf(decim.format(quntity)),String.valueOf(decim.format(totval))});
                
        
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
    public void chashout(double cash){
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
            String insert = "INSERT INTO `CashOut` (`id`,`Date`, `Value`) VALUES ('"+b+"','"+df.format(dateobj)+"','"+cash +"');";
            st.executeUpdate(insert);    
            System.out.println("inserted");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
    }

    public double total(){
        double sum = 0;        
        DecimalFormat decim = new DecimalFormat("0.00");
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        for(int i= 0; i< model.getRowCount();i++){
            sum += Float.valueOf(model.getValueAt(i,2).toString());
        }
        jTextField3.setText(decim.format(sum));
       return sum;
   }    
   
   public void clear(){
       
       DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
       model.setRowCount(0);
       
   }
   public void clean(){
       jTextField1.setText("");
       jTextField2.setText("");
       jTextField3.setText("");
       jTextField4.setText("");
       jLabel7.setText("");
      
   }

    /**
     * Creates new form Main1
     */
    public Main1() {
        initComponents();
         clean();      
        jTextField1.requestFocusInWindow();        
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
                if(keyEvent.getKeyCode()== KeyEvent.VK_I){
                    cashIn = Integer.parseInt(Main1.getCount(title));
                    chashIn();
                    clean();
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_O){                    
                    chashout(Float.valueOf(Main1.getCount(title)));
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
                         
                    
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_DOWN){
                    jTextField2.requestFocusInWindow(); 
                }else if(keyEvent.getKeyCode()== KeyEvent.VK_UP){
                    jTextField1.requestFocusInWindow(); 
                               
                }else if (keyEvent.getKeyCode()== KeyEvent.VK_RIGHT){
                    jButton1.requestFocusInWindow();
                    
                }
                
            }
        };
        jTextField1.addKeyListener(keyListener);        
        jTextField2.addKeyListener(keyListener);
        
        jTextField1.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) { 
        DecimalFormat decim = new DecimalFormat("0.00");
        if(jTextField1.getDocument().getLength() != 0){
        num1(jTextField1.getText());
        if(q){
            jLabel7.setText(search(jTextField1.getText()));            
            changecolour(jTextField1.getText());
            jTextField2.requestFocusInWindow();       //clean();
        }
        }else if(jTextField3.getDocument().getLength() != 0){
            String count = decim.format(Float.valueOf(Main1.getCount("Given Amount is: ")));
                    jTextField4.setText(count);
                    double bal = Float.valueOf(count)- total();
                    JLabel label = new JLabel("Balance is: "+String.valueOf(bal));
                    label.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null,label,"Balance",JOptionPane.WARNING_MESSAGE);
                    pf.setstring(print(),total(),Float.valueOf(count),bal);
                    pf.print();
                    writedata();
                    if(bal<0){
                        chashout(-bal);                       
                    }
                    clean();
                           
    }else {
            JOptionPane.showMessageDialog(null,"Enter Id");
        }
            
    }
    }); 
    jTextField2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(jTextField2.getDocument().getLength() != 0 ){
                prize(Integer.parseInt(jTextField1.getText()),Float.valueOf(jTextField2.getText()));
                total();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(49, 49, 49));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(166, 166, 166));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("New WallaMadda Oill Mill");

        jTable1.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Id", "Quntity", "Prize"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(138, 138, 138));
        jLabel2.setText("ID");

        jTextField1.setBackground(new java.awt.Color(72, 72, 72));
        jTextField1.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(254, 254, 254));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText("0");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(95, 95, 95));
        jLabel3.setText("Quntity");

        jTextField2.setBackground(new java.awt.Color(54, 54, 54));
        jTextField2.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(254, 254, 254));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setText("0");

        jButton1.setBackground(new java.awt.Color(254, 254, 254));
        jButton1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("ShutDown");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(1, 1, 1));
        jTextField3.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(254, 254, 254));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField3.setText("0.00");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(97, 97, 97));
        jLabel4.setText("Total");

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(1, 1, 1));
        jTextField4.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(254, 254, 254));
        jTextField4.setText("0.00");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(115, 115, 115));
        jLabel5.setText("Balance");

        jPanel2.setBackground(new java.awt.Color(254, 254, 254));
        jPanel2.setForeground(new java.awt.Color(254, 254, 254));

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(1, 1, 1));
        jLabel6.setText("Product :-");

        jLabel7.setBackground(new java.awt.Color(254, 254, 254));
        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(1, 1, 1));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Wellcome");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jTextField4))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec("shutdown -h now");
        } catch (IOException ex) {
            Logger.getLogger(Main1.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(Main1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
