from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
import os, random


class CliHelp(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.getGeneralHelp()

    def getGeneralHelp(self):
        self.printLogo()

    def printLogo(self):
        ascii_res_folder = os.path.join(os.getcwd(), "resources", "ascii")
        random_file = random.choice(os.listdir(ascii_res_folder))
        with open(os.path.join(ascii_res_folder, random_file), 'r') as f:
            self.utils.console.print(f.read(), style="cyan bold")
