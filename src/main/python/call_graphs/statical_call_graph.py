import pyan
from IPython.display import HTML


class StaticalCallGraph:
    def createHTML(self):
        with open("call_graph.html", "w") as out_html:
            html = HTML(pyan.create_callgraph(filenames="*.py", format="html"))
            out_html.write(html.data)