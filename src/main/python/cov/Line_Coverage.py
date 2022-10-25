import coverage
import os


class Line_Coverage():
    line_coverage_with_context = {}

    def __init__(self):
        pass


    def get_coverage_with_context(self):
        covdb = coverage.CoverageData()

        covdb.read()
        measured_files = covdb.measured_files()

        for file in measured_files:
            self.line_coverage_with_context[file] = covdb.contexts_by_lineno(file)
        return self.__clean(self.line_coverage_with_context)

    def __clean(self, line_coverage_with_context):
        cleaned_line_coverage_with_context = {}
        for file_name, coverage_data in line_coverage_with_context.items():
            temp_coverage_data = {}
            if "test" + os.path.sep in file_name or "tests" + os.path.sep in file_name:
                # TODO: it excludes too many things, e.g. if the absolute path has two test folders then the whole program will be ignored
                # I leave it as is, and report it in a bug ticket
                continue
            for code_element, covered_test_list in coverage_data.items():
                covered_test_list = self.__clean_empty_context_from(covered_test_list)

                if len(covered_test_list) != 0:
                    temp_coverage_data[code_element] = covered_test_list
                    cleaned_line_coverage_with_context[file_name] = temp_coverage_data
        return cleaned_line_coverage_with_context

    def __clean_empty_context_from(self, covered_test_list):
        temp_covered_test_list = [value for value in covered_test_list if value != '']
        return temp_covered_test_list
