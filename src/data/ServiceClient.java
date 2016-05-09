package data;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 09/05/16.
 */
public interface ServiceClient extends Service {
    boolean callMeBack(Remote clientAccess) throws RemoteException;
}
