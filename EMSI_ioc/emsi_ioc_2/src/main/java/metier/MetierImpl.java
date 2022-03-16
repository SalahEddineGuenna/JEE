package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {
    //couplage faible
    @Autowired
    @Qualifier("dao2")
    private IDao dao;

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double tmp= dao.getData();
        double result= tmp*100/Math.cos(tmp*Math.PI);
        return result;
    }

    /*
    Injecter dans la variable dao un objet
    d'une classe qui implémente l'interface IDAO
     */

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
