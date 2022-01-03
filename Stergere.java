package ProiectPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Stergere extends JFrame implements ActionListener{

    Container container = getContentPane();
    JLabel cod = new JLabel("Introdu Codul: ");
    JTextField codField = new JTextField();
    JButton stergere=new JButton("Stergere");

    Stergere(){
        setTitle("Stergere care dupa cod");
        setBounds(700, 400, 300, 150);
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

        cod.setBounds(20,10,200,30);
        codField.setBounds(100,10,100,30);
        stergere.setBounds(100,50,100,30);


    }

    public void addComponentsToContainer(){
       container.add(cod);
       container.add(codField);
       container.add(stergere);
    }

    public void addActionEvent(){stergere.addActionListener(this);
    }

    public Connection conect() throws SQLException {
        String url="jdbc:mysql://localhost:3306/proiect_pi";
        String user="root";
        String pass="admin";
        return DriverManager.getConnection(url, user, pass);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==stergere) {
            int c;
            c=Integer.parseInt(codField.getText());
            Stergere(c);

            Admin f = new Admin();
            setVisible(false);
            dispose();

        }
    }

    public int Stergere(int cod){
        String SQL ="delete from carti where Cod=?";


        try (Connection conn=conect()){
            PreparedStatement st = conn.prepareStatement(SQL);
            st.setInt(1,cod);
            st.executeUpdate();
            st.close();

        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return 0;
    }
}
