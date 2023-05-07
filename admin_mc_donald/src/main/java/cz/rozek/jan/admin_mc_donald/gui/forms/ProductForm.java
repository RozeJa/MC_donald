package cz.rozek.jan.admin_mc_donald.gui.forms;

import javax.swing.*;

import cz.rozek.jan.admin_mc_donald.data.models.Category;
import cz.rozek.jan.admin_mc_donald.data.models.Improvement;
import cz.rozek.jan.admin_mc_donald.data.models.Product;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.LinkedList;

public class ProductForm extends EditForm<Product> {
    private JLabel improvementLabel = new JLabel("Vylepšení");
    private List<JComboBox<String>> listOfComboboxs = new LinkedList<>();
    private List<JLabel> labels = new LinkedList<>();

    private List<Improvement> improvmentAsIndexis = new ArrayList<>();
    private List<Category> categoriesAsIndexis = new ArrayList<>();
    private String[] arrImprovement;

    private JTextField name = new JTextField(12);
    private JTextField price = new JTextField(12);
    private JComboBox<String> category;
    private Map<String, Product> products;

    private boolean updated;

    @Override
    protected void initComponents() {

        try {
            products = db.getAll(Product.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JDialog(), "Nepodařilo se načíst Vylepšení ze servru.");
        }

        loadImprevements();

        updated = data != null;

        setTitle(updated ? "Editování produktu" : "Vytváření nového produktu");

        if (!updated)
            data = new Product();

        category = new JComboBox<>(loadCategories());
        buildLayout();

        JLabel nameLabel = new JLabel("Název");
        JLabel priceLabel = new JLabel("Cena");
        JLabel categoryLabel = new JLabel("Kategorie");

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

        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(categoryLabel, gbc);

        gbc.gridx++;
        if (updated)
            category.setSelectedItem(data.getCategory().getName());
        contentPane.add(category, gbc);

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
            for (Improvement improvement : data.getAvailableImprovements().values()) {
                addComboBox(improvement);
            }
            addComboBox(null);
        }
    }

    private String[] loadCategories() {
        Map<String, Category> categories = new TreeMap<>();
        
        categories = db.getAll(Category.class);
        categories = db.getAll(Category.class);

        String[] arr = new String[categories.size() + 1];

        arr[0] = "";
        categoriesAsIndexis.add(null);

        int j = 1;
        for (Category c : categories.values()) {
            arr[j++] = c.getName();
            categoriesAsIndexis.add(c);
        }

        return arr;
    }

    private void addComboBox(Improvement imp) {
        gbc.gridx = 0;
        gbc.gridy++;

        JLabel label = new JLabel(improvementLabel.getText());
        labels.add(label);
        contentPane.add(label, gbc);

        gbc.gridx++;

        JComboBox<String> jcb = new JComboBox<>(arrImprovement);
        jcb.addKeyListener(getKeyListener());
        listOfComboboxs.add(jcb);
        if (imp != null)
            jcb.setSelectedItem(imp.getName());
        contentPane.add(jcb, gbc);

        jcb.addActionListener(l -> {
            if (listOfComboboxs.get(listOfComboboxs.size() - 1).getSelectedIndex() > 0) {
                addComboBox(null);
            }
        });
    }

    private void loadImprevements() {
        Map<String, Improvement> improvements = new TreeMap<>();
        try {
            Map<String, Improvement> allImprovements = db.getAll(Improvement.class);

            if (data != null) {
                for (Improvement improvement : allImprovements.values()) {
                    if (improvement.getAvailableCategories().get(data.getCategory().getId()) != null) {
                        improvements.put(improvement.getId(), improvement);
                    }
                }
            } else
                improvements = allImprovements;

        } catch (Exception e) {
            e.printStackTrace();
        }

        arrImprovement = new String[improvements.size() + 1];

        arrImprovement[0] = "\t";
        improvmentAsIndexis.add(null);

        int j = 1;
        for (Improvement i : improvements.values()) {
            arrImprovement[j++] = i.getName();
            improvmentAsIndexis.add(i);
        }
    }

    @Override
    protected void initFunctions() {
        confirm.addActionListener(l -> onConfirm());
        exit.addActionListener(l -> setVisible(false));
        price.addKeyListener(getKeyListener());
        name.addKeyListener(getKeyListener());
        category.addKeyListener(getKeyListener());
        category.addActionListener(l -> {

            if (categoriesAsIndexis.get(category.getSelectedIndex()) != null) {
                data.setCategory(categoriesAsIndexis.get(category.getSelectedIndex()));

                loadImprevements();

                int i = 0;
                for (JComboBox jcb : listOfComboboxs) {
                    contentPane.remove(jcb);
                    contentPane.remove(labels.get(i));
                    i++;
                }

                listOfComboboxs = new LinkedList<>();
                labels = new LinkedList<>();
                addComboBox(null);
            }
        });
    }

    @Override
    protected String setData() {
        StringBuilder sb = new StringBuilder();

        String lastName = data.getName();

        if (!name.getText().trim().equals("")) {
            data.setName(name.getText().trim());
        } else {
            sb.append("Je třeba vyplnit název produktu");
        }

        if (products.values().contains(data) && data.getId() == null) {
            sb.append(String.format("Produkt s názvem %s již existuje.", data.getName()));
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
        if (categoriesAsIndexis.get(category.getSelectedIndex()) != null) {
            data.setCategory(categoriesAsIndexis.get(category.getSelectedIndex()));
        } else {
            sb.append("\nJe třeba vybrat kategorii");
        }
        data.removeImprovements();
        for (JComboBox<String> jcob : listOfComboboxs) {
            Improvement i = improvmentAsIndexis.get(jcob.getSelectedIndex());
            if (i != null)
                data.addImprovement(i);
        }

        return sb.toString();
    }
}
