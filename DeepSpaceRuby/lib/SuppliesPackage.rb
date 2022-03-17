#encoding: UTF-8

module Deepspace
	class SuppliesPackage
		
		def initialize(aP,fU,sP)
			@ammoPower = aP
			@fuelUnits = fU
			@shieldPower = sP
		end
		
		def newCopy(SP)
			new(SP.ammoPower,SP.fuelUnits,SP.shieldPower)
		end
		
		def ammoPower
			@ammoPower
		end
		
		def fuelUnits
			@fuelUnits
		end
		
		def shieldPower
			@shieldPower
		end
		
	end
end
