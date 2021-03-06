import base64
import getopt
import magic
import requests
import json
import sys

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
        self.options["add"] = f"No args: prompt info\n" \
                              f"--fp <FilePath>: file path of the script\n" \
                              f"--desc <Description>: script description\n" \
                              f"--gest <Gesture id>: Gesture to link with\n" \
                              f"--args <ExecutableArguments>: arguments given at the exec (seperated by a comma: ',')"

        self.switcher["modify"] = self.modifyScript
        self.options["modify"] = f"<script id>: Script ID you want to modify\n" \
                                 f"No args: prompt info\n" \
                                 f"--fp <FilePath>: file path of the script\n" \
                                 f"--desc <Description>: script description\n" \
                                 f"--gest <Gesture id>: Gesture to link with\n" \
                                 f"--args <ExecutableArguments>: arguments given at the exec (seperated by a comma: ',')"

        self.switcher["listen"] = self.switchScriptState
        self.options["listen"] = f"<script id>: Id of the script you want to switch On/Off"

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
        if "-h" in args:
            self.printFunctionOptions("add")
            return

        script = self.__createScriptFromArgs(args)

        r = requests.post(self.endpoint + "/add", data=json.dumps(script))
        self.utils.checkStatusCode(r)

        self.utils.console.print(f"Script successfully added with the id [bold green]{r.text}[/bold green]",
                                 style="cyan")

    def modifyScript(self, args: list):
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


    def switchScriptState(self, args):
        if len(args) <= 1 or args[1] == "-h":
            self.printFunctionOptions("listen")
            return

        script = {"scriptId": args[1]}
        r = requests.post(self.endpoint + "/status", data=json.dumps(script))
        self.utils.checkStatusCode(r)

        action = "stop" if r.text == "true" else "launch"
        message = f"Script {args[1]} stopped with success" if r.text == "true" else f"Script {args[1]} is listened for now"
        r = requests.post(self.endpoint + "/" + action, data=json.dumps(script))
        self.utils.checkStatusCode(r)
        self.utils.console.print(message, style="bold green")


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

    def __createScriptFromArgs(self, args: list):
        script = {
            "oldId": "",
            "file": "",
            "description": "",
            "args": [],
            "execType": ""
        }

        if len(args) <= 1:
            script["file"] = input("FileName: ")
            script["description"] = input("Description: ")
            script["args"] = input("Args: (Separated by coma: ',') ").split(",")
            script["idGesture"] = input("Gesture id(optional): ")
        else:
            opts, _ = getopt.getopt(args[1:], "", ["fp=", "desc=", "args=", "gest="])
            script["file"] = dict(opts).get("--fp", "")
            script["description"] = dict(opts).get("--desc", "")
            script["idGesture"] = dict(opts).get("--gest", "")
            script["args"] = dict(opts).get("--args", "").split(",")

        try:
            if script["execType"] != "":
                script["execType"] = magic.Magic(mime=True).from_file(script["file"])
            if script["file"] != "":
                with open(script["file"], "r") as f:
                    script["file"] = str(base64.b64encode(bytes(f.read(), encoding="utf8")))
        except Exception as err:
            self.utils.console.print(f"Error: Script file not found", style="red")
            self.utils.console.print(f"Error: {err}", style="red")
            sys.exit(2)

        return script

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "Local Script help"
        helpTable.add_row("info", "Get informations about all or one script", self.getOption("info"))
        helpTable.add_row("delete", "Delete a script from a given id", self.getOption("delete"))
        helpTable.add_row("add", "Add a new script from given data", self.getOption("add"))
        helpTable.add_row("modify", "Modify a script given by his id", self.getOption("modify"))
        helpTable.add_row("listen", "Switch a script on or off", self.getOption("listen"))

        self.utils.console.print(helpTable)
        return
