#encoding: UTF-8

require_relative 'HangarToUI'

module Deepspace
    class Hangar

        @maxElements
        @weapons
        @shieldBoosters

        def initialize(capacity)
            @maxElements = capacity
            @weapons = Array.new
            @shieldBoosters = Array.new
        end

        def self.newCopy(h)
            copy = new(h.maxElements)
            h.shieldBoosters.each{ |sB|
                copy.addShieldBooster(sB)
            }
            h.weapons.each{ |w|
                copy.addWeapon(w);
            }
            return copy;
        end

        def getUIversion
            return HangarToUI.new(self)
        end

        private
        
        def spaceAvailable
            return (@shieldBoosters.length + @weapons.length) < @maxElements
        end

        public

        def addWeapon(w)
            if spaceAvailable
                @weapons.push(w)
                return true
            else
                return false
            end
        end

        def addShieldBooster(s)
            if spaceAvailable
                @shieldBoosters.push(s)
                return true
            else
                return false
            end
        end

        def maxElements
            @maxElements
        end

        def weapons
            @weapons
        end

        def shieldBoosters
            @shieldBoosters
        end

        def removeShieldBooster(s)
            if s < 0 || s >= @shieldBoosters.length
                return nil
            end
            return @shieldBoosters.delete_at(s)
        end

        def removeWeapon(w)
            if w < 0 || w >= @weapons.length
                return nil
            end
            return @weapons.delete_at(w)
        end

        def to_s
            string = "MaxElements: #{@maxElements}"
            if !@weapons.empty?
                string += "\nWeapons: "
                @weapons.each { |w|
                    string += " #{w.to_s},"
                }
            else
                string += "\nWeapons: vacío"
            end
            if !@shieldBoosters.empty?
                string += "\nShieldBoosters: "
                @shieldBoosters.each { |sB|
                    string += " #{sB.to_s},"
                }
            else
                string += "\nShieldBoosters: vacío"
            end            
            return string
        end
    end
end