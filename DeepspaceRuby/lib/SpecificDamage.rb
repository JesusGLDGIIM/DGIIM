#encoding:utf-8

require_relative 'SpecificDamageToUI'
require_relative 'Damage'

module Deepspace
    class SpecificDamage < Damage

        def initialize(w,nS)
            super(nS)
            @weapons=w.dup
        end

        def copy
            return SpecificDamage.new(@weapons,@nShields)
        end

        private
        def arrayContainsType(w,t)
            index = 0
            error = -1
            w.each { |wt|
                if wt.type == t
                    return index                  
                else
                    index += 1
                end
            }
            return error
        end

        public

        def getUIversion
            return SpecificDamageToUI.new(self)
        end

        def discardWeapon(w)
            index = @weapons.index(w.type)
            if index != nil
              @weapons.delete_at(index)
            end
        end

        def hasNoEffect
            return @weapons.empty? && super
        end

        def weapons
            @weapons
        end

        def adjust(w,s)
            minNShields = [@nShields,s.length].min
            vectorWeaponType = []
            vectorWeaponCopy = []

            @weapons.each{ |weapontype|
                vectorWeaponType.push(weapontype)
            }

            w.each{ |weapon|
                vectorWeaponCopy.push(weapon)
            }

            adjustType = Array.new

            @weapons.each{|weapontype|
                index = arrayContainsType(vectorWeaponCopy,weapontype)
                if index != -1
                    adjustType.push(weapontype)
                    vectorWeaponCopy.delete_at(index)
                end
            }
            return SpecificDamage.new(adjustType, minNShields)
        end

        def to_s
            string = "nShields: #{@nShields} "
            string += "Weapons: ["
            if @weapons == nil
                string += "none]\n"
            else
                for i in (0...@weapons.length-1) do
                    w = @weapons[i]
                    string += w.to_s
                    string += ","
                end
                w = @weapons[@weapons.length-1]
                string += w.to_s
                string += "]\n"
            end
            return string
        end
    end
end