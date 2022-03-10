#encoding: UTF-8

require_relative 'LootToUI'

module Deepspace
    class Loot
        @nSupplies
        @nWeapons
        @nShields
        @nHangars
        @nMedals

        def initialize(supplies, weapons, shields, hangars, medals, efficient=false,city=false)
            @nSupplies = supplies
            @nWeapons = weapons
            @nShields = shields
            @nHangars = hangars
            @nMedals = medals
            @efficient = efficient
            @spaceCity = city
        end

        def nSupplies
            @nSupplies
        end
        
        def nWeapons
            @nWeapons
        end

        def nShields
            @nShields
        end

        def nHangars
            @nHangars
        end

        def nMedals
            @nMedals
        end

        def efficient
            @efficient
        end

        def spaceCity
            @spaceCity
        end

        def getUIversion
            return LootToUI.new(self)
        end

        def to_s
            return "Supplies: #{@nSupplies}, Weapons: #{@nWeapons}, Shields: #{@nShields}, Hangars: #{@nHangars}, Medals: #{@nMedals}"
        end
    end
end