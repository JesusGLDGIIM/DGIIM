#encoding: UTF-8

module Deepspace
    class SpaceCity < SpaceStation
        def initialize(base, collaborators)
            copy(base)
            @base = base
            @collaborators = collaborators.dup
        end

        def collaborators
            @collaborators
        end

        def fire
            fuego = super
            @collaborators.each {|estacion|
                fuego += estacion.fire
            }
            return fuego
        end

        def protection
            escudo = super
            @collaborators.each {|estacion|
                escudo += estacion.protection
            }
            return escudo
        end

        def setLoot(loot)
            super
            return  Transformation::NOTRANSFORM
        end

        def to_s
            out = "Base: " + super
            out += "\n* Collaborators: \n"
      
            @collaborators.each { |nave|
              out += nave.to_s
              out += "\n"
            }
            return out
        end
    end
end