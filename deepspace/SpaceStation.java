/*
 * @author: Jesús García León
 * @file: SpaceStation
 */
package deepspace;

import java.util.ArrayList;

/**
 * @brief Estación del jugador
 */
public class SpaceStation implements SpaceFighter{

    private static final int MAXFUEL = 100;
    private static final float SHIELDLOSSPERUNITSHOT = 0.1f;

    private float ammoPower;
    private float fuelUnits;
    private String name;
    private int nMedals;
    private Damage pendingDamage;
    private float shieldPower;
    private Hangar hangar;
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<ShieldBooster> shieldBoosters = new ArrayList<>();

    private void assignFuelValue(float f) {
        if (f > MAXFUEL) {
            fuelUnits = MAXFUEL;
        } else {
            fuelUnits = f;
        }
    }

    private void cleanPendingDamage() {
        if (pendingDamage.hasNoEffect()) {
            pendingDamage = null;
        }
    }

    SpaceStation(String n, SuppliesPackage supplies) {
        name = n;
        ammoPower = supplies.getAmmoPower();
        fuelUnits = supplies.getFuelUnits();
        shieldPower = supplies.getShieldPower();
        nMedals = 0;
        pendingDamage = null;
        hangar = null;
    }
    
    SpaceStation(SpaceStation station){
        ammoPower = station.ammoPower;
        fuelUnits = station.fuelUnits;
        name = station.name;
        nMedals = station.nMedals;
        shieldPower = station.shieldPower;
        pendingDamage = station.pendingDamage;
        
        for(Weapon w : station.weapons){
            weapons.add(w);
        }
        for(ShieldBooster s : station.shieldBoosters){
            shieldBoosters.add(s);
        }
        hangar = station.hangar;
    }

    public void cleanUpMountedItems() {
        int i = 0;

        while (i < weapons.size()) {
            if (weapons.get(i).getUses() == 0) {
                weapons.remove(weapons.get(i));
            } else {
                i++;
            }
        }

        i = 0;

        while (i < shieldBoosters.size()) {
            if (shieldBoosters.get(i).getUses() == 0) {
                shieldBoosters.remove(shieldBoosters.get(i));
            } else {
                i++;
            }
        }
    }

    public void DiscardHangar() {
        if (!(hangar == null)) {
            hangar = null;
        }
    }

    public void DiscardShieldBooster(int i) {
        if((0 <= i) && (i < shieldBoosters.size())){
            shieldBoosters.remove(i);
            if(pendingDamage!=null){
                pendingDamage.discardShieldBooster();
                cleanPendingDamage();
            }
        } 
    }

    public void DiscardShieldBoosterInHangar(int i) {
        if (hangar != null) {
            hangar.removeShieldBooster(i);
        }
    }

    public void DiscardWeapon(int i) {
        if((0 <= i) && (i < weapons.size())){
            Weapon weapon = weapons.remove(i);
            if(pendingDamage!=null){
                pendingDamage.discardWeapon(weapon);
                cleanPendingDamage();
            }
        }      
    }

    public void DiscardWeaponInHangar(int i) {
        if (hangar != null) {
            hangar.removeWeapon(i);
        }
    }

    @Override
    public float fire() {
        float factor = 1.0f;
        int size = weapons.size();
        for (int i = 0; i < size; i++){
            Weapon w = weapons.get(i);
            factor *= w.useIt();
        }
        return factor*ammoPower;
    }

    public float getAmmoPower() {
        return ammoPower;
    }

    public float getFuelUnits() {
        return fuelUnits;
    }

    public Hangar getHangar() {
        return hangar;
    }

    public String getName() {
        return name;
    }

    public int getNMedals() {
        return nMedals;
    }

    public Damage getPendingDamage() {
        return pendingDamage;
    }

    public float getShieldPower() {
        return shieldPower;
    }

    public ArrayList<ShieldBooster> getShieldBoosters() {
        return shieldBoosters;
    }

    public float getSpeed() {
        return fuelUnits / MAXFUEL;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    SpaceStationToUI getUIversion() {
        return new SpaceStationToUI(this);
    }

    public void mountShieldBooster(int i) {
        ShieldBooster s;
        if (hangar != null) {
            if ((s = hangar.removeShieldBooster(i)) != null) {
                shieldBoosters.add(s);
            }
        }
    }

    public void mountWeapon(int i) {
        Weapon w;
        if (hangar != null) {
            if ((w = hangar.removeWeapon(i)) != null) {
                weapons.add(w);
            }
        }
    }

    public void move() {
        fuelUnits -= getSpeed()*fuelUnits;
        if (fuelUnits < 0) {
            fuelUnits = 0;
        }
    }

    @Override
    public float protection() {
        float factor = 1.0f;
        int size = shieldBoosters.size();
        for(int i = 0; i < size; i++){
            ShieldBooster s = shieldBoosters.get(i);
            factor *= s.useIt();
        }
        return factor*shieldPower;
    }

    public void receiveHangar(Hangar h) {
        if (hangar == null) {
            hangar = h;
        }
    }

    public boolean receiveShieldBooster(ShieldBooster s) {
        if (hangar == null) {
            return false;
        }

        return hangar.addShieldBooster(s);
    }

    @Override
    public ShotResult receiveShot(float shot) {
        float myProtection = protection();
        if(myProtection >= shot){
            shieldPower -= SHIELDLOSSPERUNITSHOT*shot;
            shieldPower = Math.max(0.0f,shieldPower);
            return ShotResult.RESIST;
        }
        else{
            shieldPower = 0.0f;
            return ShotResult.DONOTRESIST;
        }
    }

    public void receiveSupplies(SuppliesPackage s) {
        ammoPower += s.getAmmoPower();
        shieldPower += s.getShieldPower();
        assignFuelValue(fuelUnits + s.getFuelUnits());
    }

    public boolean receiveWeapon(Weapon w) {
        if (hangar == null) {
            return false;
        }

        return hangar.addWeapon(w);
    }

    public Transformation setLoot(Loot loot) {
        CardDealer dealer = CardDealer.getInstance();
        int h = loot.getNHangars();
        if(h > 0){
            Hangar hangarh = dealer.nextHangar();
            this.receiveHangar(hangarh);
        }
        
        int elements = loot.getNSupplies();
        for(int i = 0; i < elements; i++){
            SuppliesPackage sup = dealer.nextSuppliesPackage();
            this.receiveSupplies(sup);
        }
        
        elements = loot.getNWeapons();
        for(int i = 0; i < elements; i++){
            Weapon weap = dealer.nextWeapon();
            this.receiveWeapon(weap);
        }
        
        elements = loot.getNShields();
        for(int i = 0; i < elements; i++){
            ShieldBooster sh = dealer.nextShieldBooster();
            this.receiveShieldBooster(sh);
        }
        int medals = loot.getNMedals();
        nMedals += medals;
        
        if(loot.getEfficient()){
            return Transformation.GETEFFICIENT;
        }
        if(loot.spaceCity()){
            return Transformation.SPACECITY;
        }
        
        return Transformation.NOTRANSFORM;
    }

    public void setPendingDamage(Damage d) {
        pendingDamage = d.adjust(weapons, shieldBoosters);
        cleanPendingDamage();
    }

    public boolean validState() {
        if (pendingDamage == null) {
            return true;
        }
        return pendingDamage.hasNoEffect();
    }

    @Override
    public String toString() {
        String test;

        //CONTENT
        String namE = "+ NAME: " + name + "\n";
        String amP = "+ AMMOPOWER: " + ammoPower + " ";
        String fU = "+ FUELUNITS: " + fuelUnits + " ";
        String sPow = "+ SHIELDPOWER: " + shieldPower + " ";
        String nMed = "+ NMEDALS: " + nMedals + "\n";

        String weap = "\n+ WEAPONS MOUNTED: \n";
        if (weapons == null || weapons.isEmpty()) {
            weap += " Ninguna\n";
        } else {
            for (Weapon w : weapons) {
                weap += w.toString();
            }
        }

        String shB = "\n+ SHIELDBOOSTERS MOUNTED: \n";
        if (shieldBoosters == null || shieldBoosters.isEmpty()) {
            shB += " Ninguno\n";
        } else {
            for (ShieldBooster sB : shieldBoosters) {
                shB += sB.toString();
            }
        }

        String han = "\n* HANGAR: \n";
        if (hangar == null) {
            han += " Ninguno\n";
        } else {
            han += hangar.toString();
        }

        String pDam = "\n* PENDINGDAMAGE: \n";
        if (pendingDamage == null) {
            pDam += " Ninguno\n";
        } else {
            pDam += pendingDamage.toString() + "\n";
        }

        test = namE + amP + fU + sPow + nMed + weap + shB + han + pDam;

        return test;
    }
}