
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1","admin","Kasun@sathindu1");
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
            String insert = "INSERT INTO `sales` (`id`, `Date`, `ProId`,`quntity`, `prize`) VALUES ('"+x+"', '"+df.format(dateobj)+"', '"+model.getValueAt(i,1)+"','"+model.getValueAt(i,2)+"', '"+model.getValueAt(i,3)+"');";
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
             jLabel5.setText(rs.getString(2));
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        if(Integer.parseInt(id) == 1){
            jPanel4.setBackground(Color.red);
        }else if(Integer.parseInt(id) == 2){
            jPanel4.setBackground(Color.BLUE);
        }else if(Integer.parseInt(id) == 3){
            jPanel4.setBackground(Color.GREEN);
        }else if(Integer.parseInt(id) == 4){
            jPanel4.setBackground(Color.YELLOW);
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
        jTextField3.setText(String.valueOf(sum));
       
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
       jLabel5.setText("");
      
   }

    /**
     * Creates new form Main1
     */
    public Main1() {
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
                if(keyEvent.getKeyCode()== KeyEvent.VK_DIVIDE){
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
        if(jTextField1.getDocument().getLength() != 0){
        num1(Integer.parseInt(jTextField1.getText()));
        if(q){
            search(jTextField1.getText());       
            jTextField2.requestFocusInWindow();       //clean();
        }
        }else if(jTextField3.getDocument().getLength() != 0){
            Float count = Float.valueOf(Main.getCount("Count is "));
                    jTextField4.setText(String.valueOf(count));
                    JLabel label = new JLabel("Balance is: "+(count - sum));
                    label.setFont(new Font("Arial", Font.BOLD, 24));
                    JOptionPane.showMessageDialog(null,label,"Balance",JOptionPane.WARNING_MESSAGE);
                    //JOptionPane.showMessageDialog(null,"Balance is: "+(count - sum));
                    writedata();
                    clean();
                    sum = 0;             
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(55, 55, 55));
        jPanel1.setForeground(new java.awt.Color(42, 42, 42));

        jLabel1.setFont(new java.awt.Font("Ubuntu Mono", 2, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(254, 254, 254));
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

        jPanel2.setBackground(new java.awt.Color(56, 56, 56));

        jPanel3.setBackground(new java.awt.Color(60, 60, 60));

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(203, 203, 203));
        jLabel2.setText("ID :- ");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(152, 152, 152));
        jLabel3.setText("Quntity :- ");

        jTextField1.setBackground(new java.awt.Color(173, 173, 173));
        jTextField1.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(1, 1, 1));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText("0");

        jTextField2.setBackground(new java.awt.Color(185, 185, 185));
        jTextField2.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(1, 1, 1));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setText("0");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(254, 254, 254));
        jButton1.setFont(new java.awt.Font("Ubuntu Condensed", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("ShutDown");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jTextField2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Ubuntu Light", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(191, 191, 191));
        jLabel4.setText("Product : -");

        jPanel4.setBackground(new java.awt.Color(175, 0, 255));

        jLabel5.setFont(new java.awt.Font("Ubuntu Mono", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(1, 1, 1));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("5");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(63, 63, 63));

        jLabel6.setFont(new java.awt.Font("Ubuntu Light", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(220, 220, 220));
        jLabel6.setText("Total  : - ");

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(1, 1, 1));
        jTextField3.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(254, 254, 254));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField3.setText("00");

        jLabel7.setFont(new java.awt.Font("Ubuntu Light", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(185, 185, 185));
        jLabel7.setText("Balance :- ");

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(1, 1, 1));
        jTextField4.setFont(new java.awt.Font("Ubuntu Mono", 0, 36)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(254, 254, 254));
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField4.setText("00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
