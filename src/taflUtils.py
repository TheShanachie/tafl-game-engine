import itertools, uuid, re
from typing import (Optional, 
                    Tuple, 
                    Self)
from pathlib import Path

class UniqueID:
    _class_counters = {}

    def __init__(self):
        cls = self.__class__
        if cls not in UniqueID._class_counters:
            UniqueID._class_counters[cls] = uuid.uuid4()
        self.id = UniqueID._class_counters[cls]
        UniqueID._class_counters[cls] = uuid.uuid4()

    def __repr__(self):
        return f"{self.__class__.__name__}(id={self.id})"
    
    @property
    def str_id(self):
        return str(self.id)


class TaflGameObject(UniqueID):
    counter = itertools.count()
    def __init__(self):
        super().__init__()
    

class TaflBoard(TaflGameObject):
    def __init__(self, rows: int, columns: int):
        super().__init__()
        self.rows = rows
        self.columns = columns
    
class TaflGamePiece(TaflGameObject):
    def __init__(self, row: Optional[int] = None, column: Optional[int] = None):
        super().__init__()
        self.row = row
        self.column = column
        
    def distance(self, other: Self) -> Tuple[int, int]:
        horizontal =  min(other.row, other.row) - max(self.row, other.row)
        vertical = min(self.column, other.column) - max(self.column, other.column)
        return horizontal, vertical
    
    def update(self, row: int, column: int):
        self.row = row
        self.column = column
        
class TaflIO():
    def __init__(self, output_path: Path = None, input_path: Path = None):
        self.output_path = output_path
        self.input_path = input_path
    
    def write_output(self, message: str):
        print(message)
    
    def read_input(self, prompt: str, restrict: str | None = None) -> str | None:
        try:
            if restrict is not None:
                pattern = re.compile(restrict)
            in_ = input(prompt)
            if restrict is None or re.fullmatch(pattern, in_) is not None:
                return in_
            return None
        except Exception:
            return None
        
    def flush(self):
        print(end='', flush=True)