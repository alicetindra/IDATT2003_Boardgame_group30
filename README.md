# IDATT2003_Boardgame_group30
Board:
Made a hashMap of all actionable tiles.  Made methods for filling map from main and getting the map.

BoardGame:
In play() i loop through all the players in the game, make them roll the dice, and call player.move(roll). If the winner is found, i break the loop and declare the winner in main.

LadderAction:
I place the player on the destinationtile it gets from the actionmap.

Player:
In move(); ifs and elses and then calls landPlayer in Tile

Tile:
In landPlayer(). if the player in in an actionable tile, it calls the apropriate landaction, and gives a destinationTile.
PortalAction:
This is just a thought, we dont have to keep this. It "teleports" the player to a random tile on the board if it lands on a certain tile.

Main:
Make a boardgame, players, dice, board, make a map of actionableTiles, places all players on start, calls play() until someone wins, prints the winner.