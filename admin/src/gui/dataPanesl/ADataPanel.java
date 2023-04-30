package gui.dataPanesl;

import javax.swing.JPanel;

import data.models.IData;

import java.awt.event.*;

public abstract class ADataPanel<E extends IData> extends JPanel {
    protected E data;
    protected ActionListener update, delet;

    protected ADataPanel() {
        setSize(400, 200);
    }

    public void setData(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setUpdateEvent(ActionListener l) {
        update = l;
    }

    public void setDeleteEvent(ActionListener l) {
        delet = l;
    }

    public abstract void init();
}
