# PairProcessor2
project designed to intake large amounts of data from a .txt file and convert this into data on the frequency and distribution of certain combinations of characters, which can be used to analyse keyboard efficiency in the age of texting.

main class can take data from .txt files, write and create .txt files. once a file is selected, the analyse method converts it to the appropriate letter, word and pair objects, including a count of the frequency of those objects. At the moment this can be displayed in the form of various data statistics, such as standard deviation of pair frequencies, or listed in order of frequency. 

The graph function of pair frequency distribution is slightly buggy, but not necessary for the purpose of the program, as the 
distribution was approximately poisson, and determining this was the purpose of the graph method.

The data cleaning method currently removes punctuation from pairs and letters, as well as conglomerating any two pairs of the form yx and xy into one yx pair with the combined count. the downside to this is currently this deletes the original data, so this could maybe be stored in a different array so it can still be accessed?
