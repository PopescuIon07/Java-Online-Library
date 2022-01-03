package ProiectPI;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Cos extends JFrame implements ActionListener {
    JFrame f = new JFrame("Cosul dumneavoastra");
    JPanel panel = new JPanel();
    JButton com=new JButton("Comanda!");
    int pretTotal;
    String email;
    String adresa;

    Cos(List<Produs> list,String email,String adresa){
        setTitle("Cos");
        this.email=email;
        this.adresa=adresa;
        f.setVisible(true);
        functie(list);
        addAction();
    }

    public void addAction(){
        com.addActionListener(this);
    }


    public void functie(List<Produs> list){


        String coloana[]={"TITLU","PRET"};
        String data[][]=new String[list.size()][2];
        int i=0;
        pretTotal=0;
        for(Produs el:list){
            data[i][0]=el.getTitlu();
            data[i][1]=String.valueOf(el.getPret());
            pretTotal=pretTotal+el.getPret();
            i++;
        }
        JLabel pret=new JLabel("Pret total: "+pretTotal+" lei.");
        pret.setBounds(0,10,100,30);
        DefaultTableModel model = new DefaultTableModel(data,coloana);
        JTable table = new JTable(model);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(20);
        JScrollPane pane = new JScrollPane(table);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        com.setBounds(590,10,100,50);
        panel.add(pane);
        f.add(pret);
        f.add(com);
        f.add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(700,400,710, 500);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==com)
        {
            if(pretTotal>0){
                JOptionPane.showMessageDialog(null,"Comanda a fost trimisa, multumim ca ati cumparat de la noi!");
                try {
                    sendEmail(email,pretTotal,adresa);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
                    comanda(email,pretTotal,adresa);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);

            }
            else {
                JOptionPane.showMessageDialog(null, "Nu puteti comanda nimic, deoarece cosul dumneavoastra este gol");

            }
            f.setVisible(false);
            f.dispose();

        }

    }
    public void sendEmail(String email,int pretTotal,String adresa)throws Exception{
        Properties prop=new Properties();

        prop.put("mail.smtp.auth","true");
        prop.put("mail.stmp.ssl.trust","smtp.gmail.com");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");
        String myAccountEmail="vanmusic07@gmail.com";
        String password="abecedar07";
        Session ses=Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });
        Message mesaj=prepMesaj(ses,myAccountEmail,email,pretTotal,adresa);
        Transport.send(mesaj);

    }
    private static  Message prepMesaj(Session ses,String mail,String recepient,int pretTotal,String adresa){

        try {
            Message mesaj=new MimeMessage(ses);
            mesaj.setFrom(new InternetAddress(mail));
            mesaj.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            mesaj.setSubject("Comanda dumneavoastra.");
            mesaj.setText("Comanda dumneavoastra a fost trimisa cu succes in valoare de: "+pretTotal+" lei. Comanda va fi livrata la adresa: "+adresa+".");

            return mesaj;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    public Connection conect() throws SQLException {
        String url="jdbc:mysql://localhost:3306/proiect_pi";
        String user="root";
        String pass="admin";
        return DriverManager.getConnection(url, user, pass);
    }
    public void comanda(String email,int pretTotal,String adresa)throws SQLException{
        String SQL="insert into comenzi(Email,Pret,Adresa)"+
                "values(?,?,?)";
        try (Connection conn=conect()){
            PreparedStatement st = conn.prepareStatement(SQL);
            st.setString(1,email);
            st.setInt(2,pretTotal);
            st.setString(3,adresa);
            st.executeUpdate();
            st.close();

        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
