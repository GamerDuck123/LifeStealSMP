# LifeStealSMP
## Commands
![image](https://user-images.githubusercontent.com/30637585/144163687-2c07ef34-598d-4f07-b4ff-be112e6d2361.png)

## Config
```YAML
Defaults:
  ConvertFrom: ""
  ShouldWithdrawCommandExist: true
  WithdrawMininum: 20.0
  ShouldKeepLastPlayerAsDamager: true
  HowLongShouldPlayerBeLastDamager: 20
  ShouldDamageScaleWithHealth: false
  MaxHeartAmount: 60.0
  StartHeartAmount: 20.0
  ZeroedRespawnAmount: 15.0
  LoseHeartsOnNonPlayerDeath: false
  HeartsLostOnDeath: 2.0
  HeartsGainedOnKill: 2.0
  HeartsZeroedAmount: 0.0
  CommandsAfterDeath: []
  CommandsAfterKill: []
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
  AmountGive: 2.0
  Item:
    Material: PLAYER_HEAD
    DisplayName: "Heart Canaster"
    Lore:
    - "Right click this item to get a heart"
  RecipeEnabled: true
  Recipe:
    Type: Shaped
    Contents:
    - "*:DIAMOND"
    - "-:GOLDEN_APPLE"
    Shape:
    - "***"
    - "*-*"
    - "***"
```
## Messages
```Properties
NoPermissions=&cYou do not have permission to do that.
PlayerNotOnline=&c{0} is not online
NotANumber=&cThats not a valid number
HeartsSet=&cYour hearts have been set to {0}
HeartsLost=&cYou have lost {0} hearts, you now have {1} left
HeartsGained=&cYou have gained {0} hearts, you now have {1}
CantBeLeftWithLessThanOneHeart=&cYou cannot have less than {0} heart left
HeartsWithdrawn=&aYou have withdrawn {0} hearts
HasToBeWholeNumber=&cNumber has to be a whole number
WithdrawCorrectUsage=&cCorrect usage: /withdraw (amount)
MaxHearts=&cYou already have the max amount of hearts allowed!
```

## Permissions
```YAML
lifesteal.admin - One permission for all /lifesteal commands
``` 
