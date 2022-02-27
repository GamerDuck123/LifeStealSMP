# LifeStealSMP
## Commands
![image](https://user-images.githubusercontent.com/30637585/144163687-2c07ef34-598d-4f07-b4ff-be112e6d2361.png)

## Config
```YAML
Defaults:
  ConvertFrom: ""
  StartHeartAmount: 20.0
  LoseHeartsOnNonPlayerDeath: false
  HeartsLostOnDeath: 2.0
  HeartsGainedOnKill: 2.0
  HeartsZeroedAmount: 0.0
  CommandsAfterZeroing:
  - "tempban %player% 7d Try again later"
MySQL: 
  Enabled: false
  AutoReconnect: false
  Host: 127.0.0.1
  Database: database
  Username: LifeSteal
  Password: '12345'
  Port: 3306
TAB:
  Enabled: false
  Colors:
    - "100:&a"
    - "10:&e"
    - "5:&c"
HeartCanaster:
  Enabled: false
  AmountGive: 2.0
  Item:
    Material: PLAYER_HEAD
    Amount: 1
    DisplayName: "Heart Canaster"
    Lore:
    - "Right click this item to get a heart"
  Recipe:
    Type: Shaped
    Contents:
    - "*:DIAMOND"
    - "-:GOLDEN_APPLE"
    Shape:
    - "***"
    - "*-*"
    - "***"
Messages:
  NoPermissions: "&cYou do not have permission to do that."
  PlayerNotOnline: "&c%player% is not online"
  NotANumber: "&cThats not a valid number"
  HeartsSet: "&cYour hearts have been set to %amount%"
  HeartsLost: "&cYou have lost %amount% hearts, you now have %total% left"
  HeartsGained: "&cYou have gained %amount% hearts, you now have %total%"
```
## Permissions
```YAML
lifesteal.admin - One permission for all /lifesteal commands
``` 
