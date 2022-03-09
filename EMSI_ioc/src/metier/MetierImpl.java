package metier;

import dao.IDao;

public class MetierImpl implements IMetier {
    //couplage faible
    private IDao dao;
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
