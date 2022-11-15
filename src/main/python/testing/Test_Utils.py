import os
import sys

from error_codes import TEST_FOLDERS_NOT_FOUND


class Test_Utils:
    def __init__(self, project_path):
        self.project_path = project_path
        pass

    def get_test_files(self, project_path):
        test_folder = ""
        tests_folder = ""
        test_files = []
        tests_files = []


        if os.path.isdir(project_path + os.path.sep + "tests"):
            tests_folder = "tests"
        if os.path.isdir(project_path + os.path.sep + "test"):
            test_folder = "test"


        if tests_folder == "" and test_folder == "":
            sys.exit(TEST_FOLDERS_NOT_FOUND)

        if tests_folder != "":
            with os.scandir(project_path + os.path.sep + tests_folder) as it:
                for entry in it:
                    if not entry.name.startswith('.') and not entry.name.startswith(
                            '..') and not entry.name == "__init__.py" and entry.is_file() and entry.name.endswith(
                            '.py'):
                        tests_files.append(entry.name)

        if test_folder != "":
            with os.scandir(project_path + os.path.sep + test_folder) as it:
                for entry in it:
                    if not entry.name.startswith('.') and not entry.name.startswith(
                            '..') and not entry.name == "__init__.py" and entry.is_file() and entry.name.endswith(
                            ".py"):
                        test_files.append(entry.name)

        return test_folder, tests_folder, test_files, tests_files

    def get_tests_results(self):
        pass
