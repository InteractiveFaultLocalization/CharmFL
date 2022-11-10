import ast

class AST_Facade():
    def __init__(self):
        self.LAST_ELEMENT_INDEX = -1

    def get_method_info_for(self, filename):
        """
        This gives a list of objects containing the methods' name, start line and end line numbers of the provided file
        :param filename: Absolute path to the investigated file.
        :return: A set of method objects
        """
        method_info = []
        methods = self.__get_objects_for_granularity("method", filename)
        for _method in methods:
            last_lineno = self.__last_line_num(_method.body, _method.lineno)
            method_info.append((_method.name, _method.lineno, last_lineno))
        return method_info

    def get_class_info_for(self, filename):
        """
        This gives a list of objects containing the classes' name, start line and end line numbers of the provided file
        :param filename: Absolute path to the investigated file.
        :return: A set of class objects
        """
        class_info = []
        classes = self.__get_objects_for_granularity("class", filename)
        for _class in classes:
            last_lineno = self.__last_line_num(_class.body, _class.lineno)
            class_info.append((_class.name, _class.lineno, last_lineno))
        return class_info


    def __get_objects_for_granularity(self, level, filename):
        """
        Get the set of objects, i.e. functions or classes in the given file.
        This will give back objects according to the granularity. (should be "method" or "class")
        :param level: "method" or "class"
        :param filename: absolute path to the file which contains methods and classes.
        :return: a set of methods or classes
        """
        with open(filename) as file:
            print(filename)
            node = ast.parse(file.read())
            functions = self.__get_elements_of_list(node, ast.FunctionDef)
            classes = self.__get_elements_of_list(node, ast.ClassDef)
            for _func_node in functions:
                inner_functions, inner_classes = self.__get_inner_functions_and_classes_from(_func_node)
                functions.extend(inner_functions)
                classes.extend(inner_classes)
            for _class_node in classes:
                inner_functions, inner_classes = self.__get_inner_functions_and_classes_from(_class_node)
                functions.extend(inner_functions)
                classes.extend(inner_classes)
            if level == "class":
                return set(classes)
            else:
                return set(functions)

    def __get_elements_of_list(self, node, type):
        """
        :param node: AST node
        :param type: ClassDef or FuncDef
        :return: A list of elements belonging to the type in the body of the node
        """
        return [n for n in node.body if isinstance(n, type)]

    def __get_inner_functions_and_classes_from(self, node):
        """
        Gets the inner functions and classes
        :param node: AST node element
        :return: a list of every method and a list of every class in the file
        """
        inner_functions = self.__get_elements_of_list(node, ast.FunctionDef)
        temp_inner_functions = inner_functions.copy()
        inner_classes = self.__get_elements_of_list(node, ast.ClassDef)
        temp_inner_classes = inner_classes.copy()
        for inner_function_node in inner_functions:
            double_inner_functions, double_inner_classes = self.__get_inner_functions_and_classes_from(
                inner_function_node)
            temp_inner_functions.extend(double_inner_functions)
            temp_inner_classes.extend(double_inner_classes)

        for inner_class_node in inner_classes:
            double_inner_functions, double_inner_classes = self.__get_inner_functions_and_classes_from(
                inner_class_node)
            temp_inner_functions.extend(double_inner_functions)
            temp_inner_classes.extend(double_inner_classes)
        return temp_inner_functions, temp_inner_classes

    def __last_line_num(self, element, lineno):
        """
        Gets the last element's line number.
        :param element: AST node
        :param lineno: line number of the last element in the block
        :return: the real last line number of the block.
        """
        last_element = element[self.LAST_ELEMENT_INDEX]
        if lineno < last_element.lineno:
            if hasattr(last_element, "orelse"):
                if last_element.orelse:
                    return self.__last_line_num(last_element.orelse, last_element.lineno)
            if hasattr(last_element, "handlers"):
                if not last_element.finalbody:
                    return self.__last_line_num(last_element.handlers, last_element.lineno)
                elif last_element.finalbody:
                    return self.__last_line_num(last_element.finalbody, last_element.lineno)
            elif hasattr(last_element, "body"):
                return self.__last_line_num(last_element.body, last_element.lineno)
            else:
                return self.__last_line_num(element, last_element.lineno)
        else:
            return last_element.lineno