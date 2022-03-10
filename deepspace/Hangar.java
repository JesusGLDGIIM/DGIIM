/*
 * @author: Jesús García León
 * @file: Hangar
 */
package deepspace;

import java.util.ArrayList;

/**
 * @brief Hangar que contiene los potenciadores dde escudo y las armas
 */
public class Hangar {
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<ShieldBooster> shieldBoosters = new ArrayList<>();
    private int maxElements;
    
    Hangar(int capacity){
        maxElements = capacity;
    }
    
    //Referencia?
    Hangar(Hangar h){
        maxElements = h.getMaxElements();
        weapons = h.getWeapons();
        shieldBoosters = h.getShieldBoosters();
    }
    
    HangarToUI getUIversion(){
        return new HangarToUI(this);
    }
    
    private boolean spaceAvailable(){
        return (shieldBoosters.size() + weapons.size()) < maxElements;
    }
    
    public boolean addWeapon(Weapon w){
        if (spaceAvailable()){
            weapons.add(w);
            return true;
        }
        else return false;
    }
    
    public boolean addShieldBooster(ShieldBooster s){
        if(spaceAvailable()){
            shieldBoosters.add(s);
            return true;
        }
        else return false;
    }
    
    public int getMaxElements(){
        return maxElements;
    }
    
    public ArrayList<ShieldBooster> getShieldBoosters(){
        return shieldBoosters;
    }
    
    public ArrayList<Weapon> getWeapons(){
        return weapons;
    }
    
    public ShieldBooster removeShieldBooster(int s){
        if(s < 0 || s >= shieldBoosters.size())
            return null;
        else{
            ShieldBooster aux = shieldBoosters.get(s);
            shieldBoosters.remove(s);
            return aux;
        }
    }
    
    public Weapon removeWeapon(int w){
        if(w<0 || w >= weapons.size())
            return null;
        else{
            Weapon aux = weapons.get(w);
            weapons.remove(w);
            return aux;
        }
    }
    
    @Override
    public String toString() {
        String aux = "Espacio total: " + maxElements;
        aux += "\nArmas:\n";
        for (Weapon w: weapons)
            aux += w.toString() + "\n";
        aux += "Escudos:\n";
        for (ShieldBooster s: shieldBoosters)
            aux += s.toString() + "\n";
        return aux;
    }

}
