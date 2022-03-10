/*
 * @author: Jesús García León
 * @file: Dice
 */
package deepspace;

import java.util.Random;

/**
 * @brief Tiene la responsabilidad de tomar todas las 
 *        decisiones que dependen del azar en el juego
 */
public class Dice {
    private final float NHANGARSPROB;
    private final float NSHIELDSPROB;
    private final float NWEAPONSPROB;
    private final float FIRSTSHOTPROB;
    private final float EXTRAEFFICIENCYPROB;
    Random generator;
    
    Dice(){
        NHANGARSPROB = 0.25f;
        NSHIELDSPROB = 0.25f;
        NWEAPONSPROB = 0.33f;
        FIRSTSHOTPROB = 0.5f;
        EXTRAEFFICIENCYPROB = 0.8f;
        generator = new Random();
    }
    
    public int initWithNHangars(){
        float r = generator.nextFloat();
        if(r < NHANGARSPROB)
            return 0;
        else
            return 1;
    }
    
    public int initWithNWeapons(){
        float r = generator.nextFloat();
        if(r < NWEAPONSPROB)
            return 1;
        else if(r >= NWEAPONSPROB && r < 2*NWEAPONSPROB)
            return 2;
        else 
            return 3;
    }
    
    public int initWithNShiels(){
        float r = generator.nextFloat();
        if(r < NSHIELDSPROB)
            return 0;
        else
            return 1;
    }
    
    public int whoStarts(int nPlayers){
        return generator.nextInt(nPlayers);
    }

    public GameCharacter firstShot(){
        float r = generator.nextFloat();
        if(r <= FIRSTSHOTPROB)
            return GameCharacter.SPACESTATION;
        else
            return GameCharacter.ENEMYSTARSHIP;
    }
    
    public boolean spaceStationMoves(float speed){
        float r = generator.nextFloat();
        return r <= speed;
    }
    
    public boolean extraEfficiency(){
        float r = generator.nextFloat();
        return  r < EXTRAEFFICIENCYPROB;
    }
}
