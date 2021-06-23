import os
import sys

try:
    import pip
    print(os.path.dirname(pip.__file__))
except ImportError:
    sys.exit(1)