#encoding: UTF-8

module Deepspace
    class SuppliesPackage
        @ammoPower
        @fuelUnits
        @shieldPower
    
        def initialize(ammopower, fuelunits, shieldpower)
            @ammoPower = ammopower
            @fuelUnits = fuelunits
            @shieldPower = shieldpower
        end

        def self.newCopy(package)
            new(package.ammoPower, package.fuelUnits, package.shieldPower)
        end

        def ammoPower
            @ammoPower
        end

        def fuelUnits
            @fuelUnits
        end

        def shieldPower
            @shieldPower
        end

        def to_s
            return "AmmoPower: #{@ammoPower}, FuelUnits: #{@fuelUnits}, ShieldPower: #{@shieldPower}"
        end
    end
end