from taflUtils import TaflBoard, TaflGamePiece, UniqueID, TaflIO
from enum import Enum
from typing import Optional, Tuple
from pathlib import Path
import os, re


class HnefataflBoardState(Enum):
    EMPTY = 0
    START = 1
    GAMEOVER = 2


class HnefataflPieceType(Enum):
    KING = 0
    PAWN = 1


class HnefataflPieceColor(Enum):
    BLACK = 0
    WHITE = 1


class HnefataflGamePiece(TaflGamePiece):
    def __init__(
        self,
        pcolor: HnefataflPieceColor,
        ptype: HnefataflPieceType,
        row: Optional[int] = None,
        column: Optional[int] = None,
    ):
        super().__init__(row, column)
        self.color = pcolor
        self.type = ptype

    def __str__(self):
        if self.type == HnefataflPieceType.KING:
            if self.color == HnefataflPieceColor.BLACK:
                return "\u2B21"
            if self.color == HnefataflPieceColor.WHITE:
                return "\u2B22"
        elif self.type == HnefataflPieceType.PAWN:
            if self.color == HnefataflPieceColor.BLACK:
                return "\u25B3"
            if self.color == HnefataflPieceColor.WHITE:
                return "\u25B2"
        else:
            return "?"
        

class HnefataflGame(TaflBoard, UniqueID):            
    def __init__(self):
        super().__init__(11, 11)
        self.board = [[None for _ in range(self.columns)] for _ in range(self.rows)]
        self.black_pieces = []
        self.white_pieces = []
        self.king = None
        self.state = HnefataflBoardState.EMPTY
        self.turn = None
        
    def get_state(self) -> Tuple[HnefataflBoardState, HnefataflPieceColor | None]:
        return self.state, self.turn
    
    def is_current_piece(self, row: int, column: int) -> bool:            
        loc = self.board[row][column]
        if loc is not None:
            return (loc.pcolor == self.turn)
        else:
            return False
        
    def place_game_piece(self, piece: HnefataflGamePiece, row: int, column: int):
        piece.row = row
        piece.column = column
        self.board[row][column] = piece
        return piece

    def remove_game_piece(self, row: int, column: int) -> HnefataflGamePiece | None:
        previous = self.board[row][column]
        self.board[row][column] = None
        return previous
    
    def init_board_start(self):
        # Create the pieces
        black_pawns = []
        white_pawns = []

        # Functions to shorten code
        def place_black_pawn(pawns: list, row: int, column: int):
            pawns.append(
                self.place_game_piece(
                    HnefataflGamePiece(
                        HnefataflPieceColor.BLACK, HnefataflPieceType.PAWN
                    ),
                    row=row,
                    column=column,
                )
            )

        def place_white_pawn(pawns: list, row: int, column: int):
            pawns.append(
                self.place_game_piece(
                    HnefataflGamePiece(
                        HnefataflPieceColor.WHITE, HnefataflPieceType.PAWN
                    ),
                    row=row,
                    column=column,
                )
            )

        # Place the black pieces
        for value in range(3, 8):
            place_black_pawn(black_pawns, row=0, column=value)
            place_black_pawn(black_pawns, row=self.rows - 1, column=value)
            place_black_pawn(black_pawns, row=value, column=0)
            place_black_pawn(black_pawns, row=value, column=self.columns - 1)
        place_black_pawn(black_pawns, row=1, column=5)
        place_black_pawn(black_pawns, row=self.rows - 2, column=5)
        place_black_pawn(black_pawns, row=5, column=1)
        place_black_pawn(black_pawns, row=5, column=self.columns - 2)

        # Place the white pieces
        for value in [3, 4, 6, 7]:
            place_white_pawn(white_pawns, row=value, column=5)
            place_white_pawn(white_pawns, row=5, column=value)
        place_white_pawn(white_pawns, row=4, column=4)
        place_white_pawn(white_pawns, row=4, column=6)
        place_white_pawn(white_pawns, row=6, column=4)
        place_white_pawn(white_pawns, row=6, column=6)

        # Place the white king
        white_king = self.place_game_piece(
            HnefataflGamePiece(HnefataflPieceColor.WHITE, HnefataflPieceType.KING),
            row=5,
            column=5,
        )
        white_pawns.append(white_king)

        # Overwrite with these new member variables.
        self.black_pieces = black_pawns
        self.white_pieces = white_pawns
        self.king = white_king
        self.state = HnefataflBoardState.START
        self.turn = HnefataflPieceColor.BLACK

    def stringify(self):
        board = [
            "+++-----------------+++",
            "+++-----------------+++",
            "+---------------------+",
            "+---------------------+",
            "+---------------------+",
            "+---------+++---------+",
            "+---------+++---------+",
            "+---------------------+",
            "+---------------------+",
            "+---------------------+",
            "+++-----------------+++",
            "+++-----------------+++",
        ]
        for r in range(0, 11):
            row = self.board[r]
            rowvals = [p.__str__() if p is not None else " " for p in row]
            board.insert((2 * (r + 1)) - 1, "|" + "|".join(rowvals) + "|")
        return "\n".join(board)

    def move(
        self, old_location: Tuple[int, int], new_location: Tuple[int, int]
    ):
        pass

    def valid_position_change(
        self, old_location: Tuple[int, int], new_location: Tuple[int, int]
    ) -> bool:
        if (
            old_location[0] == new_location[0] and old_location[1] == new_location[1]
        ):  # Not the same position.
            return False
        if (
            old_location[0] != new_location[0] and old_location[1] != new_location[1]
        ):  # Not a straight line.
            return False
        if old_location[0] != new_location[0]:  # Move along row.
            axis = 0
        else:  # Move along column.
            axis = 1
        minimum = min(old_location[axis], new_location[axis])
        maximum = max(old_location[axis], new_location[axis])
        for i in range(minimum + 1, maximum):
            loc = old_location
            loc[axis] = i
            if self.board[loc[0]][loc[1]] is not None:
                return False
        return True

    def is_valid_location(self, location: Tuple[int, int]) -> bool:
        return (location[0] <= 10 and location[0] >= 0) and (
            location[1] <= 10 and location[1] >= 0
        )

    def is_pawn(self, location: Tuple[int, int], color: HnefataflPieceColor) -> bool:
        if self.is_valid_location(location):
            value = self.board[location[0]][location[1]]
            if value is not None:
                return value.type == HnefataflPieceType.PAWN and value.color == color
        return False

    def is_king(
        self,
        location: Tuple[int, int],
        color: Optional[HnefataflPieceColor] = HnefataflPieceColor.WHITE,
    ) -> bool:
        if self.is_valid_location(location):
            value = self.board[location[0]][location[1]]
            if value is not None:
                return value.type == HnefataflPieceType.KING and value.color == color
        return False

    def is_corner(self, location: Tuple[int, int]):
        return (
            (location[0] == 0 and location[1] == 0)
            or (location[0] == 0 and location[1] == 10)
            or (location[0] == 10 and location[1] == 0)
            or (location[0] == 10 and location[1] == 10)
        )

    def check_white_wins(self) -> bool:
        king = self.king
        return self.is_corner_position((king.row, king.column))

    def check_black_wins(self, location: Tuple[int, int]) -> bool:
        # TODO: finish
        pass

    def check_capture(self, location: Tuple[int, int]) -> list:
        captured = []
        board = self.board
        color = board[location[0]][location[1]]

        def is_capture(
            self, mid_location: Tuple[int, int], end_location: Tuple[int, int]
        ):
            if self.is_pawn(end_location, color) or self.is_corner(end_location):
                if self.is_pawn(mid_location, HnefataflPieceColor(not color.value)):
                    return True
            return False

        if is_capture((location[0] + 1, location[1]), (location[0] + 2, location[1])):
            captured += [board[location[0] + 1][location[1]]]

        if is_capture((location[0] - 1, location[1]), (location[0] - 2, location[1])):
            captured += [board[location[0] - 1][location[1]]]

        if is_capture((location[0], location[1] + 1), (location[0], location[1] + 2)):
            captured += [board[location[0]][location[1] + 1]]

        if is_capture((location[0], location[1] - 1), (location[0], location[1] - 2)):
            captured += [board[location[0]][location[1] - 1]]

        return captured


class TaflTUI(UniqueID):
    def __init__(self, output_path: Path = None, input_path: Path = None):
        super().__init__()
        self.io = TaflIO(output_path, input_path)
        self.position_patter = re.compile(r"\d:\d")
        self.game = None

    def read_position(self) -> Tuple[int, int]:
        pattern = r"\d:\d"
        prompt = "Enter board location [<row>:<column>]: "
        in_ = self.io.read_input(prompt, pattern)
        while in_ is None:
            self.io.write_output("The input was invalid.")
            self.io.read_input(prompt, pattern)
        return in_.split(":")

    def read_command(self, prompt: str | None = None) -> str:
        if prompt is None:
            prompt = ">> "
        in_ = self.io.read_input(prompt)
        while in_ is None:
            self.io.write_output("There was an error registering this command.")
            self.io.read_input(prompt)
        return in_.lower()

    def write_help(self):
        help_message = "HELP MESSAGE"
        self.io.write_output(help_message)
        self.io.flush()

    def write_rules(self):
        rule_message = "RULE MESSAGE"
        self.io.write_output(rule_message)
        self.io.flush()

    def write_board(self):
        if self.game is not None:
            board_message = self.game.stringify()
        else:
            board_message = "NO BOARD MESSAGE"
        self.io.write_output(board_message)
        self.io.flush()

    def write_cmd_err(self):
        command_error = "The input provided is invalid. Quit: [q], Help [h]"
        self.io.write_output(command_error)
        self.io.flush()

    def new_game(self):
        if self.game is None:
            self.game = HnefataflGame()
        else:
            message = "There is a game in play. Would you like to restart? [Y/n]: "
            while True:
                in_ = self.read_command(message)
                match in_:
                    case "y" | "yes":
                        self.game = HnefataflGame()
                        return
                    case "n" | "no":
                        return
                    case _:
                        command_error = "The input provided is invalid."
                        self.io.write_output(command_error)
                        self.io.flush()
                        
    def move(self):
        if self.game is None:
            command_error = "There is no game in play. For a New Game [ng]."
            self.io.write_output(command_error)
            self.io.flush()
            return
        else:
            self.io.write_output("Select a piece to move.")
            in_ = self.read_position()
            # Validate
            if self.game.
            
            self.io.write_output("Select location to move.")
            in_ = self.read_position()
            # Validate

    def run(self):
        while True:
            in_ = self.read_command()
            match in_:
                case "quit" | "q":
                    return
                case "newgame" | "ng":
                    self.new_game()
                case "help" | "h":
                    self.write_help()
                case "rules" | "r":
                    self.write_rules()
                case "board" | "b":
                    self.write_board()
                case "turn" | "t":
                    pass
                case "move" | "m":
                    pass
                case _:
                    self.write_cmd_err()
