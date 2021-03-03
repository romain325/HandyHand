import getopt
import sys
import time
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

    def timerCLI(self):
        for remaining in range(3, 0, -1):
            sys.stdout.write("\r")
            sys.stdout.write("Wait: {:2d}s".format(remaining))
            sys.stdout.flush()
            time.sleep(1)
        sys.stdout.flush()
        self.console.print(f"\nDon't Move !", style="cyan")
        return

    def createGestureFromArgs(self, args):
        gesture = {
            "oldId": "",
            "name": "",
            "description": "",
            "distance": False,
            "double": False
        }

        if len(args) <= 1:
            gesture["name"] = input("Gesture name: ")
            gesture["description"] = input("Description: ")
            gesture["double"] = "y" in input("Is Double Handed(y/n): ")
            gesture["distance"] = "y" in input("Does distance matter?(y/n): ")
        else:
            try:
                opts, _ = getopt.getopt(args[1:], "", ["name=", "desc=", "double", "distance"])
                gesture["name"] = dict(opts).get("--name")
                gesture["description"] = dict(opts).get("--desc")
                gesture["double"] = "--double" in dict(opts)
                gesture["distance"] = "--distance" in dict(opts)
            except getopt.GetoptError as err:
                print(err)
            except Exception as err:
                print(err)
        return gesture