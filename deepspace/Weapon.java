/*
 * @author: Jesús García León
 * @file: Weapon
 */
package deepspace;

/**
 * @brief representa a las armas de las que puede disponer una 
 *        estación espacial para potenciar su energía al disparar
 */
class Weapon implements CombatElement{
    private final String name;
    private final WeaponType type;
    private int uses;
    private static final float DEFAULTVALUE = (float)1.0;
    
    Weapon(String name, WeaponType type, int uses){
        this.name = name;
        this.type = type;
        this.uses = uses;
    }
    
    Weapon(Weapon weapon){
        this.name = weapon.name;
        this.type = weapon.type;
        this.uses = weapon.uses;
    }
    
    public WeaponType getType(){
        return type;
    }
    
    @Override
    public int getUses(){
        return uses;
    }
    
    public float power(){
        return type.getPower();
    }
    
    @Override
    public float useIt(){
        if(uses>0){
            uses--;
            return type.getPower();
        }
        else
            return DEFAULTVALUE;
    }
    
    @Override
    public String toString(){
        String aux = "Nombre: " + name;
        aux += "\nTipo: " + type;
        aux += "\nUsos: " + uses;
        return aux;
    }
    
    //getUIVersion? En el pdf pone con V mayus pero las clases estan en v minus
    WeaponToUI getUIversion(){
        return new WeaponToUI(this);
    }
}
