import os
import sys
import json
import ast

asd_cov = {}
class_dict = {}
method_cov = {"files": {}}
class_cov = {"files": {}}


def last_line_num(element, lineno):
    last_element = element[-1]
    if lineno < last_element.lineno:
        if hasattr(last_element, "orelse"):
            if last_element.orelse:
                return last_line_num(last_element.orelse, last_element.lineno)
        if hasattr(last_element, "handlers"):
            if not last_element.finalbody:
                return last_line_num(last_element.handlers, last_element.lineno)
            elif last_element.finalbody:
                return last_line_num(last_element.finalbody, last_element.lineno)
        elif hasattr(last_element, "body"):
            return last_line_num(last_element.body, last_element.lineno)
        else:
            return last_line_num(element, last_element.lineno)
    else:
        return last_element.lineno


def show_info(functionNode, filename, class_name = ''):
    if class_name not in asd_cov[str(filename)]:
        asd_cov[str(filename)][class_name] = {}
    first_element = functionNode.body[0]
    last_element = functionNode.body[-1]

    last_lineno = last_line_num(functionNode.body, functionNode.lineno)

    asd_cov[str(filename)][class_name][str(functionNode.name)] = {"class": class_name, "start": first_element.lineno, "end": last_lineno}



def do_ur_magic(filename):
    if filename not in class_dict:
        class_dict[filename] = {}
    with open(filename) as file:
        node = ast.parse(file.read())

    functions = [n for n in node.body if isinstance(n, ast.FunctionDef)]
    classes = [n for n in node.body if isinstance(n, ast.ClassDef)]

    if classes:

        for class_ in classes:
                print("Class name:", class_.name)

                last_line_num = class_.lineno
                methods = [n for n in class_.body if isinstance(n, ast.FunctionDef)]
                for method in methods:
                    show_info(method, filename, class_.name)
                    last_line_num = max(last_line_num,asd_cov[str(filename)][class_.name][str(method.name)]["end"])
                print(filename, class_.name)
                class_dict[str(filename)][str(class_.name)] = {"start": class_.lineno, "end": last_line_num}
    else:
        for method in functions:
            show_info(method, filename)



def make_method_cov(path):
    with open(path + os.path.sep + "coverage.json", 'r') as cov_json:
        with open(path + os.path.sep +"method_cov.json", "w") as output:
            data = json.load(cov_json)
            for file in data["files"]:
                asd_cov[str(file)] = {}
                try:
                    do_ur_magic(file)
                except Exception as ex:
                    print(ex)
            for file in data["files"]:
                method_cov["files"][str(file)] = {"contexts": {}}
                for line in data["files"][file]["contexts"]:
                    for func in asd_cov[str(file)]:
                        try:
                            start_line = int(asd_cov[str(file)][func]["start"])
                            end_line = int(asd_cov[str(file)][func]["end"])
                            if start_line <= int(line) < end_line:
                                tc = data["files"][file]["contexts"][line]
                                if func in method_cov["files"][str(file)]["contexts"]:
                                   tc = list(set(method_cov["files"][str(file)]["contexts"][func]["tc"]) | set(tc))
                                method_cov["files"][str(file)]["contexts"][func] = {"start_line": start_line, "end_line": end_line}
                                method_cov["files"][str(file)]["contexts"][func]["tc"] = tc

                        except Exception as ex:
                            print("Something's wrong with", func)
                            print(asd_cov[str(file)][func])
                            print(ex)



    return method_cov


def make_class_cov(path):
    with open(path + os.path.sep + "coverage.json", 'r') as cov_json:
        with open(path + os.path.sep + "class_cov.json", "w") as output:
            data = json.load(cov_json)
            for file in data["files"]:
                class_cov["files"][str(file)] = {}
                try:
                    do_ur_magic(file)
                except Exception as ex:
                    print(ex)
            for file in data["files"]:
                class_cov["files"][str(file)] = {"contexts": {}}
                for line in data["files"][file]["contexts"]:

                    for _class in class_dict[str(file)]:
                         try:
                            start_line = sys.maxsize
                            end_line = 0

                            for functions in asd_cov[str(file)][_class]:
                                if int(asd_cov[str(file)][_class][functions]["start"]) < start_line:
                                    start_line = int(asd_cov[str(file)][_class][functions]["start"])
                                if int(asd_cov[str(file)][_class][functions]["end"]) > end_line:
                                    end_line =int(asd_cov[str(file)][_class][functions]["end"])

                            if start_line <= int(line) < end_line:
                                tc = data["files"][file]["contexts"][line]
                                print(tc)
                                if _class in class_cov["files"][str(file)]["contexts"]:
                                    tc = list(set(class_cov["files"][str(file)]["contexts"][_class]) | set(tc))


                                class_cov["files"][str(file)]["contexts"][_class] = {"start_line": start_line, "end_line": end_line}

                                class_cov["files"][str(file)]["contexts"][_class]["tc"] = tc

                         except Exception as ex:
                            print(ex)
    return class_cov

