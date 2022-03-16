package pres;

import ext.DaoImp2;
import metier.MetierImpl;

public class Presentation {
    public static void main(String[] args) {
        /*
        Injection de dependances par
        instanciation statique
         */
       // DaoImpl dao = new DaoImpl();
        DaoImp2 dao=new DaoImp2();
        MetierImpl metier = new MetierImpl(dao);
        //metier.setDao(dao);
        System.out.println("Resultat : "+metier.calcul());
    }
}
