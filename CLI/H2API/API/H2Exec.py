import getopt
import json

import requests

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
from ..Utils.Display.DataDisplay import getExecTable, getExecPanel


class H2Exec(ApiEndpoint):

    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/exec"

        self.switcher["info"] = self.getAll
        self.options["info"] = "-d: return data as json\n" \
                               "-a: return all the scripts\n" \
                               "-i <uniq script id>: get only one element\n" \
                               "-h: get help"

        self.switcher["delete"] = self.deleteOne
        self.options["delete"] = f"<exec id>: Id of the exec you want to delete\n" \
                                 f"\t[i]Example: HandyHand exec delete dGV0X2pvaG5ueQ==[/i]"

        self.switcher["add"] = self.addExec
        self.options["add"] = f"No args: prompt info\n" \
                              f"--fp <FilePath>: file path of the executable\n" \
                              f"--mime <MimeType>: Type of file to execute with"

        self.switcher["modify"] = self.modifyExec
        self.options["modify"] = f"<exec id>: Exec ID you want to modify\n" \
                                 f"No args: prompt info\n" \
                                 f"--fp <FilePath>: file path of the executable\n" \
                                 f"--mime <MimeType>: Type of file to execute with"

        self.execAdaptedFunction(args[1:])

    def getAll(self, args):
        if "-h" in args:
            self.printFunctionOptions("all")
            return

        debug = "-d" in args

        opts, _ = getopt.getopt(args[1:], "hdai:")

        execId = dict(opts).get("-i")
        if execId is not None:
            self.printOne(execId, debug)
        else:
            self.printAll(debug)

    def printOne(self, id: str, debug: bool):
        r = requests.get(self.endpoint + "/" + id)
        self.utils.checkStatusCode(r)
        self.utils.console.print(r.json() if debug else getExecPanel(r.json()))

    def printAll(self, debug: bool):
        r = requests.get(self.endpoint + "/all")
        self.utils.checkStatusCode(r)

        data = {}
        for elem in r.json():
            r2 = requests.get(self.endpoint + "/" + elem)
            self.utils.checkStatusCode(r2)
            data[elem] = r2.json()

        self.utils.console.print(data if debug else getExecTable(data))

    def deleteOne(self, args):
        if len(args) <= 1 or args[1] == "-h":
            self.printFunctionOptions("delete")
            return

        r = requests.delete(self.endpoint + "/" + args[1])
        self.utils.checkStatusCode(r)
        self.utils.console.print(f"The Exec {args[1]} has been deleted with success")

    def addExec(self, args: list):
        if "-h" in args:
            self.printFunctionOptions("add")
            return

        exe = self.__createExecFromArgs(args)

        r = requests.post(self.endpoint + "/add", data=json.dumps(exe))
        self.utils.checkStatusCode(r)

        self.utils.console.print(f"Script successfully added with the id [bold green]{r.text}[/bold green]",
                                 style="cyan")

    def modifyExec(self, args: list):
        if "-h" in args:
            self.printFunctionOptions("modify")
            return

        if len(args) <= 1:
            self.utils.console.print(f"The id of the exec which will be modified is needed", style="orange3")
            return

        exe = self.__createExecFromArgs(args[1:])
        exe["oldId"] = args[1]

        r = requests.post(self.endpoint + "/modify", data=json.dumps(exe))
        self.utils.checkStatusCode(r)

        self.utils.console.print(f"Executable successfully modified,his new id is [bold green]{r.text}[/bold green]",
                                 style="cyan")

    def __createExecFromArgs(self, args: list):
        exec = {
            "oldId": "",
            "name": "",
            "execPath": []
        }

        if len(args) <= 1:
            exec["name"] = input("MimeType: ")
            exec["execPath"] = input("Executable Path: ")
        else:
            opts, _ = getopt.getopt(args[1:], "", ["mime=", "fp="])
            exec["name"] = dict(opts).get("--mime")
            exec["execPath"] = dict(opts).get("--fp")

        return exec

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "Exec's help"
        helpTable.add_row("info", "Get All type of executable", self.getOption("info"))
        helpTable.add_row("delete", "Delete an exec from a given id", self.getOption("delete"))
        helpTable.add_row("add", "Add a new exec from given data", self.getOption("add"))
        helpTable.add_row("modify", "Modify an exec given by his id", self.getOption("modify"))

        self.utils.console.print(helpTable)
        return
