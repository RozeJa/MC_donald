package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import data.DB;
import data.models.IData;
import data.models.Order;
import gui.dataPanesl.ADataPanel;
import gui.forms.EditForm;

import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.TreeMap;

public class MainFrame extends JFrame {

    private Container container;
    private JList<Object> datatypes;
    private JScrollPane datatypesSP, dataPrezentSP;
    private JPanel dataContentPanel;

    private JButton add;

    private Class<? extends IData> actualClass;
    private Map<String, Class<? extends IData>> nameToClassMap;

    private DB db;

    public MainFrame(DB db, String title) {
        setTitle(title);

        this.db = db;
        nameToClassMap = db.getDataTypes();

        initComponents();

        initFunctions();
    }

    private void initFunctions() {
        MouseListener selectDataTyps = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actualClass = nameToClassMap
                        .get(datatypes.getModel().getElementAt(datatypes.getSelectedIndex()).toString());
                fillDataContent();
            }
        };
        datatypes.addMouseListener(selectDataTyps);

        add.addActionListener(l -> {
            try {
                EditForm<IData> ef = getForm(null);

                setJDialog(ef);

                if (ef.isConfirmed()) {
                    if (db.writeData(ef.getData()) == null) {
                        JOptionPane.showMessageDialog(new JDialog(), "Přidáná se nepovedlo.");
                    }

                    fillDataContent();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("chyba při přidávání " + actualClass.getSimpleName());
            }
        });
    }

    private EditForm<IData> getForm(IData data) {
        EditForm<IData> form = null;
        try {
            form = (EditForm<IData>) Class.forName("gui.forms." + actualClass.getSimpleName() + "Form").getConstructor()
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        form.setData(data);
        form.setDb(db);

        form.init();

        return form;
    }

    private void initComponents() {
        // naházení komponent na stránku
        container = this.getContentPane();

        container.setLayout(new GridLayout(1, 2));

        datatypes = new JList<>(nameToClassMap.keySet().toArray());
        datatypesSP = new JScrollPane(datatypes);

        dataContentPanel = new JPanel();
        dataContentPanel.setMinimumSize(new DimensionUIResource(960, 1080));
        dataPrezentSP = new JScrollPane(dataContentPanel);
        fillDataContent();

        JPanel midPan = new JPanel(new GridLayout(1, 3));

        JPanel right = new JPanel(new GridLayout(4, 1));

        add = new JButton("Přidat");
        right.add(add);

        midPan.add(datatypesSP);
        midPan.add(new JPanel());
        midPan.add(right);
        midPan.add(new JPanel());

        container.add(midPan);
        container.add(dataPrezentSP);
    }

    public void fillDataContent() {
        if (actualClass == null)
            actualClass = (Class<? extends IData>) nameToClassMap.values().toArray()[0];

        Map<String, ? extends IData> data = new TreeMap<>();
        try {
            data = db.getAll(actualClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataContentPanel.removeAll();
        
        if (actualClass.getName().equals(Order.class.getName())) 
            dataContentPanel.setLayout(new GridLayout((data.size()),1));
        else 
            dataContentPanel.setLayout(new GridLayout((data.size() / 2) + data.size() % 2, 2));


        for (IData dat : data.values()) {
            dataContentPanel.add(getNewDataPanel(dat));
        }

        dataContentPanel.setVisible(false);
        dataContentPanel.setVisible(true);
    }

    private ADataPanel<IData> getNewDataPanel(IData dat) {

        ADataPanel<IData> panel = null;
        try {
            panel = (ADataPanel<IData>) Class.forName("gui.dataPanesl." + dat.getClass().getSimpleName() + "Panel")
                    .getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        panel.setData(dat);
        panel.setUpdateEvent(l -> {
            try {
                EditForm<IData> ef = getForm(dat);

                setJDialog(ef);

                if (ef.isConfirmed()) {
                    if (db.writeData(ef.getData()) == null) {
                        JOptionPane.showMessageDialog(new JDialog(), "Editování se nepovedlo.");
                    }

                    fillDataContent();
                }
            } catch (Exception e) {
                System.out.println("chyba při editování " + actualClass.getSimpleName());
            }
        });

        panel.setDeleteEvent(l -> {
            try {
                if (db.delete(dat) != null) {
                    JOptionPane.showMessageDialog(new JDialog(), "Smazaní " + dat.toString());
                } else {
                    JOptionPane.showMessageDialog(new JDialog(), "Smazání se nezdařilo.");
                }

                fillDataContent();
            } catch (Exception e) {
                System.out.println("chyba při odebírání " + actualClass.getSimpleName());
            }
        });
        panel.init();

        return panel;
    }

    private void setJDialog(JDialog frame) {
        frame.setModal(true);
        frame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}