package UniversalRegistry;


import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import javax.jms.*;

public interface URegistry extends Remote {

    void bind(String key, Object object) throws AlreadyBoundException, RemoteException;
    void rebind(String key, Object object) throws RemoteException;
    Object get(String key) throws RemoteException;
    List<String> list() throws RemoteException;
    List<Object> getLastObjects(int until) throws RemoteException;
    List<String> getLastKeys(int until) throws RemoteException;
    List<String> getMostUsedKey(int until) throws RemoteException;
    List<String> getPopularKey(int until) throws RemoteException;
    List<String> getPopularKey() throws RemoteException;
}
