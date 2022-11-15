import os
import sys

from error_codes import PIP_NOT_FOUND

try:
    import pip
    print(os.path.dirname(pip.__file__))
except ImportError:
    sys.exit(PIP_NOT_FOUND)
