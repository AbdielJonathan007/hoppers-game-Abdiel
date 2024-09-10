# hoppers-game-Abdiel
Overview
The FrogGraph project is a Java application that models a graph of frog arrangements. Each node in the graph represents a unique configuration of frogs, and edges represent valid hops between configurations. The goal of the project is to use breadth-first search (BFS) to explore all possible frog arrangements starting from a given configuration and to identify all possible winning arrangements where exactly one frog remains.

Key Components
FrogArrangement: Represents a configuration of frogs on a board. This class is used to create and manipulate different frog arrangements.
FrogGraph: Constructs and manages a graph of FrogArrangement instances. It uses BFS to explore all possible arrangements starting from an initial configuration.
Files
FrogGraph.java: Contains the main class for the project, including methods for creating the graph, printing solutions, and checking adjacency between arrangements.
FrogArrangement.java: (Assumed) Contains the class definition for FrogArrangement, including methods for checking valid moves and determining winning states.
Frog_inputs.csv: (Example) A CSV file containing test cases for frog arrangements. Each row represents a frog configuration.
