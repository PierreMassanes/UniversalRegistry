package UniversalRegistry;

import sun.text.normalizer.Utility;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by user on 05/05/16.
 */
public class URegistryImpl extends UnicastRemoteObject implements URegistry  {

    private Map<String, Object> table; //Objects and their keys
    private  Map<String, Integer> lastKeyUsed; //The most used keys and the number of times they have been used
    private Map<String, Integer> popularKeys; //The most requested keys and the number of times they have been requested

    public URegistryImpl() throws RemoteException {
        table= new HashMap<>();
        lastKeyUsed= new LinkedHashMap<>();
        popularKeys= new LinkedHashMap<>();
    }

    public URegistryImpl(int portNb) throws RemoteException {
        super(portNb);
        table= new HashMap<>();
        lastKeyUsed= new LinkedHashMap<>();
        popularKeys= new LinkedHashMap<>();
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
        lastKeyUsed.put(key, 1);
    }

    /**
     * Puts the object with the corresponding key. If already exists, removes the old one to write a new one.
     * @param key
     * @param object
     */
    @Override
    public void rebind(String key, Object object) {
        if (lastKeyUsed.containsKey(key)) {
            int coeff = lastKeyUsed.get(key);
            lastKeyUsed.remove(key);
            lastKeyUsed.put(key,++coeff);
            table.remove(key);
        }
        else lastKeyUsed.put(key, 1);
        table.put(key,object);
    }

    /**
     *
     * @param key
     * @return the object corresponding to the given key
     */
    @Override
    public Object get(String key) {
        if (popularKeys.containsKey(key)) {
            int oldValue = popularKeys.get(key);
            popularKeys.remove(key);
            popularKeys.put(key, oldValue+1);
        }else popularKeys.put(key, 1);
        return table.get(key);
    }

    /**
     * @return the list of keys in the table
     */
    @Override
    public List<String> list() {
        return new ArrayList<>(table.keySet());
    }

    public List<Object> getLastObjects(int until){
        List<Object> res= new ArrayList<>();
        List<String> keys= getLastKeys(until);
        for (String s: keys) {
            res.add(table.get(s));
        }
        return res;
    }

    /***
     *
     * @param until
     * @return Return the last N keys used to save objects, where N=until
     */
    @Override
    public List<String> getLastKeys(int until){
        List<String> res= new ArrayList<>();
        int i=0;
        Set<String> keys= lastKeyUsed.keySet();
        final Iterator itr = keys.iterator();
        Object elem = itr.next();
        while(itr.hasNext()) {
            if (i++>=keys.size()-until)
                res.add(elem.toString());
            elem=itr.next();
        }
        res.add(elem.toString());
        return res;
    }

    /***
     *
     * @param until
     * @return Return the most used keys in the last N keys used, where N=until
     */
    @Override
    public List<String> getMostUsedKey(int until){
        List<String> lastkeys= getLastKeys(until);
        List<String> res= new ArrayList<>();
        Collections.sort(lastkeys, new UtilityComparator());
        int i=0;
        for (String s : lastkeys){
            if (i++< 3)
                res.add(s);
        }
        return res;
    }

    /***
     *
     * @param until
     * @return Return the most requested keys in the last N keys used, where N=until
     */
    @Override
    public List<String> getPopularKey(int until) throws RemoteException {
        List<String> res= new ArrayList<>();
        Set<String> keys= popularKeys.keySet();
        int i=0;
        for (String s: keys){
            if (i++>=keys.size()-until)
                res.add(s);
        }
        Collections.sort(res, new PopularityComparator());
        return res;
    }

    /**
     *
     * @return the 3 most used keys
     * @throws RemoteException
     */
    @Override
    public List<String> getPopularKey() throws RemoteException {
        List<String> res= new ArrayList<>();
        List<String> keys= new ArrayList<>();
        keys.addAll(popularKeys.keySet());
        Collections.sort(keys, new PopularityComparator());
        int i=0;
        for (String s : keys){
            if (i++< 3)
                res.add(s);
        }
        return res;
    }

    class UtilityComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return lastKeyUsed.get(a) > lastKeyUsed.get(b) ? -1 : lastKeyUsed.get(a)==lastKeyUsed.get(b) ? 0 : 1;
        }
    }
    class PopularityComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return popularKeys.get(a) > popularKeys.get(b) ? -1 : popularKeys.get(a)==popularKeys.get(b) ? 0 : 1;
        }
    }
}
