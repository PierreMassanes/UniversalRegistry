package UniversalRegistry;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.util.Set;

public interface URegistry extends Remote {

    void bind(String key, Object object) throws AlreadyBoundException;
    void rebind(String key, Object object);
    Object get(String key);
    Set<String> list();
}
