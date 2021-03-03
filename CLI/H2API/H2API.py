from typing import Dict

from .API.H2DBGesture import H2DBGesture
from .API.H2DBScript import H2DBScript
from .API.H2Exec import H2Exec
from .API.H2LocalGesture import H2LocalGesture
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
            "db_script": H2DBScript,
            "exec": H2Exec,
            "local_gesture": H2LocalGesture,
            "db_gesture": H2DBGesture,
            "user": H2User,
            "help": CliHelp
        }

        self.infos.get(self.args[0], "help")(self.args, self.utils)
