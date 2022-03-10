#encoding: UTF-8

require_relative 'EnemyToUI'
require_relative 'ShotResult'

module Deepspace
    class EnemyStarShip
        @ammoPower
        @name
        @shieldPower
        @damage
        @loot

        def initialize(n,a,s,l,d)
            @ammoPower = a
            @name = n
            @shieldPower = s
            @damage = d
            @loot = l
        end

        def self.newCopy(e)
            return EnemyStarShip.new(e.name, e.ammoPower, e.shieldPower, e.loot, e.damage)
        end

        def ammoPower
            @ammoPower
        end

        def name
            @name
        end

        def shieldPower
            @shieldPower
        end

        def damage
            @damage
        end

        def loot
            @loot
        end

        def getUIversion
            return EnemyToUI.new(self)
        end

        def fire
            @ammoPower
        end

        def protection
            @shieldPower
        end

        def receiveShot(shot)
            if(shot > shieldPower)
                return ShotResult::DONOTRESIST
            else
                return ShotResult::RESIST
            end
        end

        def to_s
            string = "Name: #{@name}\n"
            string += "AmmoPower: #{@ammoPower}\n"
            string += "ShieldPower: #{@shieldPower}\n"
            if loot == nil
                string += "Loot: none\n"
            else
                string += "Loot: #{@loot.to_s}\n"
            end
            if damage == nil
                string += "Damage: none\n"
            else
                string += "Damage: #{@damage.to_s}\n"
            end 
        end
    end
end