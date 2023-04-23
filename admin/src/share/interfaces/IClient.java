package share.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    void updateUI() throws RemoteException;
}
