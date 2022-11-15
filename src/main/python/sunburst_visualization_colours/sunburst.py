import json
import os
import plotly.express as px
import pandas as pd
import sys
import numpy as np

class SunBurstVisualization:
    def __init__(self, project_path):

        f = open(project_path + os.path.sep + "results.json")

        self.result = json.load(f)

        def createsunburst(self, result):
            self.result=df

        files_ = []
        classes_ = []
        methods_ = []
        values_ = []

        for files in self.result["files"]:

            for key, value in files.items():

                if isinstance(value, list):
                    classes = value
                    for list_element_class in classes:
                        if list_element_class["name"] == "":

                            for method_element in list_element_class["methods"]:

                                if method_element["name"] == "":
                                    files_.append(files["relativePath"])
                                    classes_.append("")
                                    methods_.append(None)
                                    values_.append(method_element["och"])

                                else:
                                    files_.append(files["relativePath"])
                                    classes_.append(
                                        files["relativePath"] + ("\\") + list_element_class["name"] + ("\\") + method_element[
                                            "name"])
                                    methods_.append(None)
                                    values_.append(method_element["och"])

                        else:

                            for method_element in list_element_class["methods"]:
                                if method_element["name"] == "":
                                    files_.append(files["relativePath"])
                                    classes_.append(files["relativePath"] + ("\\") + list_element_class["name"])
                                    methods_.append("")
                                    values_.append(method_element["och"])

                                else:
                                    files_.append(files["relativePath"])
                                    classes_.append(files["relativePath"] + ("\\") + list_element_class["name"])
                                    methods_.append(
                                        files["relativePath"] + ("\\") + list_element_class["name"] + ("\\") +
                                        method_element[
                                            "name"])
                                    values_.append(method_element["och"])

        files = files_
        classes = classes_
        methods = methods_
        value = values_

        df = pd.DataFrame(
            dict(files=files, classes=classes, methods=methods, value=value)
        )

        fig = px.sunburst(df, path=['files', 'classes', 'methods'],
                          color='value',
                          color_continuous_scale='YlOrRd',
                          color_continuous_midpoint=np.average(df['value'], weights=df['value']))

        fig.write_html("sunburst.html")

        sys.exit()