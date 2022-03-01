import os
import sys
import shutil
import argparse
from configparser import ConfigParser

import coverage_matrix
import test_utils
import metrics
import statistics
import make_coverage
import ranking
from constans import COVERAGE_RC_FILE_NAME
from error_codes import FAILED_COPY_COVERAGE_RC_FILE, FAILED_WRITE_PROJECT_COVERAGE_RC_FILE


def main():
    parser = argparse.ArgumentParser(add_help=False)
    parser.version = '1.5'
    parser.add_argument("-d", "--directory", action="store", metavar="PROJECT_DIRECTORY", help="Project directory absolute path.", required=True)
    parser.add_argument("-c", "--class-coverage", action="store", metavar="CLASS_COVERAGE_FILENAME", help="To get class coverage.")
    parser.add_argument("-m", "--method-coverage", action="store", metavar="METHOD_COVERAGE_FILENAME", help="To get method coverage.")
    parser.add_argument("-s", "--spectrum", action="store", metavar="SPECTRUM", help="To get the spectrum.")
    parser.add_argument("-h", "--help", action="help", help="Show this help message.")
    parser.add_argument("-v", "--version", action="version", help="Show version number.")

    args = parser.parse_args()
    plugin_path = os.path.dirname(os.path.abspath(__file__))
    project_path = args.directory

    prepare_rc_file(plugin_path, project_path)
    os.chdir(project_path)

    res_dict = test_utils.get_tests_results(project_path)
    coverage_matrix.get_coverage_json(project_path)


    method_cov = {}

    class_cov = {}

    cov_matrix = coverage_matrix.make_cov_matrix(res_dict, project_path)



    counters = statistics.basic_stats(cov_matrix, res_dict)


    if method_cov and class_cov:
        metrics.make_score_json(counters, method_cov, class_cov)
    elif method_cov and not class_cov:
        metrics.make_score_json(counters, method_cov)
    else:
        metrics.make_score_json(counters)



def prepare_rc_file(plugin_path, project_path):
    project_coverage_rc_file_path = project_path + os.path.sep + COVERAGE_RC_FILE_NAME
    if COVERAGE_RC_FILE_NAME not in os.listdir(project_path):
        try:
            shutil.copyfile(plugin_path + os.path.sep + COVERAGE_RC_FILE_NAME, project_coverage_rc_file_path)
        except shutil.Error as err:
            sys.exit(FAILED_COPY_COVERAGE_RC_FILE)
    else:
        config = ConfigParser()
        config.read(project_coverage_rc_file_path)

        if not config.has_section("run"):
            config.add_section("run")
        config.set("run", "dynamic_context", "test_function")

        try:
            with open(project_coverage_rc_file_path, 'w') as coveragerc_file:
                config.write(coveragerc_file)
        except IOError:
            sys.exit(FAILED_WRITE_PROJECT_COVERAGE_RC_FILE)


if __name__ == "__main__":
    main()
