from scipy.stats import rankdata
import json
import pandas as pd

def scores_ranking(path_to_json, rank_type):
    print("-------------------------------------------------------------------------")
    class_scores_tarantula = []
    with open(path_to_json, 'r+') as json_file:
        data = json.load(json_file)
        for file in data["files"]:
            #print(file)
            for _class in file["classes"]:
                #print(_class)
                if "tar" in _class.keys():
                    class_scores_tarantula.append(_class["tar"])
                method_scores_tarantula = []

                for method in _class["methods"]:
                    st_scores_tarantula = []
                    if "tar" in method.keys():
                        method_scores_tarantula.append(method["tar"])
                    for statement in method["statements"]:
                        # if statement["tar"] is None:
                        #     statement["tar"] = 0.0
                        st_scores_tarantula.append(statement["tar"])
                    df = pd.DataFrame(data=st_scores_tarantula)
                    #ranks = rankdata(st_scores_tarantula, rank_type)
                    ranks = df.rank(method=rank_type, na_option="bottom", ascending=False)
                    ranks = ranks[0].tolist()
                    for idx, statement in enumerate(method["statements"]):
                        statement["tar_rank"] = ranks[idx]
                df =  pd.DataFrame(data=method_scores_tarantula)
                #ranks = rankdata(st_scores_tarantula, rank_type)
                ranks = df.rank(method=rank_type, na_option="bottom", ascending=False)
                ranks = ranks[0].tolist()
                for idx, _class in enumerate(_class["methods"]):
                    _class["tar_rank"] = ranks[idx]
            # df =  pd.DataFrame(data=class_scores_tarantula)
            # #ranks = rankdata(st_scores_tarantula, rank_type)
            # ranks = df.rank(method=rank_type, na_option="bottom", ascending=False)
            # ranks = ranks[0].tolist()
            # for idx, method in enumerate(file["classes"]):
            #     method["tar_rank"] = ranks[idx]
        json_object = json.dumps(data, indent=4)
        print(json_object)
        with open("results.json", "w") as outfile:
            json.dump(data, outfile)




# print(sorted_scores)
    # temp=[]
    # rank_type="average" # rank_type = "min" or "max" or "average"
    # max_value = max(sorted_scores)
    # for i in range(len(sorted_scores)):
    #     temp.append(max_value - sorted_scores[i])
    #
    # ranks = rankdata(temp, method=rank_type)
    #
    # return ranks.tolist()
