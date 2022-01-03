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

public class Adaugare extends JFrame implements ActionListener{

    Container container = getContentPane();

    JLabel titlu = new JLabel("Titlul");
    JLabel autor = new JLabel("Autorul");
    JLabel pret = new JLabel("Pretul");
    JLabel an = new JLabel("Anul");
    JLabel stock=new JLabel("Cantitate");

    JTextField titluField = new JTextField();
    JTextField autorField = new JTextField();
    JTextField pretField = new JTextField();
    JTextField anField = new JTextField();
    JTextField stockField=new JTextField();
    JButton adaugare=new JButton("Adaugare");

    Adaugare(){
        setTitle("Adaugare Carte");
        setBounds(300, 90, 600, 600);
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


        titlu.setBounds(20,100,200,30);
        titluField.setBounds(70,100,100,30);
        autor.setBounds(20,150,200,30);
        autorField.setBounds(70,150,100,30);
        pret.setBounds(200,100,200,30);
        stock.setBounds(100,200,100,30);
        stockField.setBounds(160,200,100,30);
        pretField.setBounds(250,100,100,30);
        an.setBounds(200,150,100,30);
        anField.setBounds(250,150,100,30);
        adaugare.setBounds(150,250,100,50);

    }

    public void addComponentsToContainer(){
        container.add(titlu);
        container.add(titluField);
        container.add(autor);
        container.add(autorField);
        container.add(pret);
        container.add(stockField);
        container.add(pretField);
        container.add(stock);
        container.add(an);
        container.add(anField);
        container.add(adaugare);
    }

    public void addActionEvent(){adaugare.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource()==adaugare) {
                int c,p,a;
                String t,au;

                t=titluField.getText();
                au=autorField.getText();
                p=Integer.parseInt(pretField.getText());
                a=Integer.parseInt(anField.getText());
                c=Integer.parseInt(stockField.getText());
                Carte obj=new Carte(t,au,p,a,c);
                Admin f = new Admin();
                setVisible(false);
                dispose();
            }

    }

}


