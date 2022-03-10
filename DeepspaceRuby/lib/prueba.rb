require_relative 'Weapon'
require_relative 'WeaponType'

module Deepspace
    prueba = Array.new
    for i in 0..5 do
        prueba.push(Weapon.new("n",WeaponType::LASER,i))
        prueba.push(Weapon.new("n",WeaponType::LASER,0))
    end
        puts prueba.to_s.inspect
        prueba.delete_if{|weapon| weapon.uses == 0}
        puts "\n"
        puts prueba.to_s.inspect
end

