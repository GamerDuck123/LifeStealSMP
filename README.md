# LifeStealSMP

### I am currently taking classes at university, I plan to try to continue weekly bug fixes and updates. However, it might take a bit longer due to classes coming first!

## Commands
![image](https://user-images.githubusercontent.com/30637585/144163687-2c07ef34-598d-4f07-b4ff-be112e6d2361.png)

## Config
```YAML
Defaults:
  ConvertFrom: ""
  IfAtMaxHeartsGiveCanaster: false
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
  DisabledWorlds: []
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
    CustomModelData: 1
    DisplayName: "Heart Canaster"
    Lore:
    - "Right click this item to get a heart"
  RecipeEnabled: true
  Recipe:
    Type: Shaped
    Contents:
      DIAMOND:
        Symbol: "*"
        CustomModelData: 1
        DisplayName: "Heart Shard"
        Lore:
        - "Right click this item to get a heart"
      GOLDEN_APPLE:
        Symbol: "-"
    Shape:
    - "***"
    - "*-*"
    - "***"
HeartShard:
  Item:
    Material: DIAMOND
    CustomModelData: 1
    DisplayName: "Heart Shard"
    Lore:
    - "Right click this item to get a heart"
  RecipeEnabled: true
  Recipe:
    Type: Shaped
    Contents:
      DIAMOND:
        Symbol: "*"
      EMERALD:
        Symbol: "-"
    Shape:
    - "***"
    - "*-*"
    - "***"
```
## Messages
```Properties
FileVersion=${project.version}
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
CannotBeNegativeNumber=&cYou cannot use a negative number
PlayerNeverJoined=&c{0} has never joined before
CheckHearts=&a{0} &7has &a{1} &7hearts
HeartCanasterRecieved=&aYou have received {0} heart canisters
```

## Permissions
```YAML
lifesteal.admin - One permission for all /lifesteal commands
``` 
