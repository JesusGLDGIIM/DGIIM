/*
 * @author: Jesús García León
 * @file: TestP1
 */
package deepspace;
        
/**
 * @brief Programa de prueba de la práctica 1
 */
public class TestP1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //WeaponType
        System.out.println("WeaponType:");
        WeaponType laser = WeaponType.LASER;
        WeaponType missile = WeaponType.MISSILE;
        WeaponType plasma = WeaponType.PLASMA;
        System.out.println(laser);
        System.out.println(laser.getPower());
        System.out.println(missile);
        System.out.println(missile.getPower());
        System.out.println(plasma);
        System.out.println(plasma.getPower());
        System.out.println("\n");
        
        //Loot
        System.out.println("Loot:");
        Loot loot = new Loot(1,2,3,4,5);
        System.out.println("HANGARES:");
        System.out.println(loot.getNHangars());
        System.out.println("MEDALLAS:");
        System.out.println(loot.getNMedals());
        System.out.println("ESCUDOS:");
        System.out.println(loot.getNShields());
        System.out.println("SUMINISTROS:");
        System.out.println(loot.getNSupplies());
        System.out.println("ARMAS:");
        System.out.println(loot.getNWeapons());
        System.out.println("\n");
        
        //SuppliesPackage
        System.out.println("SuppliesPackage:");
        SuppliesPackage suministros_1 = new SuppliesPackage(1.0f, 2.0f, 3.0f);
        SuppliesPackage suministros_2 = new SuppliesPackage(suministros_1);
        System.out.println("Poder de las armas:");
        System.out.println(suministros_1.getAmmoPower());
        System.out.println("Combustible:");
        System.out.println(suministros_1.getFuelUnits());
        System.out.println("Poder de los escudos:");
        System.out.println(suministros_1.getShieldPower());
        System.out.println("Poder de las armas:");
        System.out.println(suministros_2.getAmmoPower());
        System.out.println("Combustible:");
        System.out.println(suministros_2.getFuelUnits());
        System.out.println("Poder de los escudos:");
        System.out.println(suministros_2.getShieldPower());
        System.out.println("\n");
        
        //ShieldBooster
        System.out.println("ShieldBooster:");
        ShieldBooster escudo_1 = new ShieldBooster("escudo_1", 2.0f, 5);
        ShieldBooster escudo_2 = new ShieldBooster(escudo_1);
        System.out.println("Escudo_1:");
        System.out.println("Aumento:");
        System.out.println(escudo_1.getBoost());
        System.out.println("Usos");
        System.out.println(escudo_1.getUses());
        escudo_1.useIt();
        System.out.println("Usos");
        System.out.println(escudo_1.getUses());
        System.out.println("Escudo_2:");
        System.out.println("Aumento(2):");
        System.out.println(escudo_1.getBoost());
        System.out.println("Usos(2)");
        System.out.println(escudo_2.getUses());
        System.out.println("\n");
        
        //Weapon
        System.out.println("Weapon:");
        Weapon arma1 = new Weapon("arma1",WeaponType.LASER,1);
        Weapon arma2 = new Weapon(arma1);
        Weapon arma3 = new Weapon("arma3",WeaponType.PLASMA,1);
        System.out.println("Arma_1");
        System.out.println("Tipo:");
        System.out.println(arma1.getType());
        System.out.println("Usos:");
        System.out.println(arma1.getUses());
        System.out.println("Poder:");
        System.out.println(arma1.power());
        System.out.println(arma1.useIt());
        System.out.println(arma1.useIt());
        System.out.println("Arma_2");
        System.out.println("Tipo:");
        System.out.println(arma2.getType());
        System.out.println("Usos:");
        System.out.println(arma2.getUses());
        System.out.println("Poder:");
        System.out.println(arma2.power());
        System.out.println(arma2.useIt());
        System.out.println(arma2.useIt());
        System.out.println("Arma_3");
        System.out.println("Tipo:");
        System.out.println(arma3.getType());
        System.out.println("Usos:");
        System.out.println(arma3.getUses());
        System.out.println("Poder:");
        System.out.println(arma3.power());
        System.out.println(arma3.useIt());
        System.out.println(arma3.useIt());
        System.out.println("\n");
        
        //Dice
        System.out.println("Dice:");
        Dice dado = new Dice();
        int hangar = 0;
        int nohangar = 0;
        int armas1 = 0;
        int armas2 = 0;
        int armas3 = 0;
        int escudos = 0;
        int noescudos = 0;
        boolean result;
        int armas;
        GameCharacter personaje;
        int jugador = 0;
        int enemigo = 0;
        float speed = 0.5f;
        int mueve = 0;
        
        for(int i = 0; i < 100; i++){
            result = (dado.initWithNHangars() == 1);
            if(result)
                hangar++;
            if(!result)
                nohangar++;
            result = (dado.initWithNShiels() == 1);
            if(result)
                escudos++;
            if(!result)
                noescudos++;
            armas = dado.initWithNWeapons();
            if(armas == 1)
                armas1++;
            if(armas == 2)
                armas2++;
            if(armas == 3)
                armas3++;
            personaje = dado.firstShot();
            if(personaje == GameCharacter.SPACESTATION)
                jugador++;
            if(personaje == GameCharacter.ENEMYSTARSHIP)
                enemigo++;     
            if(dado.spaceStationMoves(speed))
                mueve++;
        }
        
        System.out.println("Hangar");
        System.out.println(hangar);
        System.out.println(nohangar);
        System.out.println("Armas");
        System.out.println(armas1);
        System.out.println(armas2);
        System.out.println(armas3);
        System.out.println("Escudos");
        System.out.println(escudos);
        System.out.println(noescudos);
        System.out.println("Empieza:");
        System.out.println(jugador);
        System.out.println(enemigo);
        System.out.println("Se mueve:");
        System.out.println(mueve);
    }
}
