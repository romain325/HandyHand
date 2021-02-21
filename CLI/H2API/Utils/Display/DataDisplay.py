from rich.table import Table
from rich.columns import Columns
from rich.panel import Panel


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
