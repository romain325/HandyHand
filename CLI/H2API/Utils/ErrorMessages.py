import rich
import sys

from requests import Response


class ErrorMessages:

    def __init__(self, console: rich.console.Console):
        self.console = console
        self.switcher = {
            200: self.__statusOK,
            400: self.__badRequest,
            404: self.__notFound,
            500: self.__internalServerError
        }

    def __statusOK(self, res):
        return

    def __notFound(self, res):
        self.console.print(
            "[bold]404 Not Found:[/bold] [i]oops![/i] we're not able to find the information you wanted",
            style="red")
        sys.exit(2)

    def __internalServerError(self, res):
        self.console.print(
            "[bold]500 Internal Server Error:[/bold] [i]oops![/i] Outch what when wrong?",
            style="red")
        sys.exit(2)

    def __badRequest(self, res: Response):
        self.console.print(
            f"[bold]400 Bad Request:[/bold] [i]oops![/i] The request has failed\n\t"
            f"[i]{res.json()['message']}[/i]",
            style="red")
        sys.exit(2)
