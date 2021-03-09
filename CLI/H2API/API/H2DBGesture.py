import getopt
import json
import sys

import magic
import requests

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
from ..Utils.Display.DataDisplay import getGesturePanel, getGestureTable


class H2DBGesture(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/gestureDB"

        self.switcher["info"] = self.getGestures
        self.options["info"] = "-a: See All\n" \
                               "-t <path to token>: Specify a particular connection token\n" \
                               "-i <id>: get an Individual gesture by ID\n" \
                               "-d : Debug Mode-> View data as JSON\n" \
                               "-o <outputFileName>: save json data to a given file"

        self.switcher["delete"] = self.deleteGesture
        self.options["delete"] = f"<gesture id>: Id of the gesture you want to delete\n" \
                                 f"-t <path to token>: Specify a particular connection token\n" \
                                 f"\t[i]Example: HandyHand db_gesture delete dGV0X2pvaG5ueQ==[/i]"

        self.switcher["add"] = self.addGesture
        self.options["add"] = f"No args: prompt info\n" \
                              f"-t <path to token>: Specify a particular connection token\n" \
                              f"--name <FilePath>: file path of the gesture\n" \
                              f"--desc <Description>: gesture description\n" \
                              f"--double: is double handed\n" \
                              f"--distance: does the distance between the hands matter"

        self.switcher["modify"] = self.modifyGesture
        self.options["modify"] = f"<gesture id>: Gesture ID you want to modify\n" \
                                 f"-t <path to token>: Specify a particular connection token\n" \
                                 f"--name <FilePath>: file path of the gesture\n" \
                                 f"--desc <Description>: gesture description\n" \
                                 f"--double: is double handed\n" \
                                 f"--distance: does the distance between the hands matter"

        self.execAdaptedFunction(args[1:])

    def getGestures(self, args):
        try:
            opts, _ = getopt.getopt(args[1:], "hado:i:t:")
            if dict(opts).get("-h") is not None:
                self.printFunctionOptions("info")
                return

            isDebug = dict(opts).get("-d") is not None
            data = ""
            if dict(opts).get("-i") is not None:
                data = self.getOneGesture(dict(opts)["-i"], isDebug, opts)
            else:
                data = self.getAllGesture(isDebug, opts)

            outFile = dict(opts).get("-o")
            if outFile is not None:
                with open(outFile, "w") as f:
                    f.write(str(data))
                self.utils.console.print(f"Data successfully written to [bold cyan]{outFile}[/bold cyan]")
        except getopt.GetoptError as err:
            print(err)
        except Exception as err:
            print(err)

    def getAllGesture(self, debug: bool, args):
        r = requests.get(self.endpoint + "/all", headers=self.utils.getConnectionHeader(args))

        self.utils.checkStatusCode(r)
        self.utils.console.print(r.json() if debug else getGestureTable(r.json()))
        return r.json()

    def getOneGesture(self, id: str, debug: bool, args):
        r = requests.get(self.endpoint + "/" + id, headers=self.utils.getConnectionHeader(args))
        self.utils.checkStatusCode(r)
        if r.content == b"":
            self.utils.console.print(f"No Gesture found with the id [bold cyan]{id}[/bold cyan]", style="red")
            sys.exit(2)

        self.utils.console.print(r.json() if debug else getGesturePanel(r.json()))
        return r.json()

    def deleteGesture(self, args):
        if len(args) <= 1 or args[1] == "-h":
            self.printFunctionOptions("delete")
            return
        r = requests.delete(self.endpoint + "/" + args[1],
                            headers=self.utils.getConnectionHeader(getopt.getopt(args[2:], "ht:")[0]))
        self.utils.checkStatusCode(r)
        self.utils.console.print(f"The Gesture {args[1]} has been deleted with success")


    def addGesture(self, args: list):
        if "-h" in args:
            self.printFunctionOptions("add")
            return

        gesture = self.utils.createGestureFromArgs(args)
        opts, _ = getopt.getopt(args[1:], "ht:")
        self.utils.timerCLI()

        r = requests.post(self.endpoint + "/add", data=json.dumps(gesture), headers=self.utils.getConnectionHeader(opts))
        self.utils.checkStatusCode(r)

        self.utils.console.print(f"Gesture successfully added with the id [bold green]{r.text}[/bold green]",
                                 style="cyan")

    def modifyGesture(self, args: list):
        if "-h" in args:
            self.printFunctionOptions("modify")
            return

        if len(args) <= 1:
            self.utils.console.print(f"The id of the script which will be modified is needed", style="orange3")
            return

        gesture = self.utils.createGestureFromArgs(args)
        gesture["oldId"] = args[1]

        opts, _ = getopt.getopt(args[2:], "ht:", ["name=", "desc=", "double", "distance"])

        self.utils.timerCLI()

        r = requests.post(self.endpoint + "/modify", data=json.dumps(gesture),
                          headers=self.utils.getConnectionHeader(opts))
        self.utils.checkStatusCode(r)

        self.utils.console.print(f"Script successfully modified,his new id is [bold green]{r.text}[/bold green]",
                                 style="cyan")


    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "DB gesture help"
        helpTable.add_row("info", "Get informations about all or one gesture", self.getOption("info"))
        helpTable.add_row("delete", "Delete a gesture from a given id", self.getOption("delete"))
        helpTable.add_row("add", "Add a new gesture from given data", self.getOption("add"))
        helpTable.add_row("modify", "Modify a gesture given by his id", self.getOption("modify"))

        self.utils.console.print(helpTable)
        return
