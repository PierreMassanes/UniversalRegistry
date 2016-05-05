package UniversalRegistry;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public interface URegistry extends Remote {

    void bind(String key, Object object) throws AlreadyBoundException, RemoteException;
    void rebind(String key, Object object) throws RemoteException;
    Object get(String key) throws RemoteException;
    List<String> list() throws RemoteException;
}
