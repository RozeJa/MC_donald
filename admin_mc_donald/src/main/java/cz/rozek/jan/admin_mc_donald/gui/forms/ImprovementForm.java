package cz.rozek.jan.admin_mc_donald.gui.forms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import cz.rozek.jan.admin_mc_donald.data.models.Category;
import cz.rozek.jan.admin_mc_donald.data.models.Improvement;


public class ImprovementForm extends EditForm<Improvement> {
    private JLabel categoryLabel = new JLabel("Kategorie");
    private List<JComboBox<String>> listOfComboboxs = new LinkedList<>();

    private String[] arrCategories;

    private JTextField name = new JTextField(12);
    private JTextField price = new JTextField(12);

    private boolean updated;

    private List<Category> categoriesAsIndexis = new ArrayList<>();
    private Map<String, Improvement> improvements;

    public ImprovementForm() {
        super();
    }

    @Override
    protected void initComponents() {

        try {
            improvements = db.getAll(Improvement.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JDialog(), "Nepodařilo se načíst Vylepšení ze servru.");
        }

        loadCategories();

        updated = data != null;
        setTitle(updated ? "Editování vylepšení" : "Vytváření nového vylepšení");

        if (!updated)
            data = new Improvement();

        buildLayout();

        JLabel nameLabel = new JLabel("Název");
        JLabel priceLabel = new JLabel("Cena");

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(nameLabel, gbc);

        gbc.gridx++;
        if (updated)
            name.setText(data.getName());
        contentPane.add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(priceLabel, gbc);

        gbc.gridx++;
        if (updated)
            price.setText(String.valueOf(data.getPrice()));
        contentPane.add(price, gbc);

        int gridy = gbc.gridy;
        gbc.gridx = 0;
        gbc.gridy = 2048;
        contentPane.add(confirm, gbc);

        gbc.gridx++;
        contentPane.add(exit, gbc);

        gbc.gridy = gridy;

        if (!updated) {
            addComboBox(null);
        } else {
            for (Category category : data.getAvailableCategories().values()) {
                addComboBox(category);
            }
            addComboBox(null);
        }
    }

    private void addComboBox(Category cat) {
        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(new JLabel(categoryLabel.getText()), gbc);

        gbc.gridx++;

        JComboBox<String> jcb = new JComboBox<>(arrCategories);
        jcb.addKeyListener(getKeyListener());
        listOfComboboxs.add(jcb);
        if (cat != null)
            jcb.setSelectedItem(cat.getName());
        contentPane.add(jcb, gbc);

        jcb.addActionListener(l -> {
            JComboBox jcba = listOfComboboxs.get(listOfComboboxs.size() - 1);
            if (jcba.getSelectedIndex() > 0) {
                addComboBox(null);
            }
        });
    }

    private void loadCategories() {
        Map<String, Category> categories = new TreeMap<>();
    
        categories = db.getAll(Category.class);

        arrCategories = new String[categories.size() + 1];

        arrCategories[0] = "\t";
        categoriesAsIndexis.add(null);

        int j = 1;
        for (Category c : categories.values()) {
            arrCategories[j++] = c.getName();
            categoriesAsIndexis.add(c);
        }
    }

    @Override
    protected void initFunctions() {
        confirm.addActionListener(l -> {
            onConfirm();
        });
        exit.addActionListener(l -> setVisible(false));
        price.addKeyListener(getKeyListener());
        name.addKeyListener(getKeyListener());
    }

    @Override
    protected String setData() {
        StringBuilder sb = new StringBuilder();

        String lastName = data.getName();
        if (!name.getText().trim().equals("")) {
            data.setName(name.getText().trim());
        } else {
            sb.append("Je třeba vyplnit název vylepšení");
        }

        if (improvements.values().contains(data) && data.getId() == null) {
            sb.append(String.format("Vylepšení s názvem %s již existuje.", data.getName()));
            data.setName(lastName);
        }

        if (!price.getText().trim().equals("")) {

            try {
                data.setPrice(Double.parseDouble(price.getText().trim()));
            } catch (Exception e) {
                sb.append(String.format("\nNelze převést %s na číslo", price.getText().trim()));
            }
        } else {
            sb.append("\nJe třeba vyplnit cenu");
        }
        data.removeCategories();
        for (JComboBox<String> jcob : listOfComboboxs) {
            Category c = categoriesAsIndexis.get(jcob.getSelectedIndex());
            if (c != null)
                data.getAvailableCategories().put(c.getId(), c);
        }

        return sb.toString();
    }
}
