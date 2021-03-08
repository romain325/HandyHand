import getopt
import json
import sys

import magic
import requests

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
from ..Utils.Display.DataDisplay import getScriptTable, getScriptPanel


class H2DBScript(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/scriptDB"

        self.switcher["info"] = self.getScripts
        self.options["info"] = "-a: See All\n" \
                               "-t <path to token>: Specify a particular connection token\n" \
                               "-i <id>: get an Individual script by ID\n" \
                               "-d : Debug Mode-> View data as JSON\n" \
                               "-o <outputFileName>: save json data to a given file"

        self.switcher["delete"] = self.deleteScript
        self.options["delete"] = f"<script id>: Id of the script you want to delete\n" \
                                 f"-t <path to token>: Specify a particular connection token\n" \
                                 f"\t[i]Example: HandyHand local_script delete dGV0X2pvaG5ueQ==[/i]"

        self.switcher["add"] = self.addScript
        self.options["add"] = f"No args: prompt info\n" \
                              f"-t <path to token>: Specify a particular connection token\n" \
                              f"--fp <FilePath>: file path of the script\n" \
                              f"--desc <Description>: script description\n" \
                              f"--args <ExecutableArguments>: arguments given at the exec (seperated by a comma: ',')"

        self.switcher["modify"] = self.modifyScript
        self.options["modify"] = f"<script id>: Script ID you want to modify\n" \
                                 f"No args: prompt info\n" \
                                 f"-t <path to token>: Specify a particular connection token\n" \
                                 f"--fp <FilePath>: file path of the script\n" \
                                 f"--desc <Description>: script description\n" \
                                 f"--args <ExecutableArguments>: arguments given at the exec (seperated by a comma: ',')"

        self.execAdaptedFunction(args[1:])

    def getScripts(self, args):
        try:
            opts, _ = getopt.getopt(args[1:], "hado:i:t:")
            if dict(opts).get("-h") is not None:
                self.printFunctionOptions("info")
                return

            isDebug = dict(opts).get("-d") is not None
            data = ""
            if dict(opts).get("-i") is not None:
                data = self.getOneScript(dict(opts)["-i"], isDebug, opts)
            else:
                data = self.getAllScript(isDebug, opts)

            outFile = dict(opts).get("-o")
            if outFile is not None:
                with open(outFile, "w") as f:
                    f.write(str(data))
                self.utils.console.print(f"Data successfully written to [bold cyan]{outFile}[/bold cyan]")
        except getopt.GetoptError as err:
            print(err)
        except Exception as err:
            print(err)

    def getAllScript(self, debug: bool, args):
        r = requests.get(self.endpoint + "/all", headers=self.utils.getConnectionHeader(args))

        self.utils.checkStatusCode(r)
        self.utils.console.print(r.json() if debug else getScriptTable(r.json()))
        return r.json()

    def getOneScript(self, id: str, debug: bool, args):
        r = requests.get(self.endpoint + "/" + id, headers=self.utils.getConnectionHeader(args))
        self.utils.checkStatusCode(r)
        if r.content == b"":
            self.utils.console.print(f"No Script found with the id [bold cyan]{id}[/bold cyan]", style="red")
            sys.exit(2)

        self.utils.console.print(r.json() if debug else getScriptPanel(r.json()))
        return r.json()

    def deleteScript(self, args):
        if len(args) <= 1 or args[1] == "-h":
            self.printFunctionOptions("delete")
            return
        r = requests.delete(self.endpoint + "/" + args[1], headers=self.utils.getConnectionHeader(getopt.getopt(args[2:], "ht:")[0]))
        self.utils.checkStatusCode(r)
        self.utils.console.print(f"The Script {args[1]} has been deleted with success")

    def addScript(self, args: list):
        if "-h" in args:
            self.printFunctionOptions("add")
            return

        opts, _ = getopt.getopt(args[1:], "ht:", ["fp=", "desc=", "args="])
        script = self.__createScriptFromArgs(opts)

        r = requests.post(self.endpoint + "/add", data=json.dumps(script), headers=self.utils.getConnectionHeader(opts))
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

        opts, _ = getopt.getopt(args[2:], "ht:", ["fp=", "desc=", "args="])
        script = self.__createScriptFromArgs(opts)
        script["oldId"] = args[1]

        r = requests.post(self.endpoint + "/modify", data=json.dumps(script), headers=self.utils.getConnectionHeader(opts))
        self.utils.checkStatusCode(r)

        self.utils.console.print(f"Script successfully modified,his new id is [bold green]{r.text}[/bold green]",
                                 style="cyan")


    def __createScriptFromArgs(self, args):
        script = {
            "oldId": "",
            "file": "",
            "description": "",
            "args": []
        }

        if len(args) <= 1:
            script["file"] = input("FileName: ")
            script["description"] = input("Description: ")
            script["args"] = input("Args: (Separated by coma: ',') ").split(",")
        else:
            script["file"] = dict(args).get("--fp")
            script["description"] = dict(args).get("--desc")
            script["args"] = dict(args).get("--args", "").split(",")
            if script["file"] == "" or script["description"] == "":
                self.utils.console.print(f"Error: Not all mandatory informations as been provided", style="red")
                sys.exit(2)

        try:
            script["execType"] = magic.Magic(mime=True).from_file(script["file"])
        except Exception as err:
            self.utils.console.print(f"Error: Script file not found", style="red")
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

        self.utils.console.print(helpTable)
        return
