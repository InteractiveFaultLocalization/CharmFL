import json
import math
import collections
import sys

scores = []
elements = []
end_res = {"files": []}


def make_score_json(basic_statistics, method_cov="", class_cov=""):
    # basic_statistics = collections.OrderedDict(sorted(basic_statistics.items()))
    print(basic_statistics)
    idx = 0
    for element in basic_statistics:
        element_parts = str(element).split(":")
        filename = element_parts[0]
        classname = ""
        methodname = ""
        statementnum = ""
        if (len(element_parts) == 4):
            classname = element_parts[1]
            methodname = element_parts[2]
            statementnum = element_parts[3]
        elif (len(element_parts) == 3):
            methodname = element_parts[1]
            statementnum = element_parts[2]
        else:
            statementnum = element_parts[1]
        #print(filename, classname, methodname, statementnum)
        if not end_res["files"]:
            files = {"path": filename,"classes": [{"name": classname, "line": 0, "methods": [{"name": methodname, "line": 0,  "statements": [{"line": statementnum}]}]}]}
            end_res["files"].append(files)

        for files in end_res["files"]:
            #print(idx)
            #print("--------------------------")
            #print(files)
            # for d in files:
            #     print(d, files[d])

            if not any(files[d] == filename for d in files):
                 files = {"path": filename,"classes": [{"name": classname, "line": 0, "methods": [
                     {"name": methodname, "line": 0,  "statements": [{"line": statementnum}]}]}]}
                 end_res["files"].append(files)

            else:
                if not any(d['name'] == classname for d in files["classes"]):
                    files["classes"].append({"name": classname, "line": 0, "methods": [
                        {{"name": methodname, "line": 0, "statements": [{"line": statementnum}]}}]})


            #print(files)
            for class_obj in files["classes"]:
                # print(class_obj)
                print(methodname, class_obj["methods"])
                if classname is not class_obj["name"]:
                    continue
                if not any(d['name'] == methodname for d in class_obj["methods"]):
                    class_obj["methods"].append({"name": methodname, "line": 0, "statements": [{"line": statementnum}]})
                for method_obj in class_obj["methods"]:
                    if method_obj["name"] != methodname:
                        continue
                    if not any(d['line'] == statementnum for d in method_obj["statements"]) and statementnum != '':
                        method_obj["statements"].append({"line": statementnum})

        idx = idx + 1
    #print(end_res)
    print("--------------")
    for element, statistics in basic_statistics.items():
        element_parts = str(element).split(":")
        filename = element_parts[0]
        classname = ""
        methodname = ""
        statementnum = ""
        if (len(element_parts) == 4):
            classname = element_parts[1]
            methodname = element_parts[2]
            statementnum = element_parts[3]
        elif (len(element_parts) == 3):
            methodname = element_parts[1]
            statementnum = element_parts[2]
        else:
            statementnum = element_parts[1]
        #print(element, statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"])

        for idx, files in enumerate(end_res["files"]):
            #print(filename, end_res["files"][idx]["path"])

            if filename != end_res["files"][idx]["path"]:
                continue
            if not statementnum and methodname: # method
                for classes in end_res["files"][idx]["classes"]:
                    for methods in classes["methods"]:
                        if methods["name"] == methodname:
                            methods["tar"] = float(tarantula(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                            methods["och"] = float(ochiai(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                            methods["wong2"] = float(wongII(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                            methods["faulty"] = "false"
                            if method_cov:
                                methods["line"] = method_cov["files"][filename]["contexts"][methodname]["start_line"]
                if not methodname: # class
                    for classes in end_res["files"][idx]["classes"]:
                        if classes["name"] == classname:
                            classes["tar"] = float(tarantula(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                            classes["och"] = float(ochiai(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                            classes["wong2"] = float(wongII(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                            classes["faulty"] = "false"
                            if class_cov:
                                classes["line"] = class_cov["files"][filename]["contexts"][classname]["start_line"]
            else: #statement
                for classes in end_res["files"][idx]["classes"]:
                    #print(classes)
                    for methods in classes["methods"]:
                        for statements in methods["statements"]:
                            if statements["line"] == statementnum:
                                statements["tar"] = float(tarantula(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                                statements["och"] = float(ochiai(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                                statements["wong2"] = float(wongII(statistics["ef"], statistics["ep"], statistics["nf"], statistics["np"]))
                                statements["faulty"] = "false"

    if end_res:
        with open("results.json", "w") as outfile:
            print(end_res)
            json.dump(end_res, outfile)
            return end_res
    else:
        #raise Exception('Something wrong with the results!')
        sys.exit(6)


def tarantula(ef, ep, nf, np):
    ef = float(ef)
    ep = float(ep)
    nf = float(nf)
    np = float(np)

    # print(element)
    try:
        score = (ef / (ef + nf)) / ((ef / (ef + nf)) + (ep / (ep + np)))
        # score = round(score, 3)
        print(score)
    except ZeroDivisionError:
        score = 0.0
    if score is None:
        return 0.0
    return round(score, 2)


def ochiai(ef, ep, nf, np):
    ef = float(ef)
    ep = float(ep)
    nf = float(nf)
    np = float(np)
    try:
        score = ef / math.sqrt((ef + nf) * (ef + ep))

    except ZeroDivisionError:
        score = 0.0
    if score is None:
        return 0.0
    return round(score, 2)


def wongII(ef, ep, nf, np):
    ef = float(ef)
    ep = float(ep)
    nf = float(nf)
    np = float(np)
    score = ef - ep

    if score is None:
        return 0.0
    return round(score, 2)
