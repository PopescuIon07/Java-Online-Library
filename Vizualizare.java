package ProiectPI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vizualizare extends JFrame implements ActionListener{

    JFrame f = new JFrame("Vizualizare");
    JPanel panel = new JPanel();
    JButton inapoi=new JButton("Back");

    public Vizualizare() {
        View();
        addActionEvent();
    }

    public void View() {
        String SQL = "select * from carti";

        try {
            String myUrl = "jdbc:mysql://localhost/proiect_pi";
            Connection con = DriverManager.getConnection(myUrl, "root", "admin");
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(SQL);
            int size = 0;
            while (r.next()) {
                size++;
            }
            ResultSet rs = st.executeQuery(SQL);
            String coloana[] = {"COD", "TITLU", "AUTOR", "PRET", "AN"};

            String data[][] = new String[size][5];
            int i = 0;
            while (rs.next()) {
                String cod = String.valueOf(rs.getInt("Cod"));
                String titlu = rs.getString("Titlu");
                String autor = rs.getString("Autor");
                String pret = String.valueOf(rs.getInt("Pret"));
                String an = String.valueOf(rs.getInt("An"));
                data[i][0] = cod;
                data[i][1] = titlu;
                data[i][2] = autor;
                data[i][3] = pret;
                data[i][4] = an;
                i++;
            }
            DefaultTableModel model = new DefaultTableModel(data, coloana);
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
            inapoi.setBounds(0,10,100,30);
            pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            panel.add(pane);
            f.add(inapoi);
            f.add(panel);
            f.setBounds(700,400,710, 500);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            f.setVisible(true);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addActionEvent(){

        inapoi.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==inapoi) {
            f.setVisible(false);
            Admin x = new Admin();
        }
    }
}
