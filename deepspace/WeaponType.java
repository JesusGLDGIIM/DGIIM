/*
 * @author: Jesús García León
 * @file: WeaponType
 */
package deepspace;

/**
 * @brief Representa a los tipos de armas del juego
 */
public enum WeaponType{
    LASER((float)2.0), MISSILE((float)3.0), PLASMA((float)4.0);
    
    private final float power;
    
    WeaponType(float power){
        this.power = power;
    }
    
    float getPower(){
        return power;
    }    
}
