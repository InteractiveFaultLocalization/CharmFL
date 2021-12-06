import os
import subprocess

subprocess.run("clear")

def run_this_command(command):
    command = str(' ').join(command)
    subprocess.run(command, shell=True)


def bugsinpy_checkout(project, bug):
    command = ["bugsinpy-checkout -p", project, "-v 0", "-i", bug]
    run_this_command(command)


def do_checkout_rcFile(project, bug, path):
    bugsinpy_checkout(project, bug)
    subprocess.run("pip3 install -r /mnt/c/Users/user/Documents/BugsInPy/projects/" + project + "/bugs/" + bug + "/requirements.txt", shell=True)
    
    if ".coveragerc" not in os.listdir(path):
        command = ["cp", "/mnt/c/Users/user/Documents/pyFL/.coveragerc", path]
        run_this_command(command)
    else:
        with open(path + "/.coveragerc") as f:
            if '[run]' not in f.read():
                command = ["echo", "\"\"", ">>", path + "/.coveragerc"]
                run_this_command(command)
                command = ["echo", "[run]", ">>", path + "/.coveragerc"]
                run_this_command(command)
                command = ["echo", "dynamic_context = test_function", ">>", path + "/.coveragerc"]
                run_this_command(command)

    os.chdir(path)