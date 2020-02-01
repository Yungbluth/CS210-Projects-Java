Matthew Yungbluth
PA5 GraphAlgPerf
CSC 210 Section C Spring 2019

Timing results for big11.mtx

heuristic: cost = 3.3969307170000005, 6 milliseconds
mine: cost = 1.652256247, 531 milliseconds
backtrack: cost = 1.3566775349999998, 563 milliseconds

Heuristic is significantly faster because it sacrifices accuracy by always progressing to the immediately lower cost option instead of checking all options for the lowest total cost. My algorithm is slightly faster but also slightly less accurate than the complete backtracking solution because it skips multiple options in the name of speed.
