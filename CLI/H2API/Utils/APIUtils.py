from requests import Response
import rich.console
from rich.table import Table

from .ErrorMessages import ErrorMessages


class APIUtils:

    def __init__(self, endpoint):
        self.endpoint = endpoint
        self.console = rich.console.Console()

    def checkStatusCode(self, res: Response):
        ErrorMessages(self.console).switcher.get(res.status_code)(res)

    def usageMessage(self):
        self.console.print("usage")

    def getStandartHelpTable(self):
        helpTable = Table()
        helpTable.add_column("Parameter", style="magenta")
        helpTable.add_column("Description")
        helpTable.add_column("Options", style="cyan")

        return helpTable
