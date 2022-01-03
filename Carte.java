package ProiectPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Carte {
    int cod;
    String titlu;
    String autor;
    int pret;
    int anAp;
    int cantitate;
    public Carte(String t, String a,int p,int an,int can){

         titlu=t;
         autor=a;
         pret=p;
         anAp=an;
         cantitate=can;
        try {
            Adaugare(titlu,autor,pret,anAp,cantitate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection conect() throws SQLException {
        String url="jdbc:mysql://localhost:3306/proiect_pi";
        String user="root";
        String pass="admin";
        return DriverManager.getConnection(url, user, pass);
    }
    public  String Adaugare(String titlu, String autor,int pret, int an,int cantitate)throws SQLException {


        String SQL ="insert into carti(Titlu,Autor,Pret,An,Stock)"
                +"values (?,?,?,?,?) ";


        try (Connection conn=conect()){
            PreparedStatement st = conn.prepareStatement(SQL);
            st.setString(1,titlu);
            st.setString(2,autor);
            st.setInt(3,pret);
            st.setInt(4,an);
            st.setInt(5,cantitate);
            st.executeUpdate();
            st.close();

        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;

    }
}
