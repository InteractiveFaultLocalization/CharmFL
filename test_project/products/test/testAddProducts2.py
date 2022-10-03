import pytest
from directory import example2 as example

def test_AddProduct_Once():
    example.cart.clear()
    example.addToCart("Apple")
    assert example.getProductCount("Apple") == 1

def test_RemoveProduct_WhenTheresOne():
    example.cart.clear()
    example.addToCart("Apple")
    example.removeFromCart("Apple")
    assert example.getProductCount("Apple") == 0

def test_RemoveProduct_FromMoreThanOne():
    example.cart.clear()
    example.addToCart("Apple")
    example.addToCart("Apple")
    example.removeFromCart("Apple")
    assert example.getProductCount("Apple") == 1

def test_AddProduct_MoreThanOnce():
    example.cart.clear()
    example.addToCart("Apple")
    example.addToCart("Apple")
    assert example.getProductCount("Apple") == 2