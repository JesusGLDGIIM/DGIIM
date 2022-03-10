#encoding: UTF-8

require_relative 'ShieldToUI'

module Deepspace
    class ShieldBooster
        @name
        @boost
        @uses
        @DEFAULT = 1.0

        def initialize(names, boosts, use)
            @name = names
            @boost = boosts
            @uses = use
            @DEFAULT = 1.0
        end

        def self.newCopy(shieldbooster)
            new(shieldbooster.name, shieldbooster.boost, shieldbooster.uses)
        end

        def name
            @name
        end

        def boost
            @boost
        end

        def uses
            @uses
        end

        def useIt
            if (@uses > 0)
                @uses = @uses - 1.0
                @boost
            else
                @DEFAULT
            end
        end

        def getUIversion
            return ShieldToUI.new(self)
        end

        def to_s
            return "Boost: #{@boost}, Uses: #{@uses}"
          end
    end  
end