#encoding: UTF-8

module Deepspace
	class Weapon
		
		@@DEFAULT = 1.0
		
		def initialize(n,t,u)
			@name = n
			@type = t
			@uses = u
		end
		
		def newCopy(w)
			new(w.name,w.type,w.uses)
		end
		
		def name
			@name
		end
			
		def type
			@type
		end
		
		def uses
			@uses
		end
		
		def power
			@type.power
		end
		
		def useIt
			if(@uses>0)
				uses = uses-1
				return power
			else
				@@DEFAULT
		end
		
	end
end
		
		
		
