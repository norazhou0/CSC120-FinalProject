This file will contain documentation for all commands available in your game.
Note:  It's a good idea to also make this list available inside the game, in response to a `HELP` command.

In our game, the player is able to use move(north/west/south/east), examine, drop, and quit. 

Specifically, move command takes in four directions to help move the player on the map. The default location of the player is at column 4, row 5 at the beginning of the game. Beyond the 10*10 grid, locations also include a forest at the south, library at the west, statue at the east, and waterfall at the north. See map cheatsheet.jpg for more information of the map. Note that 10*10 is a relatively large grid, so the player might need to keep moving until you get to the location you want. 

examine is valid for acorn, dress, laptop, apple, recipe, tool, and oak that are implemented in our code. The command can be used independently of the player location. Examine gives the user some hints and/or descriptions of certain items that might be the key for proceeding the adventure. 

drop allows the player to drop items from their backpack (Things they have already collected).
Note that there's no explicit grab coomand in this game. It's implemented in our code so that the player only needs to answer yes when needed. 

quit allows the player to exit the game at any time. 


# SPOILER ALERT

If your game includes challenges that must be overcome to win, also list them below.

To win our game, the player needs to collect certain items and bring them to the statue. Items include a dress from Ivy Day, an apple from Mountain Day, and a recipe from Julia Child.

20 acorns are randomly distributed on the grid. They would be added to the map if the player grabs one. In other words, there are always 20 acorns on the grid. The player is suppoed to move around and collect at least 5 acorns for the squirrel family, which is set at location of column 7 and row 4. With those acorns, the squirrel family would offer to help the player open the libray. In the libray, there are four different boxes, in which the player could find the apple, recipe, as well as the tool Cross. The dress is located in the forest on a oak tree. In order to reach it, the player needs to use the tool cross.

We also have two possibilies of death. If the user fall into the waterfall, there is a 50% chance that they would die. Additionally, if there are over 10 items in their backpack, they would die due to overloaidng.