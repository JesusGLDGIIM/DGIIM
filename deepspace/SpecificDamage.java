/*
 * @author: Jesús García León
 * @file: 
 */
package deepspace;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author usuario
 */
public class SpecificDamage extends Damage{
    ArrayList<WeaponType> weapons;
    
    SpecificDamage(ArrayList<WeaponType> wl, int s){
        super(s);
        weapons = new ArrayList<>(wl);
    }
    
    @Override
    public SpecificDamageToUI getUIversion() {
        return new SpecificDamageToUI(this);
    }

    @Override
    public Damage adjust(ArrayList<Weapon> w, ArrayList<ShieldBooster> s) {
        int minNShield = Math.min(nShields, s.size());
        
            ArrayList<WeaponType> typesCopy = new ArrayList<>();
            ArrayList<Weapon> weaponsCopy = new ArrayList<>();
            ArrayList<WeaponType> adjustTypes = new ArrayList<>();
            
            for(int i=0; i< weapons.size(); i++){
                typesCopy.add(weapons.get(i));
            }
            
            for(int i=0; i<w.size(); i++){
                weaponsCopy.add(w.get(i));
            }
            
            Iterator<WeaponType> it = typesCopy.iterator();
            int index;
            
            while(it.hasNext()){
               WeaponType type = it.next();
               
               index = arrayContainsType(weaponsCopy,type);
               if(index != -1){
                   weaponsCopy.remove(index);
                   adjustTypes.add(type);
               }
               it.remove();
            }
            
            return new SpecificDamage(adjustTypes,minNShield);
    }

    @Override
    public void discardWeapon(Weapon w) {
        weapons.remove(weapons.indexOf(w.getType()));
    }

    @Override
    public boolean hasNoEffect() {
       return ((nShields == 0) && (weapons.isEmpty()));
    }

    public ArrayList<WeaponType> getWeapons() {
        return weapons;
    }

    Damage copy(SpecificDamage d) {
        super.copy(d);
        for(WeaponType w : d.weapons){
            weapons.add(w);
        }
        return this;
    }
    
    @Override
    public String toString(){
        String aux;
        aux = super.toString();
        aux += "\nWeapons:\n";
        if(weapons == null || weapons.isEmpty()){
            aux += "Ninguno\n";
        }
        else{
            aux += weapons.toString() + "\n";
        }
        return aux;
    }
}
