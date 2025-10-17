#### Software Architecture and Platforms - a.y. 2025-2026

## TTT Game Service Case Study - Use Cases (examples)


### Actors

- Visitor (unregistered user)
- Registered Player (logged-in user)
- System (the game server / app)
- (Optional external) Email/Auth Provider (if you do email verification or OAuth)

### Core use cases

#### Register Account (Visitor → System)

- **Goal**: Create a player account.
- **Pre**: Visitor not registered.
- **Post**: Account exists; user can log in.
- **Notes**: Might include email verification and password reset (supporting UCs).

#### Log In (Visitor/Registered Player → System)

- **Goal**: Authenticate to use game features.
- **Pre**: Account exists.
- **Post**: Session started.

#### Create Game (Registered Player → System)

- **Goal**: Open a new Tic-Tac-Toe game waiting for an opponent.
- **Pre**: Player is logged in.
- **Main**: Player chooses “Create”; system creates a lobby with a unique game ID; player becomes host/X by default (configurable).
- **Post**: Game is “waiting for opponent.”
- **Alt**: If configuration needed (e.g., private/public), collect options.

#### List & Join Game (Registered Player → System)

- **Goal**: Find and join an open game.
- **Pre**: Player is logged in; at least one open game exists (or join by link/ID).
- **Main**: Player views open games → selects one → system admits player if slot available.
- **Post**: Two players present → Start Game triggers.
- **Exceptions**: Game just filled; game not found; private game requires invite.

#### Start Game (System) — *included from* Create/Join

- **Goal**: Initialize board, assign symbols/turn order, set status to “in progress.”
- **Post**: Board empty; X to move; timers (if any) started; both players notified.

#### Play Turn (Make Move) (Registered Player → System)

- **Goal**: Place X/O on an empty cell when it’s your turn.
- **Pre**: Game in progress; it’s the player’s turn.
- **Main**: Player selects cell → system validates move → updates board → checks win/draw → switches turn → notifies opponent.
- **Exceptions**: Not your turn; cell occupied; game already ended; connection lost.
- Supporting (included) sub-use cases:
  - Validate Move (legal cell, turn, game state)
  - Update Game State (persist move, turn toggle)
  - Check Win/Draw (three-in-a-row or no empty cells)
  - Notify Opponent (real-time push/websocket)

#### View Game State (Registered Player → System)

- **Goal**: See current board, whose turn, and status in real time.
- **Pre**: Player is in a game (waiting, in progress, or finished).
- **Post**: UI reflects latest state; updates stream as moves occur.

#### End Game (System / Registered Player)

- **Goal**: Conclude a match as Win/Loss/Draw or via Forfeit.

- **Triggers**: Win/Draw from Check Win/Draw, or Forfeit/Leave Game by a player.
- **Post**: Game status “finished”; result recorded; players can Rematch or Exit.

#### Forfeit / Leave Game (Registered Player → System)

- **Goal**: A player concedes or exits an active game.
- **Post**: Opponent declared winner; End Game runs.

#### Rematch (Registered Player → System) — <<extend>> End Game

- **Goal**: Quickly start a new game with same opponent.
- **Pre**: Previous game finished; both players agree.
- **Post**: New game created → Start Game.

### Helpful (but optional) supporting use cases

- Browse Open Games (filter/search)
- Invite by Link / Private Game
- View Match History / Stats
- Log Out
- Reset Password / Verify Email (if you include them)

### Relationships

- Create Game and Join Game *include* Start Game
- Play Turn *includes* Validate Move, Update Game State, Check Win/Draw, Notify Opponent


### Textual specs 

#### Register Account (Visitor → System)

**Primary actor**: Visitor
**Goal**: Create a player account to access gameplay features.
**Preconditions**: Visitor is not logged in.
**Postconditions**: New account exists; user can log in.

**Main flow**

1. Visitor opens the registration page.
2. System prompts for email, username, and password.
3. Visitor submits the form.
4. System validates inputs and creates the account.
5. System (optionally) sends verification email and confirms registration.

**Alternate/Exception flows**
- 4a: Email already used → System shows error, ask for another email.
- 4b: Weak password/invalid username → System shows validation errors.
- 4c: Email verification required → Account set to “pending verification”; login restricted until verified.


