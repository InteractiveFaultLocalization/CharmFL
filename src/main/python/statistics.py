import json
import os
import sys

basic_statistics = {}

def basic_stats(matrix, res_dict):
    if os.path.exists("method_cov.json") and not os.stat("method_cov.json").st_size == 0:
        if os.path.exists("class_cov.json") and not os.stat("class_cov.json").st_size == 0:
            for test in matrix:
                for element in matrix[test]:
                    if element not in basic_statistics:
                        if element.find("_test") & element.find("test_") == -1:
                            element_parts = str(element).split(":")
                            _class = str(element_parts[0] + ":" + element_parts[1] + ":")
                            method = str(element_parts[0] + ":" + element_parts[1] + ":" + element_parts[2] + ":")
                            basic_statistics[element] = {"ef": 0, "ep": 0, "nf": 0, "np": 0}
                            basic_statistics[method] = {"ef": 0, "ep": 0, "nf": 0, "np": 0}
                            basic_statistics[_class] = {"ef": 0, "ep": 0, "nf": 0, "np": 0}

            for element in basic_statistics:
                for test in matrix:
                    for cov_element in matrix[test]:
                        if element in cov_element:
                            if res_dict[test] == "FAILED":  # Executed Failed
                                basic_statistics[element]["ef"] = basic_statistics[element]["ef"] + 1
                            if res_dict[test] == "PASSED":  # Executed Passed
                                basic_statistics[element]["ep"] = basic_statistics[element]["ep"] + 1
                        if element not in cov_element:
                            if res_dict[test] == "FAILED":  # Not executed Failed
                                basic_statistics[element]["nf"] = basic_statistics[element]["nf"] + 1
                            if res_dict[test] == "PASSED":  # Not executed Passed
                                basic_statistics[element]["np"] = basic_statistics[element]["np"] + 1
        else:
            for test in matrix:
                for element in matrix[test]:
                    if element not in basic_statistics:
                        if element.find("_test") & element.find("test_")==-1:
                            element_parts = str(element).split(":")
                            method = str(element_parts[0] + ":"+ element_parts[1] + ":")
                            basic_statistics[element] = {"ef": 0, "ep": 0, "nf": 0, "np": 0}
                            basic_statistics[method] = {"ef": 0, "ep": 0, "nf": 0, "np": 0}

            for element in basic_statistics:
                for test in matrix:
                    for cov_element in matrix[test]:
                        if element in cov_element:
                            if res_dict[test] == "FAILED":  # Executed Failed
                                basic_statistics[element]["ef"] = basic_statistics[element]["ef"] + 1
                            if res_dict[test] == "PASSED":  # Executed Passed
                                basic_statistics[element]["ep"] = basic_statistics[element]["ep"] + 1
                        if element not in cov_element:
                            if res_dict[test] == "FAILED":  # Not executed Failed
                                basic_statistics[element]["nf"] = basic_statistics[element]["nf"] + 1
                            if res_dict[test] == "PASSED":  # Not executed Passed
                                basic_statistics[element]["np"] = basic_statistics[element]["np"] + 1

    else:
        for test in matrix:
            for element in matrix[test]:
                if element not in basic_statistics:
                    if element.find("_test") & element.find("test_") == -1:
                        basic_statistics[element] = {"ef": 0, "ep": 0, "nf": 0, "np": 0}

        for statement in basic_statistics:
                for test in matrix:
                    if statement in matrix[test]:
                        if res_dict[test] == "FAILED":  # Executed Failed
                            basic_statistics[statement]["ef"] = basic_statistics[statement]["ef"] + 1
                        if res_dict[test] == "PASSED":  # Executed Passed
                            basic_statistics[statement]["ep"] = basic_statistics[statement]["ep"] + 1
                    if statement not in matrix[test]:
                        if res_dict[test] == "FAILED":  # Not executed Failed
                            basic_statistics[statement]["nf"] = basic_statistics[statement]["nf"] + 1
                        if res_dict[test] == "PASSED":  # Not executed Passed
                            basic_statistics[statement]["np"] = basic_statistics[statement]["np"] + 1

    if basic_statistics:
        with open("spectrum.json", "w") as outfile:
            json.dump(basic_statistics, outfile)
            return basic_statistics
    else:
        sys.exit(5)
