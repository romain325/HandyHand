from typing import Dict

from .API.H2LocalScript import H2LocalScript
from .API.ApiEndpoint import ApiEndpoint
from .API.CliHelp import CliHelp
from .Utils.APIUtils import APIUtils
from .API.H2LeapInfo import H2LeapInfo


class H2API:
    def __init__(self, args):
        self.utils = APIUtils("http://localhost:8080")
        self.args = args
        self.infos: Dict[str, ApiEndpoint] = {
            "leap": H2LeapInfo,
            "local_script": H2LocalScript,
            "help": CliHelp
        }

        self.infos.get(self.args[0], "help")(self.args, self.utils)
