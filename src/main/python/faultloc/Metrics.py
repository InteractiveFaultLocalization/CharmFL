import json
import math
import collections
import sys


class Metrics:
    def __init__(self):
        self.scores_for_code_elements = {}
        self.scores = []
        self.elements = []
        self.end_res = {"files": []}

    def create_scores_from(self, spectra_object):
        spectrum = spectra_object.get_spectrum()
        for code_element, stats in spectrum.items():
            tarantula_score = self.tarantula(stats["ef"], stats["ep"], stats["nf"], stats["np"])
            ochiai_score = self.ochiai(stats["ef"], stats["ep"], stats["nf"], stats["np"])
            wong2_score = self.wong2(stats["ef"], stats["ep"], stats["nf"], stats["np"])
            dstar_score = self.dstar(stats["ef"], stats["ep"], stats["nf"], stats["np"])
            self.scores_for_code_elements[code_element] = {"tar": tarantula_score, "och": ochiai_score,
                                                           "wong2": wong2_score, "dstar": dstar_score}

    def get_scores(self):
        return self.scores_for_code_elements

    def tarantula(self, ef, ep, nf, np):
        ef = float(ef)
        ep = float(ep)
        nf = float(nf)
        np = float(np)

        try:
            score = (ef / (ef + nf)) / ((ef / (ef + nf)) + (ep / (ep + np)))
        except ZeroDivisionError:
            score = 0.0
        if score is None:
            return 0.0
        return round(score, 2)

    def ochiai(self, ef, ep, nf, np):
        ef = float(ef)
        ep = float(ep)
        nf = float(nf)
        np = float(np)
        try:
            score = ef / math.sqrt((ef + nf) * (ef + ep))

        except ZeroDivisionError:
            score = 0.0
        if score is None:
            return 0.0
        return round(score, 2)

    def wong2(self, ef, ep, nf, np):
        ef = float(ef)
        ep = float(ep)
        nf = float(nf)
        np = float(np)
        score = ef - ep

        if score is None:
            return 0.0
        return round(score, 2)

    def dstar(self, ef, ep, nf, np):
        ef = float(ef)
        ep = float(ep)
        nf = float(nf)
        np = float(np)
        try:
            score = math.pow(ef, 2) / (ef + nf)

        except ZeroDivisionError:
            score = 0.0
        if score is None:
            return 0.0
        return round(score, 2)