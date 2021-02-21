import rich
import sys


class ErrorMessages:

    def __init__(self, console: rich.console.Console):
        self.console = console
        self.switcher = {
            200: self.__statusOK,
            404: self.__notFound
        }

    def __statusOK(self):
        return

    def __notFound(self):
        self.console.print(
            "[bold]404 Not Found:[/bold] [i]oops![/i] we're not able to find the information you wanted",
            style="red")
        sys.exit(2)
