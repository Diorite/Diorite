# How to Contribute
If you want contribute to Diorite, you must follow instruction in this file.
You need to use our coding style and inspections, if you are using Intellij, you can import it from the `DioriteIntellijSettings.zip` file which is located in root directory of the repository. If you are using other IDE, there is small description of coding style below.

To start you only need fork and download (clone) the project, and then you can just import it to your IDE as a maven project.
Just select this pom file: [**pom.xml**](https://github.com/Diorite/Diorite/blob/master/pom.xml) all other project should be added automatically as they are sub-modules of DioriteMaster project.

## Code style
Just few rules:
* We are using [**Allman bracket/coding style**](https://en.wikipedia.org/wiki/Indent_style#Allman_style).
* Indent by 4 spaces.
* Don't break lines.
* No CRLF line endings, LF only, set your Gits 'core.autocrlf' to 'true'.
* Don't use access modifiers in interfaces. 
* Use final values if possible.
  * But not in method parameters of abstract methods. (including interfaces)
* Always use braces, so don't use `if (i > 5) return true;`
* Spaces:
  * Before `if`, `for`, `while`, `switch`, `try`, `catch`, `synchronized` parentheses, use `if (bool)` instead of `if(bool)`
  * Around operators, except for method reference (`::`), use `c = a + b` instead of `c=a+b`
  * Before keywords, use `} finally` instead of `}finally`
  * After comma/semicolon, use `for (int i = 1, k = 20; i < k; i++)` instead of `for (int i = 1,k = 20;i < k;i++)`
* Imports
  * Import one class by one, don't use `foo.*` imports.
  * Don't import inner classes.
  * Keep order (put blank line after each group, and imports in each groups are sorted alphabetically):
    * import static javax.*
    * import static java.*
    * import static com.*
    * import static org.*
    * import static org.diorite.impl.* (no empty line after this)
    * import static org.diorite.*
    * import static * (all others)
    * Two empty lines, and this same pattern for normal (non-static) imports
* Just see some code if you aren't sure.

## Implementing new packets
If you want implement new packet to Diorite: (that exist in vanilla Minecraft)
* Every packet class must have PacketClass annotation.
* Remember that there will be client and server so all packets need to be working in both ways.
* All fields should have getters and setters.
* Every packet MUST have empty, public constructor, but you can add other constructors

## Others
* Try not to use enums if possible, create classes like this: [**Difficulty.java**](https://github.com/Diorite/Diorite/blob/master/DioriteAPI/src/main/java/org/diorite/Difficulty.java)
  * Keep modding simple.
* Add TODO comments where something needs to be implemented later.
* Don't add any Minecraft decompiled source code
* Use java 8 features.

## Current TODO list, and stuff that we need.
* Documentation for all methods in API and in Core if needed and possible.
* Everything.

## Plans
There are few things that we want to implement in Diorite. When you are writing your code, make sure that it won't collide with them.

### Full multiworld support
Diorite will support multiple worlds with groups and separate player data for each of groups.

### Custom player data
Plugins instead of creating own player data storage can use Diorite storage, with special features:
* Include support for multiworld, data will be automatically saved in per world group.
  * With options to make global variables
* Maybe add more advanced options to use MySQL/SQLite

### Scoreboards
Scoreboards should be plugin-friendly, to allow multiple plugins access them without overriding other plugins data.
Server administrator or other plugins can decide how scoreboards will work:
* Disable/Enable scoreboards from plugins
* Make scoreboard to show only for a few seconds
* Show scoreboard only for players with selected permission or in selected world/world group

### Advanced permission system
Diorite permission system should:
* Support multiworld by default
* Support numeric permissions, like level permission where having `foo.bar.5` permissions will automatically make all lower number permissions check return true, like `foo.bar.2`

### Custom entites
Some ways of creating custom entities from API, I have no idea how to do that.
