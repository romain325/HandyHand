import os


def getAddress():
    filename = "../../api.address"
    if not os.path.exists(filename):
        with open(filename, "w") as file:
            file.write("http://localhost:8080")

    with open(filename, "r") as f:
        return f.read()
