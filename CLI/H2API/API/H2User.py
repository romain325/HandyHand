import base64
import json
from getpass import getpass
from typing import List

import requests

from .ApiEndpoint import ApiEndpoint
from ..Utils.APIUtils import APIUtils

import getopt, sys

from ..Utils.Display.DataDisplay import getScriptTable


class H2User(ApiEndpoint):
    def __init__(self, args, utils: APIUtils):
        super().__init__(utils)
        self.endpoint += "/user"

        self.switcher["create"] = self.createUser
        self.options["create"] = "-m <mail>: User's mail [red]REQUIRED[/red]\n" \
                                 "-p <password>: User's password [red]REQUIRED[/red]\n" \
                                 "-h: get help"

        self.switcher["connect"] = self.connect
        self.options["connect"] = "-m <mail>: User's mail [red]REQUIRED[/red]\n" \
                                  "-p <password>: User's password [red]REQUIRED[/red]\n" \
                                  "-o <outputPath>: file where the token is saved\n" \
                                  "-s : doesn't print out the connection token\n" \
                                  "-h: get help"

        self.switcher["scripts"] = self.getUserScripts
        self.options["scripts"] = "-m <mail>: User's mail [red]REQUIRED[/red]\n" \
                                  "-t <connection token file>: Path to connection token file\n" \
                                  "-o <outputPath>: file where the token is saved\n" \
                                  "-d : print data as json\n" \
                                  "-h : get help"

        self.execAdaptedFunction(args[1:])

    def createUser(self, args):
        try:
            opts, _ = getopt.getopt(args[1:], "hm:p:")
            if dict(opts).get("-h") is not None:
                self.printFunctionOptions("create")
                return

            r = requests.post(self.endpoint + "/add", data=json.dumps(self.parseUserInfo(opts)))
            self.utils.checkStatusCode(r)
            self.utils.console.print(f"New User created!", style="bold green")

        except getopt.GetoptError as err:
            print(err)
        except Exception as err:
            print(err)

    def connect(self, args):
        try:
            opts, _ = getopt.getopt(args[1:], "hso:m:p:")
            silent = False
            if dict(opts).get("-h") is not None:
                self.printFunctionOptions("connect")
                return
            if dict(opts).get("-s") is not None:
                silent = True

            r = requests.post(self.endpoint + "/connect", data=json.dumps(self.parseUserInfo(opts)))
            self.utils.checkStatusCode(r)

            if not silent:
                self.utils.console.print(f"Conenction token: {r.text}", style="bold green")

            with open(dict(opts).get("-o", "../user.token"), "w") as f:
                f.write(r.text)

        except getopt.GetoptError as err:
            print(err)
        except Exception as err:
            print(err)

    def getUserScripts(self, args):
        if "-h" in args:
            self.printFunctionOptions("scripts")
            return

        if "-m" not in args:
            self.utils.console.print(f"The mail of the user is mandatory to find user", style="red")
            return

        try:
            opts, _ = getopt.getopt(args[1:], "hdm:t:o:")
            isDebug = dict(opts).get("-d") is not None
            r = requests.get(self.endpoint + "/" + base64.b64encode(str(dict(opts).get("-m")).encode("ascii")).decode("ascii"), headers=self.utils.getConnectionHeader(opts))

            self.utils.checkStatusCode(r)
            self.utils.console.print(r.json() if isDebug else getScriptTable(r.json()))

            outFile = dict(opts).get("-o")
            if outFile is not None:
                with open(outFile, "w") as f:
                    f.write(str(r.json()))
                self.utils.console.print(f"Data successfully written to [bold cyan]{outFile}[/bold cyan]")

        except getopt.GetoptError as err:
            print(err)
        except Exception as err:
            print(err)

    def parseUserInfo(self, args: List[tuple]):
        userInfo = {}
        for o, a in args:
            if o == "-m":
                userInfo["mail"] = a
            elif o == "-p":
                userInfo["password"] = a
        if userInfo.get("mail") is None:
            userInfo["mail"] = input("User's mail:")
        if userInfo.get("password") is None:
            userInfo["password"] = getpass("User's password:")

        return userInfo

    def getPreciseHelp(self, args):
        super().getPreciseHelp(args)
        helpTable = self.utils.getStandartHelpTable()

        helpTable.title = "User help"
        helpTable.add_row("create", "Create a new User for handyHand", self.getOption("create"))
        helpTable.add_row("connect", "Get a connection token for an existing user", self.getOption("connect"))
        helpTable.add_row("scripts", "Get the scripts of an existing user", self.getOption("scripts"))

        self.utils.console.print(helpTable)
        return
