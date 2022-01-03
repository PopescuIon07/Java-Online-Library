package ProiectPI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel emailLabel = new JLabel("EMAIL");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JButton registerButton=new JButton("Register");
    JCheckBox showPassword = new JCheckBox("Show Password");


    LoginFrame() {

        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        emailLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);
        registerButton.setBounds(125,400,100,30);


    }

    public void addComponentsToContainer() {
        container.add(emailLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(registerButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        registerButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {
            String userText;
            int ok=1;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText().toString();
            if (userText.equalsIgnoreCase("admin") && pwdText.equalsIgnoreCase("admin")) {
                ok=0;
                Admin x = new Admin();
                this.setVisible(false);
            }
            else
                {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/proiect_pi","root","admin");
                        Statement stmt=con.createStatement();
                        String sql="Select * from register where Email='"+userText+"'";
                        ResultSet rs=stmt.executeQuery(sql);
                        if(rs.next()) {

                            boolean valid=rs.getBoolean("Valid");
                            if(valid==false){
                                String SQL2="Select * from register where Email='"+userText + "' and Verificare='"+pwdText+"'";
                                Statement s=con.createStatement();
                                ResultSet rss=s.executeQuery(SQL2);
                               if(rss.next()){
                                   Statement ss=con.createStatement();
                                   JOptionPane.showMessageDialog(null,"Contul a fost activat, de acu inainte va puteti conecta cu PAROLA introdusa de dumneavoastra. Introduceti parola pentru a va conecta.");
                                   s.executeUpdate("update register set Valid='"+1+"'where Email='"+userText+"'");

                               }

                            }
                            else
                                if(valid==true){
                                    String SQL3="Select * from register where Email='"+userText + "' and Parola='"+pwdText+"'";
                                    Statement s=con.createStatement();
                                    ResultSet r=s.executeQuery(SQL3);
                                    String nume;

                                    if(r.next())
                                    {
                                        String adresa=rs.getString("Adresa");
                                        nume=r.getString("Name");

                                        JOptionPane.showMessageDialog(null, "Login successful!");
                                        Client x=new Client(userText,adresa);
                                        this.setVisible(false);
                                    }
                                }
                        }
                        ok=0;
                    }catch (Exception j){
                        System.out.println(j);

                    }

                }
            if(ok==1)
            {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }


        }
        if(e.getSource() == registerButton){
            MyFrame f =new MyFrame(this);
            this.setVisible(false);
        }

        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }


        }
    }

}







