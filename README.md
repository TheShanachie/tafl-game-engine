# Tafl Game Engine

A comprehensive Python implementation of Norse Tafl games, specifically focusing on Hnefatafl, with support for both terminal-based and graphical user interfaces. This project serves as a foundation for building game agents and facilitating machine learning research in the domain of ancient board games.

`Note`:  **The key features of this project have been made private.**

## About Tafl Games

Tafl games are a family of ancient Nordic board games, with Hnefatafl being the most well-known variant. These asymmetric strategy games date back to the Viking Age and represent the eternal struggle between a small defensive force and overwhelming attackers. The games were popular throughout medieval Scandinavia and provide fascinating insights into both game theory and historical culture.

This implementation aims to preserve the traditional gameplay while providing a modern platform for both casual play and serious game research, including machine learning applications and agent-based exploration of game strategies.

## Overview

The Tafl Game Engine provides:
- **Complete Hnefatafl Implementation**: Full game logic with piece movement, capture mechanics, and win conditions
- **Multiple Interfaces**: Both Text-based UI (TUI) and Graphical UI (GUI) support using Textual
- **Extensible Architecture**: Object-oriented design allowing for easy addition of other Tafl variants
- **Game State Management**: Comprehensive board state tracking and validation
- **Configuration Support**: JSON-based game configuration system

### Game Features

#### Hnefatafl Implementation
- **11x11 Board**: Traditional Hnefatafl board configuration
- **Piece Types**: King and Pawn pieces with distinct behaviors
- **Team Colors**: Black (attackers) and White (defenders) with proper turn management
- **Movement Validation**: Straight-line movement with path obstruction checking
- **Capture Mechanics**: Sandwich capture system with special corner rules
- **Win Conditions**: King escape to corners (White wins) and king capture (Black wins)

#### User Interfaces
- **TUI Commands**: Interactive command system (`quit`, `newgame`, `help`, `rules`, `board`, `move`)
- **GUI Features**: Dark mode toggle, multiple game support, scrollable interface
- **Input Validation**: Robust position parsing and command validation
- **Visual Representation**: Unicode symbols for different piece types and colors

## Quick Start

### Terminal Interface
```bash
python src/tui.py
```

### Graphical Interface
```bash
python src/gui.py
```

### Game Commands (TUI)
- `ng` or `newgame` - Start a new game
- `b` or `board` - Display current board state
- `m` or `move` - Make a move (prompts for positions)
- `h` or `help` - Show help information
- `r` or `rules` - Display game rules
- `q` or `quit` - Exit the game

## Gameplay

### Objective
- **White (Defenders)**: Protect the King and help him escape to any corner of the board
- **Black (Attackers)**: Surround and capture the King before he can escape

## Current Status

- **Implemented Features:**: *The key features of this project have been made private.*
- **In Development:**: *The key features of this project have been made private.*

## License

Any public content is licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for full details.