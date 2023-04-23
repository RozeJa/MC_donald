package admin.gui.dataPanesl;

import javax.swing.*;

import share.data.Improvement;
import share.data.Category;

import java.awt.GridBagLayout;
import java.awt.*;

public class ImprovementPanel extends ADataPanel<Improvement> {
    @Override
    public void init() {
        GridBagConstraints gbc;

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();

        JLabel header = new JLabel("Vylepšení");
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(header, gbc);

        JLabel id = new JLabel("ID-");
        gbc.gridy++;
        gbc.gridx = 0;
        add(id, gbc);

        JLabel idVal = new JLabel(String.valueOf(data.getId()));
        gbc.gridx++;
        add(idVal, gbc);

        JLabel name = new JLabel("Název vylepšení-");
        gbc.gridy++;
        gbc.gridx = 0;
        add(name, gbc);

        JLabel nameVal = new JLabel(data.getName());
        gbc.gridx++;
        add(nameVal, gbc);

        JLabel price = new JLabel("Cena-");
        gbc.gridy++;
        gbc.gridx = 0;
        add(price, gbc);

        JLabel priceVal = new JLabel(String.valueOf(data.getPrice()));
        gbc.gridx++;
        add(priceVal, gbc);

        for (Category category : data.getCategories()) {
            JLabel avCategory = new JLabel("Dostupná pro kategorie-");
            gbc.gridy++;
            gbc.gridx = 0;
            add(avCategory, gbc);
    
            JLabel avCategoryVal = new JLabel(category.getName());
            gbc.gridx++;
            add(avCategoryVal, gbc);
        }

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
