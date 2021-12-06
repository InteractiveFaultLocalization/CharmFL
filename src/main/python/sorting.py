import numpy as np

def scores_sorting(scores, statements): 
    sorted_scores = []
    sorted_statements = []
    a = np.array(scores)
    b = np.array(statements)
    a_inds = a.argsort()
    sorted_scrs = a[a_inds[::-1]]
    sorted_stats = b[a_inds[::-1]]
    for i in range(len(sorted_scrs)):
        sorted_scores.append(round(sorted_scrs[i],3))
        sorted_statements.append(sorted_stats[i])

    return sorted_scores, sorted_statements
