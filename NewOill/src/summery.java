
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class summery extends javax.swing.JFrame {
    
    boolean x = false;
    printer pf = new printer();
    DecimalFormat decim = new DecimalFormat("0.00");

    // private Float total;
    public Connection getconnection() throws ClassNotFoundException {
        Connection con = null;        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1","user","sathindu");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;        
    }
   
    public String gday(int x) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();    
    cal.add(Calendar.DATE, -x);
    return df.format(cal.getTime());
    }
    
    public Float balance(){
        float last = 0;
        int x= 0;
        do{
            x++;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `DayBalance` WHERE `date` = '"+gday(x)+"'";
            ResultSet rs = st.executeQuery(q);   
            while(rs.next()){
            last = Float.valueOf(rs.getString(13));          
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }while(last != 0 && x<=10 );
        
        return last;
    }
    
    public void daybalance(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("work");
        Calendar cal = Calendar.getInstance();   
        int lastid = 0;
        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `DayBalance`";
            ResultSet rs = st.executeQuery(q);
            while(rs.next()){               
               lastid = Integer.parseInt(rs.getString(1));
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        lastid++;

        try{
            Connection con = getconnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO `DayBalance` (`id`,`date`, `Bottles`, `BotPrise`, `Liters`, `LitPrise`, `Kilo`, `KillPrise`, `Punak`, `PunPrise`, `CashIn`, `CashOut`, `balance`) VALUES ('"+lastid+"','"+df.format(cal.getTime())+"','"+this.searchbot()+"','"+this.searchbotprize()+"','"+this.searchlit()+"','"+this.searchlitPrize()+"','"+this.searchKilo()+"','"+this.searchKiloPrize()+"','"+this.searchPunak()+"','"+this.searchPunakPrize()+"','"+this.getCashIn()+"','"+this.getCashOut()+"', '"+this.total()+"');";
            st.executeUpdate(insert);    
            System.out.println("inserted");
            
        }catch(Exception e){ System.out.println("work");
            System.out.println(e.getMessage());
        }
    }
    
    
    public Float searchlit() {
        float liter = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();    
        //  id = jTextField1.getText();
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"' AND `ProId` = 2 ";
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                liter += Float.valueOf(rs.getString(4));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return liter;
    }

    public Float searchlitPrize() {
        float litprize = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
       
        //  id = jTextField1.getText();
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"' AND `ProId` = 2 ";
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                litprize += Float.valueOf(rs.getString(5));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return litprize;
    }
    
    public Float searchbot() {
        
        float bottle = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        //  id = jTextField1.getText();
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"' AND `ProId` = 1 ";
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                bottle += Float.valueOf(rs.getString(4));             
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return bottle;
    }

    public Float searchbotprize() {
        
        //  id = jTextField1.getText();
        float bottleprize = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"' AND `ProId` = 1 " ;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                bottleprize += Float.valueOf(rs.getString(5));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return bottleprize;
    }
    
    public Float searchKilo() {
       
        //  id = jTextField1.getText();
        float kilo = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"' AND `ProId` = 3 ";
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                kilo += Float.valueOf(rs.getString(4));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return kilo;
    }

    public Float searchKiloPrize() {
        
        //  id = jTextField1.getText();
        float kiloprize = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"' AND `ProId` = 3 " ;
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                kiloprize += Float.valueOf(rs.getString(5));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return kiloprize;
    }
    
    public Float searchPunak() {
        
        //  id = jTextField1.getText();
        float punak = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"' AND `ProId` = 4 ";
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                punak += Float.valueOf(rs.getString(4));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return punak;
    }

    public Float searchPunakPrize() {
        
        float punakprize = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        //  id = jTextField1.getText();
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `sales` WHERE `Date` = '"+df.format(dateobj)+"' AND `ProId` = 4 ";
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                punakprize += Float.valueOf(rs.getString(5));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return punakprize;
    }
   
    
    
    public Float getCashIn() {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();
        float cashin = 0;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `CashIn` WHERE `Date` = '"+df.format(dateobj)+"'";
            ResultSet rs = st.executeQuery(q);            
            while (rs.next()) {
                cashin += Float.valueOf(rs.getString(3));
                
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        
        return cashin;
    }
   

    public Float getCashOut() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date(); 
        float cashout = 0;
        try {
            Connection con = getconnection();
            Statement st = con.createStatement();
            String q = "SELECT * FROM `CashOut` WHERE `Date` = '"+df.format(dateobj)+"'";
            ResultSet rs = st.executeQuery(q);
            
            while (rs.next()) {
                cashout += Float.valueOf(rs.getString(3));
                
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
    
    public void print(){     
        
        
        String body= "";
                              
                   body = "Preday\t\t\t"+balance()+"\n"
                        + "CashIn\t\t\t"+getCashIn()+"\n"
                        + "Bottles\t"+searchbot()+"\t"+getprize(1)+"\t"+decim.format(searchbotprize())+"\n" 
                        + "Liters\t"+searchlit()+"\t"+getprize(2)+"\t"+decim.format(searchlitPrize())+"\n" 
                        + "Kilo\t"+searchKilo()+"\t"+getprize(3)+"\t"+decim.format(searchKiloPrize())+"\n" 
                        + "Punak\t"+searchPunak()+"\t"+getprize(4)+"\t"+decim.format(searchPunakPrize())+"\n"
                        + "CashOut\t\t\t"+getCashOut()+"\n";
                 
                   
             
     //  System.out.println(body);
      pf.setstring(body,total(),0,0);
      pf.print();
    }

    /**
     * Creates new form summery
     */
    public summery() {
        initComponents();
                
        jButton1.requestFocusInWindow();
        jLabel4.setText(String.valueOf(balance()));
        jLabel5.setText(String.valueOf(getCashIn()));    
        jLabel22.setText(String.valueOf(getCashOut()));
        jLabel25.setText(String.valueOf(searchlit()));
        jLabel23.setText(String.valueOf(searchbot()));
        jLabel24.setText(String.valueOf(searchKilo()));
        jLabel26.setText(String.valueOf(searchPunak()));
        jLabel13.setText(String.valueOf(searchlitPrize()));
        jLabel14.setText(String.valueOf(searchbotprize()));
        jLabel15.setText(String.valueOf(searchKiloPrize()));
        jLabel16.setText(String.valueOf(searchPunakPrize()));
        jLabel20.setText(String.valueOf(totcash()));
        jLabel21.setText(String.valueOf(total()));
        
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
              summery.this.setVisible(false);
              }
            
        }); 
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) { 
             
             daybalance();   
             print();              
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

        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
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
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(119, 119, 119));
        jLabel6.setText("Cash In");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(119, 119, 119));
        jLabel9.setText("Bottles");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));

        jLabel1.setFont(new java.awt.Font("Ubuntu Mono", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(1, 1, 1));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Day Summery");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(101, 101, 101));
        jLabel2.setText("Prevoius Balance");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(119, 119, 119));
        jLabel3.setText("Cash In");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(101, 101, 101));
        jLabel4.setText("0.00");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(103, 103, 103));
        jLabel5.setText("0.00");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(119, 119, 119));
        jLabel7.setText("Sales");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(119, 119, 119));
        jLabel8.setText("Bottles");

        jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(119, 119, 119));
        jLabel10.setText("Liters");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(119, 119, 119));
        jLabel11.setText("Kilo");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(119, 119, 119));
        jLabel12.setText("Punak");

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(103, 103, 103));
        jLabel13.setText("0.00");

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(103, 103, 103));
        jLabel14.setText("0.00");

        jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(103, 103, 103));
        jLabel15.setText("0.00");

        jLabel16.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(103, 103, 103));
        jLabel16.setText("0.00");

        jLabel17.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(105, 105, 105));
        jLabel17.setText("Total Cash In");

        jLabel18.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(105, 105, 105));
        jLabel18.setText("Cash Out");

        jLabel19.setFont(new java.awt.Font("Ubuntu", 3, 36)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(72, 72, 72));
        jLabel19.setText("Day Balance ");

        jLabel20.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(103, 103, 103));
        jLabel20.setText("0.00");

        jLabel21.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 136, 7));
        jLabel21.setText("0.00");

        jLabel22.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(103, 103, 103));
        jLabel22.setText("0.00");

        jButton1.setBackground(new java.awt.Color(1, 1, 1));
        jButton1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(254, 254, 254));
        jButton1.setText("Ok");

        jButton2.setBackground(new java.awt.Color(1, 1, 1));
        jButton2.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(254, 254, 254));
        jButton2.setText("Print");

        jLabel23.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(107, 107, 107));
        jLabel23.setText("0.0");

        jLabel24.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(107, 107, 107));
        jLabel24.setText("0.0");

        jLabel25.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(107, 107, 107));
        jLabel25.setText("0.0");

        jLabel26.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(107, 107, 107));
        jLabel26.setText("0.0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(jLabel21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel15)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(0, 39, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
