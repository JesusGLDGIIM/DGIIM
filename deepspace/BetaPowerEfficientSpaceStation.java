/*
 * @author: Jesús García León
 * @file: 
 */
package deepspace;

/**
 *
 * @author usuario
 */
public class BetaPowerEfficientSpaceStation extends PowerEfficientSpaceStation{
    private static final float EXTRAEFFICIENCY = 1.2f;
    private Dice dice = new Dice();
    
    BetaPowerEfficientSpaceStation(SpaceStation station){
        super(station);
    }
    
    @Override
    public float fire(){
        if(dice.extraEfficiency())
            return super.fire()*EXTRAEFFICIENCY;
        return super.fire();
    }
    
    @Override
    public BetaPowerEfficientSpaceStationToUI getUIversion(){
        return new BetaPowerEfficientSpaceStationToUI(this);
    }
    
    @Override
    public String toString(){
        return "BetaPowerEfficientSpaceStation\n" + super.toString();
    }
}
