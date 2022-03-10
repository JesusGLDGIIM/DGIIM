/*
 * @author: Jesús García León
 * @file: EnemyStarShip
 */
package deepspace;

/**
 * @brief Naves enemigas
 */
public class EnemyStarShip implements SpaceFighter{
    private float ammoPower;
    private String name;
    private float shieldPower;
    private Damage damage;
    private Loot loot;
    
    EnemyStarShip(String n, float a, float s, Loot l, Damage d){
        ammoPower = a;
        name = n;
        shieldPower = s;
        loot = l;
        damage = d;
    }
    
    EnemyStarShip(EnemyStarShip e){
        ammoPower = e.getAmmoPower();
        name = e.getName();
        shieldPower = e.shieldPower;
        damage = e.getDamage();
        loot = e.getLoot();
    }
    
    public float getAmmoPower(){
        return ammoPower;
    }
    
    public float getShieldPower(){
        return shieldPower;
    }
    
    public String getName(){
        return name;
    }
    
    public Loot getLoot(){
        return loot;
    }
    
    public Damage getDamage(){
        return damage;
    }
    
    EnemyToUI getUIversion(){
        return new EnemyToUI(this);
    }
    
    @Override
    public float fire(){
        return ammoPower;
    }
    
    @Override
    public float protection(){
        return shieldPower;
    }
    
    @Override
    public ShotResult receiveShot(float shot){
        if(shot > shieldPower)
            return ShotResult.DONOTRESIST;
        else
            return ShotResult.RESIST;
    }
    
    @Override
    public String toString() {
        String aux = "Nombre del enemigo: "+name;
        aux += "\nPoder de disparo: "+ammoPower;
        aux += "\nPoder de escudo: "+shieldPower;
        aux += "\nDaño de la nave:\n"+damage.toString();
        aux += "\nBotín de la nave:\n"+loot.toString();
        return aux;
    }
}
