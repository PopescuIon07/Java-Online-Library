package ProiectPI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.security.SecureRandom;
import java.sql.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


import java.io.FileNotFoundException;

public class Register  implements Serializable {
    String name;
    String email;
    String telefon;
    String DoB;
    String adresa;
    String parola;

    public Register(String n, String e, String t, String d, String a, String b) {
        name = n;
        email = e;
        telefon = t;
        DoB = d;
        adresa = a;
        parola = b;
        try {
            DB(name, email, telefon, DoB, adresa, parola);
        } catch (SQLException | FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public Connection conect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/proiect_pi";
        String user = "root";
        String pass = "admin";
        return DriverManager.getConnection(url, user, pass);
    }

    public String DB(String name, String email, String telefon, String DoB, String adresa, String parola) throws SQLException, FileNotFoundException {
        String valid=generateRandomPassword();

        int ok = 0;
        if (testVerificareUUID(email) == true) ;
        ok = 1;


        String SQL = "insert into register(Name,Email,Dob,Telefon,Adresa,Parola,Valid,Verificare)"
                + "values (?,?,?,?,?,?,?,?) ";

        if (ok == 1) {
            try (Connection conn = conect()) {

                PreparedStatement st = conn.prepareStatement(SQL);
                st.setString(1, name);
                st.setString(2, email);
                st.setString(3, DoB);
                st.setString(4, telefon);
                st.setString(5, adresa);
                st.setString(6, parola);
                st.setBoolean(7,false);
                st.setString(8,valid);

                st.executeUpdate();

            } catch (Exception e) {
                System.err.println(e.getMessage());

                JOptionPane.showMessageDialog(null,
                        "Exista deja un cont cu acest email!");

            }
        } else {
            JOptionPane.showMessageDialog(null, "Email-ul introdus nu este valid!");
        }
        try {
            sendEmail(email,valid);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;

    }
    public boolean testVerificareUUID(String email) throws FileNotFoundException {

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find() == true)
            return true;
        else
            return false;
    }

    public static void sendEmail(String email,String valid) throws Exception {

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
        Message mesaj=prepMesaj(ses,myAccountEmail,email,valid);
        Transport.send(mesaj);

    }
    private static  Message prepMesaj(Session ses,String mail,String recepient,String valid){

        try {
            Message mesaj=new MimeMessage(ses);
            mesaj.setFrom(new InternetAddress(mail));
            mesaj.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            mesaj.setSubject("Email pentru verificarea contului.");
            mesaj.setText("Buna ziua, codul pentru verificare contului dumneavoastra este "+ valid +". Va multumim pentru ca ne-ati ales!");

            return mesaj;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    public static String generateRandomPassword()
    {
        int len = 10;
        int randNumOrigin = 48, randNumBound = 122;
        SecureRandom random = new SecureRandom();
        return random.ints(randNumOrigin, randNumBound + 1)
                .filter(i -> Character.isAlphabetic(i) || Character.isDigit(i))
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
}



