package algorithms;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;


//Hikmet Orhun Güley S004428 Department of Industrial Engineering


public class PrimAndKruskal {

    /*
     * The following square matrix represents a weighted undirected graph. the value
     * in (i,j) position indicates the cost between i and j node. Zeros indicate no
     * connection
     */

    static int[][] matrix = { { 0, 3, 0, 2, 0, 0, 0, 0, 4 }, // 0
            { 3, 0, 0, 0, 0, 0, 0, 4, 0 }, // 1
            { 0, 0, 0, 6, 0, 1, 0, 2, 0 }, // 2
            { 2, 0, 6, 0, 1, 0, 0, 0, 0 }, // 3
            { 0, 0, 0, 1, 0, 0, 0, 0, 8 }, // 4
            { 0, 0, 1, 0, 0, 0, 8, 0, 0 }, // 5
            { 0, 0, 0, 0, 0, 8, 0, 0, 0 }, // 6
            { 0, 4, 2, 0, 0, 0, 0, 0, 0 }, // 7
            { 4, 0, 0, 0, 8, 0, 0, 0, 0 } // 8
    };
    /*
     * static int[][] matrix = { { 0, 2, 3, 0, 0 }, // 0 { 2, 0, 15, 2, 0 }, // 1 {
     * 3, 15, 0, 0, 13}, // 2 { 0, 2, 0, 0, 9}, // 3 { 0, 0, 13, 9, 0}, // 4 };
     */

    static int Node = matrix.length;
    static int[][] Edge = new int[Node][Node];
    static int NotConnected = 999999;

    public static void MakeGraph() {
        for (int i = 0; i < Node; i++) {
            for (int j = 0; j < Node; j++) {
                Edge[i][j] = matrix[i][j];
                if (Edge[i][j] == 0)// If Node i and Node j are not connected
                    Edge[i][j] = NotConnected;
            }
        }
        // Print the graph representation matrix.
        for (int i = 0; i < Node; i++) {
            for (int j = 0; j < Node; j++)
                if (Edge[i][j] != NotConnected)
                    System.out.print(" " + Edge[i][j] + " ");
                else // when there is no connection
                    System.out.print(" * ");
            System.out.println();
        }
    }

    static class Edge implements Comparable<Edge> {

        public int startingNode;
        public int endingNode;
        public int cost;

        public Edge(int startingNode, int endingNode, int cost) {
            // TODO Auto-generated constructor stub

            this.startingNode = startingNode;
            this.endingNode = endingNode;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge arg0) {
            // TODO Auto-generated method stub
            return this.cost - arg0.cost;
        }

    }

    public static void Prim() {

        System.out.println("OUPUT OF PRIM'S ALGORITHM:");
        // Write the code for Prim algorithm to find the minimum cost spanning tree here
        // and print the result in console with the following format:

        // ArrayList<PriorityQueue<Graph.Edge>> QueueList = new ArrayList();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue();

        int[] costOfVertices = new int[Node];
        boolean[] exploredOrNot = new boolean[Node];
        ArrayList<Edge> exploredEdges = new ArrayList();

        exploredOrNot[0] = true;

        for (int i = 0; i < Node; i++) {
            costOfVertices[i] = Integer.MAX_VALUE;

            if (Edge[0][i] != NotConnected) {

                priorityQueue.add(new Edge(0, i, Edge[0][i]));

            }

        }




        int exploredNode = 0;
        int totalExploredNodes = 0;
        int totalCost = 0;

        while (totalExploredNodes < Node - 1) {


            while (exploredOrNot[(priorityQueue.peek().endingNode)] == true) {

                priorityQueue.poll();
            }
            exploredNode = priorityQueue.peek().endingNode;
            exploredOrNot[exploredNode] = true;
            totalCost = totalCost + priorityQueue.peek().cost;
            exploredEdges.add(priorityQueue.poll());
            totalExploredNodes = totalExploredNodes + 1;

            for (int i = 0; i < Node; i++) {

                if (Edge[exploredNode][i] != NotConnected ) {

                    priorityQueue.add(new Edge(exploredNode, i, Edge[exploredNode][i]));

                }

            }
        }

        System.out.println("Cost of Spanning Tree = " + totalCost);

        System.out.println("Edges of the minimum cost spanning tree:");

        for (int i = 0; i < exploredEdges.size(); i++) {

            System.out.print("[" + exploredEdges.get(i).startingNode + ", " + exploredEdges.get(i).endingNode + "] ");

        }

        System.out.println();
        /*
         * ==========================OUTPUT FORMAT=========================== Minimun
         * Cost of Spanning Tree = "....... "
         *
         * Edges of the minimum cost spanning tree:
         * ".........................................................."
         * ==================================================================
         */

    }

    public static void Kruskal() {
        System.out.println("OUPUT OF KRUSKAL'S ALGORITHM:");
        // Write the code for Kruskal algorithm to find the minimum cost spanning tree
        // here

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>();
        boolean[] exploredOrNot = new boolean[Node];

        ArrayList<Edge> exploredEdges = new ArrayList();
        ArrayList<LinkedList<Integer>> set = new ArrayList();
        LinkedList<Integer> list = new LinkedList();

        for (int i = 0; i < Node; i++) {

            list = new LinkedList();
            list.add(i);
            set.add(list);

            for (int j = 0; j < Node; j++) {

                if (Edge[i][j] != NotConnected) {

                    priorityQueue.add(new Edge(i, j, Edge[i][j]));

                }
            }
        }

        int endingNode;
        int startingNode;
        int totalCost = 0;
        boolean InSameSet = true;
        int startingNodeSet = 0;
        int endingNodeSet = 0;

        for (int i = 0; i < Node - 1; i++) {

            startingNode = priorityQueue.peek().startingNode;
            endingNode = priorityQueue.peek().endingNode;

            while (InSameSet == true) {
                for (int j = 0; j < set.size(); j++) {

                    if (set.get(j).contains(startingNode))
                        startingNodeSet = j;
                    if (set.get(j).contains(endingNode))
                        endingNodeSet = j;

                }
                if (endingNodeSet == startingNodeSet) {
                    InSameSet = true;
                    priorityQueue.poll();
                    startingNode = priorityQueue.peek().startingNode;
                    endingNode = priorityQueue.peek().endingNode;
                } else {
                    InSameSet = false;
                }

            }

            if (InSameSet == false) {


                totalCost = totalCost + priorityQueue.peek().cost;

                while (set.get(endingNodeSet).isEmpty() == false) {

                    set.get(startingNodeSet).add(set.get(endingNodeSet).poll());

                    // set.get(startingNodeSet).add(endingNode);
                    // set.remove(endingNodeSet);
                }

                set.remove(endingNodeSet);
                exploredEdges.add(priorityQueue.poll());
                InSameSet = true;
            }

        }

        System.out.println("Cost of Spanning Tree = " + totalCost);

        System.out.println("Edges of the minimum cost spanning tree:");

        for (int i = 0; i < exploredEdges.size(); i++) {

            System.out.print("[" + exploredEdges.get(i).startingNode + ", " + exploredEdges.get(i).endingNode + "] ");

        }

        System.out.println();

        // and print the result in console with the following format:
        /*
         * ==========================OUTPUT FORMAT=========================== Minimun
         * Cost of Spanning Tree = "....... "
         *
         * Edges of the minimum cost spanning tree:
         * ".........................................................."
         * ==================================================================
         */
    }

    public static void main(String[] args) {

        MakeGraph();
        Prim();
        Kruskal();


    }

}
