package UniversalRegistry;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 05/05/16.
 */
public class URegistryImpl extends UnicastRemoteObject implements URegistry  {
    private Map<String, Object> table;

    public URegistryImpl() throws RemoteException {
        table= new HashMap<>();
    }

    public URegistryImpl(int portNb) throws RemoteException {
        super(portNb);
        table= new HashMap<>();
    }

    /**
     * Puts the object with the corresponding key. The key should not already exist.
     * @param key
     * @param object
     * @throws AlreadyBoundException
     */
    @Override
    public void bind(String key, Object object) throws AlreadyBoundException {
        if(table.containsKey(key))
            throw new AlreadyBoundException();
        table.put(key, object);
    }

    /**
     * Puts the object with the corresponding key. If already exists, removes the old one to write a new one.
     * @param key
     * @param object
     */
    @Override
    public void rebind(String key, Object object) {
        table.put(key,object);
    }

    /**
     *
     * @param key
     * @return the object corresponding to the given key
     */
    @Override
    public Object get(String key) {
        return  table.get(key);
    }

    /**
     * @return the list of keys in the table
     */
    @Override
    public Set<String> list() {
        return table.keySet();
    }
}
