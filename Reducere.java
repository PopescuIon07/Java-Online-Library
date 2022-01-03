package ProiectPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.sql.*;

public class Reducere extends JFrame implements ActionListener{

    Container container = getContentPane();
    JLabel procent = new JLabel("Introdu procentul: ");
    JLabel cod = new JLabel("Cod:");
    JTextField codField = new JTextField();
    JTextField procentField = new JTextField();
    JButton modifica = new JButton("Modifica pret");

    Reducere(){
        setTitle("Marire pret dupa un anumit procent");
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

    public void setLocationAndSize(){

        cod.setBounds(20, 10, 200, 30);
        codField.setBounds(125, 10, 100, 30);
        procent.setBounds(20, 50, 200, 30);
        procentField.setBounds(125, 50, 100, 30);
        modifica.setBounds(100, 100, 100, 30);


    }

    public void addComponentsToContainer(){
        container.add(cod);
        container.add(codField);
        container.add(procent);
        container.add(procentField);
        container.add(modifica);
    }

    public void addActionEvent(){modifica.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==modifica) {
            int c, p;
            c = Integer.parseInt(codField.getText());
            p = Integer.parseInt(procentField.getText());
            try {
                Reducere(c,p);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            Admin f = new Admin();
            setVisible(false);
            dispose();
        }
    }
    public int Reducere(int c,int p) throws SQLException {
        String SQL = "Select * from carti where Cod=" + c;
        int pr=0;

        {


            try {

                String myUrl = "jdbc:mysql://localhost/proiect_pi";
                Connection conn = DriverManager.getConnection(myUrl, "root", "admin");


                Statement st = conn.createStatement();


                ResultSet rs = st.executeQuery(SQL);



                while(rs.next()) {

                    int pret = rs.getInt("Pret");



                    pr=pret - (pret*p) /100;

                    Statement s=conn.createStatement();
                    s.executeUpdate("update carti set Pret='"+pr+"'where Cod='"+c+"'");




                    s.close();
                }


                st.close();
            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        }
        return 0;
    }
}
