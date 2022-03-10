#encoding: UTF-8

require_relative 'Damage'
require_relative 'NumericDamageToUI'

module Deepspace
    class NumericDamage < Damage

        def initialize(nW,sB)
            super(sB)
            @nWeapons = nW
        end

        def copy
            return NumericDamage.new(@nWeapons,nShields)
        end

        def getUIversion
            return NumericDamageToUI.new(self)
        end

        def adjust(w,s)
            minNShields = [@nShields,s.length].min
            minNWeapons = [@nWeapons,w.length].min
            
            return NumericDamage.new(minNWeapons,minNShields)
        end

        def nWeapons
            return @nWeapons
        end

        def discardWeapon(w)
            if(@nWeapons > 0)
                @nWeapons-=1	
            end
        end

        def hasNoEffect
            return @nWeapons == 0 && super
        end

        def to_s
            out = super
            out += "nWeapons: #{@nWeapons} "
        end
    end   
end