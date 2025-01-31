## Build Battle Changelog

Changelog is followed by special scheme which is required in order to allow automatic discord
deploy webhooks to print changelog successfully

## Log scheme
`### <current version from pom.xml> <anything else here>`
`<update log line>`

That's all, matcher will stop when detects next line started with `###` match

### 4.0.2 Release (15.06.2019 - 13.07.2019)
* Join permission message outputs required permission node now
* Biome menu won't throw errors when using it now
* Fixed firework spawn after the game if they were disabled in config
* Fixed odd game behaviour when game couldn't start because there were some lacking plots
when player left game before he was removed from the plot
* Locales with special characters like Russian or Korean will now work properly and won't be seen as `?` characters
* Fixed locales might not work in fresh install of plugin
* Added 6 new locales: Portuguese (BR), Slovenian, Dutch, Lithuanian, Japanese and Italian (thanks a lot to our POEditor contributors!)
* Fixed Russian locale didn't work

### 4.0.1 Release (11.06.2019)
* Fixed game wouldn't start if there were 2 players or more, sorry
* Fixed error occurred when you didn't look at sign while adding sign via setup GUI

### 4.0.0 Release (10.06.2019)
* Fixed typo in message accessor for guess the build game mode message
* Now players aren't saved async in onDisable method which led to exceptions in disable stage
* Fixed bad logic of assigning players to teams in teams game mode
* Teams game mode require now to have at least 3 minimum players to play, it will set it to 3 if
it's set lower
* Improved faster and more efficient scoreboard library (thanks to TigerHix)
* Reload and arena delete commands now require confirmation to execute, you must type the command twice to confirm the action
* Implemented faster Hikari database connection pool library instead of BoneCP, jar is now 3 times smaller
* First time database creation is now made async not in main server thread
* Apache Commons-io library is shaded into the jar now, 1.14 removed it
* You cannot set non occluding blocks as a floor block
* **CRITICAL** Players received packets that would break their chunks nearby them due to wrong arena plot reset check, now it's fixed
* Debug in config is no longer visible by default, no one need it
* Removed hiding/showing players in-game and outside game thing, it was bugged and better to avoid problems with it
* Fixed game ending message was never showed when using any of the locales (accessor was broken)
* /bba reload command was undeprecated and it's usage is no longer discouraged
* Made /bb help and /bba help commands actually working
* Added not playing message node to language.yml, locales already had it somehow

### 4.0.0 Beta Pre Releases 3-15 (03.10.2018 - 06.06.2019)
   > RC 2
   * Fixed plugin couldn't start if Cloudflare was blocked in your country (or services were offline)
   * Removed locale suggester, it will no longer spam console when English (default) locale is used
   * Fixed issue with plot adding via setup menu
   > RC 1
   * Fixed animals couldn't spawn and breed outside arenas
   * Fixed spawning animals on arena even if option for disable-spawning was false
   * Fixed /bba addnpc didn't work
   * Fixed setup GUI didn't work properly
   * Fixed [#612 exception when registering arenas](https://www.plajer.xyz/errorservice/viewer.php?id=612) when arenas.yml file was empty
   * Fixed [#610 exception when special item didn't have material-name section set](https://www.plajer.xyz/errorservice/viewer.php?id=610) now by default
   it will have BEDROCK value if absent
   * Added pro tips about user voice and /bba votes executable from console too
   > Pre 15
   * Fixed [#595 /bb randomjoin command exception](https://www.plajer.xyz/errorservice/viewer.php?id=595) when no arena type
   args were specified
   * Fixed [#592 /bba addplot command exception](https://www.plajer.xyz/errorservice/viewer.php?id=592) when no arena name
   was typed
   * Fixed [#589 options menu click exceptions](https://plajer.xyz/errorservice/viewer.php?id=589) when click was on
   air or outside inventory slots
   * Fixed Signs weren't updating if Signs-Block-States-Enabled option was false
   * Abandoned the direct try-catch exception handling in most of methods and event listeners in favour of
   logger listening, code looks better now
   * Implemented Holiday Events for April Fools, Christmas and Halloween, special themes
   for solo and teams will be applied if enabled
   > Pre 14 
   * Fixed time couldn't be set for plots because we were checking raw slot click instead of normal slot
   * Made particles.yml particle items more user friendly (lores and names looks now better)
   * Fixed bba addplot command didn't work properly
   * Added fully configurable biomes in biomes.yml
   * Fixed biomes are now properly reset in plot reset option and after arena reset
   * Added "Go Back" button in particles remove menu
   > Pre 13
   * Added GuessTheBuild game mode (alpha)
   * When using super vote you won't see two game prefixes now
   * Merged /bba addvotes and /bba setvotes into /bba votes add/set command
   * Added message sent to player when receives super votes
   > Pre 12
   * Added BBPlayersPlotReceiveEvent to API
   * Fixed /bba forcestart wasn't working properly
   * Added BBPlotResetEvent to API
   * Particles speed is now 1, they may behave better for builders now
   * Now you can properly execute all /bb and /bba commands in-game while being non op player
   * Somehow plugin was lacking lobby events and you could lose food and health in lobby
   * Added chat events so in-game chat is no separated from server chat (I thought it's obvious)
   * Now after selection of head in heads inventory won't be closed, user will close it when he wants
   * Now language manager will use cached language.yml file so plugin will perform bit better
   * Theme vote GUI is now properly listening for click events, you don't need to click few times to make it vote
   * Added useSSL=false argument in mysql.yml address to avoid mysql warnings in console (not harmful)
   * Whole plugin runs now definitely faster and code execution time and plugin load decreases in timings
   * Setup GUI option for setting min/max players amount now runs smoothly
   > Pre 11
   * Added biomes in Options menu
   * Added time in Options menu
   * Code improvements and changes
   * Fixed arena signs were incorrectly saved via setup menu (4.0.0 pre issue)
   > Pre 10
   * Added setup tutorial link to setup menu
   * Added tip about downloadable maps when no arenas are set up while typing /bba list
   * Fixed exp not saving properly with InventoryManager
   > Pre 9
   * Fixed NPE when player was null (https://plajer.xyz/errorservice/viewer.php?id=347)
   * Code must be changed to avoid those NPE's in the future
   > Pre 8
   * Fixed NPE because of wrong string access in code
   * Locales will be now automatically disabled in this version - ppl don't read warnings about locales so I force them
   to disable
   * Now /bba reload /bba addvotes/setvotes and /bba list can be now properly executed from console
   > Pre 7
   * Updates are now checked async without freezing the main thread which caused TPS to drop a bit
   > Pre 6
   * Particles in menu are now incrementing automatically
   * Particle redstone requires DustOptions and causes errors so it was removed from menu
   * Fixed possible NPEs in block spread event (https://plajer.xyz/errorservice/viewer.php?id=282)
   * Fixed NPE when teleporting to plot, now players will be teleported 1 x and z block away from center if
   height reach Y plot limit (https://plajer.xyz/errorservice/viewer.php?id=283)
   > Pre 5
   * You cannot join game now infinitely through command (lol????)
   * Now players that will leave game will be visible by other players outside game
   > Pre 4
   * API change - now you can access BuildBattle API via pl.plajer.buildbattle.api
   * Maybe a fix for https://plajer.xyz/errorservice/viewer.php?id=244
   > Pre 3
   * Removed annoying "Please enable bStats" message
   * Code improvements
   * Added PR0 TIPS when editing arena

### 4.0.0 Release (20.08.2018 - 28.09.2018)
* Fixed NullPointerException for users who where no longer online
* Now using default values when they not exist in config to avoid NullPointerException from ConfigPreferences
* Fixed IllegalArgumentException when you add invalid item name to the in-game items blacklist - now it will notify you in console
* Fixed %player% placeholder wasn't parsed in /bb stats <player>
* Added PlaceholderAPI placeholders in scoreboard
* Added Russian, Czech, Romanian and Estonian locales (thanks to POEditor contributors!)
* Added dynamic locale manager system - you can now get latest locales on demand from our repository
* Fixed scoreboard color bugs (see https://i.imgur.com/kaZy5s2.png)
* Fixed update checker bugs while using my other minigames
* /bba help command is now executable via players only
* Temporarily merged PLCore to prevent issues with other plugins
* Added blacklisted themes
* Dropped 1.9-1.10 support
* Fixed bug where plot floor wasn't reset while using Citizens
* Fixed message You became 1th/2th/3th was displayed - it shouldn't and it wasn't grammatically okay
* Added extra permission "buildbattle.command.bypass"
* Added solo and team themes
* Added cancel lobbystart when there are not enough players
* Fixed bedrock was displayed in plot floor change option menu
* Head blocks textures are now loading instantly and do not cause server to freeze at the start now
* Fixed /bb top was reversed while MySQL was enabled
* Disabled game end rewards by default because they were confusing for users
* Fixed server was stopped using Shutdown-When-Game-Ends option but player just left not started arena
* Player will be given now survival gamemode on server stop
* Removed unused code for entities

### 3.5.1 Release (17.08.2018)
* Fixed NumberFormatException for language.yml migrator - this error is very rare to occur in normal environment but it was reported so I fixed it
* Fixed NullPointerException for plot adding via selector wand due to lack of code stop when message with not full selection was sent
* Fixed Can't fly outside plot message but you were in plot
* Fixed scoreboard contents were always in Waiting state
* Fixed IndexOutOfBoundsException in join event while bungee is enabled
* Fixed NullPointerException in scoreboard formatting while player was null
* Fixed NoDefClassFoundException in 1.13 while using sign game states

### 3.5.0 Release (08/16.08.2018)
* Built against PLCore API
* Hooked code with Error reporter service
* Fixed MySQL error
* Removed WorldEdit from arena usages
* Added ability to edit floor in game (requires arenas.yml edit or new setup!)
* Added joints when users have same points in voting time
* Fixed small problem when game was started and voting for theme started, timer was set for build time instead of voting time and it
could be seen in scoreboard for a second
* Plot reset now include weather reset
* Added Turkish and Indonesian locales (thanks to POEditor contributors)
* Added /bba removeplot command
* Added new command /bba plotwand

### 3.4.1 Release (30.07/03.08.2018)
* Added public access to MySQL executeUpdate method
* Added BBPlayerStatisticChangeEvent event
* Updated Spanish and Korean locales
* Added French locale (thanks to POEditor contributors!)
* Removed scoreboard saving in InventoryManager due to errors

### 3.4.0 Release (25/29.07.2018)
* Added super votes
* Added /bb top command
* Fixed locales and updated them
* Added Korean language (thanks to POEditor contributors!)
* Added /bba forcestart <theme> to start with predefined theme (suggested by ColaIan)

### 3.3.0 Release (24.07.2018)
* Now while teleporting to the plot you won't fall down
* Fixed xp was given wrong using inventory manager
* Removed unnecessary listeners from spectator code
* Use-Name-Instead-Of-UUID-In-Database option was removed, it's no longer supported
* Removed Particle-Offset option, was useless, default offset should stay same for every server
* Removed Disable-Scoreboard-Ingame option as scoreboard is integral part of the game
* Removed Hook-Into-Vault option as plugin automatically hooks with Vault if found
(this hook really doesn't add anything except an extra placeholder on the scoreboard which is anyway medium useful in this game)
* Using item flags instead of empty custom enchant which didn't work in 1.13
* **Added 1.13 support**
(still lots of code uses deprecated code which shouldn't be used in 1.13 but because 1.13 has got backwards compatibility
we will keep that for a while as an temporary workaround)
**Keep in mind that 1.13 forces me to do changes with ID's in BuildBattle and those changes will be done soon**

### 3.2.2 Release (20.07.2018)
* Fixed NoSuchMethod error in 1.9.4 for title
* Added support for Vietnamese, Hungarian and Chinese simplified locales
* Added ability to change weather in plot

### 3.2.1 Release (14.07.2018)
* Fixed NPE when clicking not named Villager
* Fixed migration error

### 3.2.0 Release (06/12.07.2018)
* Added video tutorial link while creating new arena
* Added new game mode: TEAM
* Fixed error on disabling caused by disabled boss bar feature
* Item rewards at the end of the game now will be properly given after clearing in-game inventory of players
* Vote items now look like Hypixel ones
* Worst vote will now count as 1 point not 0 (so every other vote is +1 point now)
* Implemented theme voting feature like hypixel, before game starts players will vote for theme
* Added localization support via POEditor
* Fixed language migration from version 1 (very old)
* Added missing plugin prefixes to some command messages
* Added Spanish locale (thanks to TheLordDarkYT for POEditor contribution!)
* Separate game timer for team mode

### 3.1.2 Release (05.07.2018)
* Fixed error when 1st winner UUID was null (somehow)
* Added subtitle "x seconds left" in game

### 3.1.0 Release (08.06.2018)
* New good looking summary message at the end of the game
* Added game sign block states
* /bb command is now translatable via language.yml
* Admin commands (/bba) are now better with hover and click event (JSON messages)
* Default map name when creating new arena is arena ID not "0" now
* Now warning message "can't save language.yml because already exists!" won't occur anymore
* Fixed error in console when player left the arena and his plot was cleared even if he didn't have it
* Now you can't change your floor by NPC during the voting time

### 3.0.4 Release (04.06.2018)
* Rewards will be given now only once after the game
* Now you can leave started game without spamming infinitely errors in console
* Fixed broken heads lores in heads menu and changed some strings there
* Fixed wrong displayed winners names at the end in summary
* Now sending "You've became xth" message for 4th winners and lower
* Voting for player plot title is now displayed longer
* Added missing plugin prefix in voting messages
* Immediately voting after game timer ends now 

### 3.0.2 Release (03.06.2018)
* Fixed /bb create command not working
* Added /bba settheme command
* Fixed game boss bar not removing after plugin force disable

### 3.0.0 Release (14.05.2018 - 01.06.2018)
* Add everything here