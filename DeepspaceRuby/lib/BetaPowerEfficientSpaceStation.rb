#encoding:utf-8

require_relative 'PowerEfficientSpaceStation'
require_relative 'Dice'

module Deepspace
    class BetaPowerEfficientSpaceStation < PowerEfficientSpaceStation
        @@EXTRAEFFICIENCY = 1.2

        def initialize(station)
            super
            @dice = Dice.new
        end

        def fire
            if @dice.extraEfficiency
                return super * @@EXTRAEFFICIENCY
            end
            return super
        end

        def to_s
            return "Beta " + super
        end
    end
end