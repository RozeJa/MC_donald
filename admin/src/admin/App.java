package admin;
import java.rmi.Naming;

import javax.swing.JFrame;

import admin.gui.Client;
import admin.gui.MainFrame;
import share.interfaces.IClient;
import share.interfaces.ServerApi;

public class App {
    
    public static void main(String[] args) throws Exception {
        // nalezení nasdíleného objektu 
        // příklad je použit pro localhosta
        ServerApi db = (ServerApi) Naming.lookup("rmi://localhost:8585/dbApi");

        MainFrame mf = new MainFrame(db, "MC donald");

        IClient c = new Client(mf);

        db.addClient(c);

        setFrame(mf);
    }
    

    // zobrazení okna
    private static void setFrame(JFrame frame) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

}
