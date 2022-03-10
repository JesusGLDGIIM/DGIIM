/*
 * @author: Jesús García León
 * @file: Loot
 */
package deepspace;

/**
 * @brief Representa el botín que se obtiene al vencer a una nave enemiga
 */
class Loot {
    private final int nSupplies;
    private final int nWeapons;
    private final int nShields;
    private final int nHangars;
    private final int nMedals;
    private boolean getEfficient;
    private boolean spaceCity;

    Loot(int supplies, int weapons, int shields, int hangars, int medals){
        nSupplies = supplies;
        nWeapons = weapons;
        nShields = shields;
        nHangars = hangars;
        nMedals = medals;
        getEfficient = false;
        spaceCity = false;
    }
    
    Loot(int supplies, int weapons, int shields, int hangars, int medals, boolean efficient, boolean city){
        this(supplies, weapons, shields, hangars, medals);
        getEfficient = efficient;
        spaceCity = city;
    }
    
    public int getNSupplies(){
        return nSupplies;
    }
    
    public int getNWeapons(){
        return nWeapons;
    }
    
    public int getNShields(){
        return nShields;
    }
    
    public int getNHangars(){
        return nHangars;
    }
    
    public int getNMedals(){
        return nMedals;
    }
    
    public boolean getEfficient(){
        return getEfficient;
    }
    
    public boolean spaceCity(){
        return spaceCity;
    }
    
    @Override
    public String toString(){
        String aux = "Suministros: " + nSupplies;
        aux += ", Armas" + nWeapons;
        aux += ", Escudos:" + nShields;
        aux += ", Hangares" + nHangars;
        aux += ", Medallas: " + nMedals;
        return aux;
    }
    
    LootToUI getUIversion(){
        return new LootToUI(this);
    }
}
