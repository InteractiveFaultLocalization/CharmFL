import sys
from pathlib import Path
path = Path(__file__).parent.resolve()
sys.path.append(str(path.parent.absolute()))

import unittest
from testing.Use_PyTest import Use_Pytest
from cov.Line_Coverage import Line_Coverage
from faultloc.Spectra import Spectra
from cov.Method_Coverage import Method_Coverage
from cov.Class_Coverage import Class_Coverage



class TestSpectrumForProducts(unittest.TestCase):

    def setUp(self):
        self.test_object = Use_Pytest("C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\")
        self.test_object.results_dict = {'tests/testAddProducts.py::test_AddProduct_Once|run': 'PASSED',
                                         'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run': 'FAILED',
                                         'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run': 'FAILED',
                                         'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run': 'FAILED',
                                         'tests/test_add_products.py::test_AddProduct_Once|run': 'PASSED',
                                         'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run': 'FAILED',
                                         'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run': 'FAILED',
                                         'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run': 'FAILED',
                                         'test/testAddProducts2.py::test_AddProduct_Once|run': 'PASSED',
                                         'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run': 'PASSED',
                                         'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run': 'FAILED',
                                         'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run': 'FAILED'}
        self.test_object.number_of_failed_tests = 8
        self.test_object.number_of_passed_tests = 4

    def test_get_spectrum_for_line_cov(self):
        coverage_result = Line_Coverage()
        spectra = Spectra()
        coverage_result.line_coverage_with_context = {
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py': {
                9: ['tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                    'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                    'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                    'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                    'tests/test_add_products.py::test_AddProduct_Once|run',
                    'tests/testAddProducts.py::test_AddProduct_Once|run',
                    'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                    'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run'],
                10: ['tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                     'tests/test_add_products.py::test_AddProduct_Once|run',
                     'tests/testAddProducts.py::test_AddProduct_Once|run',
                     'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run'],
                38: ['tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                     'tests/test_add_products.py::test_AddProduct_Once|run',
                     'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                     'tests/testAddProducts.py::test_AddProduct_Once|run'],
                39: ['tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                     'tests/test_add_products.py::test_AddProduct_Once|run',
                     'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                     'tests/testAddProducts.py::test_AddProduct_Once|run'],
                42: ['tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                     'tests/test_add_products.py::test_AddProduct_Once|run',
                     'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                     'tests/testAddProducts.py::test_AddProduct_Once|run'],
                16: ['tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run'],
                12: ['tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                     'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run']},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py': {
                25: ['tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run'],
                27: ['tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run'],
                8: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                    'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                    'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                    'test/testAddProducts2.py::test_AddProduct_Once|run'],
                9: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                    'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                    'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                    'test/testAddProducts2.py::test_AddProduct_Once|run'],
                34: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                     'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                     'test/testAddProducts2.py::test_AddProduct_Once|run'],
                35: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                     'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                     'test/testAddProducts2.py::test_AddProduct_Once|run'],
                38: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                     'test/testAddProducts2.py::test_AddProduct_Once|run'],
                15: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'],
                16: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'],
                18: ['test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'],
                19: ['test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'],
                36: ['test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'],
                11: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run'],
                17: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run']}}
        spectra.create_spectrum_from(coverage_result, self.test_object)
        expected_spectrum = {
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::25': {'ef': 4,
                                                                                                              'ep': 0,
                                                                                                              'nf': 4,
                                                                                                              'np': 4},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::27': {'ef': 4,
                                                                                                              'ep': 0,
                                                                                                              'nf': 4,
                                                                                                              'np': 4},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::8': {'ef': 2,
                                                                                                             'ep': 2,
                                                                                                             'nf': 6,
                                                                                                             'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::9': {'ef': 2,
                                                                                                             'ep': 2,
                                                                                                             'nf': 6,
                                                                                                             'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::34': {'ef': 2,
                                                                                                              'ep': 2,
                                                                                                              'nf': 6,
                                                                                                              'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::35': {'ef': 2,
                                                                                                              'ep': 2,
                                                                                                              'nf': 6,
                                                                                                              'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::38': {'ef': 2,
                                                                                                              'ep': 1,
                                                                                                              'nf': 6,
                                                                                                              'np': 3},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::15': {'ef': 1,
                                                                                                              'ep': 1,
                                                                                                              'nf': 7,
                                                                                                              'np': 3},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::16': {'ef': 1,
                                                                                                              'ep': 1,
                                                                                                              'nf': 7,
                                                                                                              'np': 3},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::18': {'ef': 0,
                                                                                                              'ep': 1,
                                                                                                              'nf': 8,
                                                                                                              'np': 3},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::19': {'ef': 0,
                                                                                                              'ep': 1,
                                                                                                              'nf': 8,
                                                                                                              'np': 3},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::36': {'ef': 0,
                                                                                                              'ep': 1,
                                                                                                              'nf': 8,
                                                                                                              'np': 3},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::11': {'ef': 2,
                                                                                                              'ep': 0,
                                                                                                              'nf': 6,
                                                                                                              'np': 4},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::17': {'ef': 1,
                                                                                                              'ep': 0,
                                                                                                              'nf': 7,
                                                                                                              'np': 4},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::9': {'ef': 6, 'ep': 2,
                                                                                                 'nf': 2, 'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::10': {'ef': 6, 'ep': 2,
                                                                                                  'nf': 2, 'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::38': {'ef': 2, 'ep': 2,
                                                                                                  'nf': 6, 'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::39': {'ef': 2, 'ep': 2,
                                                                                                  'nf': 6, 'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::42': {'ef': 2, 'ep': 2,
                                                                                                  'nf': 6, 'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::16': {'ef': 4, 'ep': 0,
                                                                                                  'nf': 4, 'np': 4},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::12': {'ef': 4, 'ep': 0,
                                                                                                  'nf': 4, 'np': 4}}

        #print(spectra.get_spectrum())
        #print(expected_spectrum)
        print(",,,,,,,,,,",spectra.get_spectrum())
        self.assertDictEqual(expected_spectrum, spectra.get_spectrum())

    def test_get_spectrum_for_method_cov(self):
        coverage_result = Method_Coverage()
        spectra = Spectra()
        coverage_result.method_coverage_with_context = {
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py': {
                'printProductsInCart::24::29': {'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                                                'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                                                'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                                                'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run'},
                'addToCart::7::11': {'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                                     'test/testAddProducts2.py::test_AddProduct_Once|run',
                                     'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                                     'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'},
                'getProductCount::33::38': {'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                                            'test/testAddProducts2.py::test_AddProduct_Once|run',
                                            'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                                            'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'},
                'removeFromCart::14::21': {'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                                           'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'}},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py': {
                'addToCart::8::12': {'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                                     'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                                     'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                                     'tests/testAddProducts.py::test_AddProduct_Once|run',
                                     'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                                     'tests/test_add_products.py::test_AddProduct_Once|run',
                                     'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                                     'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run'},
                'getProductCount::37::42': {'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                                            'tests/testAddProducts.py::test_AddProduct_Once|run',
                                            'tests/test_add_products.py::test_AddProduct_Once|run',
                                            'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run'},
                'removeFromCart::15::23': {'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                                           'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                                           'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                                           'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run'}}}

        spectra.create_spectrum_from(coverage_result, self.test_object)
        expected_spectrum = {
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::printProductsInCart::24::29': {
                'ef': 4, 'ep': 0, 'nf': 4, 'np': 4},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::addToCart::7::11': {
                'ef': 2, 'ep': 2, 'nf': 6, 'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::getProductCount::33::38': {
                'ef': 2, 'ep': 2, 'nf': 6, 'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py::removeFromCart::14::21': {
                'ef': 1, 'ep': 1, 'nf': 7, 'np': 3},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::addToCart::8::12': {'ef': 6,
                                                                                                                'ep': 2,
                                                                                                                'nf': 2,
                                                                                                                'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::getProductCount::37::42': {
                'ef': 2, 'ep': 2, 'nf': 6, 'np': 2},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py::removeFromCart::15::23': {
                'ef': 4, 'ep': 0, 'nf': 4, 'np': 4}}


        self.assertDictEqual(expected_spectrum, spectra.get_spectrum())

    def test_get_spectrum_for_empty_class_cov(self):
        coverage_result = Class_Coverage()
        spectra = Spectra()
        coverage_result.class_coverage_with_context = {
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\example.py': {},
            'C:\\Users\\user\\Documents\\charmfl\\pyfl\\test_project\\products\\directory\\example2.py': {}}

        spectra.create_spectrum_from(coverage_result, self.test_object)
        expected_spectrum = {}

        self.assertDictEqual(expected_spectrum, spectra.get_spectrum())


if __name__ == '__main__':
    unittest.main()
