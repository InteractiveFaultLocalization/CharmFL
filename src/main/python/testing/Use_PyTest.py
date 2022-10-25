import os
import sys
import pytest
import json
import subprocess as sp
from coverage import Coverage

from constans import EXCEL_REPORT_FILE_NAME, JSON_REPORT_FILE_NAME
from error_codes import EXCEL_REPORT_FILE_NOT_FOUND, RESULT_DICT_EMPTY, JSON_REPORT_FILE_NOT_FOUND
from testing.Test_Utils import Test_Utils


class Use_Pytest(Test_Utils):
    project_path = ""
    results_dict = {}
    number_of_failed_tests = 0
    number_of_passed_tests = 0

    def __init__(self, project_path):
        self.project_path = project_path

    def run_tests(self):
        test_folder, tests_folder, test_files, tests_files = super(Use_Pytest, self).get_test_files(self.project_path)

        pytest_args = ["-v",  "--json-report"]
        for file in tests_files:
            pytest_args.append(self.project_path + os.path.sep + tests_folder + os.path.sep + file)
        for file in test_files:
            pytest_args.append(self.project_path + os.path.sep + test_folder + os.path.sep + file)

        cov = Coverage(config_file=True)
        cov.erase()
        cov.set_option("run:dynamic_context", "test_function")
        cov.start()
        pytest.main(pytest_args)
        cov.stop()
        cov.save()
        os.system("coverage combine")


        self.__get_test_results_from_json(self.results_dict)

    def get_tests_results(self):

        if self.results_dict:
            return self.results_dict
        else:
            sys.exit(RESULT_DICT_EMPTY)


    def __get_test_results_from_json(self, results_dict):
        report_file_path = self.project_path + os.path.sep + JSON_REPORT_FILE_NAME

        if not os.path.exists(report_file_path):
            sys.exit(JSON_REPORT_FILE_NOT_FOUND)
        report_json = open(report_file_path)
        data = json.load(report_json)
        report_json.close()
        for test_object in data["tests"]:
            coverage_test_name = test_object["nodeid"]
            coverage_test_name = str(coverage_test_name).replace("/",".")
            coverage_test_name = str(coverage_test_name).replace("::",".")
            coverage_test_name = str(coverage_test_name).replace(".py","")
            test_result =str(test_object["outcome"]).upper()
            if test_result == "PASSED":
                self.number_of_passed_tests = self.number_of_passed_tests + 1
            if test_result == "FAILED":
                self.number_of_failed_tests = self.number_of_failed_tests + 1
            if test_result == "PASSED" or test_result == "FAILED":
                results_dict[coverage_test_name] = test_result


    def get_number_of_failed_test_cases(self):
        return self.number_of_failed_tests

    def get_number_of_passed_test_cases(self):
        return self.number_of_passed_tests