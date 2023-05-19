package cz.rozek.jan.admin_mc_donald.gui.dataPanesl;

import javax.swing.*;

import cz.rozek.jan.admin_mc_donald.data.models.Improvement;
import cz.rozek.jan.admin_mc_donald.data.models.Product;

import java.awt.GridBagLayout;
import java.awt.*;

public class ProductPanel extends ADataPanel<Product> {
    @Override
    public void init() {
        GridBagConstraints gbc;

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();

        JLabel header = new JLabel("Produkt");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(header, gbc);

        JLabel id = new JLabel("ID-");
        gbc.gridy++;
        gbc.gridx = 0;
        add(id, gbc);

        JLabel idVal = new JLabel(String.valueOf(data.getId()));
        gbc.gridx++;
        add(idVal, gbc);

        JLabel name = new JLabel("Název produktu-");
        gbc.gridy++;
        gbc.gridx = 0;
        add(name, gbc);

        JLabel nameVal = new JLabel(data.getName());
        gbc.gridx++;
        add(nameVal, gbc);

        JLabel bgImgURI = new JLabel("Odkaz na pozadí produktu-");
        gbc.gridy++;
        gbc.gridx = 0;
        add(bgImgURI, gbc);

        JLabel bgImgURIVal = new JLabel(data.getBgImgURI());
        gbc.gridx++;
        add(bgImgURIVal, gbc);

        JLabel cat = new JLabel("Kategorie-");
        gbc.gridy++;
        gbc.gridx = 0;
        add(cat, gbc);

        JLabel catVal = new JLabel(data.getCategory().getName());
        gbc.gridx++;
        add(catVal, gbc);

        JLabel price = new JLabel("Cena-");
        gbc.gridy++;
        gbc.gridx = 0;
        add(price, gbc);

        JLabel priceVal = new JLabel(String.valueOf(data.getPrice()));
        gbc.gridx++;
        add(priceVal, gbc);

        for (Improvement improvement : data.getAvailableImprovements().values()) {
            JLabel avImprovement = new JLabel("Dostupné vylepšení-");
            gbc.gridy++;
            gbc.gridx = 0;
            add(avImprovement, gbc);

            JLabel avImprovementVal = new JLabel(improvement.getName());
            gbc.gridx++;
            add(avImprovementVal, gbc);
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
