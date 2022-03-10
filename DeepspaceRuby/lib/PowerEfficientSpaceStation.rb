#encoding: UTF-8

require_relative 'SpaceStation'
require_relative 'Transformation'

module Deepspace
    class PowerEfficientSpaceStation < SpaceStation
        @@EFFICIENCYFACTOR = 1.1

        def initialize(station)
            copy(station)
        end

        def fire
            return super * @@EFFICIENCYFACTOR
        end

        def protection
            return super * @@EFFICIENCYFACTOR
        end
          
        def setLoot(l)
            super
            
            if l.getEfficient == Transformation::GETEFFICIENT
              return Transformation::GETEFFICIENT
            else
              return Transformation::NOTRANSFORM
            end
         end
          
        def to_s
            return "PowerEfficientSpaceStation\n" + super
        end
    end   
end