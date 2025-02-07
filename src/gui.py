from textual.app import App, ComposeResult
from textual.containers import ScrollableContainer
from textual.widgets import Button, Footer, Header, Static
from hnefatafl import HnefataflGame as G

class HnefataflGame(Static):
    """ """

class Hnefatafl(Static):
    def compose(self) -> ComposeResult:
        g = G()
        print(g.empty_hnefatafl_board())
        yield HnefataflGame(g.empty_hnefatafl_board())

class GameGUI(App):
    """A Textual app to manage stopwatches."""

    BINDINGS = [
        ("d", "toggle_dark", "Toggle dark mode"),
        ("a", "add_game", "Add"),
        ("r", "remove_game", "Remove"),
    ]

    def compose(self) -> ComposeResult:
        """Create child widgets for the app."""
        yield Header()
        yield Footer()
        yield ScrollableContainer(Hnefatafl(), id="games")

    def action_toggle_dark(self) -> None:
        """An action to toggle dark mode."""
        self.dark = not self.dark
        
    def action_add_game(self) -> None:
        """An action to add a game."""
        new_game = Hnefatafl()
        self.query_one("#games").mount(new_game)
        new_game.scroll_visible()

    def action_remove_game(self) -> None:
        """Called to remove a game."""
        games = self.query("Hnefatafl")
        if games:
            games.last().remove()


if __name__ == "__main__":
    app = GameGUI()
    app.run()