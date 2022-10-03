import io
import subprocess
import networkx as nx

import pyan
from IPython.display import HTML
import os
import sys
from glob import glob

files = [fn for fn in glob(sys.argv[1], recursive=True)
         if 'venv' not in os.path.normpath(fn)]

call_graph = HTML(pyan.create_callgraph(filenames=files, format='html'))

project_path = sys.argv[2]

destination = project_path + "/static_call_graph.html"

call_graph_file = open(destination, 'w')
message = call_graph.data
call_graph_file.write(message)
call_graph_file.close()

cmd = "python -m pyan " + ' '.join(files) + " --uses --no-defines  --annotated --yed"

results = subprocess.check_output(cmd, shell=True, text=True)

G = nx.read_graphml(io.BytesIO(results.encode("UTF-8")))

print(G.edges)


