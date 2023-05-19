package cz.rozek.jan.admin_mc_donald.gui.forms;

import java.util.Map;

import javax.swing.*;

import cz.rozek.jan.admin_mc_donald.data.models.Category;

public class CategoryForm extends EditForm<Category> {
    private JTextField name = new JTextField(12);
    private JTextField bgImgURI = new JTextField(12);
    private Map<String, Category> categories;

    @Override
    protected void initComponents() {

        try {
            categories = db.getAll(Category.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JDialog(), "Nepodařilo se načíst Kategorie ze servru.");
        }

        setTitle(data != null ? "Editování kategorie" : "Vytváření nové kategorie");

        if (data == null)
            data = new Category();

        buildLayout();

        JLabel nameLabel = new JLabel("Název");

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(nameLabel, gbc);

        gbc.gridx++;
        if (data != null)
            name.setText(data.getName());
        contentPane.add(name, gbc);

        JLabel bgImgURILabel = new JLabel("Pozadí kategorie");
         
        gbc.gridx = 0;
        gbc.gridy++;
        contentPane.add(bgImgURILabel, gbc);

        gbc.gridx++;
        if (data != null)
            bgImgURI.setText(data.getBgImgURI());
        contentPane.add(bgImgURI, gbc);

        int gridy = gbc.gridy;
        gbc.gridx = 0;
        gbc.gridy = 2048;
        contentPane.add(confirm, gbc);

        gbc.gridx++;
        contentPane.add(exit, gbc);

        gbc.gridy = gridy;
    }

    @Override
    protected void initFunctions() {
        confirm.addActionListener(l -> {
            onConfirm();
        });
        exit.addActionListener(l -> setVisible(false));
        name.addKeyListener(getKeyListener());
        bgImgURI.addKeyListener(getKeyListener());
    }

    @Override
    protected String setData() {
        StringBuilder sb = new StringBuilder("");

        String lastName = data.getName();

        if (name.getText().trim().equals("")) {
            sb.append("Je třeba vyplnit název kategorie");
        }
        data.setName(name.getText().trim());

        data.setBgImgURI(bgImgURI.getText().trim());

        if (categories.values().contains(data)) {
            sb.append(String.format("Kategorie s názvem %s již existuje.", data.getName()));
            data.setName(lastName);
        }

        return sb.toString();
    }
}
