package cz.rozek.jan.admin_mc_donald.gui.dataPanesl;

import javax.swing.*;

import cz.rozek.jan.admin_mc_donald.data.models.Improvement;
import cz.rozek.jan.admin_mc_donald.data.models.Order;
import cz.rozek.jan.admin_mc_donald.data.models.ProductInOrder;

import java.awt.GridBagLayout;
import java.awt.*;

public class OrderPanel extends ADataPanel<Order> {
    @Override
    public void init() {
        GridBagConstraints gbc;

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();

        JLabel header = new JLabel("Obědnávka " + data.getId());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(header, gbc);

        JLabel products = new JLabel("Produkty " + data.getId());
        gbc.gridy++;
        add(products, gbc);

        for (ProductInOrder product : data.getProducts()) {
            gbc.gridy++;
            JLabel p = new JLabel(product.getProduct().getName());
            add(p, gbc);
            gbc.gridx++;
            for (Improvement improvement : product.getImprovements()) {
                gbc.gridy++;
                JLabel i = new JLabel(improvement.getName());
                add(i, gbc);
            }
            gbc.gridx = 0;
        }

        gbc.gridy++;
        JLabel price = new JLabel("Cena celkem: " + getPrice());
        add(price, gbc);
    }

    private double getPrice() {
        double price = 0;

        for (ProductInOrder p : data.getProducts()) {
            price += p.countPrice();
        }

        return price;
    }
}
