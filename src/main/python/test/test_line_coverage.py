import os.path
import sys
from pathlib import Path
path = Path(__file__).parent.resolve()
sys.path.append(str(path.parent.absolute()))

import unittest
from cov.Line_Coverage import Line_Coverage


class TestLineCoverage(unittest.TestCase):
    def test_clean(self):
        line_cov = Line_Coverage()
        line_coverage_with_context = {
            '/home/runner/work/test_me/example.py': {1: [''], 2: [''], 5: [''], 8: [''], 6: [
                'test/test_example_e.py::MyTestCase::test_congratulations_for_10|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 3: [
                'test/test_example_e.py::MyTestCase::test_error|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 9: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 10: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 11: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 13: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'], 18: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 19: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 14: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'], 15: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 16: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 12: [
                'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run']},
            '/home/runner/work/test_me/test'+os.path.sep+'__init__.py': {1: ['']},
            '/home/runner/work/test_me/test'+os.path.sep+'test_example_e.py': {1: [''], 3: ['']
                , 6: [''], 7: [''], 11: [''], 15: [''], 19: [''], 22: [''], 26: [''], 20: [
                    'test/test_example_e.py::MyTestCase::test_congratulations_for_10|run'], 23: [
                    'test/test_example_e.py::MyTestCase::test_error|run'], 24: [
                    'test/test_example_e.py::MyTestCase::test_error|run'], 16: [
                    'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run'], 17: [
                    'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run'], 12: [
                    'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'], 13: [
                    'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'], 8: [
                    'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 9: [
                    'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run']}}
        expected_clean_dictionary = {'/home/runner/work/test_me/example.py': {6: ['test/test_example_e.py::MyTestCase::test_congratulations_for_10|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 3: ['test/test_example_e.py::MyTestCase::test_error|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 9: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 10: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 11: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 13: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'], 18: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 19: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 14: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'], 15: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 16: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run', 'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'], 12: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run']}}
        print(line_cov._Line_Coverage__clean(line_coverage_with_context))

        self.assertEqual(expected_clean_dictionary, line_cov._Line_Coverage__clean(line_coverage_with_context))

if __name__ == '__main__':
    unittest.main()
