#encoding: UTF-8

module Deepspace
	class Dice
		def initialize
			@NHANGARSPROB=0.25 
			@NSHIELDSPROB=0.25
			@NWEAPONSPROB=0.33
			@FIRSTSHOTPROB=0.5
			@generator= Random.new
		end
		
		def initWithNHangars
			r = @generator.rand(1.0)
			if r < @NHANGARSPROB
				return 0
			end
			return 1
		end
		
		def initWithNWeapons
			r = @generator.rand(1.0)
			if r < @NWEPAONSPROB
				return 1
			elsif ((r > @NWEAPONSPROB) && (r < 2*@NWEAPONSPROB))
				return 2
			else
				return 3
			end
		end
		
		def initWithNShields
			r = @generator.rand(1.0)
			if r < @NSHIELDSPROB
				return 0
			end
			return 1
		end
		
		def whoStarts(nPlayers)
			return @generator.rand(nPlayers)
		end
		
		def firstShot
			r = @generator.rand(1.0)
			if r < @FIRSTSHOTPROB
				return GameCharacter::SPACESTATION
			end
			return GameCharacter::ENEMYSTARSHIP
		end
		
		def spaceStationMoves(speed)
			r = @generator.rand(1.0)
			if r < speed
				return 1
			end
			return 0
		end
	end
end
