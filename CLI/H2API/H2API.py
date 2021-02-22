from typing import Dict

from .API.H2Exec import H2Exec
from .API.H2LocalScript import H2LocalScript
from .API.ApiEndpoint import ApiEndpoint
from .API.CliHelp import CliHelp
from .API.H2User import H2User
from .Utils.APIUtils import APIUtils
from .API.H2LeapInfo import H2LeapInfo


class H2API:
    def __init__(self, args):
        self.utils = APIUtils("http://localhost:8080")
        self.args = args
        self.infos: Dict[str, ApiEndpoint] = {
            "leap": H2LeapInfo,
            "local_script": H2LocalScript,
            "exec": H2Exec,
            "user": H2User,
            "help": CliHelp
        }

        self.infos.get(self.args[0], "help")(self.args, self.utils)
