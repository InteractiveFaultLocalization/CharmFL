# This is a simple example script for FL
# It should represent a webshop, or at least part of it
from directory import example2

cart = {}



def add_to_cart(product):
    if (product not in cart.keys()):
        cart[str(product)] = 1
    else:
        cart[str(product)] = cart[(str(product))] + 1


def remove_from_cart(product):
    if (product in cart.keys()):
        if (cart[str(product)] > 1):
            cart[str(product)] = cart[str(product)] - 2
        elif (cart[str(product)] == 1):
            del cart[str(product)]
    else:
        print("Something's fishy")


def print_products_in_cart():
    print("Your cart: ")

    product_count = 0

    for product_name in cart.keys():
        product_count = get_product_count(product_name)
        print("* " + str(product_name) + ": " + str(product_count))
    print("No. Products in cart:", str(product_count) + "/" + str(get_all_product_num()))


def get_product_count(product):
    if (product not in cart.keys()):
        return 0
    else:
        return cart[str(product)]


def get_all_product_num():
    return sum(cart.values())


if __name__ == "__main__":
    import sys

    for line in sys.stdin:
        if 'Exit' == line.rstrip():
            break

        print(f'Processing Message from sys.stdin *****{line}*****')
        add_to_cart(line)
        print_products_in_cart()
    print("Done")
