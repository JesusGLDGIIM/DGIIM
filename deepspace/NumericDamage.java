/*
 * @author: Jesús García León
 * @file: 
 */
package deepspace;

import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class NumericDamage extends Damage{
    private int nWeapons;
    
    NumericDamage(int w, int s){
        super(s);
        nWeapons = w;
    }

    @Override
    public NumericDamageToUI getUIversion() {
        return new NumericDamageToUI(this);
    }

    @Override
    public Damage adjust(ArrayList<Weapon> w, ArrayList<ShieldBooster> s) {
        int minNShield = Math.min(nShields, s.size());
        int minNWeapons = Math.min(nWeapons, w.size());
        
        return new NumericDamage(minNWeapons, minNShield);
    }

    @Override
    public void discardWeapon(Weapon w) {
        if (nWeapons > 0)
            nWeapons--;
    }

    @Override
    public boolean hasNoEffect() {
        return ((nWeapons == 0) && (nShields == 0));
    }

    public int getNWeapons() {
        return nWeapons;
    }

    Damage copy(NumericDamage d) {
        super.copy(d);
        nWeapons= d.nWeapons;
        return this;
    }
    
    @Override
    public String toString(){
        String aux = super.toString();
        aux += "\nnWeapons: " + nWeapons + "\n";
        return aux;
    }
    
}
