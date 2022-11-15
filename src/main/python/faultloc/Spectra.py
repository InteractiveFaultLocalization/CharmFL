import json
import os
import sys

class Spectra:
    def __init__(self):
        self.spectrum = {}
        self.SEPARATOR_CHARACTER = "::"


    def create_spectrum_from(self, coverage_object, test_object):
        test_result = test_object.get_tests_results()
        coverage_result = coverage_object.get_coverage_with_context()

        number_of_fails = test_object.get_number_of_failed_test_cases()
        number_of_pass = test_object.get_number_of_passed_test_cases()



        for file, cov_elements in coverage_result.items():
            for code_element, covered_tests in cov_elements.items():
                ef, ep = 0, 0
                for test in covered_tests:
                    #if test in test_result:
                    counted_tests = [t for t in test_result if str(t).endswith(test)]
                    if len(counted_tests) == 1:
                        ef = ef + 1 if test_result[counted_tests[0]] == "FAILED" else ef
                        ep = ep + 1 if test_result[counted_tests[0]] == "PASSED" else ep
                    else:
                        print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                        print(test)
                nf = number_of_fails - ef
                np = number_of_pass - ep
                self.spectrum[str(file)+self.SEPARATOR_CHARACTER+str(code_element)] = {"ef": ef, "ep":ep, "nf":nf,"np":np}


    def get_spectrum(self):
        return self.spectrum

    # def dump_json(self):
    #     output = open('spectrum.json', 'w')
    #     json.dump(self.spectrum, output)
