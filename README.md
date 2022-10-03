![Build ](https://github.com/SzatmariA/pyfl/actions/workflows/main.yml/badge.svg)


![](src/main/resources/CharmFL_logo_with_both_texts_shadsie_little_1_270x300.png)

# CharmFL
A Fault Localization tool for Python, which can be integrated in to the latest PyCharm IDE. 
After running the python unittests in your project is constructs a spectrum and the suspiciousness scores for each program element (e.g. statement, method).
CharmFL also provides context for localizing faults. You are able to see different visualizations of your code and program spectra. 

CharmFL uses [pyan3](https://pypi.org/project/pyan3/) to construct the static call-graphs for your python project. From which we define "**far**" contexts for each program statement. 
For example, in the following program code, the far contexts are commented next to the statement:

```python
def addToCart(product):
    if (product not in cart.keys()): 
        cart[str(product)] = 1 #Far context: getProductCount
    else:
        cart[str(product)] = cart[(str(product))] + 2  #Far context: getProductCount

def getProductCount(product):
    addToCart("apple") #Far context: addToCart
    if (product not in cart.keys()):
        return 0 #Far context: addToCart
    else:
        return cart[str(product)] #Far context: addToCart
```
Therefore, for each method the caller and callee methods are considered as far context. 
This is aggregated down to statement level, i.e. for each statement in the method the far context is considered as the method's far context.



# Steps to set up 

 First, download the compressed ZIP from the releases

 Then: 

- Drag and drop into your PyCharm IDE

**Or** 

- Go to File/Settings
- Click on Plugins
- Click on the Gear Icon
- Select "Install Plugin from Disk..."
- Select <path-to-charmfl-zip>/CharmFL-1.3.0.zip

# Main features
- Run Fault Localization on 3 different granularities (statement, method, class)
- Change metrics (Tarantula, DStar, Ochiai, Wong2)
- Change tie strategy for ranking
- Generate the static call-graph for your python project
  - CharmFL highlights the far context for the selected method
- Sunburst visualization 
- Heatmap visualization
