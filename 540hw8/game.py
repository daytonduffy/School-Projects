import random
import copy

class Teeko2Player:
    """ An object representation for an AI game player for the game Teeko2.
    """
    board = [[' ' for j in range(5)] for i in range(5)]
    pieces = ['b', 'r']

    def __init__(self):
        """ Initializes a Teeko2Player object by randomly selecting red or black as its
        piece color.
        """
        self.my_piece = random.choice(self.pieces)
        self.opp = self.pieces[0] if self.my_piece == self.pieces[1] else self.pieces[1]

    def droptime(self, state):
        pieces = 0
        # iterate over the whole board
        for i in range(5):
            for j in range(5):
                if state[i][j] != ' ':  # if r or b
                    pieces = pieces + 1  # add piece
        if pieces == 8:
            return False  # 8 pieces means no more drops
        else:
            return True  # any other number means we need to drop!

    def successors(self, state, drop, AI):
        succ = list()  # list to hold successors
        moves = list()

        if AI:  # AI's turn
            if drop:  # drop phase, every open space
                # iterate over the whole board
                for i in range(len(state)):
                    for j in range(len(state[i])):
                        # the only possibilities are using one open space
                        if state[i][j] == ' ':  # if you find an empty space that's a chance
                            deep = copy.deepcopy(state)  # on each good find need a copy
                            deep[i][j] = self.my_piece  # place the new piece down
                            move = [(i, j)]
                            moves.append(move)  # for drop phase move is just location of spot
                            succ.append(deep)  # add this new possibility to the list
            else:  # continuous phase, every piece
                # iterate over the whole board, easiest way to find 4 pieces
                # when piece found look at all 8 spaces it could move to and try it
                for i in range(len(state)):
                    for j in range(len(state[i])):
                        if state[i][j] == self.my_piece:
                            # need to check up/down, left/right, diagonals (-/+ 1)
                            # (-i, -j) (-i, j) (-i, +j) - (i, -j) (current spot) (i, +j) - (+i, -j) (+i, j) (+i, +j)

                            # upper row
                            if i != 0:  # can't go past the top row
                                if j != 0:  # can't go past the left edge
                                    if state[i - 1][j - 1] == ' ':  # top left open
                                        deep = copy.deepcopy(state)  # on each good find need a copy
                                        deep[i - 1][j - 1] = self.my_piece  # set successor
                                        deep[i][j] = ' '  # reset square being moved away from
                                        move = [(i-1, j-1), (i, j)]
                                        moves.append(move)  # for game phase need where you goin and where you from
                                        succ.append(deep)
                                # center one just passes
                                if state[i - 1][j] == ' ':  # top center open
                                    deep = copy.deepcopy(state)  # on each good find need a copy
                                    deep[i - 1][j] = self.my_piece  # set successor
                                    deep[i][j] = ' '  # reset square being moved away from
                                    move = [(i - 1, j), (i, j)]
                                    moves.append(move)  # for game phase need where you goin and where you from
                                    succ.append(deep)
                                if j != 4:  # can't go past the right edge
                                    if state[i - 1][j + 1] == ' ':  # top right open
                                        deep = copy.deepcopy(state)  # on each good find need a copy
                                        deep[i - 1][j + 1] = self.my_piece  # set successor
                                        deep[i][j] = ' '  # reset square being moved away from
                                        move = [(i - 1, j + 1), (i, j)]
                                        moves.append(move)  # for game phase need where you goin and where you from
                                        succ.append(deep)

                            # center row
                            if j != 0:  # can't go past left edge
                                if state[i][j - 1] == ' ':  # center left open
                                    deep = copy.deepcopy(state)  # on each good find need a copy
                                    deep[i][j - 1] = self.my_piece  # set successor
                                    deep[i][j] = ' '  # reset square being moved away from
                                    move = [(i, j - 1), (i, j)]
                                    moves.append(move)  # for game phase need where you goin and where you from
                                    succ.append(deep)
                            if j != 4:  # can't go past right edge
                                if state[i][j + 1] == ' ':  # center right open
                                    deep = copy.deepcopy(state)  # on each good find need a copy
                                    deep[i][j + 1] = self.my_piece  # set successor
                                    deep[i][j] = ' '  # reset square being moved away from
                                    move = [(i, j + 1), (i, j)]
                                    moves.append(move)  # for game phase need where you goin and where you from
                                    succ.append(deep)

                            # bottom row
                            if i != 4:  # can't go past the bottom row
                                if j != 0:  # can't go past the left edge
                                    if state[i + 1][j - 1] == ' ':  # bottom left open
                                        deep = copy.deepcopy(state)  # on each good find need a copy
                                        deep[i + 1][j - 1] = self.my_piece  # set successor
                                        deep[i][j] = ' '  # reset square being moved away from
                                        move = [(i + 1, j - 1), (i, j)]
                                        moves.append(move)  # for game phase need where you goin and where you from
                                        succ.append(deep)
                                # center one just passes
                                if state[i + 1][j] == ' ':  # bottom center open
                                    deep = copy.deepcopy(state)  # on each good find need a copy
                                    deep[i + 1][j] = self.my_piece  # set successor
                                    move = [(i + 1, j), (i, j)]
                                    moves.append(move)  # for game phase need where you goin and where you from
                                    succ.append(deep)
                                if j != 4:  # can't go past the right edge
                                    if state[i + 1][j + 1] == ' ':  # bottom right open
                                        deep = copy.deepcopy(state)  # on each good find need a copy
                                        deep[i + 1][j + 1] = self.my_piece  # set successor
                                        deep[i][j] = ' '  # reset square being moved away from
                                        move = [(i + 1, j + 1), (i, j)]
                                        moves.append(move)  # for game phase need where you goin and where you from
                                        succ.append(deep)
        else:  # opponents turn to play
            if drop:  # drop phase, every open space
                # iterate over the whole board
                for i in range(len(state)):
                    for j in range(len(state[i])):
                        # the only possibilities are using one open space
                        if state[i][j] == ' ':  # if you find an empty space that's a chance
                            deep = copy.deepcopy(state)  # on each good find need a copy
                            deep[i][j] = self.opp  # place the new piece down
                            move = [(i, j)]
                            moves.append(move)  # for drop phase move is just location of spot
                            succ.append(deep)  # add this new possibility to the list
            else:  # continuous phase, every piece
                # iterate over the whole board, easiest way to find 4 pieces
                # when piece found look at all 8 spaces it could move to and try it
                for i in range(len(state)):
                    for j in range(len(state[i])):
                        if state[i][j] == self.opp:
                            # need to check up/down, left/right, diagonals (-/+ 1)
                            # (-i, -j) (-i, j) (-i, +j) - (i, -j) (current spot) (i, +j) - (+i, -j) (+i, j) (+i, +j)

                            # upper row
                            if i != 0:  # can't go past the top row
                                if j != 0:  # can't go past the left edge
                                    if state[i - 1][j - 1] == ' ':  # top left open
                                        deep = copy.deepcopy(state)  # on each good find need a copy
                                        deep[i - 1][j - 1] = self.opp  # set successor
                                        deep[i][j] = ' '  # reset square being moved away from
                                        move = [(i - 1, j - 1), (i, j)]
                                        moves.append(move)  # for game phase need where you goin and where you from
                                        succ.append(deep)
                                # center one just passes
                                if state[i - 1][j] == ' ':  # top center open
                                    deep = copy.deepcopy(state)  # on each good find need a copy
                                    deep[i - 1][j] = self.opp  # set successor
                                    deep[i][j] = ' '  # reset square being moved away from
                                    move = [(i - 1, j), (i, j)]
                                    moves.append(move)  # for game phase need where you goin and where you from
                                    succ.append(deep)
                                if j != 4:  # can't go past the right edge
                                    if state[i - 1][j + 1] == ' ':  # top right open
                                        deep = copy.deepcopy(state)  # on each good find need a copy
                                        deep[i - 1][j + 1] = self.opp  # set successor
                                        deep[i][j] = ' '  # reset square being moved away from
                                        move = [(i - 1, j + 1), (i, j)]
                                        moves.append(move)  # for game phase need where you goin and where you from
                                        succ.append(deep)

                            # center row
                            if j != 0:  # can't go past left edge
                                if state[i][j - 1] == ' ':  # center left open
                                    deep = copy.deepcopy(state)  # on each good find need a copy
                                    deep[i][j - 1] = self.opp  # set successor
                                    deep[i][j] = ' '  # reset square being moved away from
                                    move = [(i, j - 1), (i, j)]
                                    moves.append(move)  # for game phase need where you goin and where you from
                                    succ.append(deep)
                            if j != 4:  # can't go past right edge
                                if state[i][j + 1] == ' ':  # center right open
                                    deep = copy.deepcopy(state)  # on each good find need a copy
                                    deep[i][j + 1] = self.opp  # set successor
                                    deep[i][j] = ' '  # reset square being moved away from
                                    move = [(i, j + 1), (i, j)]
                                    moves.append(move)  # for game phase need where you goin and where you from
                                    succ.append(deep)

                            # bottom row
                            if i != 4:  # can't go past the bottom row
                                if j != 0:  # can't go past the left edge
                                    if state[i + 1][j - 1] == ' ':  # bottom left open
                                        deep = copy.deepcopy(state)  # on each good find need a copy
                                        deep[i + 1][j - 1] = self.opp  # set successor
                                        deep[i][j] = ' '  # reset square being moved away from
                                        move = [(i + 1, j - 1), (i, j)]
                                        moves.append(move)  # for game phase need where you goin and where you from
                                        succ.append(deep)
                                # center one just passes
                                if state[i + 1][j] == ' ':  # bottom center open
                                    deep = copy.deepcopy(state)  # on each good find need a copy
                                    deep[i + 1][j] = self.opp  # set successor
                                    move = [(i + 1, j), (i, j)]
                                    moves.append(move)  # for game phase need where you goin and where you from
                                    succ.append(deep)
                                if j != 4:  # can't go past the right edge
                                    if state[i + 1][j + 1] == ' ':  # bottom right open
                                        deep = copy.deepcopy(state)  # on each good find need a copy
                                        deep[i + 1][j + 1] = self.opp  # set successor
                                        deep[i][j] = ' '  # reset square being moved away from
                                        move = [(i + 1, j + 1), (i, j)]
                                        moves.append(move)  # for game phase need where you goin and where you from
                                        succ.append(deep)
        return succ, moves

    def max_pair(self, moves):
        set = list()
        setVal = 0
        for move in moves:
            val = self.game_value(move[2])  # is this state terminal?
            if val == 1:  # this state ends it
                return move  # just take the win right here
            else:  # -1 or any other number
                hur = self.heuristic_game_value(move[2])
                if hur > setVal:  # just take the best one
                    set = move
                    setVal = hur
        return set  # should have best nonterminal move in it

    def min_pair(self, moves):
        set = list()
        setVal = 10000
        for move in moves:
            val = self.game_value(move[2])  # is this state terminal?
            if val == -1:  # this state ends it
                return move  # just take the win right here
            else:  # 1 or any other number
                hur = self.heuristic_game_value(move[2])
                if hur < setVal:  # just take the best one
                    set = move
                    setVal = hur
        return set  # should have best nonterminal move in it

    # i'm not sure if I need to be returning state I dont think I ever use it
    def max_value(self, state, depth, AI):
        val = self.game_value(state)
        abs = list()
        united = list()
        if val != 0:  # terminal state
            return val, state
        elif depth == 3:  # start at 3 rounds of depth
            return self.heuristic_game_value(state), state
        elif AI:  # not terminal, not at depth limit, Max's turn
            succ, moves = self.successors(state, self.droptime(state), AI)
            m = 0
            for i in range(len(succ)):
                # take val/state pair and pit it in a list of (val, state) pairs
                # then you can find the max val and use the state tied to it to know the move later
                value, mState = self.max_value(succ[i], depth + 1, not AI)
                #abs.append(value)
                if value > m:  # new maximum value, this tracks max
                    abs = list()  # reset list
                    m = value  # set new m
                    abs.append((value, i, mState))  # store value with its location in succ
                elif value == m:  # add to possible bests
                    abs.append((value, i, mState))  # store value with its location in succ

                # united.append(mState)
            #m = max(abs)
            if len(abs) > 1:
                best = self.max_pair(abs)
            else:  # only one just take the only option
                best = abs[0]

            if depth == 0:  # on the first try the thing you want returned isn't the curr state but one down
                # WAS "return moves[abs.index(m)]"
                return moves[best[1]]  # maybe this lets me cheat the system and get the move back on the final hit
            else:  # most cases you want to send the best score with the move that got it
                return m, state
        else:  # not terminal, not at depth limit, Min's turn
            succ, moves = self.successors(state, self.droptime(state), AI)
            m = 1000000
            for i in range(len(succ)):
                # take val/state pair and pit it in a list of (val, state) pairs
                # then you can find the max val and use the state tied to it to know the move later
                value, mState = self.max_value(succ[i], depth + 1, not AI)
                #abs.append(value)
                if value < m:  # new maximum value, this tracks max
                    abs = list()  # reset list
                    m = value  # set new m
                    abs.append((value, i, mState))  # store value with its location in succ
                elif value == m:  # add to possible bests
                    abs.append((value, i, mState))  # store value with its location in succ

            if len(abs) > 1:
                best = self.min_pair(abs)
            else:  # only one just take the only option
                best = abs[0]
            #m = min(abs)
            if depth == 0:  # on the first try the thing you want returned isn't the curr state but one down
                return moves[best[1]]  # maybe this lets me cheat the system and get the move back on the final hit
            else:  # most cases you want to send the best score with the move that got it
                return m, state

    def make_move(self, state):
        """ Selects a (row, col) space for the next move. You may assume that whenever
        this function is called, it is this player's turn to move.

        Args:
            state (list of lists): should be the current state of the game as saved in
                this Teeko2Player object. Note that this is NOT assumed to be a copy of
                the game state and should NOT be modified within this method (use
                place_piece() instead). Any modifications (e.g. to generate successors)
                should be done on a deep copy of the state.

                In the "drop phase", the state will contain less than 8 elements which
                are not ' ' (a single space character).

        Return:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.

        Note that without drop phase behavior, the AI will just keep placing new markers
            and will eventually take over the board. This is not a valid strategy and
            will earn you no points.
        """

        # function does drop phase check and final return is the optimal move
        best_move = self.max_value(state, 0, True)
        # drop_phase = self.droptime(state)  # detect drop phase

        # PLACE PIECE already covers removing the piece in the move
        # if not drop_phase:  # this means continuous phase AFTER Drop Phase
            # TODO: choose a piece to move and remove it from the board
            # (You may move this condition anywhere, just be sure to handle it)
            #
            # Until this part is implemented and the move list is updated
            # accordingly, the AI will not follow the rules after the drop phase!
        #    pass

        # select an unoccupied space randomly
        # TODO: implement a minimax algorithm to play better

        # This is currently a section meant for the continuous page thta picks
        # a random unoccupied space to move a piece it never specifies ? This shit goofy
        # move = []
        # (row, col) = (random.randint(0, 4), random.randint(0, 4))
        # while not state[row][col] == ' ':
        #    (row, col) = (random.randint(0, 4), random.randint(0, 4))

        # ensure the destination (row,col) tuple is at the beginning of the move list
        # move.insert(0, best_move)
        return best_move

    def opponent_move(self, move):
        """ Validates the opponent's next move against the internal board representation.
        You don't need to touch this code.

        Args:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.
        """
        # validate input
        if len(move) > 1:
            source_row = move[1][0]
            source_col = move[1][1]
            if source_row != None and self.board[source_row][source_col] != self.opp:
                self.print_board()
                raise Exception("You don't have a piece there!")
            if abs(source_row - move[0][0]) > 1 or abs(source_col - move[0][1]) > 1:
                self.print_board()
                raise Exception('Illegal move: Can only move to an adjacent space')
        if self.board[move[0][0]][move[0][1]] != ' ':
            raise Exception("Illegal move detected")
        # make move
        self.place_piece(move, self.opp)

    def place_piece(self, move, piece):
        """ Modifies the board representation using the specified move and piece

        Args:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.

                This argument is assumed to have been validated before this method
                is called.
            piece (str): the piece ('b' or 'r') to place on the board
        """
        if len(move) > 1:
            self.board[move[1][0]][move[1][1]] = ' '
        self.board[move[0][0]][move[0][1]] = piece

    def print_board(self):
        """ Formatted printing for the board """
        for row in range(len(self.board)):
            line = str(row) + ": "
            for cell in self.board[row]:
                line += cell + " "
            print(line)
        print("   A B C D E")

    def game_value(self, state):
        """ Checks the current board status for a win condition

        Args:
        state (list of lists): either the current state of the game as saved in
            this Teeko2Player object, or a generated successor state.

        Returns:
            int: 1 if this Teeko2Player wins, -1 if the opponent wins, 0 if no winner

        complete checks for diagonal and 3x3 square corners wins
        """
        # check horizontal wins
        for row in state:
            for i in range(2):
                if row[i] != ' ' and row[i] == row[i + 1] == row[i + 2] == row[i + 3]:
                    return 1 if row[i] == self.my_piece else -1

        # check vertical wins
        for col in range(5):
            for i in range(2):
                if state[i][col] != ' ' and state[i][col] == state[i + 1][col] == state[i + 2][col] == state[i + 3][col]:
                    return 1 if state[i][col] == self.my_piece else -1

        # check \ diagonal wins
        for i in range(2):
            if state[i][i] != ' ' and state[i][i] == state[i + 1][i + 1] == state[i + 2][i + 2] == state[i + 3][i + 3]:
                return 1 if state[i][i] == self.my_piece else -1
        if state[0][1] != ' ' and state[0][1] == state[1][2] == state[2][3] == state[3][4]:
            return 1 if state[0][1] == self.my_piece else -1
        if state[1][0] != ' ' and state[1][0] == state[2][1] == state[3][2] == state[4][3]:
            return 1 if state[0][1] == self.my_piece else -1

        # check / diagonal wins
        for i in range(2):
            if state[i][4 - i] != ' ' and state[i][4 - i] == state[i + 1][4 - (i + 1)] == state[i + 2][4 - (i + 2)] == state[i + 3][4 - (i + 3)]:
                return 1 if state[i][4-i] == self.my_piece else -1
        if state[4][1] != ' ' and state[4][1] == state[3][2] == state[2][3] == state[1][4]:
            return 1 if state[0][1] == self.my_piece else -1
        if state[3][0] != ' ' and state[3][0] == state[2][1] == state[1][2] == state[0][3]:
            return 1 if state[0][1] == self.my_piece else -1

        # check 3x3 square corners wins
        # 3x3 corners can only start in first 3x3
        for i in range(3):
            for j in range(3):
                if state[i][j] != ' ':  # found potential square
                    if state[i+1][j+1] == ' ':  # center of square must be empty
                        # check the four corners
                        if state[i][j] == state[i+2][j] == state[i][j+2] == state[i+2][j+2]:
                            return 1 if state[i][j] == self.my_piece else -1

        return 0  # no winner yet

    def heuristic_game_value(self, state):
        # i think the best way is to count the number of open wins with 2+ pairs in them
        # you gotta go over all the win states again tho
        totalA = 0
        totalO = 0

        AInum = 0
        Oppnum = 0

        # check horizontal wins
        for row in state:
            for i in range(2):  # 2 win conditions per row
                for j in range(4):  # four nodes to look at per win condition
                    if row[i+j] == self.my_piece:
                        AInum = AInum + 1
                    elif row[i+j] == self.opp:
                        Oppnum = Oppnum + 1
                # after checking the win condition nodes have to do the real check
                # if conditions not met there's no wins here
                if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
                    if AInum == 2:  # one set of pairs with 2 nodes
                        totalA = totalA + 1
                    elif AInum == 3:  # 3 set of pairs with 3 nodes
                        totalA = totalA + 3
                elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
                    if Oppnum == 2:  # one set of pairs with 2 nodes
                        totalO = totalO + 1
                    elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                        totalO = totalO + 3
                # reset nums
                Oppnum = 0
                AInum = 0

        # check vertical wins
        for col in range(5):
            for i in range(2):
                for j in range(4):  # four nodes to look at per win condition
                    if state[j+i][col] == self.my_piece:
                        AInum = AInum + 1
                    elif state[j+i][col] == self.opp:
                        Oppnum = Oppnum + 1
                # after checking the win condition nodes have to do the real check
                # if conditions not met there's no wins here
                if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
                    if AInum == 2:  # one set of pairs with 2 nodes
                        totalA = totalA + 1
                    elif AInum == 3:  # 3 set of pairs with 3 nodes
                        totalA = totalA + 3
                elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
                    if Oppnum == 2:  # one set of pairs with 2 nodes
                         totalO = totalO + 1
                    elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                        totalO = totalO + 3
                # reset nums
                Oppnum = 0
                AInum = 0

        # check \ diagonal wins
        for i in range(2):  # center wins
            for j in range(4):
                if state[i+j][i+j] == self.my_piece:
                    AInum = AInum + 1
                elif state[i+j][i+j] == self.opp:
                    Oppnum = Oppnum + 1
                    # after checking the win condition nodes have to do the real check
                    # if conditions not met there's no wins here
            if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
                if AInum == 2:  # one set of pairs with 2 nodes
                    totalA = totalA + 1
                elif AInum == 3:  # 3 set of pairs with 3 nodes
                    totalA = totalA + 3
            elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
                if Oppnum == 2:  # one set of pairs with 2 nodes
                    totalO = totalO + 1
                elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                    totalO = totalO + 3
            # reset nums
            Oppnum = 0
            AInum = 0
        # above center win
        for i in range(4):
            if state[i][i + 1] == self.my_piece:
                AInum = AInum + 1
            elif state[i][i + 1] == self.opp:
                Oppnum = Oppnum + 1
        if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
            if AInum == 2:  # one set of pairs with 2 nodes
                totalA = totalA + 1
            elif AInum == 3:  # 3 set of pairs with 3 nodes
                totalA = totalA + 3
        elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
            if Oppnum == 2:  # one set of pairs with 2 nodes
                totalO = totalO + 1
            elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                totalO = totalO + 3
        # reset nums
        Oppnum = 0
        AInum = 0
        for i in range(4):
            if state[i + 1][i] == self.my_piece:
                AInum = AInum + 1
            elif state[i + 1][i] == self.opp:
                Oppnum = Oppnum + 1
        if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
            if AInum == 2:  # one set of pairs with 2 nodes
                totalA = totalA + 1
            elif AInum == 3:  # 3 set of pairs with 3 nodes
                totalA = totalA + 3
        elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
            if Oppnum == 2:  # one set of pairs with 2 nodes
                totalO = totalO + 1
            elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                totalO = totalO + 3
        # reset nums
        Oppnum = 0
        AInum = 0

        # check / diagonal wins
        for i in range(2):  # center wins
            for j in range(4):
                if state[i + j][4 - (i + j)] == self.my_piece:
                    AInum = AInum + 1
                elif state[i + j][4 - (i + j)] == self.opp:
                    Oppnum = Oppnum + 1
                    # after checking the win condition nodes have to do the real check
                    # if conditions not met there's no wins here
            if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
                if AInum == 2:  # one set of pairs with 2 nodes
                    totalA = totalA + 1
                elif AInum == 3:  # 3 set of pairs with 3 nodes
                    totalA = totalA + 3
            elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
                if Oppnum == 2:  # one set of pairs with 2 nodes
                    totalO = totalO + 1
                elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                    totalO = totalO + 3
            # reset nums
            Oppnum = 0
            AInum = 0
        # above center win
        for i in range(4):
            if state[i][3 - i] == self.my_piece:
                AInum = AInum + 1
            elif state[i][3 - i] == self.opp:
                Oppnum = Oppnum + 1
        if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
            if AInum == 2:  # one set of pairs with 2 nodes
                totalA = totalA + 1
            elif AInum == 3:  # 3 set of pairs with 3 nodes
                totalA = totalA + 3
        elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
            if Oppnum == 2:  # one set of pairs with 2 nodes
                totalO = totalO + 1
            elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                totalO = totalO + 3
        # reset nums
        Oppnum = 0
        AInum = 0
        for i in range(4):
            if state[i + 1][4 - i] == self.my_piece:
                AInum = AInum + 1
            elif state[i + 1][4 - i] == self.opp:
                Oppnum = Oppnum + 1
        if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
            if AInum == 2:  # one set of pairs with 2 nodes
                totalA = totalA + 1
            elif AInum == 3:  # 3 set of pairs with 3 nodes
                totalA = totalA + 3
        elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
            if Oppnum == 2:  # one set of pairs with 2 nodes
                totalO = totalO + 1
            elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                totalO = totalO + 3
        # reset nums
        Oppnum = 0
        AInum = 0

        # check 3x3 square corners wins
        # 3x3 corners can only start in first 3x3
        # check the 5 squares for each win condition, 5 must be combo of ' ' and ONE players pieces
        for i in range(3):
            for j in range(3):
                if state[i][j] == self.my_piece:  # top left piece
                    AInum = AInum + 1
                elif state[i][j] == self.opp:
                    Oppnum = Oppnum + 1
                if state[i+2][j] == self.my_piece:  # bottom left piece
                    AInum = AInum + 1
                elif state[i+2][j] == self.opp:
                    Oppnum = Oppnum + 1
                if state[i][j+2] == self.my_piece:  # top right piece
                    AInum = AInum + 1
                elif state[i][j+2] == self.opp:
                    Oppnum = Oppnum + 1
                if state[i+2][j+2] == self.my_piece:  # bottom right piece
                    AInum = AInum + 1
                elif state[i+2][j+2] == self.opp:
                    Oppnum = Oppnum + 1
                if state[i+1][j+1] == self.my_piece:  # center piece
                    AInum = AInum + 1
                elif state[i+1][j+1] == self.opp:
                    Oppnum = Oppnum + 1
            if AInum > 1 and Oppnum == 0:  # AI's but no opponent nodes!
                if AInum == 2:  # one set of pairs with 2 nodes
                    totalA = totalA + 1
                elif AInum == 3:  # 3 set of pairs with 3 nodes
                    totalA = totalA + 3
            elif AInum == 0 and Oppnum > 1:  # Opponent nodes but no AI nodes
                if Oppnum == 2:  # one set of pairs with 2 nodes
                    totalO = totalO + 1
                elif Oppnum == 3:  # 3 set of pairs with 3 nodes
                    totalO = totalO + 3
            # reset nums
            Oppnum = 0
            AInum = 0

        # check which is doing better Opp or AI
        # 20 is mostly a guess rn the best I've ever gotten in test is 18 so 20 is a safety
        if totalA >= totalO:  # AI has more close win conditions
            return float(totalA/20)
        else:  # Opp has more close win conditions
            return float(totalO/20)*(-1)


############################################################################
#
# THE FOLLOWING CODE IS FOR SAMPLE GAMEPLAY ONLY
#
############################################################################
def main():
    print('Hello, this is Samaritan')
    ai = Teeko2Player()
    piece_count = 0
    turn = 0

    # drop phase
    while piece_count < 8 and ai.game_value(ai.board) == 0:

        # get the player or AI's move
        if ai.my_piece == ai.pieces[turn]:
            ai.print_board()
            move = ai.make_move(ai.board)
            ai.place_piece(move, ai.my_piece)
            print(ai.my_piece + " moved at " + chr(move[0][1] + ord("A")) + str(move[0][0]))
        else:
            move_made = False
            ai.print_board()
            print(ai.opp + "'s turn")
            while not move_made:
                player_move = input("Move (e.g. B3): ")
                while player_move[0] not in "ABCDE" or player_move[1] not in "01234":
                    player_move = input("Move (e.g. B3): ")
                try:
                    ai.opponent_move([(int(player_move[1]), ord(player_move[0]) - ord("A"))])
                    move_made = True
                except Exception as e:
                    print(e)

        # update the game variables
        piece_count += 1
        turn += 1
        turn %= 2

    # move phase - can't have a winner until all 8 pieces are on the board
    while ai.game_value(ai.board) == 0:

        # get the player or AI's move
        if ai.my_piece == ai.pieces[turn]:
            ai.print_board()
            move = ai.make_move(ai.board)
            ai.place_piece(move, ai.my_piece)
            print(ai.my_piece + " moved from " + chr(move[1][1] + ord("A")) + str(move[1][0]))
            print("  to " + chr(move[0][1] + ord("A")) + str(move[0][0]))
        else:
            move_made = False
            ai.print_board()
            print(ai.opp + "'s turn")
            while not move_made:
                move_from = input("Move from (e.g. B3): ")
                while move_from[0] not in "ABCDE" or move_from[1] not in "01234":
                    move_from = input("Move from (e.g. B3): ")
                move_to = input("Move to (e.g. B3): ")
                while move_to[0] not in "ABCDE" or move_to[1] not in "01234":
                    move_to = input("Move to (e.g. B3): ")
                try:
                    ai.opponent_move([(int(move_to[1]), ord(move_to[0]) - ord("A")),
                                      (int(move_from[1]), ord(move_from[0]) - ord("A"))])
                    move_made = True
                except Exception as e:
                    print(e)

        # update the game variables
        turn += 1
        turn %= 2

    ai.print_board()
    if ai.game_value(ai.board) == 1:
        print("AI wins! Game over.")
    else:
        print("You win! Game over.")


if __name__ == "__main__":
    main()
