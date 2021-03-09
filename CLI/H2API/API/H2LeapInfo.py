import getopt

import requests

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils


class H2LeapInfo(ApiEndpoint):

    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/leap"

        self.switcher["state"] = self.getLeapState
        self.switcher["view"] = self.getLeapView

        self.options["view"] = "-o [green]<outputFileName>[/green]: Select outputFilename\n-h : Possible options"

        self.execAdaptedFunction(args[1:])

    def getLeapState(self, args):
        r = requests.get(self.endpoint + "/state")
        self.utils.checkStatusCode(r)

        if r.text.lower() == "true":
            self.utils.console.print("Your leap motion is [bold]Connected[/bold]", style="green")
        else:
            self.utils.console.print("Your leap motion is [bold]Not Connected[/bold]", style="orange3")

    def getLeapView(self, args):
        r = requests.get(self.endpoint + "/view")
        self.utils.checkStatusCode(r)

        try:
            opts, _ = getopt.getopt(args[1:], "ho:")
            if dict(opts).get("-h") is not None:
                self.printFunctionOptions("view")
                return

            output = dict(opts).get("-o", "../LeapOutput") + ".png"

            with open(output, 'wb') as f:
                for chunk in r.iter_content(1024):
                    f.write(chunk)
            self.utils.console.print("File saved to [bold cyan]" + output + "[/bold cyan]")
        except getopt.GetoptError as err:
            print(err)
        except Exception as err:
            print(err)
            self.utils.console.print("An error occurred while saving the file", style="red")

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "Leap's Informations help"
        helpTable.add_row("state", "Is a Leap motion currently connected", self.getOption("state"))
        helpTable.add_row("view", "Save the current visual return of the Leap camera", self.getOption("view"))
        helpTable.add_row("help", "display help", self.getOption("help"))

        self.utils.console.print(helpTable)
        return
