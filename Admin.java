package ProiectPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class Admin extends JFrame implements ActionListener {
    Container container = getContentPane();
    JButton adaugare = new JButton("Adaugare carte");
    JButton stergere = new JButton("Stergere carte");
    JButton marirePret=new JButton("Marire pret");
    JButton redPret=new JButton("Reducere pret");
    JButton vizualizareArhiva=new JButton("Vizualizare arhiva cartii");
    JButton stock=new JButton("Actualizare stock");

    public Admin() {
        setTitle("Panou Administrator");
        setBounds(700, 200, 370, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setVisible(true);



    }
    public void setLayoutManager() {
        container.setLayout(null);
    }
    public void setLocationAndSize() {

        adaugare.setBounds(50,100,100,100);
        stergere.setBounds(50,200,100,100);
        marirePret.setBounds(200,100,100,100);
        redPret.setBounds(200,200,100,100);
        vizualizareArhiva.setBounds(50,300,100,100);
        stock.setBounds(200,300,100,100);


    }
    public void addComponentsToContainer() {
        container.add(adaugare);
        container.add(stergere);
        container.add(marirePret);
        container.add(redPret);
        container.add(vizualizareArhiva);
        container.add(stock);
    }

    public void addActionEvent() {
        adaugare.addActionListener(this);
        stergere.addActionListener(this);
        marirePret.addActionListener(this);
        redPret.addActionListener(this);
        vizualizareArhiva.addActionListener(this);
        stock.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==adaugare){
            Adaugare x=new Adaugare();
            this.setVisible(false);
        }
        if(e.getSource()==stergere){
            Stergere x=new Stergere();
            this.setVisible(false);
        }

        if(e.getSource()==marirePret){
            Marire x=new Marire();
            this.setVisible(false);
        }

        if(e.getSource()==redPret){
            Reducere x=new Reducere();
            this.setVisible(false);
        }

        if(e.getSource()==vizualizareArhiva){
            Vizualizare x= new Vizualizare();
            this.setVisible(false);
        }
        if(e.getSource()==stock)
        {
            Stock x=new Stock();
            this.setVisible(false);
        }

    }
}
