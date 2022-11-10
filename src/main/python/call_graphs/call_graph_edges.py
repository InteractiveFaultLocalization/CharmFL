import io
import subprocess
import networkx as nx

from IPython.display import HTML
import os
import sys
from glob import glob

#from call_graph_highlight import Highlighted_Callgraph

files = [fn for fn in glob(sys.argv[1], recursive=True)
         if 'venv' not in os.path.normpath(fn)]

project_path = sys.argv[2]
node = sys.argv[3]
venv_script = sys.argv[4]

files_without_tests = []
for file in files:
    file_name = file[file.find(project_path)+len(project_path):]
    if "test" not in file_name:
        files_without_tests.append(file)

# call_graph = HTML(pyan.create_callgraph(filenames=files_without_tests, format='html'))
#
# destination = project_path + "/static_call_graph.html"
#
# call_graph_file = open(destination, 'w')
# message = call_graph.data
# call_graph_file.write(message)
# call_graph_file.close()
#
# highlight_maker = Highlighted_Callgraph(project_path, node)
# highlight_maker.add_highlighted_callgraph()

cmd = venv_script+" -m pyan " + ' '.join(files_without_tests) + " --uses --no-defines  --annotated --yed"

results = subprocess.check_output(cmd, shell=True, text=True)

G = nx.read_graphml(io.BytesIO(results.encode("UTF-8")))

print(G.edges)