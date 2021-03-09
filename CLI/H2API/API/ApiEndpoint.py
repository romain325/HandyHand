from ..Utils.APIUtils import APIUtils


class ApiEndpoint:
    def __init__(self, utils: APIUtils):
        self.endpoint = utils.endpoint
        self.utils = utils
        self.switcher = {
            "help": self.getPreciseHelp
        }
        self.options = {}

    def execAdaptedFunction(self, args):
        if len(args) == 0:
            args.append("help")

        self.switcher.get(args[0], self.switcher["help"])(args)

    def getPreciseHelp(self, args):
        self.utils.console.print("Help:", style="bold magenta")
        return

    def printFunctionOptions(self, func : str):
        self.utils.console.print("[i green]Options:[/i green] \n" + self.getOption(func))
        return

    def getOption(self, func: str):
        return self.options.get(func, "None")