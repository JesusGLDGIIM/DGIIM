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
public class SpaceCity extends SpaceStation{
    SpaceStation base;
    ArrayList<SpaceStation> collaborators;
    
    
    SpaceCity(SpaceStation base, ArrayList<SpaceStation> rest){
        super(base);
        this.base = base;
        collaborators = new ArrayList<>(rest);
    }
    
    ArrayList<SpaceStation> getCollaborators(){
        return collaborators;
    }
    
    @Override
    public float fire(){
        float fire = super.fire();                                               //super.fire()?
        for(int i = 0; i < collaborators.size(); i++)
            fire += collaborators.get(i).fire();
        return fire;
    }
    
    @Override
    public float protection(){
        float protection = super.protection();                                   //super.protection()?
        for(int i = 0; i < collaborators.size(); i++)
            protection += collaborators.get(i).protection();
        return protection;
    }
    
    @Override
    public Transformation setLoot(Loot loot){
        super.setLoot(loot);                                                     //super.setLoot(loot)?
        return Transformation.NOTRANSFORM;
    }
    
    @Override
    public SpaceCityToUI getUIversion(){
        return new SpaceCityToUI(this);
    }
    
    @Override
    public String toString(){
        String output = "Ciudad Espacial:\n";
        output += "Ciudad Base:\n";
        output += super.toString();
        output += "\nCollaborators:\n";
        for(int i = 0; i < collaborators.size(); i++){
            output += collaborators.get(i).toString() +"\n";
        }
        return output;
    }
}
