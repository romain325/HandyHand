import requests
import getopt

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
from ..Utils.Display.DataDisplay import getScriptTable


class H2LocalScript(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/script"
        self.switcher["all"] = self.getAllScripts

        self.options["all"] = "-d : Debug Mode-> View data as JSON\n-o <outputFileName>: save json data to a given file"
        self.execAdaptedFunction(args[1:])

    def getAllScripts(self, args):
        r = requests.get(self.endpoint + "/all")
        self.utils.checkStatusCode(r)

        try:
            opts, _ = getopt.getopt(args, "hdo:")
            if dict(opts).get("-h") is not None:
                self.printFunctionOptions("all")
                return
            isDebug = dict(opts).get("-d") is not None

        except getopt.GetoptError as err:
            print(err)
        except:
            print("aille")

        self.utils.console.print(getScriptTable(r.json()))

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
