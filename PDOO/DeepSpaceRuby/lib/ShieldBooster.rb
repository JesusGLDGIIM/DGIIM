#encoding: UTF-8

module Deepspace
	class ShieldBooster
		
		@@DEFAULT = 1.0
		
		def initialize(n,b,u)
			@name = n
			@boost = b
			@uses = u
		end
		
		def newCopy(SB)
			new(SB.name,SB.boost,SB.uses)
		end
		
		def name
			@name
		end
		
		def boost
			@boost
		end
		
		def	uses
			@uses
		end
		
		def	useIt
			if(@uses > 0)
				@uses = @uses-1
				return @boost
			else
				return @@DEFAULT
		end
	end
end
