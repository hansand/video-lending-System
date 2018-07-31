/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stii.v2.pkg0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dbconnection.dbconnect;
import java.awt.Color;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author hansaka
 */
public class common extends javax.swing.JFrame {

        Connection conn=null;
        PreparedStatement state=null;
        ResultSet result=null;
        
    public common() {
        initComponents();
        
        conn =dbconnect.dbcon();
        user_idf.hide();//hide user id in top bar
        this.setLocationRelativeTo(null); //form to center
        view();//set visible false to unwanted layers
        

        
        
        
    }
      public void view()
    {
        lend.setVisible(false);
        manage_vid.setVisible(false);
        return_layer.setVisible(false);
        history.setVisible(false);
        manage_emp.setVisible(false);
        manage_accl.setVisible(false);
        manageuser.setVisible(false);
        welcome.setVisible(true);
        repots.setVisible(false);
        
    }
      
      public void button_hide(String type,String name,String userid){
          
          user_name.setText(name);
          user_idf.setText(userid);
          typelog.setText(type);
          String typex = null;
          if(type.equals("employee"))
          {
              try{
                  String quary="SELECT * FROM employees WHERE empid ='"+ userid +"' ";
                  state=conn.prepareStatement(quary);
                  result=state.executeQuery();
                  result.first();
                  typex=result.getString("type");
              }catch(Exception e){
                  System.out.println(e);
              }
              
              if(typex.equals("normal"))
              {
                  manageempb.setVisible(false);
              }
              
          }
          else if(type.equals("customer"))
          {
              returnb.setVisible(false);
              managevidb1.setVisible(false);
              manageusers.setVisible(false);
              manageempb.setVisible(false);
              report.setVisible(false);
          }
          
          
          
}
    
    
    //lend methods
      
    public void temp()
    {
                {//creating Temp table
            try{
                String quary="CREATE TEMPORARY TABLE temp (vid_id varchar(20),name VARCHAR(20),type VARCHAR(20),days int(1)) ";
                state=conn.prepareStatement(quary);
                state.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public void decrement_lended(String id,String type)
    {
        if(type.equals("employee"))
            try{
                String quary="UPDATE employees  SET lended=lended-1 WHERE empid='"+id+"' AND lended>0 ";
                state=conn.prepareStatement(quary);
                state.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        else if(type.equals("customer"))
        {
            try{
                String quary1="UPDATE customers  SET lended=lended-1 WHERE userid='"+id+"' AND lended>0  ";
                state=conn.prepareStatement(quary1);
                state.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public void increment_lended(String id,String type)
    {
        if(type.equals("employee"))
            try{
                String quary="UPDATE employees  SET lended=lended+1 WHERE empid='"+id+"' AND lended>0 ";
                state=conn.prepareStatement(quary);
                state.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        else if(type.equals("customer"))
        {
            try{
                String quary1="UPDATE customers  SET lended=lended+1 WHERE userid='"+id+"' AND lended>0  ";
                state=conn.prepareStatement(quary1);
                state.execute();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
      
    public void load_user_table() //load user table in in lend panel
    {
        try{
            String quary="SELECT vid_id,vid_name,type FROM videos   ";
            state=conn.prepareStatement(quary);
            result=state.executeQuery();
            table_user.setModel(DbUtils.resultSetToTableModel(result));
        }catch(Exception e){
            System.out.println(e);
        }
            
    }
    
    public boolean load_temp() //load temp table in lend panel
    {
        boolean empty=false;
        try{
            String quary="SELECT * FROM temp";
            state=conn.prepareStatement(quary);
            result=state.executeQuery();
            
            temp.setModel(DbUtils.resultSetToTableModel(result));
            
            empty=result.first();
        }catch(Exception e){
            System.out.println(e);
        }
        return empty;
            
    }
    
    public void filters(String x) //filter option for videos table
       {
            String typename=x;
            try {
                
                String quary="SELECT vid_name,type "
                        + "FROM videos WHERE type='"+ typename +"'" ;
                state=conn.prepareStatement(quary);
                result=state.executeQuery();
                table_user.setModel(DbUtils.resultSetToTableModel(result));
            } catch (Exception e) {
                
                System.out.println(e);
            }
       }
    
    public void decriment_copies(String vid_id) //decrement copies from videos table
    {
        try{
            
            String quary="UPDATE videos SET copies=copies-1 WHERE vid_id='"+ vid_id +"' AND copies>0 ";
            state=conn.prepareStatement(quary);
            state.execute();
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    
    //manage videos Methods
    public boolean check_copies(String x){
        
        String num_copies=x;
        boolean y=false;
             //check whether num oF copies are Int
            for(int i=0;i<num_copies.length();i++)
            {
                if(!Character.isDigit(num_copies.charAt(i)))
                {
                    
                    erro_copies.setText("This Must be a Number");
                    y= false;
                }
                else
                {
                    erro_copies.setText("");
                    y= true;
                } 
            }
        return y;
    } //check copies are int
    
    
    //check name's Excestence
    public boolean check_name(String x){
        String name=x;
        boolean y=false;
          try{
                String quary="SELECT * FROM videos WHERE vid_name='"+ name +"' ";
                state = conn.prepareStatement(quary);
                result=state.executeQuery();
                y=result.first();
                
            }catch(Exception e){
                
                JOptionPane.showMessageDialog(null,"Problem Ocuured With Database..please Contact Us.","warning",JOptionPane.WARNING_MESSAGE);
            }
            
            if(y==true)
            {
                erro_name.setText("This Video Already Added");
            }
            else
            {
                 erro_name.setText("");
            }
        return y;
        
    } 
    
    public boolean check_location(String x)// location Excistence
    {
        String location=x;
        boolean y=false;
        try{
                String quary="SELECT * FROM videos WHERE location='"+ location +"' ";
                state = conn.prepareStatement(quary);
                result=state.executeQuery();
                y=result.first();
                
            }catch(Exception e){
                
                JOptionPane.showMessageDialog(null,"Problem Ocuured With Database..please Contact Us.","warning",JOptionPane.WARNING_MESSAGE);
            }
            if(y==true)
            {
                erro_location.setText("This Location is Used");
            }
            else
            {
                erro_location.setText("");
            }
        return y;
    }
    
       public void tableload()//load vedios table
    {
        try {
            String getvalue="SELECT * FROM videos";
            state=conn.prepareStatement(getvalue);
            result = state.executeQuery();
            videos.setModel(DbUtils.resultSetToTableModel(result));
        } catch (SQLException ex) {
            System.out.println("getting Faild");
        }
       
    }
       
       public void set_lable_null() //set Erro varibles to null
       {
           erro_name.setText("");
           erro_copies.setText("");
           erro_location.setText("");
           namef.setText("");
           num_copiesf.setText("");
           locationf.setText("");
           filterf.setSelectedIndex(0);
           filterf.setSelectedIndex(0);
       }
       
    //history methods
       
    public void load_lend(String id) //load lend table in history layer
    {
            try {
                String quary="SELECT * FROM lend WHERE user_id='"+ id +"'  ";
                state=conn.prepareStatement(quary);
                result=state.executeQuery();
                lendf.setModel(DbUtils.resultSetToTableModel(result));
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    
    //Employee Mangement Methods
    
    public void load_users() // load users table in mangae emp tab
    {
        try{
            String quary="SELECT userid,name FROM customers "  ;
            state=conn.prepareStatement(quary);
            result=state.executeQuery();
            userE.setModel(DbUtils.resultSetToTableModel(result));
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void load_emp()  //load emp table in manage emp tab
    {
            try {
                
                String quary="SELECT empid,name,email,nic,lended,salary,type FROM employees ";
                state=conn.prepareStatement(quary);
                result=state.executeQuery();
                employees.setModel(DbUtils.resultSetToTableModel(result));
                
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    //user management Methods
    
    public void load_user_manage()
    {
            try{
            String quary="SELECT userid,name,email,nic,lended FROM customers   " ;
            state=conn.prepareStatement(quary);
            result=state.executeQuery();
            user_manage_table.setModel(DbUtils.resultSetToTableModel(result));
        }catch(Exception e){
            System.out.println(e);
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

        lend = new javax.swing.JLayeredPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_user = new javax.swing.JTable();
        topic = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        update_days = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        temp = new javax.swing.JTable();
        filters_pane = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        filterf = new javax.swing.JComboBox<>();
        searchf = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        details = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        typef = new javax.swing.JLabel();
        namef = new javax.swing.JLabel();
        dayf = new javax.swing.JComboBox<>();
        idf_lend = new javax.swing.JLabel();
        ID = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        return_layer = new javax.swing.JLayeredPane();
        topic2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        return_id = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        erro_return = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        return_id1 = new javax.swing.JTextField();
        manage_vid = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        videos = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        filters_pane1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        filterf1 = new javax.swing.JComboBox<>();
        searchf1 = new javax.swing.JTextField();
        Text_labels = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        video_ID = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        Text_varibles = new javax.swing.JPanel();
        idf1 = new javax.swing.JLabel();
        namef1 = new javax.swing.JTextField();
        erro_name = new javax.swing.JLabel();
        num_copiesf = new javax.swing.JTextField();
        erro_copies = new javax.swing.JLabel();
        typef1 = new javax.swing.JComboBox<>();
        locationf = new javax.swing.JTextField();
        erro_location = new javax.swing.JLabel();
        topic1 = new javax.swing.JLabel();
        history = new javax.swing.JLayeredPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        lendf = new javax.swing.JTable();
        topic3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        manage_emp = new javax.swing.JLayeredPane();
        topic4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        userE = new javax.swing.JTable();
        to_emp_id = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        employees = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        search_emp = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        search_emp1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        emp_idf = new javax.swing.JLabel();
        salf = new javax.swing.JTextField();
        erro_sal = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        emp_idf1 = new javax.swing.JLabel();
        promote = new javax.swing.JButton();
        demote = new javax.swing.JButton();
        manageuser = new javax.swing.JLayeredPane();
        topic5 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        user_manage_table = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        idm = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        welcome = new javax.swing.JLayeredPane();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        manage_accl = new javax.swing.JLayeredPane();
        namebox = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton16 = new javax.swing.JButton();
        repots = new javax.swing.JLayeredPane();
        jButton13 = new javax.swing.JButton();
        topic6 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        empid = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        top_bar = new javax.swing.JLayeredPane();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        user_name = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        user_idf = new javax.swing.JLabel();
        typelog = new javax.swing.JLabel();
        side_bar = new javax.swing.JLayeredPane();
        report = new javax.swing.JLabel();
        manageaccb = new javax.swing.JLabel();
        lendb = new javax.swing.JLabel();
        manageempb = new javax.swing.JLabel();
        historyb = new javax.swing.JLabel();
        returnb = new javax.swing.JLabel();
        manageusers = new javax.swing.JLabel();
        managevidb1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        back_image = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lend.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lendFocusGained(evt);
            }
        });
        lend.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_userMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_user);

        lend.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 420, 200));

        topic.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        topic.setForeground(new java.awt.Color(255, 255, 255));
        topic.setText("Lend Videos");
        lend.add(topic, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 260, 40));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Requsted Video List:");
        lend.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 200, 40));

        jButton1.setText("Add to List");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        lend.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 390, 120, 40));

        update_days.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", " " }));
        update_days.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_daysActionPerformed(evt);
            }
        });
        lend.add(update_days, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 130, 30));

        temp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(temp);

        lend.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 420, 180));

        filters_pane.setBackground(new java.awt.Color(39, 43, 51));
        filters_pane.setForeground(new java.awt.Color(39, 43, 51));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Filter By Genre");

        filterf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Music", "Movie", "Cartoon" }));
        filterf.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterfItemStateChanged(evt);
            }
        });

        searchf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfActionPerformed(evt);
            }
        });
        searchf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchfKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout filters_paneLayout = new javax.swing.GroupLayout(filters_pane);
        filters_pane.setLayout(filters_paneLayout);
        filters_paneLayout.setHorizontalGroup(
            filters_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(filters_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(filters_paneLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(filterf, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)
                    .addComponent(searchf, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        filters_paneLayout.setVerticalGroup(
            filters_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(filters_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(filters_paneLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(filters_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(filters_paneLayout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(filterf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        lend.add(filters_pane, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 540, 50));

        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        lend.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        details.setBackground(new java.awt.Color(39, 43, 51));
        details.setForeground(new java.awt.Color(39, 43, 51));
        details.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Video Name          :");
        details.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 160, 40));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Video Type           :");
        details.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 160, 40));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Days                     :");
        details.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 180, 40));

        typef.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        typef.setForeground(new java.awt.Color(255, 255, 255));
        typef.setText("Type");
        details.add(typef, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 140, 40));

        namef.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        namef.setForeground(new java.awt.Color(255, 255, 255));
        namef.setText("name");
        details.add(namef, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 140, 40));

        dayf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", " " }));
        details.add(dayf, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 130, 30));

        idf_lend.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        idf_lend.setForeground(new java.awt.Color(255, 255, 255));
        idf_lend.setText("ID");
        details.add(idf_lend, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 140, 40));

        ID.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ID.setForeground(new java.awt.Color(255, 255, 255));
        ID.setText("ID                         :");
        details.add(ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 160, 40));

        lend.add(details, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, 340, 170));

        jButton3.setText("Change Days");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        lend.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, -1, -1));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Video List:");
        lend.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 160, 40));

        jButton4.setText("Confirm your Order");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4MouseEntered(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        lend.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 300, 50));

        getContentPane().add(lend, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        return_layer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topic2.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        topic2.setForeground(new java.awt.Color(255, 255, 255));
        topic2.setText("Return ");
        return_layer.add(topic2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 260, 40));

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Video ID                        :");
        return_layer.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 170, 30));

        return_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_idActionPerformed(evt);
            }
        });
        return_layer.add(return_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 170, 30));

        jButton8.setText("Return");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        return_layer.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 90, -1));
        return_layer.add(erro_return, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, -1, -1));

        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("User ID");
        return_layer.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 170, 30));

        return_id1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_id1ActionPerformed(evt);
            }
        });
        return_layer.add(return_id1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 170, 30));

        getContentPane().add(return_layer, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        manage_vid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                manage_vidFocusGained(evt);
            }
        });
        manage_vid.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        videos.setForeground(new java.awt.Color(0, 0, 0));
        videos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        videos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                videosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(videos);

        manage_vid.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, -1, 230));

        jButton6.setText("Update");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        manage_vid.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, -1, -1));

        jButton7.setText("Delete");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        manage_vid.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 340, -1, -1));

        filters_pane1.setBackground(new java.awt.Color(39, 43, 51));
        filters_pane1.setForeground(new java.awt.Color(39, 43, 51));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Filter By Genre");

        filterf1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Music", "Movie", "Cartoon" }));
        filterf1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterf1ItemStateChanged(evt);
            }
        });
        filterf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterf1ActionPerformed(evt);
            }
        });

        searchf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchf1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout filters_pane1Layout = new javax.swing.GroupLayout(filters_pane1);
        filters_pane1.setLayout(filters_pane1Layout);
        filters_pane1Layout.setHorizontalGroup(
            filters_pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
            .addGroup(filters_pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(filters_pane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(filterf1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)
                    .addComponent(searchf1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        filters_pane1Layout.setVerticalGroup(
            filters_pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(filters_pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(filters_pane1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(filters_pane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(filters_pane1Layout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(filterf1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchf1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        manage_vid.add(filters_pane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 540, 50));

        Text_labels.setBackground(new java.awt.Color(39, 43, 51));
        Text_labels.setForeground(new java.awt.Color(39, 43, 51));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Number of Copies :");

        video_ID.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        video_ID.setForeground(new java.awt.Color(255, 255, 255));
        video_ID.setText("Video ID                 :");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Name                     :");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Genre                    :");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Stored Location     :");

        javax.swing.GroupLayout Text_labelsLayout = new javax.swing.GroupLayout(Text_labels);
        Text_labels.setLayout(Text_labelsLayout);
        Text_labelsLayout.setHorizontalGroup(
            Text_labelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
            .addGroup(Text_labelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Text_labelsLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(Text_labelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(video_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        Text_labelsLayout.setVerticalGroup(
            Text_labelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
            .addGroup(Text_labelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Text_labelsLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(video_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        manage_vid.add(Text_labels, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 170, 210));

        jButton5.setText("Insert");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        manage_vid.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, -1, -1));

        Text_varibles.setBackground(new java.awt.Color(39, 43, 51));
        Text_varibles.setForeground(new java.awt.Color(39, 43, 51));
        Text_varibles.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        idf1.setForeground(new java.awt.Color(255, 255, 255));
        idf1.setText("ID");
        Text_varibles.add(idf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 15, 130, -1));
        Text_varibles.add(namef1, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 45, 150, 30));

        erro_name.setForeground(new java.awt.Color(255, 0, 0));
        erro_name.setText("jLabel10");
        Text_varibles.add(erro_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 55, -1, -1));
        Text_varibles.add(num_copiesf, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 85, 120, 30));

        erro_copies.setForeground(new java.awt.Color(255, 0, 0));
        erro_copies.setText("erro_copies");
        Text_varibles.add(erro_copies, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 95, -1, -1));

        typef1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Music", "Movie", "Cartoon" }));
        Text_varibles.add(typef1, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 125, 150, 30));
        Text_varibles.add(locationf, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 165, 120, 30));

        erro_location.setForeground(new java.awt.Color(255, 0, 0));
        erro_location.setText("jLabel10");
        Text_varibles.add(erro_location, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 175, -1, -1));

        manage_vid.add(Text_varibles, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 340, 210));

        topic1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        topic1.setForeground(new java.awt.Color(255, 255, 255));
        topic1.setText("Manage Videos");
        manage_vid.add(topic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 260, 40));

        getContentPane().add(manage_vid, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        history.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lendf.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(lendf);

        history.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, 370));

        topic3.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        topic3.setForeground(new java.awt.Color(255, 255, 255));
        topic3.setText("History");
        history.add(topic3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 260, 40));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Lending History");
        history.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 180, -1));

        getContentPane().add(history, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        manage_emp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topic4.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        topic4.setForeground(new java.awt.Color(255, 255, 255));
        topic4.setText("Manage Employees");
        manage_emp.add(topic4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 260, 40));

        userE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        userE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userEMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(userE);

        manage_emp.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 390, 240));

        to_emp_id.setForeground(new java.awt.Color(255, 255, 255));
        to_emp_id.setText("user id");
        manage_emp.add(to_emp_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 50, 40));

        jButton9.setText("Add As a Employee");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        manage_emp.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 170, -1));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Search Employees");
        manage_emp.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 30, 150, -1));

        employees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        employees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeesMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(employees);

        manage_emp.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, -1, 230));

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Users List");
        manage_emp.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        search_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_empActionPerformed(evt);
            }
        });
        search_emp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_empKeyPressed(evt);
            }
        });
        manage_emp.add(search_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 200, -1));

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Salary");
        manage_emp.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 320, 60, -1));

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Search Users");
        manage_emp.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 150, -1));

        search_emp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_emp1ActionPerformed(evt);
            }
        });
        search_emp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_emp1KeyPressed(evt);
            }
        });
        manage_emp.add(search_emp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 200, -1));

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Employee  List");
        manage_emp.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 120, -1));

        jButton10.setText("Update Salry");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        manage_emp.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 320, 130, -1));

        emp_idf.setForeground(new java.awt.Color(255, 255, 255));
        manage_emp.add(emp_idf, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 320, 30, -1));

        salf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salfActionPerformed(evt);
            }
        });
        manage_emp.add(salf, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 320, 140, -1));

        erro_sal.setForeground(new java.awt.Color(255, 0, 0));
        manage_emp.add(erro_sal, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 320, 170, 30));

        jButton11.setText("Remove From Employee");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        manage_emp.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, -1, -1));

        emp_idf1.setForeground(new java.awt.Color(255, 255, 255));
        emp_idf1.setText("Employee ID");
        manage_emp.add(emp_idf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, 120, -1));

        promote.setText("Promote to Admin");
        promote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                promoteActionPerformed(evt);
            }
        });
        manage_emp.add(promote, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 360, 180, -1));

        demote.setText("Demote to Employee");
        demote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                demoteActionPerformed(evt);
            }
        });
        manage_emp.add(demote, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 360, 190, -1));

        getContentPane().add(manage_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        manageuser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topic5.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        topic5.setForeground(new java.awt.Color(255, 255, 255));
        topic5.setText("MANAGE USERS");
        manageuser.add(topic5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 260, 40));

        user_manage_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        user_manage_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                user_manage_tableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(user_manage_table);

        manageuser.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, -1, 350));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Users List");
        manageuser.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("User ID :");
        manageuser.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, -1, -1));

        idm.setForeground(new java.awt.Color(255, 255, 255));
        idm.setText("ID");
        manageuser.add(idm, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, -1, -1));

        jButton12.setText("Remove This User");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        manageuser.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, -1, -1));

        getContentPane().add(manageuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        welcome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Welcome");
        welcome.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));
        welcome.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 450));

        getContentPane().add(welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        manage_accl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        namebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameboxActionPerformed(evt);
            }
        });
        manage_accl.add(namebox, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 93, 130, 30));

        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("User Name");
        manage_accl.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 80, -1));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Manage Account");
        manage_accl.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 260, -1));

        jButton14.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton14.setText("Rename");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        manage_accl.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 90, -1));

        jButton15.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton15.setText("Delete my Account");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        manage_accl.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 390, 150, -1));

        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText(" Enter current password");
        manage_accl.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 180, -1));

        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Enter new Password");
        manage_accl.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 170, -1));
        manage_accl.add(jPasswordField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 190, 30));

        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });
        manage_accl.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 91, 190, 30));

        jButton16.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jButton16.setText("Change Password");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        manage_accl.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 180, -1));

        getContentPane().add(manage_accl, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        repots.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton13.setText("Get a Report of All Customers");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        repots.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        topic6.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        topic6.setForeground(new java.awt.Color(255, 255, 255));
        topic6.setText("Genarate Reports");
        repots.add(topic6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 230, 40));

        jButton17.setText("Get a Report of All Employees");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        repots.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));
        repots.add(empid, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 70, 40));

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("ID ");
        repots.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 30, 20));

        jButton18.setText("Get The Report of Lended History");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        repots.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, 290, -1));

        jButton19.setText("Get a Report of  Lend History");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        repots.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 240, -1));

        jButton20.setText("Get a Report of Videos");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        repots.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 240, -1));

        getContentPane().add(repots, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        top_bar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        top_bar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Video Lending System 2.0");
        top_bar.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 5, 590, 50));

        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Log Out");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });
        top_bar.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 40, -1, -1));

        user_name.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        user_name.setForeground(new java.awt.Color(255, 255, 255));
        user_name.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        user_name.setText("user");
        top_bar.add(user_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 370, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stii/v2/pkg0/xxxxxxx.png"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(640, 202));
        top_bar.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1326, 65));

        user_idf.setText("14");
        top_bar.add(user_idf, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 20, -1, -1));

        typelog.setText("jLabel32");
        top_bar.add(typelog, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, -1, -1));

        getContentPane().add(top_bar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 70));

        side_bar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        side_bar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        report.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        report.setForeground(new java.awt.Color(255, 255, 255));
        report.setText("GENARATE REPORT");
        report.setToolTipText("manage employees");
        report.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reportMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reportMouseEntered(evt);
            }
        });
        side_bar.add(report, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 210, 50));

        manageaccb.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        manageaccb.setForeground(new java.awt.Color(255, 255, 255));
        manageaccb.setText("MANAGE ACOUNT");
        manageaccb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageaccbMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageaccbMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageaccbMouseEntered(evt);
            }
        });
        side_bar.add(manageaccb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 190, 50));

        lendb.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lendb.setForeground(new java.awt.Color(255, 255, 255));
        lendb.setText("LEND");
        lendb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lendbMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lendbMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lendbMouseEntered(evt);
            }
        });
        side_bar.add(lendb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 190, 50));

        manageempb.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        manageempb.setForeground(new java.awt.Color(255, 255, 255));
        manageempb.setText("MANAGE EMPLOYEES");
        manageempb.setToolTipText("manage employees");
        manageempb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageempbMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageempbMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageempbMouseEntered(evt);
            }
        });
        side_bar.add(manageempb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 220, 50));

        historyb.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        historyb.setForeground(new java.awt.Color(255, 255, 255));
        historyb.setText("HISTORY");
        historyb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historybMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                historybMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                historybMouseEntered(evt);
            }
        });
        side_bar.add(historyb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 190, 50));

        returnb.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        returnb.setForeground(new java.awt.Color(255, 255, 255));
        returnb.setText("RETURN");
        returnb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                returnbMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                returnbMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                returnbMouseEntered(evt);
            }
        });
        side_bar.add(returnb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 190, 50));

        manageusers.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        manageusers.setForeground(new java.awt.Color(255, 255, 255));
        manageusers.setText("MANAGE USERS");
        manageusers.setToolTipText("manage employees");
        manageusers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageusersMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageusersMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageusersMouseEntered(evt);
            }
        });
        side_bar.add(manageusers, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 180, 50));

        managevidb1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        managevidb1.setForeground(new java.awt.Color(255, 255, 255));
        managevidb1.setText("MANAGE VIDEOS");
        managevidb1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                managevidb1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                managevidb1MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                managevidb1MouseEntered(evt);
            }
        });
        side_bar.add(managevidb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 190, 50));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(37, 52, 71));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stii/v2/pkg0/xxxxxxx.png"))); // NOI18N
        side_bar.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 238, 448));

        getContentPane().add(side_bar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 240, 450));

        back_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/stii/v2/pkg0/zxc (2).png"))); // NOI18N
        getContentPane().add(back_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 980, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void historybMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historybMouseEntered
        historyb.setForeground(Color.CYAN);        // TODO add your handling code here:
    }//GEN-LAST:event_historybMouseEntered

    private void historybMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historybMouseExited
        historyb.setForeground(Color.WHITE);        // TODO add your handling code here:
    }//GEN-LAST:event_historybMouseExited

    private void manageempbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageempbMouseEntered
                // TODO add your handling code here:
        manageempb.setForeground(Color.CYAN);

    }//GEN-LAST:event_manageempbMouseEntered

    private void manageempbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageempbMouseExited
        manageempb.setForeground(Color.WHITE);        // TODO add your handling code here:
    }//GEN-LAST:event_manageempbMouseExited

    private void lendbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lendbMouseEntered
        // TODO add your handling code here:
        lendb.setForeground(Color.CYAN);
    }//GEN-LAST:event_lendbMouseEntered

    private void lendbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lendbMouseExited
        lendb.setForeground(Color.WHITE);
    }//GEN-LAST:event_lendbMouseExited

    private void manageaccbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageaccbMouseEntered
        manageaccb.setForeground(Color.CYAN);        // TODO add your handling code here:
    }//GEN-LAST:event_manageaccbMouseEntered

    private void manageaccbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageaccbMouseExited
        manageaccb.setForeground(Color.WHITE);        // TODO add your handling code here:
    }//GEN-LAST:event_manageaccbMouseExited

    private void table_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_userMouseClicked

        int row=table_user.getSelectedRow();
        String id=table_user.getValueAt(row,0).toString();
        String name=table_user.getValueAt(row,1).toString();
        String type=table_user.getValueAt(row, 2).toString();

        idf_lend.setText(id);
        namef.setText(name);
        typef.setText(type);
    }//GEN-LAST:event_table_userMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        String user_id=user_idf.getText();
        String id=idf_lend.getText();
        String name=namef.getText();
        String type=namef.getText();
        int copies = 0;
        int days=dayf.getSelectedIndex();
        boolean ordered=false,requested=false;
        
        
        //check user alredy oreded the requsted copy
        try{
            String quary = "SELECT * FROM lend WHERE user_id='"+ user_id  +"' AND vid_id='"+ id +"' AND status='not returned'   " ;
            state=conn.prepareStatement(quary);
            result=state.executeQuery();
            requested=result.first();
            
        }catch(Exception e){
            System.out.println(e);
        }
        

        if(name.equals("name") || id.equals("ID") || type.equals("type") || days==0)
        {
            JOptionPane.showMessageDialog(null,"Please Fill All Details.","warning",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            if(requested==true)
            {
                JOptionPane.showMessageDialog(null,"Sorry You have alredy Oreder This Video and Not Returned Yet","warning",JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                        
                    

                    //get num of copies
                    try{
                        String quary="SELECT copies FROM videos WHERE vid_id='"+ id +"'  ";
                        state=conn.prepareStatement(quary);
                        result=state.executeQuery();

                        while (result.next()){
                            copies=Integer.parseInt(result.getString("copies"));

                        }

                    }catch(Exception e){
                        System.out.println("sdad");
                    }

                    if(copies>0)
                    {
                        try{
                            String quary="SELECT * FROM temp WHERE vid_id='"+ id +"' ";
                            state=conn.prepareStatement(quary);
                            result=state.executeQuery();
                            ordered=result.first();

                        }catch(Exception e){
                            System.out.println(e);
                        }

                        //check whether video is already orderd
                        if(ordered==true)
                        {
                            JOptionPane.showMessageDialog(null, "You Have Alredy Orded This Video", "Alredy Added", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                        {
                            try{
                                String quary="INSERT INTO temp VALUES('"+ id +"','"+ name +"','"+ type +"','"+ days +"')";
                                state=conn.prepareStatement(quary);
                                state.execute();
                                this.load_temp();
                                JOptionPane.showMessageDialog(null, "Sucssesfully Added to the List");

                            }catch(Exception e){

                                System.out.println(e);

                            }
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Sorry..We Are Out of Copies", "Out OF Stock", JOptionPane.WARNING_MESSAGE);
                    }

                }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void update_daysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_daysActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_daysActionPerformed

    private void filterfItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterfItemStateChanged
        // TODO add your handling code here:
        String filter=filterf.getSelectedItem().toString();
        if(filter.equals("All"))
        {
            load_user_table();
        }
        else
        {

            filters(filter);
        }
    }//GEN-LAST:event_filterfItemStateChanged

    private void searchfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfActionPerformed

    }//GEN-LAST:event_searchfActionPerformed

    private void searchfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchfKeyPressed

        String keyword=searchf.getText();
        if(keyword.equals(""))
        {
            load_user_table();
        }
        else
        {
            try{

                String quary="SELECT vid_name,type FROM videos WHERE vid_name LIKE '%"+ keyword +"%' "   ;
                state=conn.prepareStatement(quary);
                result=state.executeQuery();
                table_user.setModel(DbUtils.resultSetToTableModel(result));

            }catch(Exception e)
            {
                System.out.println(e);
            }
        }

    }//GEN-LAST:event_searchfKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int row=temp.getSelectedRow();
        String id=temp.getValueAt(row,0).toString();
        int x=JOptionPane.showConfirmDialog(null, "Dou really want to Delete ");
        if(x==0)
        {
            try{
                String quary="DELETE FROM temp WHERE vid_id='"+id+"' ";
                state=conn.prepareStatement(quary);
                state.execute();
                load_temp();
                JOptionPane.showMessageDialog(null, "Sucssesfully Removed");
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        int days=update_days.getSelectedIndex();
        int row=temp.getSelectedRow();
        String id=temp.getValueAt(row,0).toString();
        if(days==0)
        {
            JOptionPane.showMessageDialog(null, "You Can't Order for 0 days","0 Days..Really?",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            int x=JOptionPane.showConfirmDialog(null, "Dou really want to Change ");
            if(x==0)
            {
                try{
                    String quary="UPDATE temp SET days='"+ days +"' WHERE vid_id='"+ id +"' ";
                    state=conn.prepareStatement(quary);
                    state.execute();
                    load_temp();
                    JOptionPane.showMessageDialog(null, "Sucssesfully Changed Days");
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseExited
        //jButton4.setBackground(Color.CYAN);
    }//GEN-LAST:event_jButton4MouseExited

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseEntered
        //jButton4.setBackground(Color.GREEN);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseEntered

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        String vid_id = null,days,copies;
        int user_id;
        user_id=Integer.parseInt(user_idf.getText());
        String id=user_idf.getText();
        String type=typelog.getText();
        boolean x=load_temp();
        if(x==true)//check whether user has requsted any Vedios
        {
            int y=JOptionPane.showConfirmDialog(null, "Dou really want to Order? ");
            if(y==0)
            {
                try{
                    String quary="SELECT * FROM temp";
                    state=conn.prepareStatement(quary);
                    result=state.executeQuery();
                    while(result.next())
                    {
                        vid_id=result.getString("vid_id");
                        days=result.getString("days");
                        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());

                        //add data to lend table
                        try{
                            String add="INSERT INTO lend(user_id,vid_id,days,date) "
                            + "VALUES('"+ user_id +"','"+ vid_id +"','"+ days +"','"+ date +"') ";
                            state=conn.prepareStatement(add);
                            state.execute();
                            
                            
                            //increment lended in customer table
                            increment_lended(id,type);

                        }catch(Exception e){
                            System.out.println(e);
                        }

                    }
                    decriment_copies(vid_id);//decrement copies in vedios table
                    JOptionPane.showMessageDialog(null, "You have Succesesfully Lended Video(s)..Please Collect Your Copy(s) From the Counter");
                    
                    //delete tuples from temp
                        String dele="DELETE FROM temp";
                        state=conn.prepareStatement(dele);
                        state.execute();
                        load_temp();
                }catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You Haven't Requested any Videos Yet","oops",JOptionPane.WARNING_MESSAGE);
        }
        
        String w=user_idf.getText();
        load_lend(w);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void returnbMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnbMouseExited
            returnb.setForeground(Color.WHITE);             // TODO add your handling code here:
    }//GEN-LAST:event_returnbMouseExited

    private void returnbMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnbMouseEntered
            returnb.setForeground(Color.CYAN);        // TODO add your handling code here:
    }//GEN-LAST:event_returnbMouseEntered

    private void historybMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historybMouseClicked
            welcome.setVisible(false);
            String id=user_idf.getText();
            load_lend(id);   
            lend.setVisible(false);
            manage_vid.setVisible(false);
            return_layer.setVisible(false);
            history.setVisible(true);
            manage_emp.setVisible(false);
            manage_accl.setVisible(false);
            manageuser.setVisible(false);
            repots.setVisible(false);
    }//GEN-LAST:event_historybMouseClicked

    private void returnbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnbMouseClicked
                    lend.setVisible(false);
        manage_vid.setVisible(false);
        return_layer.setVisible(true);
        history.setVisible(false);
        manage_emp.setVisible(false);
        manage_accl.setVisible(false);
        manageuser.setVisible(false);
        welcome.setVisible(false);
        repots.setVisible(false);
    }//GEN-LAST:event_returnbMouseClicked

    private void manageempbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageempbMouseClicked
        load_users();
        load_emp();
        lend.setVisible(false);
        manage_vid.setVisible(false);
        return_layer.setVisible(false);
        history.setVisible(false);
        manage_emp.setVisible(true);
        manage_accl.setVisible(false);
        manageuser.setVisible(false);
        welcome.setVisible(false);
        repots.setVisible(false);
    }//GEN-LAST:event_manageempbMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        String name,num_copies,typename,location;
        int type;
        boolean all_filled,copies_int,video_exist,location_exist,type_set;

        name=namef1.getText();
        num_copies=num_copiesf.getText();
        type=typef1.getSelectedIndex();
        typename=typef1.getSelectedItem().toString();
        location=locationf.getText();

        if(name.equals("") || num_copies.equals("") || type==0 || location.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Fill all the Required Details.","warning",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            type_set=true;
            all_filled=true;
            copies_int=this.check_copies(num_copies);
            video_exist=this.check_name(name);
            location_exist=this.check_location(location);

            if(all_filled==true && location_exist==false && video_exist==false && copies_int==true && type_set==true)
            {
                int x=JOptionPane.showConfirmDialog(null, "Dou really want to Insert ");
                if(x==0)
                {
                    try{

                        String quary="INSERT INTO videos (vid_name,type,copies,location) "
                        + "VALUES ('"+ name +"','"+ typename +"','"+ num_copies +"','"+ location +"') ";
                        state = conn.prepareStatement(quary);
                        state.execute();
                        tableload();
                        set_lable_null();
                        JOptionPane.showMessageDialog(null,"Sucsessfully Inserted Data.","warning",JOptionPane.WARNING_MESSAGE);

                    }catch(Exception e){

                        JOptionPane.showMessageDialog(null,"Problem Ocuured With Database..please Contact Us.","warning",JOptionPane.WARNING_MESSAGE);

                    }
                }

            }

        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        //update Method
        String name,num_copies,typename,location;
        String name_t="",location_t="";
        int type,id;

        name=namef1.getText();
        num_copies=num_copiesf.getText();
        type=typef1.getSelectedIndex();
        typename=typef1.getSelectedItem().toString();
        location=locationf.getText();
        id=Integer.parseInt(idf1.getText());

        //checking whether all the Fields are filled
        if(name.equals("") || num_copies.equals("") || type==0 || location.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Fill all the Required Details.","warning",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            //Getting Selected row Values and Assign them into varibles
            try{
                String x="SELECT *"
                + "FROM videos WHERE vid_id='"+ id +"' ";

                state=conn.prepareStatement(x);
                result=state.executeQuery();
                while(result.next())
                {
                    name_t= result.getString("vid_name");
                    location_t=result.getString("location");

                }
            }catch(Exception e){
                System.out.println(e);

            }

            //#1
            if(name.equals(name_t) && location.equals(location_t))
            {
                int x=JOptionPane.showConfirmDialog(null, "Dou really want to upgarade");

                if(check_copies(num_copies)==true && x==0)
                {
                    try {
                        String quary="UPDATE videos "
                        + "SET copies='"+ num_copies +"', type='"+ typename +"' "
                        + "WHERE vid_id='"+ id +"'  ";

                        state=conn.prepareStatement(quary);
                        state.execute();
                        tableload();
                        JOptionPane.showMessageDialog(null,"Sucsessfully Updated.","Done",JOptionPane.WARNING_MESSAGE);
                        set_lable_null();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Faild To Update.","warning",JOptionPane.WARNING_MESSAGE);
                }
            }

            //#2
            else if(name.equals(name_t) && !location.equals(location_t))
            {

                int x=JOptionPane.showConfirmDialog(null, "Dou really want to upgarade");

                if(check_copies(num_copies) && !check_location(location) && x==0)
                {
                    try {
                        String quary="UPDATE videos "
                        + "SET copies='"+ num_copies +"', type='"+ typename +"',location='"+ location +"' "
                        + "WHERE vid_id='"+ id +"'  ";

                        state=conn.prepareStatement(quary);
                        state.execute();
                        tableload();
                        JOptionPane.showMessageDialog(null,"Sucsessfully Updated.","Done",JOptionPane.WARNING_MESSAGE);
                        set_lable_null();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Faild To Update.","warning",JOptionPane.WARNING_MESSAGE);
                }
            }

            //#3
            else if(!name.equals(name_t) && location.equals(location_t))
            {
                int x=JOptionPane.showConfirmDialog(null, "Dou really want to upgarade");

                if(check_copies(num_copies) && !check_name(location) && x==0)
                {
                    try {
                        String quary="UPDATE videos "
                        + "SET copies='"+ num_copies +"', type='"+ typename +"',vid_name='"+ name +"' "
                        + "WHERE vid_id='"+ id +"'  ";

                        state=conn.prepareStatement(quary);
                        state.execute();
                        tableload();
                        JOptionPane.showMessageDialog(null,"Sucsessfully Updated.","Done",JOptionPane.WARNING_MESSAGE);
                        set_lable_null();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Faild To Update.","warning",JOptionPane.WARNING_MESSAGE);
                }
            }

            //#4
            else
            {
                int x=JOptionPane.showConfirmDialog(null, "Dou really want to upgarade");

                if(check_copies(num_copies) && !check_name(location) && x==0 && !check_location(location))
                {
                    try {
                        String quary="UPDATE videos "
                        + "SET copies='"+ num_copies +"', type='"+ typename +"',vid_name='"+ name +"',location='"+ location +"' "
                        + "WHERE vid_id='"+ id +"'  ";

                        state=conn.prepareStatement(quary);
                        state.execute();
                        tableload();
                        JOptionPane.showMessageDialog(null,"Sucsessfully Updated.","Done",JOptionPane.WARNING_MESSAGE);
                        set_lable_null();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Faild To Update.","warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        int id=Integer.parseInt(idf1.getText());
        int x= JOptionPane.showConfirmDialog(null,"Do u Really want to Delete ?");
        if(x==0)
        {

            try{
                String q1="DELETE FROM videos "
                + "WHERE vid_id='"+ id +"'  ";
                state=conn.prepareStatement(q1);
                state.execute();
                tableload();
                JOptionPane.showMessageDialog(null,"Sucsessfully Delteted.","Done",JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(promote, state, q1, HEIGHT);

            }catch(Exception e){

                JOptionPane.showMessageDialog(null,"Faild To Delete.","warning",JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void videosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_videosMouseClicked

        int row=videos.getSelectedRow();

        String id=videos.getValueAt(row,0).toString();
        String name=videos.getValueAt(row,1).toString();
        String type=videos.getValueAt(row,2).toString();
        String copies=videos.getValueAt(row,3).toString();
        String location=videos.getValueAt(row,4).toString();

        namef1.setText(name);
        typef1.setSelectedItem(type);
        locationf.setText(location);
        num_copiesf.setText(copies);
        idf1.setText(id);
    }//GEN-LAST:event_videosMouseClicked

    private void filterf1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterf1ItemStateChanged
        // TODO add your handling code here:
        String filter=filterf1.getSelectedItem().toString();
        if(filter.equals("All"))
        {
            tableload();
        }
        else
        {
            filters(filter);
        }
    }//GEN-LAST:event_filterf1ItemStateChanged

    private void filterf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterf1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterf1ActionPerformed

    private void searchf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchf1ActionPerformed

        String keyword=searchf.getText();
        if(keyword.equals(""))
        {
            tableload();
        }
        else
        {
            try{

                String quary="SELECT *"
                + "FROM videos WHERE vid_name LIKE '%"+ keyword +"%' "   ;
                state=conn.prepareStatement(quary);
                result=state.executeQuery();
                videos.setModel(DbUtils.resultSetToTableModel(result));

            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_searchf1ActionPerformed

    private void lendbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lendbMouseClicked

        this.temp();
        this.load_user_table();
        lend.setVisible(true);
        manage_vid.setVisible(false);
        return_layer.setVisible(false);
        history.setVisible(false);
        manage_emp.setVisible(false);
        manage_accl.setVisible(false);
        manageuser.setVisible(false);
        welcome.setVisible(false);
        repots.setVisible(false);
        
    }//GEN-LAST:event_lendbMouseClicked

    private void lendFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lendFocusGained
        System.out.println("u got it");
        
    }//GEN-LAST:event_lendFocusGained

    private void manage_vidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_manage_vidFocusGained
         System.out.println("not");
    }//GEN-LAST:event_manage_vidFocusGained

    private void managevidb1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_managevidb1MouseClicked
        tableload();
        lend.setVisible(false);
        manage_vid.setVisible(true);
        return_layer.setVisible(false);
        history.setVisible(false);
        manage_emp.setVisible(false);
        manage_accl.setVisible(false);
        manageuser.setVisible(false);
        welcome.setVisible(false);
        this.set_lable_null();
        repots.setVisible(false);
    }//GEN-LAST:event_managevidb1MouseClicked

    private void managevidb1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_managevidb1MouseExited
        managevidb1.setForeground(Color.WHITE);
    }//GEN-LAST:event_managevidb1MouseExited

    private void managevidb1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_managevidb1MouseEntered
       managevidb1.setForeground(Color.CYAN);
    }//GEN-LAST:event_managevidb1MouseEntered

    private void manageaccbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageaccbMouseClicked
        welcome.setVisible(false);
                lend.setVisible(false);
        manage_vid.setVisible(false);
        return_layer.setVisible(false);
        history.setVisible(false);
        manage_emp.setVisible(false);
        manage_accl.setVisible(true);
        manageuser.setVisible(false);
        repots.setVisible(false);
    }//GEN-LAST:event_manageaccbMouseClicked

    private void return_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_return_idActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        
        String idx=return_id.getText();
        String id=return_id1.getText();
        boolean x=check_copies(idx);
        String type=typelog.getText();
        boolean z=check_copies(id);
        if(x==false)
        {
           JOptionPane.showMessageDialog(null,"Both Fields Must be Numbers");
        }
        else
        {
            int y=JOptionPane.showConfirmDialog(null, "Do you Really Wants to Proceed ");
            if(y==0)
            {
                try {
                    String quary="UPDATE lend SET status='Returned' WHERE vid_id='"+ idx +"' AND user_id='"+id+"'  ";
                    state=conn.prepareStatement(quary);
                    state.execute();
                    JOptionPane.showMessageDialog(null, "Succsessfuly Returned");
                    decrement_lended(id,type);
                
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
            
        String id=to_emp_id.getText();
        String empid = null,name = null,email = null,password = null,nic = null,lended = null;
        
        if(id.equals("user id"))
        {
           JOptionPane.showMessageDialog(null,"Please Select A User.","warning",JOptionPane.WARNING_MESSAGE); 
        
        }
        else
        {
            int x= JOptionPane.showConfirmDialog(null,"Do you Really Want to Add This User As an Employee");
            if(x==0)
            {
                try {
                    //get values From customer table
                    String quary=" SELECT * FROM customers WHERE userid='"+ id +"' ";
                    state=conn.prepareStatement(quary);
                    result=state.executeQuery();
                    result.first();
                      empid=result.getString("userid");
                        name=result.getString("name");
                        email=result.getString("email");
                       password=result.getString("password");
                       nic=result.getString("nic");
                        lended=result.getString("lended"); 
                    

                    //insert Into empolyee table

                    String quary1="INSERT INTO employees(empid,name,email,password,nic,lended) VALUES('"+ empid +"' ,'"+ name +"' ,'"+ email +"' ,'"+ password +"' ,'"+ nic +"' ,'"+ lended +"')   ";
                    state=conn.prepareStatement(quary1);
                    state.execute();
                    
                    //delete from cusomer table

                    String quary2="DELETE FROM customers WHERE userid='"+ empid +"'  ";
                    state=conn.prepareStatement(quary2);
                    state.execute();
                    this.load_users();
                    this.load_emp();
                    to_emp_id.setText("user id");
                    JOptionPane.showMessageDialog(null,"Sucssesfuly Added as An Employee."); 


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void userEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userEMouseClicked
            
        int row=userE.getSelectedRow();
        String id=userE.getValueAt(row, 0).toString();
        to_emp_id.setText(id);
        
    }//GEN-LAST:event_userEMouseClicked

    private void search_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_empActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_empActionPerformed

    private void search_emp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_emp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_emp1ActionPerformed

    private void search_emp1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_emp1KeyPressed
       
        String key=search_emp1.getText();
        
        if(key.equals(""))
        {
            this.load_users();
        }
        else
        {
            try {
                String quary="SELECT userid,name FROM customers WHERE name LIKE '%"+ key +"%' OR userid='"+ key +"' ";
                state=conn.prepareStatement(quary);
                result=state.executeQuery();
                userE.setModel(DbUtils.resultSetToTableModel(result));
                
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
    }//GEN-LAST:event_search_emp1KeyPressed

    private void search_empKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_empKeyPressed
        
         String key=search_emp.getText();
        
        if(key.equals(""))
        {
            this.load_emp();
        }
        else
        {
            try {
                String quary="SELECT empid,name,email,nic,lended,salary FROM employees WHERE name LIKE '%"+ key +"%' OR empid='"+ key +"' ";
                state=conn.prepareStatement(quary);
                result=state.executeQuery();
                employees.setModel(DbUtils.resultSetToTableModel(result));
                
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
    }//GEN-LAST:event_search_empKeyPressed

    private void employeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeesMouseClicked
        
        int row = employees.getSelectedRow();
        
        String id=employees.getValueAt(row, 0).toString();
        emp_idf.setText(id);
    }//GEN-LAST:event_employeesMouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
        String salary=salf.getText();
        String id=emp_idf.getText();
        boolean salrynum=this.check_copies(salary);
        
        if(salrynum==false || id.equals(""))
        {
            erro_sal.setText("This Must be a Number");
            JOptionPane.showMessageDialog(null, "Erro...Please Select A Employee OR Insert Corect Salary");
        }
        else
        {
            erro_sal.setText("");
            int y= JOptionPane.showConfirmDialog(null, "Are You Sure ?");
            if(y==0)
            {
                try{
                    
                    String quary="UPDATE employees SET salary='"+ salary +"' WHERE empid='"+ id +"' ";
                    state=conn.prepareStatement(quary);
                    state.execute();
                    this.load_emp();
                    emp_idf.setText("");
                    JOptionPane.showMessageDialog(null,"Sucssesfully Updated Salary."); 
                    
                }catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        
        String id=emp_idf.getText();
        String userID = null,name = null,email = null,password = null,nic = null,lended = null;
        
        if(id.equals("") )
        {
            JOptionPane.showMessageDialog(null, "Please Select an Employee to Remove");
        }
        else
        {
            int y= JOptionPane.showConfirmDialog(null, "This employee will Remove as an Employee and will be added as an User,Do you Wish to Continue ?");
            if(y==0)
            {
                try{
                    //getting values From employee table
                    String quary="SELECT * FROM employees WHERE empid='"+ id +"'  ";
                    state=conn.prepareStatement(quary);
                    result=state.executeQuery();
                    while(result.next())
                            {
                              userID=result.getString("empid");
                              name=result.getString("name");
                              email=result.getString("email");
                              password=result.getString("password");
                              nic=result.getString("nic");
                              lended=result.getString("lended");
                            }
                    //insert Values to Customer table
                    String quary1="INSERT INTO customers VALUES('"+ userID +"','"+ name +"','"+ email +"','"+ password +"','"+ nic +"','"+ lended +"') ";
                    state=conn.prepareStatement(quary1);
                    state.execute();
                    
                    //Delte tuples from employees
                    
                    String quary3="DELETE FROM employees WHERE empid='"+ id +"'  ";
                    state=conn.prepareStatement(quary3);
                    state.execute();
                    
                    this.load_emp();
                    this.load_users();
                    emp_idf.setText("");
                    
                    JOptionPane.showMessageDialog(null,"Sucssesfully Removed Employee."); 
                    
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void salfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salfActionPerformed

    private void manageusersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageusersMouseClicked
           load_user_manage();
        lend.setVisible(false);
        manage_vid.setVisible(false);
        return_layer.setVisible(false);
        history.setVisible(false);
        manage_emp.setVisible(false);
        manage_accl.setVisible(false);
        manageuser.setVisible(true);
        welcome.setVisible(false);
        repots.setVisible(false);

    }//GEN-LAST:event_manageusersMouseClicked

    private void manageusersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageusersMouseExited
        // TODO add your handling code here:
        manageusers.setForeground(Color.WHITE); 
    }//GEN-LAST:event_manageusersMouseExited

    private void manageusersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageusersMouseEntered
        // TODO add your handling code here:
        manageusers.setForeground(Color.CYAN);
    }//GEN-LAST:event_manageusersMouseEntered

    private void user_manage_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_user_manage_tableMouseClicked
        
        int row=user_manage_table.getSelectedRow();
        
        String id=user_manage_table.getValueAt(row, 0).toString();
        idm.setText(id);
    }//GEN-LAST:event_user_manage_tableMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        
        String id=idm.getText();
        if(id.equals("ID"))
        {
            JOptionPane.showMessageDialog(null, "Please Selecct an User", "Warnning", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            int y=JOptionPane.showConfirmDialog(null,"Are you Sure Remove User '"+ id +"' ");
            if(y==0)
            {
                try{
                    String quary="DELETE FROM customers WHERE userid='"+ id +"'  ";
                    state=conn.prepareStatement(quary);
                    state.execute();
                    load_user_manage();
                    
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        
        this.hide();
        
        login form=new login();
        form.setVisible(true);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void promoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promoteActionPerformed
        
        String id=emp_idf.getText();
        
        if(id.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Select A Employee");
        }
        else
        {
            int y=JOptionPane.showConfirmDialog(null, "Are You Sure ?");
            if(y==0)
            {

                try{
                    String quary="UPDATE employees SET type='admin' WHERE empid='"+ id +"'   ";
                    state=conn.prepareStatement(quary);
                    state.execute();
                    JOptionPane.showMessageDialog(null, "Sucsessfuly Updated");
                    this.load_emp();
                }catch(Exception e){
                    
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_promoteActionPerformed

    private void demoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_demoteActionPerformed
            String id=emp_idf.getText();
        
        if(id.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Select A Employee");
        }
        else
        {
            int y=JOptionPane.showConfirmDialog(null, "Are You Sure ?");
            if(y==0)
            {

                try{
                    String quary="UPDATE employees SET type='normal' WHERE empid='"+ id +"'   ";
                    state=conn.prepareStatement(quary);
                    state.execute();
                    JOptionPane.showMessageDialog(null, "Sucsessfuly Updated");
                    this.load_emp();
                }catch(Exception e){
                    
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_demoteActionPerformed

    private void nameboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameboxActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        
            String id=user_idf.getText();
            String name = namebox.getText();
            String type=typelog.getText();
            String Currentname=user_name.getText();
            
            if(name.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Please Fill Both Fields");
            }
            else
            {
                int x=JOptionPane.showConfirmDialog(null,"Are You Sure?");
                
                if(type.equals("employee") && x==0)
                {
                    try{
                        String quary="UPDATE employees SET name='"+ name +"' WHERE empid='"+ id +"' ";
                        state=conn.prepareStatement(quary);
                        state.execute();
                        JOptionPane.showMessageDialog(null,"Successfully Renamed");
                        user_name.setText(name);
                    }catch(Exception e){
                        System.out.println(e);
                        user_name.setText(Currentname);
                    }
                }
                else if(type.equals("customer") && x==0)
                {
                    try{
                        String quary="UPDATE customers SET name='"+ name +"' WHERE userid='"+ id +"' ";
                        state=conn.prepareStatement(quary);
                        state.execute();
                        JOptionPane.showMessageDialog(null,"Successfully Renamed");
                        user_name.setText(name);
                        
                    }catch(Exception e){
                        System.out.println(e);
                        user_name.setText(Currentname);
                    } 
                }
                else
                {
                    
                }
            }
            
                        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        String id=user_idf.getText();
        String type=typelog.getText();
        login form=new login();
        int ans=JOptionPane.showConfirmDialog(null,"Are you Sure?");
        if(ans==0)
        {
            if(type.equals("employee"))
            {
                try{
                    String quary="SELECT * FROM employees WHERE empid='"+ id +"'  "  ;
                    state=conn.prepareStatement(quary);
                    result=state.executeQuery();
                    result.first();
                    int lended=Integer.parseInt(result.getString("lended"));

                    if(lended==0)
                    {
                        try{
                            String quary1="DELETE from employees WHERE empid='"+ id +"' ";
                            state=conn.prepareStatement(quary1);
                            state.execute();
                            JOptionPane.showMessageDialog(null, "Successfully Deleted Your Account");
                            this.hide();
                            form.setVisible(true);
                        }catch(Exception x)
                        {
                            System.out.println(x);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Sorry You Can not Delete Your Account,You have not Returned Some Videos That You Have Lended");
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
            else if(type.equals("customer"))
                {
                try{
                    String quary="SELECT * FROM customers WHERE userid='"+ id +"'  "  ;
                    state=conn.prepareStatement(quary);
                    result=state.executeQuery();
                    result.first();
                    String x=result.getString("lended");
                    int lended=Integer.parseInt(x);

                    if(lended==0)
                    {
                        try{
                            String quary1="DELETE from customers WHERE userid='"+ id +"' ";
                            state=conn.prepareStatement(quary1);
                            state.execute();
                             JOptionPane.showMessageDialog(null, "Successfully Deleted Your Account");
                            this.hide();
                            form.setVisible(true);
                        }catch(Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Sorry You Can not Delete Your Account,You have not Returned Some Videos That You Have Lended");
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            
                }
        }
        
        
            
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
            

            
            String id=user_idf.getText();
            String password=jPasswordField1.getText();
            String npassword=jPasswordField2.getText();
            String type=typelog.getText();
            System.out.println(password);
            System.out.println(npassword);

            if(password.equals("") || npassword.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Fill both the Forms");
            }
            else{
                int ans=JOptionPane.showConfirmDialog(null, "Are you Sure ?");
                if(ans==0)
                {
                    if(type.equals("employee"))
                    {
                    try{
                        String quary="SELECT * FROM employees WHERE empid='"+ id +"' AND password='"+ password +"' " ;
                        state=conn.prepareStatement(quary);
                        result=state.executeQuery();
                        result.first();
                        String pass=result.getString("password");


                        if(pass.equals(password))
                        {
                            try{

                                String quary1="UPDATE employees SET password='"+ npassword +"' WHERE empid='"+ id +"' ";
                                state=conn.prepareStatement(quary1);
                                state.execute();
                                this.hide();
                                login form=new login();
                                form.setVisible(true);

                            }catch(Exception e){
                                System.out.println(e);
                            }
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }

                    }

                    else if(type.equals("customer"))
                    {
                          try{
                        String quary="SELECT * FROM customers WHERE userid='"+ id +"' AND password='"+ password +"' " ;
                        state=conn.prepareStatement(quary);
                        result=state.executeQuery();
                        result.first();
                        String pass=result.getString("password");


                        if(pass.equals(password))
                        {
                            try{

                                String quary1="UPDATE customers SET password='"+ npassword +"' WHERE userid='"+ id +"' ";
                                state=conn.prepareStatement(quary1);
                                state.execute();
                                this.hide();
                                login form=new login();
                                form.setVisible(true);

                            }catch(Exception e){
                                System.out.println(e);
                            }
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }

                    } 

                }
            }
        
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void reportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportMouseClicked
                lend.setVisible(false);
        manage_vid.setVisible(false);
        return_layer.setVisible(false);
        history.setVisible(false);
        manage_emp.setVisible(false);
        manage_accl.setVisible(false);
        manageuser.setVisible(false);
        welcome.setVisible(false);
        repots.setVisible(true);
    }//GEN-LAST:event_reportMouseClicked

    private void reportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportMouseExited
 report.setForeground(Color.WHITE);        // TODO add your handling code here:
    }//GEN-LAST:event_reportMouseExited

    private void reportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportMouseEntered
report.setForeground(Color.CYAN);        // TODO add your handling code here:
    }//GEN-LAST:event_reportMouseEntered

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
       try{
           
           String report="//home//hansaka//NetBeansProjects//STII v2.0//src//stii//v2//pkg0//customer_report.jrxml";
           JasperReport jr=JasperCompileManager.compileReport(report);
           JasperPrint jp=JasperFillManager.fillReport(jr,null,conn);
           JasperViewer.viewReport(jp);
       }catch(Exception e){
           System.out.println(e);
       }
           
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
            
        try{
           
           String report="//home//hansaka//NetBeansProjects//STII v2.0//src//stii//v2//pkg0//employee_report.jrxml";
           JasperReport jr=JasperCompileManager.compileReport(report);
           JasperPrint jp=JasperFillManager.fillReport(jr,null,conn);
           JasperViewer.viewReport(jp);
       }catch(Exception e){
           System.out.println(e);
       }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        
        String id=empid.getText();
        boolean isint=this.check_copies(id);
        Map mapx= new HashMap();  
        mapx.put("id",id);
        if(isint==true)
        {
        try{
           
           String report="//home//hansaka//NetBeansProjects//STII v2.0//src//stii//v2//pkg0//lended_report.jrxml";
           JasperReport jr=JasperCompileManager.compileReport(report);
           JasperPrint jp=JasperFillManager.fillReport(jr,mapx,conn);
           JasperViewer.viewReport(jp);
       }catch(Exception e){
           System.out.println(e);
       }
        }
        
        
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
                    try{
           
           String report="//home//hansaka//NetBeansProjects//STII v2.0//src//stii//v2//pkg0//lend_report.jrxml";
           JasperReport jr=JasperCompileManager.compileReport(report);
           JasperPrint jp=JasperFillManager.fillReport(jr,null,conn);
           JasperViewer.viewReport(jp);
       }catch(Exception e){
           System.out.println(e);
       }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        
              try{
           
           String report="//home//hansaka//NetBeansProjects//STII v2.0//src//stii//v2//pkg0//videos_report.jrxml";
           JasperReport jr=JasperCompileManager.compileReport(report);
           JasperPrint jp=JasperFillManager.fillReport(jr,null,conn);
           JasperViewer.viewReport(jp);
       }catch(Exception e){
           System.out.println(e);
       }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void return_id1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_id1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_return_id1ActionPerformed

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
            java.util.logging.Logger.getLogger(common.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(common.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(common.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(common.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new common().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ID;
    private javax.swing.JPanel Text_labels;
    private javax.swing.JPanel Text_varibles;
    private javax.swing.JLabel back_image;
    private javax.swing.JComboBox<String> dayf;
    private javax.swing.JButton demote;
    private javax.swing.JPanel details;
    private javax.swing.JLabel emp_idf;
    private javax.swing.JLabel emp_idf1;
    private javax.swing.JTextField empid;
    private javax.swing.JTable employees;
    private javax.swing.JLabel erro_copies;
    private javax.swing.JLabel erro_location;
    private javax.swing.JLabel erro_name;
    private javax.swing.JLabel erro_return;
    private javax.swing.JLabel erro_sal;
    private javax.swing.JComboBox<String> filterf;
    private javax.swing.JComboBox<String> filterf1;
    private javax.swing.JPanel filters_pane;
    private javax.swing.JPanel filters_pane1;
    private javax.swing.JLayeredPane history;
    private javax.swing.JLabel historyb;
    private javax.swing.JLabel idf1;
    private javax.swing.JLabel idf_lend;
    private javax.swing.JLabel idm;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLayeredPane lend;
    private javax.swing.JLabel lendb;
    private javax.swing.JTable lendf;
    private javax.swing.JTextField locationf;
    private javax.swing.JLayeredPane manage_accl;
    private javax.swing.JLayeredPane manage_emp;
    private javax.swing.JLayeredPane manage_vid;
    private javax.swing.JLabel manageaccb;
    private javax.swing.JLabel manageempb;
    private javax.swing.JLayeredPane manageuser;
    private javax.swing.JLabel manageusers;
    private javax.swing.JLabel managevidb1;
    private javax.swing.JTextField namebox;
    private javax.swing.JLabel namef;
    private javax.swing.JTextField namef1;
    private javax.swing.JTextField num_copiesf;
    private javax.swing.JButton promote;
    private javax.swing.JLabel report;
    private javax.swing.JLayeredPane repots;
    private javax.swing.JTextField return_id;
    private javax.swing.JTextField return_id1;
    private javax.swing.JLayeredPane return_layer;
    private javax.swing.JLabel returnb;
    private javax.swing.JTextField salf;
    private javax.swing.JTextField search_emp;
    private javax.swing.JTextField search_emp1;
    private javax.swing.JTextField searchf;
    private javax.swing.JTextField searchf1;
    private javax.swing.JLayeredPane side_bar;
    private javax.swing.JTable table_user;
    private javax.swing.JTable temp;
    private javax.swing.JLabel to_emp_id;
    private javax.swing.JLayeredPane top_bar;
    private javax.swing.JLabel topic;
    private javax.swing.JLabel topic1;
    private javax.swing.JLabel topic2;
    private javax.swing.JLabel topic3;
    private javax.swing.JLabel topic4;
    private javax.swing.JLabel topic5;
    private javax.swing.JLabel topic6;
    private javax.swing.JLabel typef;
    private javax.swing.JComboBox<String> typef1;
    private javax.swing.JLabel typelog;
    private javax.swing.JComboBox<String> update_days;
    private javax.swing.JTable userE;
    private javax.swing.JLabel user_idf;
    private javax.swing.JTable user_manage_table;
    private javax.swing.JLabel user_name;
    private javax.swing.JLabel video_ID;
    private javax.swing.JTable videos;
    private javax.swing.JLayeredPane welcome;
    // End of variables declaration//GEN-END:variables
}
