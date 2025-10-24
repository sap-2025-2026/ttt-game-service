#### Software Architecture and Platforms - a.y. 2025-2026

## TTT Game Service Case Study - Ubiquitous Language

v.0.9.0-20251024

### Glossary

- **TicTacToe (TTT) Game**
  - the [tic-tac-toe game](https://en.wikipedia.org/wiki/Tic-tac-toe), to be played online
- **Players**
  - the two players of a running game
- **Game board** 
  - the board of a running game, as a grid of 9 cells that can be either `X` or `O`
- **Game service**
  - the online system that allows users to play online TTT games
  - it provides functionalities to rregistered users to create new games and to join & play running games 
- **User**
  - the actor that access the game service
- **Account**
  - In order to access the features of the game service, a user must register into the system, creating an account specifying a unique username - functioning as user identifier -- and a password
  - Users that have an account are also referred as registered users
- **To login** 
  - This is the action that a registered user must do - specifying user name and password - in order to start a session to interact with the game service system. 
  - Registered users that logged in are also referred as logged users
- **To create a game**
  - A logged user can create new instances of TTT games, by specifying a unique game identifier (a name)
- **To join a game**
  - A logged user can join an existing instance of game, specifying its name, to start playing
  - A logged user that joined a game is referred as player
- **To make a move**
  - A player plays by selecting moves to do on the board, following the TTT rules
  - Moves made by a player are made immediately visible to the adversary player
 


