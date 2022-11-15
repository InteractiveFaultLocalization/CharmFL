import sys
from pathlib import Path

class Highlighted_Callgraph:

    def __init__(self, project_path, node):
        self.project_path = project_path
        self.node = node

    def __found_the_node(self, project_path, f_name):
        graph_file_path = project_path + "/static_call_graph.html"
        another_file = open(graph_file_path, 'r')
        index = 0
        # find the first prefix in the file
        for line in another_file:
            index += 1
            if f_name in line:
                break
        another_file.seek(0)
        index_j = 0
        start_index = 0
        counter = 0
        node = ""
        for line_j in another_file:
            index_j += 1
            if index + 2 == index_j:
                for node_index, char in enumerate(line_j):
                    if char == "\"" and counter == 0:
                        start_index = node_index
                        counter += 1
                    elif char == "\"" and counter == 1:
                        node = line_j[start_index + 1:node_index]
                        break
        another_file.close()
        return node


    def __lighter(self, node: str):
        lighter_node = 'set_highlight = function (){var elem = document.getElementById("' + node
        lighter_result = lighter_node + '"); var $set = $(), $set = $set.add(' \
                                        'gv.linkedFrom(elem, true)),$set = $set.add(gv.linkedTo(elem, true)); $set.push(' \
                                        'elem), gv.highlight($set, ' \
                                        'true)},window.onload = function(){set_highlight();} '
        return lighter_result


    def __add_highlight_to_data(self, project_path, solution_node):
        graph_file_path = project_path + "/static_call_graph.html"
        highlighted_graph = project_path + "/static_call_graph_highlighted.html"
        call_graph_start_file = open(graph_file_path, 'r')
        read_file = call_graph_start_file.read()
        new_data = read_file[:-66]
        new_data = new_data + solution_node
        new_data = new_data + read_file[-66:]
        call_graph_start_file.close()
        call_graph_end_file = open(highlighted_graph, 'w')
        call_graph_end_file.write(new_data)
        call_graph_end_file.close()


    def __call_functions(self, project_path, node):
        node_id = self.__found_the_node(project_path, node)
        solution = self.__lighter(node_id)
        self.__add_highlight_to_data(project_path, solution)


    def add_highlighted_callgraph(self):
        c_g_file_path = self.project_path + "/static_call_graph.html"
        call_graph_file_path = Path(c_g_file_path)
        if call_graph_file_path.is_file():
            self.__call_functions(self.project_path, self.node)
        else:
            problem_with_the_file = open(self.project_path + "/static_call_graph_highlighted.html", 'w')
            problem_with_the_file.write("<!DOCTYPE html><html><head><style>h1 {color: red;}</style></head><body><h1>First run the Start Call Graph</h1></body></html>")
            problem_with_the_file.close()