import sys
from pathlib import Path

path = Path(__file__).parent.resolve()
sys.path.append(str(path.parent.absolute()))

import unittest
from cov.Method_Coverage import Method_Coverage


class TestMethodCoverage(unittest.TestCase):
    def test_get_coverage_with_context_from_test_project(self):
        method_cov = Method_Coverage()
        line_cov = {'/home/runner/work/CharmFL-dev/CharmFL-dev/test_project/products/example.py': {
            9: ['tests/testAddProducts.py::test_AddProduct_Once|run',
                'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                'tests/test_add_products.py::test_AddProduct_Once|run',
                'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run'],
            10: ['tests/testAddProducts.py::test_AddProduct_Once|run',
                 'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                 'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                 'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                 'tests/test_add_products.py::test_AddProduct_Once|run',
                 'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                 'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                 'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run'],
            38: ['tests/testAddProducts.py::test_AddProduct_Once|run',
                 'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                 'tests/test_add_products.py::test_AddProduct_Once|run',
                 'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run'],
            39: ['tests/testAddProducts.py::test_AddProduct_Once|run',
                 'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                 'tests/test_add_products.py::test_AddProduct_Once|run',
                 'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run'],
            42: ['tests/testAddProducts.py::test_AddProduct_Once|run',
                 'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                 'tests/test_add_products.py::test_AddProduct_Once|run',
                 'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run'],
            16: ['tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                 'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                 'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                 'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run'],
            12: ['tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                 'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                 'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                 'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run']},
            '/home/runner/work/CharmFL-dev/CharmFL-dev/test_project/products/directory/example2.py': {
                25: ['tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run'],
                27: ['tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                     'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run'],
                8: ['test/testAddProducts2.py::test_AddProduct_Once|run',
                    'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                    'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                    'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run'],
                9: ['test/testAddProducts2.py::test_AddProduct_Once|run',
                    'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                    'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                    'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run'],
                34: ['test/testAddProducts2.py::test_AddProduct_Once|run',
                     'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                     'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run'],
                35: ['test/testAddProducts2.py::test_AddProduct_Once|run',
                     'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                     'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run'],
                38: ['test/testAddProducts2.py::test_AddProduct_Once|run',
                     'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run'],
                15: ['test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                     'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run'],
                16: ['test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run',
                     'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run'],
                18: ['test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'],
                19: ['test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'],
                36: ['test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'],
                11: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                     'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run'],
                17: ['test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run']}}
        method_cov.line_coverage_result = line_cov
        method_cov.make_coverage_with_context()

        expected_method_cov = {'/home/runner/work/CharmFL-dev/CharmFL-dev/test_project/products/example.py': {
            'addToCart::8::12': {'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                                 'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run',
                                 'tests/testAddProducts.py::test_AddProduct_Once|run',
                                 'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                                 'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                                 'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run',
                                 'tests/test_add_products.py::test_AddProduct_Once|run',
                                 'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run'},
            'getProductCount::37::42': {'tests/testAddProducts.py::test_AddProduct_MoreThanOnce|run',
                                        'tests/testAddProducts.py::test_AddProduct_Once|run',
                                        'tests/test_add_products.py::test_AddProduct_Once|run',
                                        'tests/test_add_products.py::test_AddProduct_MoreThanOnce|run'},
            'removeFromCart::15::23': {'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                                       'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                                       'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                                       'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run'}},
                               '/home/runner/work/CharmFL-dev/CharmFL-dev/test_project/products/directory/example2.py': {
                                   'printProductsInCart::24::29': {
                                       'tests/test_add_products.py::test_RemoveProduct_WhenTheresOne|run',
                                       'tests/testAddProducts.py::test_RemoveProduct_WhenTheresOne|run',
                                       'tests/testAddProducts.py::test_RemoveProduct_FromMoreThanOne|run',
                                       'tests/test_add_products.py::test_RemoveProduct_FromMoreThanOne|run'},
                                   'addToCart::7::11': {'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                                                        'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                                                        'test/testAddProducts2.py::test_AddProduct_Once|run',
                                                        'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'},
                                   'getProductCount::33::38': {
                                       'test/testAddProducts2.py::test_AddProduct_MoreThanOnce|run',
                                       'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                                       'test/testAddProducts2.py::test_AddProduct_Once|run',
                                       'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'},
                                   'removeFromCart::14::21': {
                                       'test/testAddProducts2.py::test_RemoveProduct_FromMoreThanOne|run',
                                       'test/testAddProducts2.py::test_RemoveProduct_WhenTheresOne|run'}}}
        self.assertEqual(expected_method_cov, method_cov.get_coverage_with_context())


if __name__ == '__main__':
    unittest.main()
