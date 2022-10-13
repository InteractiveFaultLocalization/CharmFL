import sys
from pathlib import Path
path = Path(__file__).parent.resolve()
sys.path.append(str(path.parent.absolute()))

import unittest
from cov.Class_Coverage import Class_Coverage


class TestClassCoverage(unittest.TestCase):
    def test_get_coverage_with_context_from_empty_classes(self):
        class_cov = Class_Coverage()
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
        class_cov.line_coverage_result = line_cov
        class_cov.make_coverage_with_context()
        expected_class_cov = {'/home/runner/work/CharmFL-dev/CharmFL-dev/test_project/products/example.py': {},
                              '/home/runner/work/CharmFL-dev/CharmFL-dev/test_project/products/directory/example2.py': {}}

        self.assertEqual(expected_class_cov, class_cov.get_coverage_with_context())

    # def test_get_coverage_with_context_from_valid_classes(self):
    #     class_cov = Class_Coverage()
    #     line_cov = {'C:\\Users\\user\\Documents\\test_me\\example.py': {
    #         6: ['test/test_example_e.py::MyTestCase::test_congratulations_for_10|run',
    #             'test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
    #             'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #             'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         3: ['test/test_example_e.py::MyTestCase::test_error|run',
    #             'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #             'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         9: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
    #             'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #             'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         10: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         11: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         13: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'],
    #         18: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         19: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_10|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         14: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'],
    #         15: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         16: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run',
    #              'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run'],
    #         12: ['test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run']}}
    #     class_cov.line_coverage_result = line_cov
    #     class_cov.make_coverage_with_context()
    #     expected_class_cov = {'C:\\Users\\user\\Documents\\test_me\\example.py': {
    #         'Error::1::3': {'test/test_example_e.py::MyTestCase::test_guess_the_num_with_9|run',
    #                         'test/test_example_e.py::MyTestCase::test_error|run',
    #                         'test/test_example_e.py::MyTestCase::test_guess_the_num_with_11|run'}}}
    #     self.assertEqual(expected_class_cov, class_cov.get_coverage_with_context())


if __name__ == '__main__':
    unittest.main()
