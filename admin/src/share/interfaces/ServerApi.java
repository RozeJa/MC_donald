package share.interfaces;

import java.rmi.RemoteException;

public interface ServerApi extends IDB {
    void addClient(IClient o) throws RemoteException;
}
