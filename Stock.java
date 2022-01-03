package ProiectPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Stock extends JFrame implements ActionListener {
    Container container=getContentPane();
    JLabel cod=new JLabel("Cod: ");
    JLabel stock=new JLabel("Stock: ");
    JTextField codField=new JTextField();
    JTextField stockField=new JTextField();
    JButton modifica=new JButton("Modifica");
    Stock(){
        setTitle("Actualizare stock");
        setBounds(700, 400, 300, 200);
        setLayoutManager();
        setLocationAndSize();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addComponentsToContainer();
        addActionEvent();
        setVisible(true);
    }
    public void setLayoutManager() {
        container.setLayout(null);
    }
    public void setLocationAndSize() {

        cod.setBounds(20, 10, 200, 30);
        codField.setBounds(125, 10, 100, 30);
        stock.setBounds(20, 50, 200, 30);
        stockField.setBounds(125, 50, 100, 30);
        modifica.setBounds(100, 100, 100, 30);
    }
    public void addComponentsToContainer(){
        container.add(cod);
        container.add(codField);
        container.add(stock);
        container.add(stockField);
        container.add(modifica);
    }
    public void addActionEvent(){modifica.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource()==modifica){
            int c, p;
            c = Integer.parseInt(codField.getText());
            p = Integer.parseInt(stockField.getText());
            try {
                Actualizare(c,p);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            Admin f = new Admin();
            setVisible(false);
            dispose();
        }
        }
        public void Actualizare(int c,int p) throws  SQLException{
            String SQL = "Select * from carti where Cod=" + c;
            int so=0;

            {


                try {

                    String myUrl = "jdbc:mysql://localhost/proiect_pi";
                    Connection conn = DriverManager.getConnection(myUrl, "root", "admin");


                    Statement st = conn.createStatement();


                    ResultSet rs = st.executeQuery(SQL);



                    while(rs.next()) {

                        int stock = rs.getInt("Stock");

                        so=stock+p;
                        Statement s=conn.createStatement();
                        s.executeUpdate("update carti set Stock='"+so+"'where Cod='"+c+"'");

                        s.close();
                    }


                    st.close();
                } catch (Exception e) {
                    System.err.println("Got an exception! ");
                    System.err.println(e.getMessage());
                }
            }
        }
}

