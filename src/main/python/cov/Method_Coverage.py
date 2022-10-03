from utils.AST_Facade import AST_Facade


class Method_Coverage():
    method_coverage_with_context = {}
    line_coverage_result = {}

    def __init__(self):
        self.SEPARATOR_CHARACTER = "::"
        self.ast_facade = AST_Facade()
        pass

    def set_base_coverage(self, coverage_object):
        self.line_coverage_result = coverage_object.get_coverage_with_context()
        return self

    def make_coverage_with_context(self):
        """
        This will go through the line coverage dictionary. Gets the file and its coverage.
        For each file we list the methods info (i.e. method name, start line number, end line number) then we decide for
        each statement which method it belongs to.
        :return: A dictionary, which contains FILENAME : { NAME_INDEX::START_LINE_INDEX::END_LINE_INDEX : [test], NAME_INDEX::START_LINE_INDEX::END_LINE_INDEX : [test]}
        """
        for file, cov_elements in self.line_coverage_result.items():
            method_info = self.ast_facade.get_method_info_for(file)
            self.method_coverage_with_context[file] = {}
            for statement_number, covered_tests in cov_elements.items():
                for method_name, start_line_number, end_line_number in method_info:
                    if start_line_number < statement_number <= end_line_number:
                        method_index = method_name + self.SEPARATOR_CHARACTER + str(
                            start_line_number) + self.SEPARATOR_CHARACTER + str(end_line_number)
                        if method_index not in self.method_coverage_with_context[file]:
                            self.method_coverage_with_context[file][method_index] = covered_tests
                        else:
                            self.method_coverage_with_context[file][method_index].extend(covered_tests)
                        break
        return self

    def get_coverage_with_context(self):
        for file, cov_elements in self.method_coverage_with_context.items():
            for method_index, test_list in cov_elements.items():
                self.method_coverage_with_context[file][method_index] = set(test_list)
        return self.method_coverage_with_context