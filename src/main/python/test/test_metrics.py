import sys
from pathlib import Path
path = Path(__file__).parent.resolve()
sys.path.append(str(path.parent.absolute()))

import unittest
from faultloc.Metrics import Metrics
from faultloc.Spectra import Spectra


class TestMetrics(unittest.TestCase):

    def setUp(self):
        self.metrics = Metrics()
        ...

    def testTarantula(self):
        tarantula_score = self.metrics.tarantula(5, 3, 2, 100)
        self.assertEqual(0.96, tarantula_score)

    def testOchiai(self):
        ochiai_score = self.metrics.ochiai(5, 3, 2, 100)
        self.assertEqual(0.67, ochiai_score)

    def testGetLineScores(self):
        spectra_object = Spectra()
        spectra_object.spectrum = {
            'C:\\Users\\user\\Documents\\test_me\\example.py::6': {'ef': 3, 'ep': 1, 'nf': 0, 'np': 1},
            'C:\\Users\\user\\Documents\\test_me\\example.py::3': {'ef': 2, 'ep': 1, 'nf': 1, 'np': 1}}
        self.metrics.create_scores_from(spectra_object)
        self.assertEqual(
            {'C:\\Users\\user\\Documents\\test_me\\example.py::6': {'tar': 0.67, 'och': 0.87, 'wong2': 2.0},
             'C:\\Users\\user\\Documents\\test_me\\example.py::3': {'tar': 0.57, 'och': 0.67, 'wong2': 1.0}},
            self.metrics.get_scores())

    def testGetMethodScores(self):
        spectra_object = Spectra()
        spectra_object.spectrum = {
            'C:\\Users\\user\\Documents\\test_me\\example.py::my_method::6::10': {'ef': 3, 'ep': 1, 'nf': 0, 'np': 1}}
        self.metrics.create_scores_from(spectra_object)
        self.assertEqual({'C:\\Users\\user\\Documents\\test_me\\example.py::my_method::6::10': {'tar': 0.67,
                                                                                                'och': 0.87,
                                                                                                'wong2': 2.0}},
                         self.metrics.get_scores())


if __name__ == '__main__':
    unittest.main()
