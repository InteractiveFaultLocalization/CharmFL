import os
import sys
import json
import subprocess

from constans import COVERAGE_RC_FILE_NAME, COVERAGE_JSON_FILE_NAME, CLASS_COVERAGE_JSON_FILE_NAME, METHOD_COVERAGE_JSON_FILE_NAME, COVERAGE_MATRIX_JSON_FILE_NAME
from error_codes import COVERAGE_MATRIX_EMPTY
import test_utils


def shell_execute(command):
    command = str(" ").join(command)
    subprocess.run(command, shell=True)


def get_coverage_json(project_path):
    test_folder, tests_folder, test_files, tests_files = test_utils.get_test_files(project_path)
    command = ["coverage", "run", "--source=\"" + project_path + "\"", "--rcfile=" + COVERAGE_RC_FILE_NAME, "-m", "pytest"]

    for file in tests_files:
        command.append("\"" + project_path + os.path.sep + tests_folder + os.path.sep + file + "\"")
    for file in test_files:
        command.append("\"" + project_path + os.path.sep + test_folder + os.path.sep + file + "\"")
    #print(command)

    shell_execute(command)
    shell_execute(['coverage', 'json', '--show-contexts'])


def make_cov_matrix(res_dict, project_path):
    cov_matrix = {}

    for key in res_dict.keys():
        #print("yo_key", key)
        cov_matrix[key] = []

    # it is actually called in main.py before make_cov_matrix function
    if not os.path.exists(COVERAGE_JSON_FILE_NAME):
        get_coverage_json(project_path)

    if os.path.exists(METHOD_COVERAGE_JSON_FILE_NAME) and not os.stat(METHOD_COVERAGE_JSON_FILE_NAME).st_size == 0:
        if os.path.exists(CLASS_COVERAGE_JSON_FILE_NAME) and not os.stat(CLASS_COVERAGE_JSON_FILE_NAME).st_size == 0:

            with open(CLASS_COVERAGE_JSON_FILE_NAME, "r") as class_cov_json:
                class_data = json.load(class_cov_json)
                with open(COVERAGE_JSON_FILE_NAME, "r") as cov_json:
                    with open(METHOD_COVERAGE_JSON_FILE_NAME, "r") as method_cov_json:
                        method_data = json.load(method_cov_json)
                        statement_data = json.load(cov_json)

                        for file in statement_data["files"]:

                            for _class in class_data["files"][file]["contexts"]:
                                start_line = int(class_data["files"][file]["contexts"][_class]["start_line"])
                                last_line = int(class_data["files"][file]["contexts"][_class]["end_line"])

                                for statement in statement_data["files"][file]["contexts"]:
                                    method_name = ""
                                    for method in method_data["files"][file]["contexts"]:
                                        method_start_line = int(method_data["files"][file]["contexts"][method]["start_line"])
                                        method_last_line = int(method_data["files"][file]["contexts"][method]["end_line"])
                                        if method_start_line <= int(statement) <= method_last_line:
                                            method_name = method

                                    if start_line <= int(statement) <= last_line:
                                        tcs = statement_data["files"][file]["contexts"][statement]
                                        try:
                                            for tc in tcs:
                                                tc = tc.replace('.', "/", 1)
                                                tc = tc.replace('.', "\\", 1)
                                                cov_matrix[tc].append(str(file + ":" + _class + ":" + method_name + ":" + statement))
                                        except Exception as ex:
                                            print("Exception: ", ex)
                                            pass
        else:
            with open(METHOD_COVERAGE_JSON_FILE_NAME, "r") as method_cov_json:
                method_data = json.load(method_cov_json)
                with open(COVERAGE_JSON_FILE_NAME, "r") as cov_json:
                    statement_data = json.load(cov_json)

                    for file in statement_data["files"]:

                        for method in method_data["files"][file]["contexts"]:
                            #method_tcs = method_data["files"][file]["contexts"][method]["tc"]
                            start_line = int(method_data["files"][file]["contexts"][method]["start_line"])
                            last_line = int(method_data["files"][file]["contexts"][method]["end_line"])
                            #print(method, start_line, last_line)

                            for statement in statement_data["files"][file]["contexts"]:
                                if start_line <= int(statement) <= last_line:
                                    #print(statement)
                                    tcs = statement_data["files"][file]["contexts"][statement]
                                    #print(tcs)
                                    try:
                                        for tc in tcs:
                                            tc = tc.replace('.', "/", 1)
                                            tc = tc.replace('.', "\\", 1)
                                            print(tc)
                                            print(str(file + ":" + method + ":" + statement))
                                            print("MIAFASZ", cov_matrix[tc])

                                            cov_matrix[tc].append(str(file + ":" + method + ":" + statement))
                                        print("This is mething,", cov_matrix)
                                    except Exception as ex:
                                        print("Exception: ", ex)

    else:
        with open(COVERAGE_JSON_FILE_NAME, "r") as cov_json:
            statement_data = json.load(cov_json)

            for file in statement_data["files"]:
                #print(data["files"][file]["contexts"])

                for statement in statement_data["files"][file]["contexts"]:
                    tcs = statement_data["files"][file]["contexts"][statement]
                    #print(file, statement)
                    try:
                        for tc in tcs:
                            print(type(tc))
                            if str(tc) != "":
                                if tc not in cov_matrix:
                                    cov_matrix[tc] = [str(file + ":" + statement)]
                                else:
                                    cov_matrix[tc].append(str(file + ":" + statement))
                    except Exception as ex:
                        print("Exception: ", ex)

    if cov_matrix:
        #print(cov_matrix)
        with open(COVERAGE_MATRIX_JSON_FILE_NAME, "w") as outfile:
            #print(cov_matrix)
            json.dump(cov_matrix, outfile)
            return cov_matrix
    else:
        sys.exit(COVERAGE_MATRIX_EMPTY)
