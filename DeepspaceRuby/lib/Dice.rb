#encoding: UTF-8

require_relative 'GameCharacter'

module Deepspace
    class Dice
        @NHANGARSPROB
        @NSHIELDSPROB
        @NWEAPONSPROB
        @FIRSTSHOTPROB
        @EXTRAEFFICIENCYPROB
        @generator
        
        def initialize
            @NHANGARSPROB = 0.25
            @NSHIELDSPROB = 0.25
            @NWEAPONSPROB = 0.33
            @FIRSTSHOTPROB = 0.5
            @EXTRAEFFICIENCYPROB=0.8
            @generator = Random.new
        end
        
        def initWithNHangars
            r = @generator.rand(1.0)
            if r < @NHANGARSPROB
                return 0
            else
                return 1
            end
        end

        def initWithNWeapons
            r = @generator.rand(1.0)
            if r < @NWEAPONSPROB
                return 0
            elsif ((r > @NWEAPONSPROB) && (r < 2*@NWEAPONSPROB))
                return 1
            else
                return 2
            end
        end

        def initWithNShields
            r = @generator.rand(1.0)
            if r < @NSHIELDSPROB
                return 0
            else
                return 1
            end
        end

        def whoStarts nPlayers
            return @generator.rand(nPlayers)
        end

        def firstShot
            r = @generator.rand(1.0)
            if r < @FIRSTSHOTPROB
                return GameCharacter::SPACESTATION
            else
                return GameCharacter::ENEMYSTARSHIP
            end
        end

        def spaceStationMoves(speed)
            r = @generator.rand(1.0)
            return r < speed
        end

        def extraEfficiency
            r = @generator.rand(1.0)
            return r < @EXTRAEFFICIENCYPROB
        end

        def to_s
      
            initnHangars = "- INIT WITH NHANGARS: #{initWithNHangars} "
            initnWeapons = "- INIT WITH NWEAPONS: #{initWithNWeapons} "
            initnShields = "- INIT WITH NSHIELDS: #{initWithNShields}\n"
            if firstShot == Deepspace::GameCharacter::SPACESTATION
              firstS = "- FIRST SHOT: SPACESTATION\n"
            else
              firstS = "- FIRST SHOT: ENEMYSTARSHIP\n"
            end
            
            return initnHangars + initnWeapons + initnShields + firstS
        end
    end
end