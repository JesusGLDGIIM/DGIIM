#encoding: UTF-8

require_relative 'DamageToUI'

module Deepspace
    class Damage
        def initialize(nS)
            @nShields = nS
        end

        public

        def getUIversion
            return DamageToUI.new(self)
        end

        def discardShieldBooster
            if @nShields > 0
                @nShields -= 1
            end
        end

        def hasNoEffect
            return @nShields == 0
        end

        def nShields
            @nShields
        end

        def to_s
            string = "nShields: #{@nShields} "
            return string
        end
    end
end