/*
 * @author: Jesús García León
 * @file: GameUniverse
 */
package deepspace;

import java.util.ArrayList;

/**
 *
 */
public class GameUniverse {

    private final static int WIN = 10;
    private int currentStationIndex;
    private int turns;
    private Dice dice;
    private GameStateController gameState;
    private EnemyStarShip currentEnemy;
    private SpaceStation currentStation;
    private ArrayList<SpaceStation> spaceStations = new ArrayList<>();
    private boolean haveSpaceCity;
    
    public GameUniverse() {
        gameState = new GameStateController();
        turns = 0;
        dice = new Dice();
        currentStationIndex = 0;
        haveSpaceCity = false;
    }
    
    private void createSpaceCity(){
        if(!haveSpaceCity){
            ArrayList<SpaceStation> stations = new ArrayList<>();
            for(int i=0; i<spaceStations.size(); i++)
                if(i != currentStationIndex)
                    stations.add(spaceStations.get(i));
           
            currentStation = new SpaceCity(currentStation, stations);
            spaceStations.remove(currentStationIndex);
            spaceStations.add(currentStationIndex, currentStation);
            haveSpaceCity = true;
        }
    }
    
    private void makeStationEfficient(){
        currentStation = new PowerEfficientSpaceStation(currentStation);
        if(dice.extraEfficiency()){
            currentStation = new BetaPowerEfficientSpaceStation(currentStation);
        }
        
        spaceStations.remove(currentStationIndex);
        spaceStations.add(currentStationIndex, currentStation);
    }
    
    CombatResult combat(SpaceStation station, EnemyStarShip enemy) {
        GameCharacter ch = dice.firstShot();
        boolean enemyWins;
        float fire;
        ShotResult result;
        CombatResult combatResult = null;
        if (ch == GameCharacter.ENEMYSTARSHIP){
            fire = enemy.fire();
            result = station.receiveShot(fire);
            if (result == ShotResult.RESIST){
                fire = station.fire();
                result = enemy.receiveShot(fire);
                enemyWins = (result == ShotResult.RESIST);
            }
            else
                enemyWins = true;
        }
        else{
            fire = station.fire();
            result = enemy.receiveShot(fire);
            enemyWins = (result==ShotResult.RESIST);
        }
        if(enemyWins){
            float s = station.getSpeed();
            boolean moves = dice.spaceStationMoves(s);
            if(!moves){
                Damage damage = enemy.getDamage();
                station.setPendingDamage(damage);
                combatResult = CombatResult.ENEMYWINS;
            }
            else{
                station.move();
                combatResult = CombatResult.STATIONESCAPES;
            }
        }
        else{
            Loot aLoot = enemy.getLoot();
            Transformation transformation = station.setLoot(aLoot);
            if(transformation == Transformation.GETEFFICIENT){
                makeStationEfficient();
                combatResult = CombatResult.STATIONWINSANDCONVERTS;
            }
            else if(transformation == Transformation.SPACECITY){
                createSpaceCity();
                combatResult = CombatResult.STATIONWINSANDCONVERTS;
            }
            else if(transformation == Transformation.NOTRANSFORM){
                combatResult = CombatResult.STATIONWINS;
            }
        }
        gameState.next(turns, spaceStations.size());
        return combatResult;
    }
    
    public CombatResult combat() {
        GameState state = gameState.getState();
        if ((state == GameState.BEFORECOMBAT)||(state==GameState.INIT))
            return combat(currentStation,currentEnemy);
        else
            return CombatResult.NOCOMBAT;
    }
    
    public void discardHangar() {
        if ((gameState.getState() == GameState.INIT) || (gameState.getState() == GameState.AFTERCOMBAT)) {
            currentStation.DiscardHangar();
        }
    }
    
    public void discardShieldBooster(int i) {
        if ((gameState.getState() == GameState.INIT) || (gameState.getState() == GameState.AFTERCOMBAT)) {
            currentStation.DiscardShieldBooster(i);
        }
    }
    
    public void discardShieldBoosterInHangar(int i) {
        if ((gameState.getState() == GameState.INIT) || (gameState.getState() == GameState.AFTERCOMBAT)) {
            currentStation.DiscardShieldBoosterInHangar(i);
        }
    }
    
    public void discardWeapon(int i) {
        if ((gameState.getState() == GameState.INIT) || (gameState.getState() == GameState.AFTERCOMBAT)) {
            currentStation.DiscardWeapon(i);
        }
    }
    
    public void discardWeaponInHangar(int i) {
        if ((gameState.getState() == GameState.INIT) || (gameState.getState() == GameState.AFTERCOMBAT)) {
            currentStation.DiscardWeaponInHangar(i);
        }
    }
    
    public GameState getState() {
        return gameState.getState();
    }
    
    public GameUniverseToUI getUIversion() {
        return new GameUniverseToUI(this.currentStation, this.currentEnemy);
    }
    
    public boolean haveAWinner() {
        return (currentStation.getNMedals() >= WIN);
    }
    
    public void init(ArrayList<String> names) {
        //turns = 0;
        GameState state = gameState.getState();
        if(state == GameState.CANNOTPLAY){
            CardDealer dealer = CardDealer.getInstance();
            for(int i = 0; i < names.size(); i++){
                SuppliesPackage supplies = dealer.nextSuppliesPackage();
                SpaceStation station = new SpaceStation(names.get(i),supplies);
                spaceStations.add(station);
                
                int nh = dice.initWithNHangars();
                int nw = dice.initWithNWeapons();
                int ns = dice.initWithNShiels();
                Loot lo = new Loot(0,nw,ns,nh,0);
                
                station.setLoot(lo);
                
            }
        currentStationIndex = dice.whoStarts(names.size());
        currentStation = spaceStations.get(currentStationIndex);
        currentEnemy = dealer.nextEnemy();
        gameState.next(turns, spaceStations.size());
        }
    }
    
    public void mountShieldBooster(int i) {
        if ((gameState.getState() == GameState.INIT) || (gameState.getState() == GameState.AFTERCOMBAT)) {
            currentStation.mountShieldBooster(i);
        }
    }
    
    public void mountWeapon(int i) {
            if((gameState.getState() == GameState.INIT) || (gameState.getState() == GameState.AFTERCOMBAT))
            currentStation.mountWeapon(i);
    }
    
    public boolean nextTurn() {
        GameState state = gameState.getState();
        if(state == GameState.AFTERCOMBAT){
            boolean stationState = currentStation.validState();
            if(stationState){
                currentStationIndex=(currentStationIndex+1)%spaceStations.size();
                turns++;
                currentStation = spaceStations.get(currentStationIndex);
                currentStation.cleanUpMountedItems();
                CardDealer dealer = CardDealer.getInstance();
                currentEnemy = dealer.nextEnemy();
                gameState.next(turns, spaceStations.size());
                return true;
            }   
        }
        return false;
    }
    
    @Override
    public String toString(){
        String aux = "CurrentStationIndex: " + currentStationIndex + "\n";
        aux += "Turns" + turns + "\n";
        aux += "Dice: " + dice.toString() + "\n";
        aux += "GameState: " + gameState.getState() + "\n";
        aux += "CurrentEnemy: " + currentEnemy.toString() + "\n";
        aux += "CurrentStation: " + currentStation.toString() + "\n";
        aux += "SpaceStations: " + spaceStations.toString() + "\n";
        return aux;
    }
}
