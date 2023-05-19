package cz.rozek.jan.admin_mc_donald.gui.dataPanesl;

import javax.swing.*;

import cz.rozek.jan.admin_mc_donald.data.models.Category;

import java.awt.GridBagLayout;
import java.awt.*;

public class CategoryPanel extends ADataPanel<Category> {
    @Override
    public void init() {
        GridBagConstraints gbc;

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();

        JLabel header = new JLabel("Kategorie");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(header, gbc);

        JLabel id = new JLabel("ID -");
        gbc.gridy++;
        add(id, gbc);           

        JLabel idVal = new JLabel(String.valueOf(data.getId()));
        gbc.gridx++;
        add(idVal, gbc);

        JLabel name = new JLabel("Název kategorie -");
        gbc.gridx = 0;
        gbc.gridy++;
        add(name, gbc);

        JLabel nameVal = new JLabel(data.getName());
        gbc.gridx++;
        add(nameVal, gbc);

        JLabel bgImgURI = new JLabel("Pozadí kategorie -");
        gbc.gridx = 0;
        gbc.gridy++;
        add(bgImgURI, gbc);

        JLabel bgImgURIVal = new JLabel(data.getBgImgURI());
        gbc.gridx++;
        add(bgImgURIVal, gbc);


        JButton edit = new JButton("Editovat");
        edit.addActionListener(update);
        gbc.gridx = 0;
        gbc.gridy++;
        add(edit, gbc);

        JButton delete = new JButton("Odebrat");
        delete.addActionListener(delet);
        gbc.gridx++;
        add(delete, gbc);
    }
}
