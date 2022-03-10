#encoding: UTF-8

require_relative 'SpaceStationToUI'
require_relative 'ShotResult'
require_relative 'CardDealer'
require_relative 'Damage'
require_relative 'Transformation'

module Deepspace
    class SpaceStation
        @@MAXFUEL = 100.0
        @@SHIELDLOSSPERUNITSHOT = 0.1

        @ammoPower
        @fuelUnits
        @name
        @nMedals
        @shieldPower
        @pendingDamage
        @hangar
        @weapons
        @shieldBoosters

        def initialize(n,supplies)
            @name = n;
            @ammoPower = supplies.ammoPower
            @fuelUnits = supplies.fuelUnits
            @shieldPower = supplies.shieldPower
            @nMedals = 0
            @pendingDamage = nil
            @hangar = nil
            @weapons = Array.new
            @shieldBoosters = Array.new
        end

        def copy(station)
            @ammoPower = station.ammoPower
            @fuelUnits = assignFuelValue(station.fuelUnits)
            @name = station.name
            @nMedals = station.nMedals
            @shieldPower = station.shieldPower
            @pendingDamage = station.pendingDamage
            @weapons = station.weapons.dup
            @shieldBoosters = station.shieldBoosters.dup
            @hangar = station.hangar
        end

        private
        def assignFuelValue(f)
            if f > @@MAXFUEL
                f = @@MAXFUEL
            end
            @fuelUnits = f
        end

        def cleanPendingDamage
            if @pendingDamage.hasNoEffect
                @pendingDamage = nil
            end
        end

        public

        def cleanUpMountedItems
            @weapons.delete_if{|weapon| weapon.uses == 0}
            @shieldBoosters.delete_if{|sB| sB.uses == 0}
        end

        def discardHangar
            if @hangar != nil
                @hangar = nil
            end
        end

        def discardShieldBooster(i)
            if ((0 <= i) && (i < @shieldBoosters.length()))
                @shieldBoosters.delete_at(i)
                if @pendingDamage != nil
                    @pendingDamage.discardShieldBooster()
                    cleanPendingDamage()
                end
            end
        end

        def discardShieldBoosterInHangar(i)
            if @hangar != nil
                @hangar.removeShieldBooster(i)
            end
        end

        def discardWeapon(i)
            if ((0 <= i) && (i < @weapons.length()))
                weapon = @weapons.delete_at(i)
                if @pendingDamage != nil
                    @pendingDamage.discardWeapon(weapon)
                    cleanPendingDamage()
                end
            end
        end

        def discardWeaponInHangar(i)
            if @hangar != nil
                @hangar.removeWeapon(i)
            end
        end

        def fire
            factor = 1.0
            size = @weapons.length
            for i in (0..size-1)
                w = @weapons.at(i)
                factor = factor * w.useIt()
            end
            return factor*@ammoPower
        end

        def ammoPower
            @ammoPower
        end

        def fuelUnits
            @fuelUnits
        end

        def name
            @name
        end
        
        def nMedals
            @nMedals
        end

        def shieldPower
            @shieldPower
        end
          
        def pendingDamage
            @pendingDamage
        end

        def hangar
            @hangar
        end
          
        def weapons
            @weapons
        end
            
        def shieldBoosters
            @shieldBoosters
        end
           
        def getSpeed
            return @fuelUnits.to_f/@@MAXFUEL.to_f
        end

        def getUIversion
            return SpaceStationToUI.new(self)
        end

        def mountShieldBooster(i)
            if @hangar != nil
                if ((s=@hangar.removeShieldBooster(i)) != nil)
                    @shieldBoosters.push(s)
                end  
            end
        end

        def mountWeapon(i)
            if @hangar != nil
                if ((w=@hangar.removeWeapon(i)) != nil)
                    @weapons.push(w)
                end  
            end
        end

        def move
            @fuelUnits -= (getSpeed*fuelUnits)
            if @fuelUnits < 0
                @fuelUnits = 0
            end
        end

        def protection
            factor = 1.0
            size = @shieldBoosters.length
            for i in (0..size-1)
                s = @shieldBoosters.at(i)
                factor = factor * s.useIt()
            end
            return factor*@shieldPower
        end

        def receiveHangar(h)
            if @hangar == nil
                @hangar = h
            end
        end

        def receiveShieldBooster(s)
            if @hangar == nil
                return false
            end
            return @hangar.addShieldBooster(s)
        end

        def receiveShot(shot)
            myProtection = protection()
            if myProtection >= shot
                @shieldPower -= @@SHIELDLOSSPERUNITSHOT*shot
                @shieldPower = [@shieldPower,0.0].max
                return ShotResult::RESIST
            else
                @shieldPower = 0.0
                return ShotResult::DONOTRESIST
            end

        end

        def receiveSupplies(s)
            @ammoPower += s.ammoPower
            @shieldPower += s.shieldPower
            assignFuelValue(@fuelUnits+s.fuelUnits)
        end

        def receiveWeapon(w)
            if @hangar == nil
                return false
            end
            return @hangar.addWeapon(w)
        end

        def setLoot(l)
            dealer = CardDealer.instance
            h = l.nHangars
            if h>0
                hangar = dealer.nextHangar
                receiveHangar(hangar)
            end

            elements = l.nSupplies
            for i in (0..elements-1)
                sup = dealer.nextSuppliesPackage
                receiveSupplies(sup)
            end

            elements = l.nWeapons
            for i in (0..elements-1)
                w = dealer.nextWeapon
                receiveWeapon(w)
            end

            elements = l.nShields
            for i in (0..elements-1)
                sh = dealer.nextShieldBooster
                receiveShieldBooster(sh)
            end

            medals = l.nMedals
            @nMedals += medals

            if l.efficient
                return Transformation::GETEFFICIENT
            elsif l.spaceCity
                return Transformation::SPACECITY
            else
                return Transformation::NOTRANSFORM
            end
        end

        def setPendingDamage(d)
            @pendingDamage = d.adjust(@weapons,@shieldBoosters)
            cleanPendingDamage
        end

        def validState
            if @pendingDamage == nil
                return true
            end
            return pendingDamage.hasNoEffect            
        end

        def to_s
            namE = "+ NAME: #{@name}\n"
            aP = "+ AMMOPOWER: #{@ammoPower} "
            fU = "+ FUELUNITS: #{@fuelUnits} "
            sP = "+ SHIELDPOWER: #{@shieldPower}\n"
            nMed = "+ NMEDALS: #{@nMedals}\n"
            
            pDam = "+ PENDING DAMAGE: Ninguno \n"
            if pendingDamage != nil
                pDam = "+ PENDING DAMAGE: \n#{@pendingDamage.to_s}"
            end
            
            wP = "+ WEAPONS MOUNTED: Ninguno \n"
            if !weapons.empty?
                wP = "\n+ WEAPONS MOUNTED: \n"
                @weapons.each { |w|
                wP += "#{w.to_s}"
                }
            end
            
            sB = "+ SHIELDBOOSTERS MOUNTED: Ninguno \n"
            if !shieldBoosters.empty?
                sB = "\n+ SHIELDBOOSTERS MOUNTED: \n"
                @shieldBoosters.each { |shieldB|
                sB += "#{shieldB.to_s}"
                }
            end
            
            hG = "+ HANGAR: Ninguno \n"
            if hangar != nil
                hG = "\n+ HANGAR: \n#{@hangar.to_s}\n"
            end
            
            return namE + aP + fU + nMed + sP + pDam + wP + sB + hG
        end
    end
end