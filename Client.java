package ProiectPI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Client extends JFrame implements ActionListener {
    String nume;
    JFrame f = new JFrame("Client");
    JPanel panel = new JPanel();
    JButton adauga=new JButton("Adauga");
    JButton cauta=new JButton("Cautare");
    JButton cos=new JButton("Cos");
    JLabel cod=new JLabel("Cod:");
    JTextField codField=new JTextField();
    List<Produs> list=new ArrayList<>();
    String adresa;

    public Client(String nume,String adresa) {
        this.nume=nume;
        this.adresa=adresa;
        Adaugare(nume);
        addActionEvent();

    }
    public void addActionEvent(){
        adauga.addActionListener(this);
        cauta.addActionListener(this);
        cos.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==adauga)
        {
            String c=codField.getText();
            try {
                Adauga(c);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            String def="";
            codField.setText(def);
        }

        if(e.getSource()==cauta)
        {
            Cautare x=new Cautare();
        }
        if(e.getSource()==cos)
        {
          Cos x=new Cos(list,nume,adresa);

        }
    }

    public int Adaugare(String nume){
        JLabel buna=new JLabel("Bun venit "+nume+"!");
        String SQL="select * from carti";
        buna.setBounds(0,10,100,30);

        try {
            String myUrl = "jdbc:mysql://localhost/proiect_pi";
            Connection con = DriverManager.getConnection(myUrl, "root", "admin");
            Statement st=con.createStatement();
            ResultSet r=st.executeQuery(SQL);
            int size=0;
            while(r.next()){
                size++;
            }
            ResultSet rs=st.executeQuery(SQL);
            String coloana[]={"COD","TITLU","AUTOR","PRET","AN"};

            String data[][] = new String[size][5];
            int i=0;
            while(rs.next()){
                String cod=String.valueOf(rs.getInt("Cod"));
                String titlu=rs.getString("Titlu");
                String autor=rs.getString("Autor");
                String pret=String.valueOf(rs.getInt("Pret"));
                String an=String.valueOf(rs.getInt("An"));
                data[i][0]=cod;
                data[i][1]=titlu;
                data[i][2]=autor;
                data[i][3]=pret;
                data[i][4]=an;
                i++;
            }
            DefaultTableModel model = new DefaultTableModel(data,coloana);
            JTable table = new JTable(model);
            table.setShowGrid(true);
            table.setShowVerticalLines(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setDefaultEditor(Object.class, null);
            table.setRowHeight(20);
           table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
            JScrollPane pane = new JScrollPane(table);
            pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            panel.setBounds(100,80,200,500);
            adauga.setBounds(590,10,100,50);
            cod.setBounds(580,50,100,50);
            codField.setBounds(610,65,75,30);
            cauta.setBounds(5,50,100,50);
            cos.setBounds(590,400,100,50);
            panel.add(pane);
            f.add(cos);
            f.add(cauta);
            f.add(cod);
            f.add(codField);
            f.add(adauga);
            f.add(buna);
            f.add(panel);
            f.setBounds(700,400,710, 500);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            f.setVisible(true);




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public void Adauga(String c) throws SQLException{
         String SQL="Select * from carti where Cod='"+c+"'";
         try {
             String myUrl = "jdbc:mysql://localhost/proiect_pi";
             Connection conn = DriverManager.getConnection(myUrl, "root", "admin");

             Statement st = conn.createStatement();

             ResultSet rs = st.executeQuery(SQL);
             if(rs.next())
             {
                 int stock=rs.getInt("Stock");
                 if(stock!=0)
                 {
                     stock=stock-1;
                     Statement s=conn.createStatement();
                     s.executeUpdate("update carti set Stock='"+stock +"'where Cod='"+c+"'");
                     String titlu = rs.getString("Titlu");
                     int pret = rs.getInt("Pret");
                     Produs obj = new Produs(titlu, pret);
                     list.add(obj);
                 }
                 else
                 {
                     JOptionPane.showMessageDialog(null,"Momentan, cartea dorita nu este pe stock!");
                 }
             }
         }catch (Exception e){
             System.err.println("Exceptie");
             System.err.println(e.getMessage());
         }



    }
}
