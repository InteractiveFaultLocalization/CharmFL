import pytest
import example

def test_AddProduct_Once():
    example.cart.clear()
    example.add_to_cart("Apple")
    assert example.get_product_count("Apple") == 1

def test_RemoveProduct_WhenTheresOne():
    example.cart.clear()
    example.add_to_cart("Apple")
    example.remove_from_cart("Apple")
    assert example.get_product_count("Apple") == 0

def test_RemoveProduct_FromMoreThanOne():
    example.cart.clear()
    example.add_to_cart("Apple")
    example.add_to_cart("Apple")
    example.remove_from_cart("Apple")
    assert example.get_product_count("Apple") == 1

def test_AddProduct_MoreThanOnce():
    example.cart.clear()
    example.add_to_cart("Apple")
    example.add_to_cart("Apple")
    assert example.get_product_count("Apple") == 2