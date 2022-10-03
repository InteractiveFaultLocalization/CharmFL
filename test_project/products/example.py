# This is a simple example script for FL
# It should represent a webshop, or at least part of it
from directory import example2

cart = {}


def addToCart(product):
    if (product not in cart.keys()):
        cart[str(product)] = 1
    else:
        cart[str(product)] = cart[(str(product))] + 2  # bug -> should be 1


def removeFromCart(product):
    example2.printProductsInCart()
    if (product in cart.keys()):
        if (cart[str(product)] > 1):
            cart[str(product)] = cart[str(product)] - 1
        elif (cart[str(product)] == 1):
            del cart[str(product)]
    else:
        print("Something's fishy")


def printProductsInCart():
    print("Your cart: ")

    getProductCount()
    example2.printProductsInCart()

    for product_name in cart.keys():
        product_count = cart[str(product_name)]
        # print("* " + str(product_name) + ": " + str(product_count))


def getProductCount(product):
    addToCart("apple")
    if (product not in cart.keys()):
        return 0
    else:
        return cart[str(product)]


"""
# We need
#        4 apples
#        1 orange juice
#        cheeser mum
#        U

for i in range(1,4):
    addToCart("apple")

removeFromCart("apple")
addToCart("OJ")
addToCart("cheese")
addToCart("Ur mum")

printProductsInCart()
"""

if __name__ == "__main__":
    printProductsInCart()
