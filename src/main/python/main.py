import getopt
import os
import re
import json
import subprocess
import math
import sys
import shutil

import numpy as np
from scipy.stats import rankdata

res_dict = {}
cov_matrix = {}
basic_statistics = {}
tests_folder = ""
test_folder = ""
tests_files = []
test_files = []

def main(argv):
    try:
        opts, args = getopt.getopt(argv, "hd:", "--directory")
    except getopt.GetoptError:
        print('main.py -d <your_projects_directory>')
        sys.exit(1)
    for opt, arg in opts:
        if opt == '-h':
            print('main.py -d <your_projects_directory>')
            sys.exit()
        elif opt in ("-d", "--directory"):
            path = arg
    copy_rc_file(path)
    get_test_files(path)
    os.chdir(path)
    get_coverage_json(path)
    get_test_results(path)
    make_cov_matrix()

    print("----------")
    basic_stats()
    tarantula()
    print("----------")

def run_this_command(command):
    command = str(' ').join(command)
    subprocess.run(command, shell=True)


def copy_rc_file(path):
    if ".coveragerc" not in os.listdir(path):
        shutil.copyfile(os.path.dirname(os.path.abspath(__file__))+os.path.sep + ".coveragerc", path + ".coveragerc")
    else:
        f = open(path + os.path.sep + ".coveragerc", "r")
        if("[run]" not in f.read()):
            f.close()
            f = open(path + os.path.sep + ".coveragerc", "a")
            f.write("\n")
            f.write("[run]\n")
            f.write("dynamic_context = test_function")
        f.close()


def get_test_files(path):
    global tests_folder
    global test_folder
    
    if os.path.isdir(path + os.path.sep + "tests"):
        tests_folder = "tests"
    if os.path.isdir(path + os.path.sep + "test"):
        test_folder = "test"
    if tests_folder == "" and test_folder == "":
        sys.exit(2)
        
    if tests_folder != "":
        with os.scandir(path + os.path.sep + tests_folder) as it:
            for entry in it:
                if not entry.name.startswith('.') and not entry.name == "__init__.py" and entry.is_file():
                    tests_files.append(entry.name)
    
    if test_folder != "":
        with os.scandir(path + os.path.sep + test_folder) as it:
            for entry in it:
                if not entry.name.startswith('.') and not entry.name == "__init__.py" and entry.is_file():
                    test_files.append(entry.name)


def get_coverage_json(path):
    # if "coverage.json" not in os.listdir(path):
    command = ["coverage", "run", "--source=\"" + path + "\"", "--rcfile=.coveragerc", "-m", "pytest"]
    for file in tests_files:
        command.extend([ "\"" + path + os.path.sep + tests_folder + os.path.sep + file + "\""])
    for file in test_files:
        command.extend([ "\"" + path + os.path.sep + test_folder + os.path.sep + file + "\""])
    run_this_command(command)
    command = ['coverage', 'json', '--show-contexts']
    run_this_command(command)


def get_test_results(path):
    command = ["pytest"]
    for file in tests_files:
        command.extend([ "\"" + path + os.path.sep + tests_folder + os.path.sep + file +"\""])
    for file in test_files:
        command.extend([ "\"" + path + os.path.sep + test_folder + os.path.sep + file +"\""])
    command.extend(["-v"])
    command = str(' ').join(command)
    result = subprocess.run(command, shell=True, stdout=subprocess.PIPE)  # shell=True to support wildchars
    test_report = result.stdout.decode("utf-8")  # test_res stores test report (test file name, test case name, test case result)
    temp = str(test_report).split("\n")
    #temp = temp[3:len(temp) - 3]
    #print(temp)
    
    count = 0
    for item in temp:
        if len(item) == 1 and item == "\r":
            count = count + 1
            continue
        if count == 1:
            testName_testResult = item.split(" ", 2)[:2]
            if len(testName_testResult) == 0:
                sys.exit(3)
            complete_test_name = testName_testResult[0]
            complete_test_name = str(complete_test_name).replace("::", ".")
            complete_test_name = str(complete_test_name).replace("/", ".")
            complete_test_name = str(complete_test_name).replace(".py", "")
            test_result = testName_testResult[1]
            res_dict[complete_test_name] = test_result
        if count == 2:
            break
    #print(res_dict)


def init_cov_matrix():
    for key in res_dict.keys():
        cov_matrix[key] = []


def make_cov_matrix():
    init_cov_matrix()
    with open("coverage.json", 'r') as cov_json:
        data = json.load(cov_json)
        for file in data["files"]:
            for statement in data["files"][file]["contexts"]:
                tcs = data["files"][file]["contexts"][statement]
                try:
                    for tc in tcs:
                        cov_matrix[tc].append(str(file + ":" + statement))
                except:
                    pass


def basic_stats():
    # This makes the dict that contains the ef, ep etc.
    #print(cov_matrix)
    for test in cov_matrix:
        for statement in cov_matrix[test]:  # init
            if statement not in basic_statistics:
                #if (("test_" or "_test") not in statement):
                basic_statistics[statement] = {"ef": 0, "ep": 0, "nf": 0, "np": 0}
    #print(basic_statistics)

    for statement in basic_statistics:
        #if (("test_" or "_test") not in statement):
            for test in cov_matrix:
                if statement in cov_matrix[test]:
                    if res_dict[test] == "FAILED":  # Executed Failed
                        basic_statistics[statement]["ef"] = basic_statistics[statement]["ef"] + 1
                    if res_dict[test] == "PASSED":  # Executed Passed
                        basic_statistics[statement]["ep"] = basic_statistics[statement]["ep"] + 1
                if statement not in cov_matrix[test]:
                    if res_dict[test] == "FAILED":  # Not executed Failed
                        basic_statistics[statement]["nf"] = basic_statistics[statement]["nf"] + 1
                    if res_dict[test] == "PASSED":  # Not executed Passed
                        basic_statistics[statement]["np"] = basic_statistics[statement]["np"] + 1
    #print(basic_statistics)


scores=[]
statements=[]
def tarantula():
    for statement in basic_statistics:
        ef = float(basic_statistics[statement]["ef"])
        ep = float(basic_statistics[statement]["ep"])
        nf = float(basic_statistics[statement]["nf"])
        np = float(basic_statistics[statement]["np"])
        #print(statement)
        try:
            score = (ef / (ef + nf)) / ((ef / (ef + nf)) + (ep / (ep + np)))
            #score = round(score, 3)
        except ZeroDivisionError:
            score = 0.0
        if score != 0.0:
            #print(str(statement) + " tar " + str(score))
            statements.append(statement)
            scores.append(score)
            print(statement, score)

    #print(statements)
    #print(scores)
    

def ochiai():
    for statement in basic_statistics:
        ef = float(basic_statistics[statement]["ef"])
        ep = float(basic_statistics[statement]["ep"])
        nf = float(basic_statistics[statement]["nf"])
        np = float(basic_statistics[statement]["np"])
        try:
            score = ef / math.sqrt((ef + nf) * (ef + ep))
        except ZeroDivisionError:
            score = 0.0
        if score != 0.0:
            print(str(statement) + " ochiai " + str(score))


def wongII():
    for statement in basic_statistics:
        ef = float(basic_statistics[statement]["ef"])
        ep = float(basic_statistics[statement]["ep"])
        nf = float(basic_statistics[statement]["nf"])
        np = float(basic_statistics[statement]["np"])
        score = ef - ep
        try:
            score = ef / math.sqrt((ef + nf) * (ef + ep))
        except ZeroDivisionError:
            score = 0.0
        if score != 0.0:
            print(str(statement) + " wongII " + str(score))


sorted_scores=[]
sorted_statements=[]
def scores_sorting(): # This function later will be called by each spectra metric with its own name, info, etc.
    a = np.array(scores)
    b = np.array(statements)
    a_inds = a.argsort()
    sorted_scrs = a[a_inds[::-1]]
    sorted_stats = b[a_inds[::-1]]
    for i in range(len(sorted_scrs)):
        sorted_scores.append(round(sorted_scrs[i], 3))
        sorted_statements.append(sorted_stats[i])

        
temp=[]
def scores_ranking():  
    rank_type="min" # rank_type = "min" or "max" or "average"
    max_value = max(sorted_scores )
    for i in range(len(sorted_scores)):
        temp.append(max_value - sorted_scores[i])

    ranks = rankdata(temp, method=rank_type)
    for i in range(len(ranks)): 
        print(ranks[i], sorted_scores[i], sorted_statements[i], " ")
    
if __name__ == "__main__":
    main(sys.argv[1:])

# This is the core

#scores_sorting()
#scores_ranking()
# ochiai()
# wongII()

