package share.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import share.data.IData;

public interface IDB extends Remote {
    <E extends IData> Map<Integer, E> getAll(Class<E> class1) throws  RemoteException;
    <E extends IData> E getOne(Class<E> class1, int id) throws RemoteException;

    <E extends IData> E writeData(E data) throws RemoteException;
    <E extends IData> E delete(E data) throws RemoteException;

    Map<String, Class<? extends IData>> getDataTypes() throws RemoteException;
}
