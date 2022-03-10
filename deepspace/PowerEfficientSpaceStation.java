/*
 * @author: Jesús García León
 * @file: 
 */
package deepspace;

/**
 *
 * @author usuario
 */
public class PowerEfficientSpaceStation extends SpaceStation{
    private static final float EFFICIENCYFACTOR = 1.10f;
    
    PowerEfficientSpaceStation(SpaceStation station){
        super(station);
    }
    
    @Override
    public float fire(){
        return super.fire() * EFFICIENCYFACTOR;
    }
    
    @Override
    public float protection(){
        return super.protection() * EFFICIENCYFACTOR;
    }
    
    @Override
    public Transformation setLoot(Loot loot){
        super.setLoot(loot);
        if(loot.getEfficient())
            return Transformation.GETEFFICIENT;
        else
            return Transformation.NOTRANSFORM;
    }
    
    @Override
    public PowerEfficientSpaceStationToUI getUIversion(){
        return new PowerEfficientSpaceStationToUI(this);
    }
    
    @Override
    public String toString(){
        return "PowerEfficientSpaceStation\n" + super.toString();
    }
}
