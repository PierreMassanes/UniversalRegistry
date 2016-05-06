package data;

import java.rmi.RemoteException;

/**
 * Created by user on 05/05/16.
 */
public interface Service {
    String getInfo();
    ReponseService accesService();
    void iniConnection() throws RemoteException;
    void suscribe(String clientName) throws RemoteException;
    void closeConnection() throws RemoteException;
    void publish(String content) throws RemoteException;
}
