from rich.table import Table


def getScriptTable(jsonData: list):
    scriptTable = Table(title="Script table")

    scriptTable.add_column("Name", style="bold magenta")
    scriptTable.add_column("Description", style="cyan")
    scriptTable.add_column("Id", style="italic grey3", max_width=20)

    for elem in jsonData:
        scriptTable.add_row(str(elem["file"]).split("/")[-1], elem["description"], elem["id"])

    return scriptTable
