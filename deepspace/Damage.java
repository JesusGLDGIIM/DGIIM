/*
 * @author: Jesús García León
 * @file: Damage
 */
package deepspace;

import java.util.ArrayList;

/**
 * @brief Representa el daño producido a una estación espacial por una nave
 * enemiga cuando se pierde un combate
 */
public abstract class Damage {
    public int nShields;

    Damage(int s) {
        nShields = s;
    }

    Damage copy(Damage d){
        nShields = d.getNShields();
        return this;
    }

    abstract public DamageToUI getUIversion();
    
    int arrayContainsType(ArrayList<Weapon> w, WeaponType t) {
        for (int i = 0; i < w.size(); i++) {
            if (w.get(i).getType() == t) {
                return i;
            }
        }
        return -1;
    }
    
    abstract public Damage adjust(ArrayList<Weapon> w, ArrayList<ShieldBooster> s);
    
    abstract public void discardWeapon(Weapon w);
    
    public void discardShieldBooster(){
        if(nShields > 0)
            nShields--;
    }
    
    abstract public boolean hasNoEffect();
    
    int getNShields(){
        return nShields;
    }
    
    @Override
    public String toString(){
        String aux = "Escudos a perder: " + nShields;
        return aux;
    }
}
