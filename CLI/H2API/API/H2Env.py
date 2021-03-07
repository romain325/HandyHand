import getopt
import json
import sys

import requests

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
from ..Utils.Display.DataDisplay import getGesturePanel, getGestureTable


class H2Env(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/env"

        self.switcher["sync"] = self.syncEnv
        self.options["sync"] = f"-t <path to token>: Specify a particular connection token\n" \
                               f"-h: Get Help"

        self.execAdaptedFunction(args[1:])

    def syncEnv(self, args):
        opts, _ = getopt.getopt(args[1:], "ht:")
        if dict(opts).get("-h") is not None:
            self.printFunctionOptions("info")
            return

        r = requests.get(self.endpoint + "/sync", headers=self.utils.getConnectionHeader(opts))

        self.utils.checkStatusCode(r)
        self.utils.console.print("Your environnements has been synchronized", style="bold green")
        return

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "Environnement help"
        helpTable.add_row("sync", "Synchronize your online env with your local env", self.getOption("sync"))

        self.utils.console.print(helpTable)
        return
