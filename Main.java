package ProiectPI;



import javax.swing.JFrame;


public class Main {

    public static void main(String[] args) {

        LoginFrame frame = new LoginFrame();
       frame.setTitle("Login Form");
      frame.setVisible(true);
        frame.setBounds(700, 200, 370, 600);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setResizable(false);
        //Admin x=new Admin();
        //Client x=new Client("Cineva");
        //Cautare x=new Cautare();
        //Vizualizare x=new Vizualizare();
        //Adaugare x=new Adaugare();

    }

}
