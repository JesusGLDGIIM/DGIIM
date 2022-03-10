/*
 * @author: Jesús García León
 * @file: SuppliesPackage
 */
package deepspace;

/**
 * @brief Representa a un paquete de suministros para una estación espacial
 */
class SuppliesPackage{
    private final float ammoPower;
    private final float fuelUnits;
    private final float shieldPower;
    
    SuppliesPackage(float ammoPower, float fuelUnits, float shieldPower){
        this.ammoPower = ammoPower;
        this.fuelUnits = fuelUnits;
        this.shieldPower = shieldPower;
    }
    
    SuppliesPackage(SuppliesPackage suppliesPackage){
        this.ammoPower = suppliesPackage.ammoPower;
        this.fuelUnits = suppliesPackage.fuelUnits;
        this.shieldPower = suppliesPackage.shieldPower;
    }
    
    public float getAmmoPower(){
        return ammoPower;
    }
    
    public float getFuelUnits(){
        return fuelUnits;
    }
    
    public float getShieldPower(){
        return shieldPower;
    }
    
    @Override
    public String toString(){
        String aux = "Potencia: " + ammoPower;
        aux += ", Combustible: " + fuelUnits;
        aux += ", Poder de escudos: " + shieldPower;
        return aux;
    }
}
