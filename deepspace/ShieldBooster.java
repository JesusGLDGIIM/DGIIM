/*
 * @author: Jesús García León
 * @file: ShieldBooster
 */
package deepspace;

/**
 * @brief Representa a los potenciadores de escudo 
 *        que pueden tener las estaciones espaciales.
 */
class ShieldBooster implements CombatElement{
    private final String name;
    private final float boost;
    private int uses;
    private static final float DEFAULTVALUE = (float)1.0;
    
    ShieldBooster(String name, float boost, int uses){
        this.name = name;
        this.boost = boost;
        this.uses = uses;
    }
    
    ShieldBooster(ShieldBooster shieldBooster){
        this.name = shieldBooster.name;
        this.boost = shieldBooster.boost;
        this.uses = shieldBooster.uses;
    }
    
    /*
    public String getName(){
        return name;
    }
    */
    
    public float getBoost(){
        return boost;
    }
    
    @Override
    public int getUses(){
        return uses;
    }
    
    @Override
    public float useIt(){
        if(uses>0){
            uses--;
            return boost;
        }
        else
            return DEFAULTVALUE;
    }
    
    @Override
    public String toString(){
        String aux = "Nombre: " + name;
        aux += "Potenciador: " + boost;
        aux += "Usos: " + uses;
        return aux;
    }
    
    ShieldToUI getUIversion(){
        return new ShieldToUI(this);
    }
}
