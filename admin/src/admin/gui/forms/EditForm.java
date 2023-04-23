package admin.gui.forms;

import javax.swing.JDialog;

import share.interfaces.IDB;
import share.data.IData;

import java.awt.*;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class EditForm<E extends IData> extends JDialog {
    protected IDB db;

    protected E data;
    protected boolean confirmed = false;

    private Container container;
    protected JPanel contentPane = new JPanel(new GridBagLayout());
    protected GridBagConstraints gbc = new GridBagConstraints();    
    
    protected JButton confirm = new JButton("Potvrdit"), exit = new JButton("Zrušit");

    public E getData() {
        return data;
    }
    public void setData(E data) {
        this.data = data;
    }
    public void setDb(IDB db) {
        this.db = db;
    }

    protected KeyListener getKeyListener() {
        return new KeyListener() {  
            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (KeyEvent.VK_ENTER  == e.getKeyCode()) {
                    onConfirm();
                }  
            }
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException();
            }
        };
    }

    protected void onConfirm() {
        String message = setData();        
        if (message.equals("")) {
            this.confirmed = true;
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(new JDialog(), message);
        }
    }

    protected abstract String setData();

    public void init() {
        initComponents();
        initFunctions();
    }

    protected abstract void initComponents();

    protected abstract void initFunctions();

    protected void buildLayout() {
        container = this.getContentPane();

        container.setLayout(new GridLayout(1,3));
        container.add(new JPanel());

        container.add(new JScrollPane(contentPane));
        
        container.add(new JPanel());
    }
    public boolean isConfirmed() {
        return confirmed;
    }
}
