# ![Diorite](http://hydra-media.cursecdn.com/minecraft.gamepedia.com/0/08/Diorite.png?version=b51c48a2486c6efd87f3ba9b13c8738a) Diorite Project [![Build Status](https://travis-ci.org/Diorite/Diorite.svg?branch=master)](https://travis-ci.org/Diorite/Diorite)

Diorite is currently in a very early stage of development, most of the features still don't work or are buggy.

Diorite project is made of 4 modules:
* DioriteAPI -> Shared basic API for client and server.
* DioriteCore -> Shared core code between client and server, it may sometimes change but we will try not to break plugins using it too. It is needed to write core mods.
* Diorite -> Server implementation of Diorite.
* DioritOS -> Client implementation of Diorite.

Why are all modules in one repo? We wanted to make contributing simple, without need to configure multiple modules in correct directories, using more advanced git commands etc, you only need to fork and then clone it.

<!--- 'most of server/client code will be somewhere used in pipeline' - could you fix that one? you mean 'sometime'? -->
Diorite is/will be based on pipelines (all events will be replaced by pipelines too), at the end most of the server/client code will be somewhere used in pipeline allowing plugins/mods to change how it works.
Also Diorite will contain special objects which contains default behavior, like drop, inventory slots, etc; these objects can be changed by plugins on start, so you don't even have to use pipelines/events here.

Why are we creating a client too? When it will start working we plan to add a new level of interaction between Diorite Server and Client, for e.g. adding custom blocks at runtime. (Currently there are no plans to allow sending custom Java code between server/client, as it may be too hard to do so securely) Both server and client will be compatible with other Minecraft servers and clients.

## How to Contribute
See here: https://github.com/Diorite/Diorite/blob/master/CONTRIBUTING.md

## Goals
(Goals here are not related to DioritOS project, client project isn't currently in development)

#### Map
- [x] Basic map generation
- [x] Basic structures
- [x] Basic block breaking.
- [ ] Full block breaking, with checking time etc...
- [x] Basic drop system.
- [ ] Full drop system with all correct drops.
- [ ] Biomes
- [ ] Light
- [ ] Advanced structures
- [ ] Working vanillia-like map generation.

#### Tile entities.
- [ ] Signs
- [ ] Containers
- [ ] Furnaces
- [ ] Enchanting
- [ ] Anvil
- [ ] Crafting table.

#### Players
- [x] Client may connect to Diorite server.
- [x] Authorization with Mojang servers.
- [x] Working chat.
- [x] Command system and API
- [x] Basic inventory.
- [ ] Opening and editing other inventories, like chests.
- [ ] Damage and other stuff.

#### Entites
- [x] First working entity. (ItemEntity)
- [ ] Custom names and other stuff.
- [ ] Basic physics.
- [ ] Basic interactions between entities and world. Like damage etc.
- [ ] Basic AI for animals and monster.
- [ ] Better AI
- [ ] Whole interaction system.
- [ ] Implement all entities.

##### Entites, one by one.
- [ ] Dropped items (partially works)
- [ ] Experience Orb
- [ ] Lead knot
- [ ] Painting
- [ ] Item Frame
- [ ] Armor Stand
- [ ] Ender Crystal
- [ ] Thrown egg
- [ ] Shot arrow
- [ ] Thrown snowball
- [ ] Ghast fireball
- [ ] Blaze fireball
- [ ] Thrown Ender Pearl
- [ ] Thrown Eye of Ender
- [ ] Thrown splash potion
- [ ] Thrown Bottle o' Enchanting
- [ ] Wither Skull
- [ ] Firework Rocket
- [ ] Primed TNT
- [ ] Falling blocks
- [ ] Minecart with Command Block
- [ ] Boat
- [ ] Minecart
- [ ] Minecart with Chest
- [ ] Minecart with Furnace
- [ ] Minecart with TNT
- [ ] Minecart with Hopper
- [ ] Minecart with Spawner
- [ ] Creeper
- [ ] Skeleton
- [ ] Wither Skeleton	
- [ ] Spider
- [ ] Giant
- [ ] Zombie
- [ ] Zombie Villager	
- [ ] Slime
- [ ] Ghast
- [ ] Zombie Pigman	
- [ ] Enderman
- [ ] Cave Spider
- [ ] Silverfish
- [ ] Blaze
- [ ] Magma Cube
- [ ] Ender Dragon
- [ ] Wither
- [ ] Endermite
- [ ] Witch
- [ ] Guardian
- [ ] Elder Guardian
- [ ] Shulker
- [ ] Killer Rabbit
- [ ] Bat
- [ ] Pig
- [ ] Sheep
- [ ] Cow
- [ ] Chicken
- [ ] Squid
- [ ] Wolf
- [ ] Mooshroom
- [ ] Snow Golem
- [ ] Ocelot
- [ ] Iron Golem
- [ ] Horse
- [ ] Rabbit
- [ ] Villager
- [ ] Polar Bear

#### Features
- [ ] Enchanting
- [ ] Crafting
- [ ] Potions
- [ ] Food
- [ ] Health
- [ ] Air
- [ ] Suffering in blocks.
- [ ] Scoreboards
- [x] Titles
- [x] World border
- [ ] Redstone stuff.
- [x] Boss bars

#### Others
- [x] Create basic plugin loader
- [x] Basic Core mods.
- [x] Make server start as pipeline and allow to edit it from core mods.
- [ ] Add dependency settings to plugins.
- [ ] Make looking for main class faster. (currently there is only cache but first loading can be still slow)
- [ ] Creating custom packets.
- [ ] All this stuff that I forgot to list here.
