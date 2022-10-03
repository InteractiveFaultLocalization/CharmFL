from scipy.stats import rankdata
import json
import pandas as pd


class Ranking:
    def __init__(self, path_to_json):
        self.path_to_json = path_to_json

    def scores_ranking(self, rank_type):
        print("-------------------------------------------------------------------------")
        class_scores_tarantula = []
        with open(self.path_to_json, 'r+') as json_file:
            data = json.load(json_file)
            for file in data["files"]:

                for _class in file["classes"]:

                    if "tar" in _class.keys():
                        class_scores_tarantula.append(_class["tar"])
                    method_scores_tarantula = []

                    for method in _class["methods"]:
                        st_scores_tarantula = []
                        if "tar" in method.keys():
                            method_scores_tarantula.append(method["tar"])
                        for statement in method["statements"]:
                            st_scores_tarantula.append(statement["tar"])
                        df = pd.DataFrame(data=st_scores_tarantula)

                        ranks = df.rank(method=rank_type, na_option="bottom", ascending=False)
                        ranks = ranks[0].tolist()
                        for idx, statement in enumerate(method["statements"]):
                            statement["tar_rank"] = ranks[idx]
                    df = pd.DataFrame(data=method_scores_tarantula)

                    ranks = df.rank(method=rank_type, na_option="bottom", ascending=False)
                    ranks = ranks[0].tolist()
                    for idx, _class in enumerate(_class["methods"]):
                        _class["tar_rank"] = ranks[idx]

            json_object = json.dumps(data, indent=4)
            print(json_object)
            with open("results.json", "w") as outfile:
                json.dump(data, outfile)
