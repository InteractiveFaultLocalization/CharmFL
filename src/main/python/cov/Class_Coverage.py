from utils.AST_Facade import AST_Facade


class Class_Coverage():

    def __init__(self):
        self.SEPARATOR_CHARACTER = "::"
        self.ast_facade = AST_Facade()
        self.class_coverage_with_context = {}
        self.line_coverage_result = {}
        ...

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
            class_info = self.ast_facade.get_class_info_for(file)
            self.class_coverage_with_context[file] = {}
            for statement_number, covered_tests in cov_elements.items():
                for class_name, start_line_number, end_line_number in class_info:
                    if start_line_number < statement_number <= end_line_number:
                        class_index = class_name + self.SEPARATOR_CHARACTER + str(
                            start_line_number) + self.SEPARATOR_CHARACTER + str(end_line_number)
                        if class_index not in self.class_coverage_with_context[file]:
                            self.class_coverage_with_context[file][class_index] = covered_tests
                        else:
                            self.class_coverage_with_context[file][class_index].extend(covered_tests)
                        break
        return self

    def get_coverage_with_context(self):
        for file, cov_elements in self.class_coverage_with_context.items():
            for class_index, test_list in cov_elements.items():
                self.class_coverage_with_context[file][class_index] = set(test_list)
        return self.class_coverage_with_context