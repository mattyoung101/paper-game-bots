# Guan Pai AI

by Matt Young, 2021.

This project implements an AI to play the Chinese shedding-style card game _guan pai_ (关牌). It is somewhat similar to 
the Western game Presidents (sometimes "warlords and scumbags" in Australia).

## Method
This implementation uses a Monte-Carlo state space sampling approach to account for the imperfect information of the
game.

1. Generate possible moves in current hand
2. Select top _n_ moves to explore
3. For each move, run a few Monte-Carlo simulations (i.e. semi-random games) to determine who wins
4. Select the move that minimises our loses (e.g. longest average depth to game loss)

## Results
The project has been developed as part of a fun competition with my girlfriend (who is making her own implementation).
Results will be available soon.

# TODO

- Triple and double