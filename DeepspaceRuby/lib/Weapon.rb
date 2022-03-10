#encoding: UTF-8

require_relative 'WeaponToUI'

module Deepspace
    class Weapon
        @name
        @type
        @uses
        @DEFAULT = 1.0

        def initialize(name, type, uses)
            @name = name
            @type = type
            @uses = uses
            @DEFAULT = 1.0
        end

        def self.newCopy(weap)
            weapon = Weapon.new(weap.name, weap.type, weap.uses)
        end

        def type
            @type
        end

        def uses
            @uses
        end

        def name
            @name
        end

        def power
            @type.power
        end

        def useIt
            if (@uses > 0)
                @uses = @uses - 1.0
                return power
            else
                @DEFAULT
            end
        end

        def getUIversion
            return WeaponToUI.new(self)
        end

        def to_s
            return "Type: #{@type} Uses: #{@uses}"
        end
    end
end