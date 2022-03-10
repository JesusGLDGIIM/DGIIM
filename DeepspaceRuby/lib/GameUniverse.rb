#encoding: UTF-8

require_relative 'GameStateController'
require_relative 'GameState'
require_relative 'Dice'
require_relative 'GameUniverseToUI'
require_relative 'CardDealer'
require_relative 'ShotResult'
require_relative 'SpaceStation'
require_relative 'CombatResult'
require_relative 'PowerEfficientSpaceStation'
require_relative 'BetaPowerEfficientSpaceStation'
require_relative 'SpaceCity'

module Deepspace
    class GameUniverse
        @@WIN = 10

        @currentStationIndex
        @turns
        @dice
        @gameState
        @currentEnemy
        @currentStation
        @spaceStations
        @haveSpaceCity

        def initialize
            @gameState = GameStateController.new
            @turns = 0
            @dice = Dice.new
            @spaceStations = Array.new
            @haveSpaceCity = false
        end

        private
        def combatGo(station,enemy)
            combatResult = nil
            ch = @dice.firstShot
            if ch == GameCharacter::ENEMYSTARSHIP
                fire = enemy.fire
                result = station.receiveShot(fire)
                if result == ShotResult::RESIST
                    fire = station.fire
                    result = enemy.receiveShot(fire)
                    enemyWins = (result == ShotResult::RESIST)
                else
                    enemyWins = true
                end
            else
                fire = station.fire
                result = enemy.receiveShot(fire)
                enemyWins = (result == ShotResult::RESIST)
            end
            if enemyWins
                s = station.getSpeed
                moves = @dice.spaceStationMoves(s)
                if moves
                    station.move
                    combatResult = CombatResult::STATIONESCAPES
                else
                    damage = enemy.damage
                    station.setPendingDamage(damage)
                    combatResult = CombatResult::ENEMYWINS
                end
            else
                aLoot = enemy.loot
                transformation = station.setLoot(aLoot)
                if transformation == Transformation::GETEFFICIENT
                    makeStationEfficient
                    combatResult = CombatResult::STATIONWINSANDCONVERTS
                elsif transformation == Transformation::SPACECITY
                    createSpaceCity
                    combatResult = CombatResult::STATIONWINSANDCONVERTS
                else
                    combatResult = CombatResult::STATIONWINS
                end
            end
            gameState.next(@turns, @spaceStations.length)
            return combatResult
        end

        public

        def combat
            state = @gameState.state    
            if ((state == GameState::BEFORECOMBAT)||(state==GameState::INIT))
                return combatGo(@currentStation,@currentEnemy);
            else
                return CombatResult::NOCOMBAT;
            end
        end

        def discardHangar
            if @gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT
                @currentStation.discardHangar
            end
        end

        def discardShieldBooster(i)
            if @gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT
                @currentStation.discardShieldBooster(i)
            end
        end

        def discardShieldBoosterInHangar(i)
            if @gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT
                @currentStation.discardShieldBoosterInHangar(i)
            end
        end

        def discardWeapon(i)
            if @gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT
                @currentStation.discardWeapon(i)
            end
        end

        def discardWeaponInHangar(i)
            if @gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT
                @currentStation.discardWeaponInHangar(i)
            end
        end

        def gameState
            @gameState
        end

        def state
            @gameState.state
        end

        def dice
            @dice
        end

        private
        def currentEnemy
            @currentEnemy
        end

        def currentStation
            @currentStation
        end

        def currentStationIndex
            @currentStationIndex
        end

        def spaceStations
            @spaceStations
        end

        public
        def getUIversion()
            return GameUniverseToUI.new(@currentStation,@currentEnemy)
        end

        def haveAWinner
            return @currentStation.nMedals >= @@WIN
        end

        def init(names)
            state = @gameState.state
            if state == GameState::CANNOTPLAY
                dealer = CardDealer.instance
                for i in (0..((names.length) -1))
                    supplies = dealer.nextSuppliesPackage
                    station = SpaceStation.new(names.at(i),supplies)
                    @spaceStations.push(station)

                    nh = @dice.initWithNHangars
                    nw = @dice.initWithNWeapons
                    ns = @dice.initWithNShields

                    lo = Loot.new(0,nw,ns,nh,0)
                    station.setLoot(lo)
                end
            end
            @currentStationIndex = dice.whoStarts(names.length)
            @currentStation = @spaceStations.at(i)
            @currentEnemy = dealer.nextEnemy
            @gameState.next(@turns, @spaceStations.length)
        end

        def mountShieldBooster(i)
            if @gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT
                @currentStation.mountShieldBooster(i)
            end
        end

        def mountWeapon(i)
            if @gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT
                @currentStation.mountWeapon(i)
            end
        end

        def nextTurn
            state = @gameState.state
            if state == GameState::AFTERCOMBAT
                stationState = @currentStation.validState
                if stationState
                    @currentStationIndex = (@currentStationIndex-1) % @spaceStations.length
                    @turns += 1
                    @currentStation = spaceStations.at(currentStationIndex)
                    @currentStation.cleanUpMountedItems
                    dealer = CardDealer.instance
                    @currentEnemy = dealer.nextEnemy
                    @gameState.next(@turns, @spaceStations.length)
                    return true
                end
                return false
            end
        end

        def makeStationEfficient
            @currentStation = PowerEfficientSpaceStation.new(@currentStation)
            if @dice.extraEfficiency
                @currentStation=BetaPowerEfficientSpaceStation.new(@currentStation)
            end
            
            @spaceStations[@currentStationIndex] = @currentStation
        end
        
        def createSpaceCity
            if !@haveSpaceCity
            collaborators = Array.new
            @spaceStations.each{ |station|
                if(station != @currentStation)
                collaborators.push(station)
                end
            }
            @currentStation = SpaceCity.new(@currentStation,collaborators)
            @spaceStations[@currentStationIndex] = @currentStation
            
            @haveSpaceCity = true
            end
        end

        def to_s
            out = "CurrentStationIndex: #{@currentStationIndex}\n"
            out += "Turns: #{@turns}\n"
            out += "GameState: "
            if gameState == Deepspace::GameState::INIT
                out += "INIT\n"
            elsif gameState == Deepspace::GameState::CANNOTPLAY
                out += "CANNOTPLAT\n"
            elsif gameState == Deepspace::GameState::BEFORECOMBAT
                out += "BEFORECOMBAT\n"
            else
                out += "AFTERCOMBAT\n"
            end
            
            if @currentEnemy != nil
                out += "CurrentEnemy: \n#{@currentEnemy.to_s}\n"
            else
                out += "CURRENTENEMY: nil\n"
            end

            out += "Dice: #{@dice}\n"

            
            if @currentStation != nil
                out += "CurrentStation: \n#{@currentStation.to_s}\n"
            else
                out += "CurrentStation: nil\n"
            end
      
            out += "SpaceStations: \n"
            @spaceStations.each{ |station|
                out += "#{station.to_s}\n"
            }

            return out
        end
    end
end