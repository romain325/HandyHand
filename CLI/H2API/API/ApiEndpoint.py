from ..Utils.APIUtils import APIUtils


class ApiEndpoint:
    def __init__(self, utils: APIUtils):
        self.endpoint = utils.endpoint
        self.utils = utils
        self.switcher = {}

    def execAdaptedFunction(self, args):
        self.switcher.get(args[0], self.switcher["help"])(args)
