import sys
from pathlib import Path

import pyan
from IPython.display import HTML


def make_call_graph(project_path, main_name):
    main_name_use = '/' + main_name
    source_file = project_path + main_name_use
    call_graph = HTML(pyan.create_callgraph(filenames=source_file, format='html'))

    destination = project_path + '/static_call_graph.html'

    call_graph_file = open(destination, 'w')
    message = call_graph.data
    call_graph_file.write(message)
    call_graph_file.close()


"""project_path_arg = sys.argv[1]
main_name_arg = sys.argv[2]
try_main = project_path_arg + '/' + main_name_arg
call_graph_file_path = Path(try_main)
if call_graph_file_path.is_file():
    make_call_graph(project_path_arg, main_name_arg)
else:
    problem_with_the_file = open(project_path_arg + '/static_call_graph.html', 'w')
    problem_with_the_file.write('<!DOCTYPE html><html><head><style>h1 {color: red;}</style></head><body><h1>Invalid '
                                'Main File</h1></body></html>')
    problem_with_the_file.close()"""