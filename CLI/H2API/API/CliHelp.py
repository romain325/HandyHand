from rich.table import Table

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils
import os, random


class CliHelp(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.getGeneralHelp()

    def getGeneralHelp(self):
        self.printLogo()
        self.printDescTable()



    def printLogo(self):
        ascii_res_folder = os.path.join(os.getcwd(), "resources", "ascii")
        random_file = random.choice(os.listdir(ascii_res_folder))
        with open(os.path.join(ascii_res_folder, random_file), 'r') as f:
            self.utils.console.print(f.read(), style="cyan bold")

    def printDescTable(self):
        table = Table()
        table.add_column("Parameter", style="magenta bold")
        table.add_column("Description")

        table.add_row("help", "Get help about the main object to interact with")
        table.add_row("leap", "Get general informations about the Leap Motion")
        table.add_row("local_script", "Interact with script in a local environnement")
        table.add_row("db_script", "Interact with script in an online environnement")
        table.add_row("user", "Interact the users of H^2")
        table.add_row("exec", "Configure your local executable")
        table.add_row("local_gesture", "Interact with gesture handled locally")
        table.add_row("db_gesture", "Interact with gesture handled online")
        table.add_row("env", "Action over your environnements like synchronisation")

        self.utils.console.print(table)
