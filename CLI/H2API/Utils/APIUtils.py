import sys
from typing import List

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

    def getConnectionHeader(self, args: List[tuple]):
        filePath = dict(args).get("-t")
        if filePath is None:
            filePath = "../user.token"
        headers = {}
        try:
            with open(filePath, "r") as f:
                headers["Authorization"] = f.read()
            return headers
        except Exception as err:
            self.console.print(f"No connection token found in the file [bold cyan]{filePath}[/bold cyan]", style="red")
            sys.exit(2)

    def getStandartHelpTable(self):
        helpTable = Table()
        helpTable.add_column("Parameter", style="magenta")
        helpTable.add_column("Description")
        helpTable.add_column("Options", style="cyan")

        return helpTable
