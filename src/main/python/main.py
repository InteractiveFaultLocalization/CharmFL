import os
import argparse
import platform

from cov.Class_Coverage import Class_Coverage
from cov.Line_Coverage import Line_Coverage
from cov.Method_Coverage import Method_Coverage
from testing.Use_PyTest import Use_Pytest
from faultloc.Spectra import Spectra
from faultloc.Metrics import Metrics
from utils.Result_Builder import Result_Builder


# import call_graphs.statical_call_graph as cg
import sunburst_visualization_colours.sunburst as vs

from constans import COVERAGE_RC_FILE_NAME
from error_codes import FAILED_COPY_COVERAGE_RC_FILE, FAILED_WRITE_PROJECT_COVERAGE_RC_FILE


# from call_graph_maker import make_call_graph

# logging.basicConfig(filename='charmfl.log', level=logging.INFO, format='%(levelname)s:%(filename)s:%(lineno)d:%(message)s')


def main():
    parser = argparse.ArgumentParser(add_help=False)
    parser.version = 'CharmFL 1.6.5'
    parser.add_argument("-d", "--directory", action="store", metavar="PROJECT_DIRECTORY",
                        help="Project directory absolute path.", required=True)
    parser.add_argument("-fl", "--FaultLoc", help="To start the Fault Localization process", action="store_true")
    parser.add_argument("-cg", "--CallGraph", help="To start the statical call graph construction", action="store_true")
    parser.add_argument("-c", "--class-cov", action="store", metavar="CLASS_COVERAGE_FILENAME",
                        help="To get class cov.")
    parser.add_argument("-m", "--method-cov", action="store", metavar="METHOD_COVERAGE_FILENAME",
                        help="To get method cov.")
    parser.add_argument("-s", "--spectrum", action="store", metavar="SPECTRUM", help="To get the spectrum.")
    parser.add_argument("-h", "--help", action="help", help="Show this help message.")
    parser.add_argument("-v", "--version", action="version", help="Show version number.")
    parser.add_argument("-vs", "--SunBurst", help="To visalize the sunburst chart", action="store_true")


    args = vars(parser.parse_args())
    plugin_path = os.path.dirname(os.path.abspath(__file__))
    venv_path = ""
    if (platform.system() == "Linux"):
        venv_path = venv_path + "/bin" + os.path.sep
    if(platform.system() == "Windows"):
        venv_path = venv_path + "/venv" + os.path.sep + "Scripts" + os.path.sep
    project_path = args["directory"]
    os.chdir(project_path)
    if (args["FaultLoc"] == True):
        tests = Use_Pytest(project_path)
        tests.run_tests(venv_path)
        line_cov = Line_Coverage()
        line_spectra = Spectra()
        line_metrics = Metrics()
        line_spectra.create_spectrum_from(line_cov, tests)
        line_metrics.create_scores_from(line_spectra)

        method_cov = Method_Coverage()
        method_spectra = Spectra()
        method_metrics = Metrics()
        method_cov.set_base_coverage(line_cov) \
            .make_coverage_with_context()
        method_spectra.create_spectrum_from(method_cov, tests)
        method_metrics.create_scores_from(method_spectra)

        class_cov = Class_Coverage()
        class_spectra = Spectra()
        class_metrics = Metrics()
        class_cov.set_base_coverage(line_cov) \
            .make_coverage_with_context()
        class_spectra.create_spectrum_from(class_cov, tests)
        class_metrics.create_scores_from(class_spectra)

        result_builder = Result_Builder()
        result_builder.set_path_to_root(project_path)\
            .set_line_scores(line_metrics.get_scores()) \
            .set_method_scores(method_metrics.get_scores()) \
            .set_class_scores(class_metrics.get_scores()) \
            .produce_results()

        with open("results.json", "w") as output:
            output.write(result_builder.toJSON())

    if (args["CallGraph"] == True):
        call_graph = cg.StaticalCallGraph()
        call_graph.createHTML()

    if (args["SunBurst"] == True):
        sunburst = vs.SunBurstVisualization(project_path)
        sunburst.createHTML()


if __name__ == "__main__":
    main()
