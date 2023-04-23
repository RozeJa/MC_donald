package admin.gui;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import share.interfaces.IClient;

public class Client extends UnicastRemoteObject implements IClient {
    private static final long serialVersionUID = 42L;
    private MainFrame mf;

    public Client(MainFrame mf) throws RemoteException {
        this.mf = mf;
    }

    @Override
    public void updateUI() throws RemoteException {
        mf.fillDataContent();
    }
}
