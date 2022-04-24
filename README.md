# LifeStealSMP
## Commands
![image](https://user-images.githubusercontent.com/30637585/144163687-2c07ef34-598d-4f07-b4ff-be112e6d2361.png)

## Config
```YAML
Defaults:
  ConvertFrom: "" # This will auto fill out when you do /lifesteal convert
  ShouldDamageScaleWithHealth: false # Should damage scale with health i.e 40 health = double damage than normal
  StartHeartAmount: 20.0 # The amount of hearts a player starts with
  LoseHeartsOnNonPlayerDeath: false # Does a player lose hearts when killed by the enviroment?
  HeartsLostOnDeath: 2.0 # How many hearts are lost on death (2 = 1 full heart)
  HeartsGainedOnKill: 2.0 # How many hearts are gained on a player kill (2 = 1 full heart)
  HeartsZeroedAmount: 0.0 # How many hearts does the player have to have until they "zero out" or loses
  CommandsAfterZeroing: # The command to run when a player hits the zero out amount
  - "tempban %player% 7d Try again later"
MySQL: # MySQL Support, even when disabled I use SQLite for fast and non performance damaging methods
  Enabled: false # Enable MySQL over SQLite
  AutoReconnect: false # Should MySQL Auto Reconnect with connection is lost?
  Host: 127.0.0.1 # MySQL IP Address
  Database: database # MySQL Database you want to store data in
  Username: LifeSteal # MySQL account username
  Password: '12345' # MySQL account password
  Port: 3306 # MySQL Port
TAB: # Should the tab show how many lives the player has?
  Enabled: false # Enable Tab Feature
  Colors: # List of colors
    - "100:&a" # 100 = The highest amount of hearts for the color | &a = the color for everyone below 100 hearts
    - "10:&e" # This will have all players with 10 or less hearts with the color e (yellow)
    - "5:&c" # This will have all players with 5 or less hearst with the color c (red)
Messages:
  NoPermissions: "&cYou do not have permission to do that." # Message sent when a player does not have perms for a command
  PlayerNotOnline: "&c%player% is not online" # Message sent when the target player is offline
  NotANumber: "&cThats not a valid number" # Message sent when the argument isnt a number
  HeartsSet: "&cYour hearts have been set to %amount%" # Message sent to player when their hearts have been set (through commands)
  HeartsLost: "&cYou have lost %amount% hearts, you now have %total% left" # Message sent when player loses hearts (through commands or death)
  HeartsGained: "&cYou have gained %amount% hearts, you now have %total%" # Message sent when player gains hearts (through commands or kills
```
## Permissions
```YAML
lifesteal.admin - One permission for all /lifesteal commands
``` 
