import getopt

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

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "Exec's help"
        helpTable.add_row("info", "Get All type of executable", self.getOption("info"))

        self.utils.console.print(helpTable)
        return
