package ProiectPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Cautare extends JFrame implements ActionListener {
    Container con=getContentPane();
    JLabel nume=new JLabel("Introdu numele cartii");
    JTextField nField=new JTextField();
    JButton cauta=new JButton("Cautare");
    public Cautare(){
        setTitle("Cautare");
        setBounds(700,200,300,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();


    }
    public void setLayoutManager() {
        con.setLayout(null);
    }
    public void setLocationAndSize(){
        nume.setBounds(0,10,200,30);
        nField.setBounds(120,10,160,30);
        cauta.setBounds(100,40,100,30);

    }
    public void addComponentsToContainer(){
        con.add(nume);
        con.add(nField);
        con.add(cauta);

    }
    public void addActionEvent(){
      cauta.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==cauta){
            try {
                String titlu=nField.getText();
                Search(titlu);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public String Search(String titlu) throws SQLException {
        String SQL="Select * from carti where Titlu='"+titlu+"'";
        try {
            String myUrl = "jdbc:mysql://localhost/proiect_pi";
            Connection conn = DriverManager.getConnection(myUrl, "root", "admin");


            Statement st = conn.createStatement();


            ResultSet rs = st.executeQuery(SQL);
            if(rs.next()){
                String cod=String.valueOf(rs.getInt("Cod"));
                String autor=rs.getString("Autor");
                String pret=String.valueOf(rs.getInt("Pret"));
                String an=String.valueOf(rs.getInt("An"));
                JOptionPane.showMessageDialog(null,"Am gasit cartea "+titlu+" scrisa de " + autor +" in valoare de "+ pret +" lei avand codul "+cod+" .");
                dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Cartea cautata nu se afla in arhivaza noastra.");
                dispose();
            }

        }catch (Exception e){
            System.err.println("Exceptie!");
            System.err.println(e.getMessage());
        }
        return null;
    }
}
