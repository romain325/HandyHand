import getopt
import magic
import requests
import json

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
from ..Utils.Display.DataDisplay import *


class H2LocalScript(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/script"

        self.switcher["info"] = self.getScripts
        self.options["info"] = "-a: See All\n" \
                               "-i <id>: get an Individual script by ID\n" \
                               "-d : Debug Mode-> View data as JSON\n" \
                               "-o <outputFileName>: save json data to a given file"

        self.switcher["delete"] = self.deleteScript
        self.options["delete"] = f"<script id>: Id of the script you want to delete\n" \
                                 f"\t[i]Example: HandyHand local_script delete dGV0X2pvaG5ueQ==[/i]"

        self.switcher["add"] = self.addScript
        self.options["add"] = f"<script id>: Id of the script you want to delete\n" \
                              f"\t[i]Example: HandyHand local_script delete dGV0X2pvaG5ueQ==[/i]"

        self.execAdaptedFunction(args[1:])

    def getScripts(self, args):
        try:
            opts, _ = getopt.getopt(args[1:], "hado:i:")
            if dict(opts).get("-h") is not None:
                self.printFunctionOptions("info")
                return

            isDebug = dict(opts).get("-d") is not None
            data = ""
            if dict(opts).get("-i") is not None:
                data = self.getOneScript(dict(opts)["-i"], isDebug)
            else:
                data = self.getAllScript(isDebug)

            outFile = dict(opts).get("-o")
            if outFile is not None:
                with open(outFile, "w") as f:
                    f.write(str(data))
                self.utils.console.print(f"Data successfully written to [bold cyan]{outFile}[/bold cyan]")
        except getopt.GetoptError as err:
            print(err)
        except Exception as err:
            print(err)

    def deleteScript(self, args):
        if len(args) <= 1 or args[1] == "-h":
            self.printFunctionOptions("delete")
            return

        r = requests.delete(self.endpoint + "/" + args[1])
        self.utils.checkStatusCode(r)
        self.utils.console.print(f"The Script {args[1]} has been deleted with success")

    def addScript(self, args: list):
        script = {
            "file": "",
            "description": "",
            "args": [],
            "execType": ""
        }

        if "-h" in args:
            self.printFunctionOptions("delete")
            return

        if len(args) <= 3:
            script["file"] = input("FileName: ")
            script["description"] = input("Description: ")
            script["args"] = input("Args: (Separated by coma: ',') ").split(",")
        else:
            opts, _ = getopt.getopt(args[1:], "", ["fp=", "desc=", "args="])
            script["file"] = dict(opts).get("--fp")
            script["description"] = dict(opts).get("--desc")
            script["args"] = dict(opts).get("--args", [])

        try:
            if None in script:
                raise Exception("Not enough Args")
            script["execType"] = magic.Magic(mime=True).from_file(script["file"])
        except Exception as err:
            print(err)
            self.printFunctionOptions("add")
            return

        r = requests.post(self.endpoint + "/add", data=json.dumps(script))
        self.utils.checkStatusCode(r)

        self.utils.console.print(f"Script successfully added with the id [bold green]{r.text}[/bold green]", style="cyan")

    def getAllScript(self, debug: bool):
        r = requests.get(self.endpoint + "/all")
        self.utils.checkStatusCode(r)
        self.utils.console.print(r.json() if debug else getScriptTable(r.json()))
        return r.json()

    def getOneScript(self, id: str, debug: bool):
        r = requests.get(self.endpoint + "/" + id)
        self.utils.checkStatusCode(r)
        self.utils.console.print(r.json() if debug else getScriptPanel(r.json()))
        return r.json()

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "Local Script help"
        helpTable.add_row("info", "Get informations about all or one script", self.getOption("info"))

        self.utils.console.print(helpTable)
        return
