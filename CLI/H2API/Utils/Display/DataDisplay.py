from rich.table import Table
from rich.columns import Columns
from rich.panel import Panel
from typing import List, Dict


def getExecTable(jsonData: Dict[str, dict]):
    execTable = Table(title="Exec table")

    execTable.add_column("Exec type", style="bold magenta")
    execTable.add_column("Path", style="cyan")
    execTable.add_column("Id")

    for key, val in jsonData.items():
        e = list(val.items())[0]
        execTable.add_row(e[0], e[1], key)

    return execTable


def getScriptTable(jsonData: list):
    scriptTable = Table(title="Script table")

    scriptTable.add_column("Name", style="bold magenta")
    scriptTable.add_column("Description", style="cyan")
    scriptTable.add_column("Id", style="italic grey3")

    for elem in jsonData:
        scriptTable.add_row(str(elem["file"]).split("/")[-1], elem["description"], elem["id"])

    return scriptTable


def getScriptPanel(jsonData):
    return Panel(f"[bold cyan]Name: [/bold cyan] {jsonData['file']}\n"
                 f"[bold cyan]Description: [/bold cyan] {str(jsonData['description'])}\n"
                 f"[bold cyan]Exec Type: [/bold cyan]{str(jsonData['execType'])}\n"
                 f"[bold cyan]Exec Args: [/bold cyan]{str(jsonData['args'])}\n"
                 f"[bold cyan]Id: [/bold cyan] {jsonData['id']}",
                 expand=False)


def getExecPanel(jsonData: dict):
    data = list(jsonData.items())[0]
    return Panel(f"[bold cyan]Type: [/bold cyan] {data[0]}\n"
                 f"[bold cyan]Path: [/bold cyan] {data[1]}",
                 expand=False)
