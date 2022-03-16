package dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImpl implements IDao{
    @Override
    public double getData() {
        /*
        Recuperation de la température a travers la BD
         */
        System.out.println("Version DB");
        double temp=Math.random()*40;
        return temp;
    }
}
