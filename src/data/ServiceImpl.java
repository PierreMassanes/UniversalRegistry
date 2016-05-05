package data;

/**
 * Created by user on 05/05/16.
 */
public class ServiceImpl implements Service {
    private ReponseService reponseService;

    @Override
    public String getInfo() {
        return reponseService.getInfo();
    }

    @Override
    public ReponseService accesService() {
        return reponseService;
    }


}
