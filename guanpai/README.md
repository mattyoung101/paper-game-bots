# Guan Pai AI

by Matt Young, 2021.

This project implements an AI to play the Chinese shedding-style card game _guan pai_ (关牌). It is somewhat similar to 
the Western game Presidents (sometimes "warlords and scumbags" in Australia).

## Method
This implementation uses a Monte-Carlo state space sampling approach to account for the imperfect information of the
game. The approach roughly works as follows:

1. Generate possible moves in current hand
2. For each move, run a few Monte-Carlo simulations (i.e. semi-random games) to determine who wins
3. Select the move that yields the lowest average number of cards remaining (as the goal of guan pai is shed all your cards)

## Notes
- Unfortunately I implemented the Player class kind of poorly, it would have been smarter to have Player with subclasses
HumanPlayer and AIPlayer that both have a getMove() method, but I think this is too hard to refactor now.

## Results
The project has been developed as part of a fun competition with my girlfriend (who is making her own implementation).
Results will be available soon.

Initial tests using just the MoveSelector (the fast but not super strategic agent used inside the Monte Carlo simulation)
shows positive results, but room for improvement. Hopefully the simulations will do that.