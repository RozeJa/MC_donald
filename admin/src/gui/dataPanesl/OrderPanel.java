package gui.dataPanesl;

import data.models.Improvement;
import data.models.Order;
import data.models.ProductInOrder;

import javax.swing.*;

import data.models.Order;

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
