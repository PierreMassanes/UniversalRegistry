package data;

/**
 * Created by user on 05/05/16.
 */
public class DonneeImpl implements Donnee {
    String name;
    String surname;

    public DonneeImpl(String name, String surname){
        this.name=name;
        this.surname=surname;
    }

    @Override
    public String toString(){
        return name+" "+surname;
    }
}
