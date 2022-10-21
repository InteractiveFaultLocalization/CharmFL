import json


class Result_Builder():
    def __init__(self):
        self.path_to_root = ""
        self.line_scores = {}
        self.method_scores = {}
        self.class_scores = {}
        self.results_dictionary = {"files": []}
        self.FILE_NAME_INDEX = 0
        self.NAME_INDEX = 1
        self.START_LINE_INDEX = 2
        self.END_LINE_INDEX = 3
        self.NOT_FOUND_CONTEXT_INDEX = -1
        self.SEPARATOR_CHARACTER = "::"

    def set_path_to_root(self, path_to_root):
        self.path_to_root = path_to_root
        return self

    def set_line_scores(self, line_score_dictionary):
        self.line_scores = line_score_dictionary
        return self

    def set_method_scores(self, method_score_dictionary):
        self.method_scores = method_score_dictionary
        return self

    def set_class_scores(self, class_score_dictionary):
        self.class_scores = class_score_dictionary
        return self

    def produce_results(self):
        LINE_NUMBER_INDEX = 1
        for key, line_scores in self.line_scores.items():
            print(key,line_scores)
            file_name = str(key).split(self.SEPARATOR_CHARACTER)[self.FILE_NAME_INDEX]
            absolute_path_to_root, relative_path = self.__separate_absolute_and_relative_path(file_name)
            line_num = str(key).split(self.SEPARATOR_CHARACTER)[LINE_NUMBER_INDEX]
            class_name, class_start_line_num, class_tar, class_och, class_wong2 = self.__get_lines_context_info(
                self.class_scores, file_name, line_num)
            method_name, method_start_line_num, method_tar, method_och, method_wong2 = self.__get_lines_context_info(
                self.method_scores, file_name, line_num)
            context_scores_and_info = {"absolute_path_to_root": absolute_path_to_root, "relative_path": relative_path,
                                       "line_num": line_num,
                                       "class_name": class_name, "class_start_line_num": class_start_line_num,
                                       "class_tar": class_tar,
                                       "class_och": class_och, "class_wong2": class_wong2,
                                       "method_name": method_name, "method_start_line_num": method_start_line_num,
                                       "method_tar": method_tar, "method_och": method_och, "method_wong2": method_wong2}
            self.__put_line_scores_to_place(context_scores_and_info, line_scores)
        return self

    def toJSON(self):
        return json.dumps(self.results_dictionary, indent=4)

    def __separate_absolute_and_relative_path(self, fullpath):
        absolute_path = fullpath[:len(self.path_to_root) + 1]
        relative_path = fullpath[len(absolute_path):]
        return absolute_path, relative_path

    def __put_line_scores_to_place(self, context_scores_and_info, line_scores):
        relative_path_index = self.__get_index_of_context_containing_property(self.results_dictionary["files"],
                                                                              "relativePath",
                                                                              context_scores_and_info["relative_path"])
        if relative_path_index == self.NOT_FOUND_CONTEXT_INDEX:
            self.__append_file_to_results_dict(context_scores_and_info, line_scores)
        else:
            class_index = self.__get_index_of_context_containing_property(
                self.results_dictionary["files"][relative_path_index]["classes"], "line",
                context_scores_and_info[
                    "class_start_line_num"])
            if class_index == self.NOT_FOUND_CONTEXT_INDEX:
                self.results_dictionary["files"][relative_path_index]["classes"].append(
                    self.__get_class_scores_dictionary(context_scores_and_info, line_scores))
            else:
                method_index = self.__get_index_of_context_containing_property(
                    self.results_dictionary["files"][relative_path_index]["classes"][class_index]["methods"], "line",
                    context_scores_and_info[
                        "method_start_line_num"])
                if method_index == self.NOT_FOUND_CONTEXT_INDEX:
                    self.results_dictionary["files"][relative_path_index]["classes"][class_index]["methods"].append(
                        self.__get_method_scores_dictionary(context_scores_and_info, line_scores))
                else:
                    self.results_dictionary["files"][relative_path_index]["classes"][class_index][
                        "methods"][method_index]["statements"].append(
                        self.__get_line_scores_dictionary(context_scores_and_info, line_scores))

    def __append_file_to_results_dict(self, context_scores_and_info, line_scores):
        self.results_dictionary["files"].append(
            {"pathToRoot": context_scores_and_info["absolute_path_to_root"],
             "relativePath": context_scores_and_info["relative_path"],
             "classes": [self.__get_class_scores_dictionary(context_scores_and_info, line_scores)]}
        )

    def __get_path_scores_dictionary(self, context_scores_and_info, line_scores):
        return {"pathToRoot": context_scores_and_info["absolute_path_to_root"],
                "relativePath": context_scores_and_info["relative_path"],
                "classes": [self.__get_class_scores_dictionary(context_scores_and_info, line_scores)]}

    def __get_class_scores_dictionary(self, context_scores_and_info, line_scores):
        return {"name": context_scores_and_info["class_name"],
                "line": context_scores_and_info["class_start_line_num"],
                "tar": context_scores_and_info["class_tar"], "och": context_scores_and_info["class_och"],
                "wong2": context_scores_and_info["class_wong2"],
                "methods": [self.__get_method_scores_dictionary(context_scores_and_info, line_scores)
                            ]}

    def __get_method_scores_dictionary(self, context_scores_and_info, line_scores):
        return {"name": context_scores_and_info["method_name"],
                "line": context_scores_and_info["method_start_line_num"],
                "tar": context_scores_and_info["method_tar"],
                "och": context_scores_and_info["method_och"],
                "wong2": context_scores_and_info["method_wong2"],
                "statements": [self.__get_line_scores_dictionary(context_scores_and_info, line_scores)
                               ]}

    def __get_line_scores_dictionary(self, context_scores_and_info, line_scores):
        return {"line": context_scores_and_info["line_num"], "tar": line_scores["tar"],
                "och": line_scores["och"],
                "wong2": line_scores["wong2"],
                "faulty": "false"}

    def __get_index_of_context_containing_property(self, list_in_dict, property_name, context_value):
        for idx, element in enumerate(list_in_dict):
            if list_in_dict[idx][str(property_name)] == context_value:
                return idx
        return self.NOT_FOUND_CONTEXT_INDEX

    def __get_lines_context_info(self, context_scores, file_name, line_number):
        for key, value in context_scores.items():
            context_file_name = str(key).split(self.SEPARATOR_CHARACTER)[self.FILE_NAME_INDEX]
            context_name = str(key).split(self.SEPARATOR_CHARACTER)[self.NAME_INDEX]
            context_start_line_number = str(key).split(self.SEPARATOR_CHARACTER)[self.START_LINE_INDEX]
            context_end_line_number = str(key).split(self.SEPARATOR_CHARACTER)[self.END_LINE_INDEX]
            tar_score = context_scores[key]["tar"]
            och_score = context_scores[key]["och"]
            wong2_score = context_scores[key]["wong2"]

            if context_file_name == file_name and int(context_start_line_number) < int(line_number) <= int(context_end_line_number):
                return context_name, context_start_line_number, tar_score, och_score, wong2_score

        context_name = ""
        context_start_line_number = 0
        tar_score = 0
        och_score = 0
        wong2_score = 0
        return context_name, context_start_line_number, tar_score, och_score, wong2_score
