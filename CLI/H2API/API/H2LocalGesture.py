import getopt
import magic
import requests
import json
import sys

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
from ..Utils.Display.DataDisplay import *


class H2LocalGesture(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/gesture"

        self.switcher["info"] = self.getGestures
        self.options["info"] = "-a: See All\n" \
                               "-i <id>: get an Individual gesture by ID\n" \
                               "-d : Debug Mode-> View data as JSON\n" \
                               "-o <outputFileName>: save json data to a given file"

        self.switcher["delete"] = self.deleteGesture
        self.options["delete"] = f"<gesture id>: Id of the gesture you want to delete\n" \
                                 f"\t[i]Example: HandyHand local_gesture delete dGV0X2pvaG5ueQ==[/i]"

        self.switcher["add"] = self.addGesture
        self.options["add"] = f"No args: prompt info\n" \
                              f"--fp <FilePath>: file path of the gesture\n" \
                              f"--desc <Description>: gesture description\n" \
                              f"--args <ExecutableArguments>: arguments given at the exec (seperated by a comma: ',')"

        self.switcher["modify"] = self.modifyGesture
        self.options["modify"] = f"<gesture id>: Gesture ID you want to modify\n" \
                                 f"No args: prompt info\n" \
                                 f"--fp <FilePath>: file path of the gesture\n" \
                                 f"--desc <Description>: gesture description\n" \
                                 f"--args <ExecutableArguments>: arguments given at the exec (seperated by a comma: ',')"

        self.execAdaptedFunction(args[1:])

    def getGestures(self, args):
        try:
            opts, _ = getopt.getopt(args[1:], "hado:i:")
            if dict(opts).get("-h") is not None:
                self.printFunctionOptions("info")
                return

            isDebug = dict(opts).get("-d") is not None
            data = ""
            if dict(opts).get("-i") is not None:
                data = self.getOneGesture(dict(opts)["-i"], isDebug)
            else:
                data = self.getAllGesture(isDebug)

            outFile = dict(opts).get("-o")
            if outFile is not None:
                with open(outFile, "w") as f:
                    f.write(str(data))
                self.utils.console.print(f"Data successfully written to [bold cyan]{outFile}[/bold cyan]")
        except getopt.GetoptError as err:
            print(err)
        except Exception as err:
            print(err)

    def deleteGesture(self, args):
        if len(args) <= 1 or args[1] == "-h":
            self.printFunctionOptions("delete")
            return

        r = requests.delete(self.endpoint + "/" + args[1])
        self.utils.checkStatusCode(r)
        self.utils.console.print(f"The Script {args[1]} has been deleted with success")

    # //TODO Real Implementation here
    def addGesture(self, args: list):
        if "-h" in args:
            self.printFunctionOptions("add")
            return

        script = self.__createScriptFromArgs(args)

        r = requests.post(self.endpoint + "/add", data=json.dumps(script))
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

        script = self.__createScriptFromArgs(args[1:])
        script["oldId"] = args[1]

        r = requests.post(self.endpoint + "/modify", data=json.dumps(script))
        self.utils.checkStatusCode(r)

        self.utils.console.print(f"Script successfully modified,his new id is [bold green]{r.text}[/bold green]",
                                 style="cyan")

    def getAllGesture(self, debug: bool):
        r = requests.get(self.endpoint + "/all")
        self.utils.checkStatusCode(r)
        self.utils.console.print(r.json() if debug else getGestureTable(r.json()))
        return r.json()

    def getOneGesture(self, id: str, debug: bool):
        r = requests.get(self.endpoint + "/" + id)
        self.utils.checkStatusCode(r)
        self.utils.console.print(r.json() if debug else getGesturePanel(r.json()))
        return r.json()

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "Local gesture help"
        helpTable.add_row("info", "Get informations about all or one gesture", self.getOption("info"))
        helpTable.add_row("delete", "Delete a gesture from a given id", self.getOption("delete"))
        helpTable.add_row("add", "Add a new gesture from given data", self.getOption("add"))
        helpTable.add_row("modify", "Modify a gesture given by his id", self.getOption("modify"))

        self.utils.console.print(helpTable)
        return
