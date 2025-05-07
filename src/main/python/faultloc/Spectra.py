import json
import os
import sys

class Spectra:
    def __init__(self):
        self.spectrum = {}
        self.SEPARATOR_CHARACTER = "::"


    def create_spectrum_from(self, coverage_object, test_object):
        project_path = os.path.abspath("cc.json")
        call_chains = {}
        test_result = test_object.get_tests_results()
        coverage_result = coverage_object.get_coverage_with_context()

        number_of_fails = test_object.get_number_of_failed_test_cases()
        number_of_pass = test_object.get_number_of_passed_test_cases()

        for file, cov_elements in coverage_result.items():
            for code_element, covered_tests in cov_elements.items():
                ef, ep = 0, 0
                ef_mod, ep_mod = 0, 0
                for test in covered_tests:
                    counted_tests = [t for t in test_result if str(t).endswith(test)]
                    if len(counted_tests) == 1:
                        ef = ef + 1 if test_result[counted_tests[0]] == "FAILED" else ef
                        ep = ep + 1 if test_result[counted_tests[0]] == "PASSED" else ep

                        h = self.heuristic_analyzer(test)

                        test_name = counted_tests[0]
                        status = test_result[test_name]
                        call_chains[test_name] = [status, h]


                        ef_mod = ef_mod + 1 if test_result[counted_tests[0]] == "FAILED" else ef_mod
                        ep_mod = ep_mod + 1 if test_result[counted_tests[0]] == "PASSED" and h != "put" else ep_mod

                nf = number_of_fails - ef
                np = number_of_pass - ep

                self.spectrum[str(file)+self.SEPARATOR_CHARACTER+str(code_element)] = {"ef": ef, "ep":ep, "nf":nf,"np":np, "efmod": ef_mod, "epmod" : ep_mod}

        with open(project_path, "w") as f:
            json.dump(call_chains, f)

    def heuristic_analyzer(self,test):
        project_path = os.path.abspath("callchains.json")

        with open(project_path) as file:
            data = file.read()
            call_chains = json.loads(data)

        chains = call_chains[test]

        chain_lengths = len(chains)

        if all([len(elem) == 1 for elem in chains]) and 0 < chain_lengths <= 1:
            return "put"

        if all([len(elem) <= 1 for elem in chains]) and chain_lengths > 0:
            return "smt"

        if chain_lengths == 1:
            if all([len(elem) <= 3 for elem in chains]):
                return "lmc"
            return "smc"
        return "other"

    def get_spectrum(self):
        return self.spectrum

    # def dump_json(self):
    #     output = open('spectrum.json', 'w')
    #     json.dump(self.spectrum, output)

