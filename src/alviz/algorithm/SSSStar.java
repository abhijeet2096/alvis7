/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alviz.algorithm;

import alviz.algorithm.State;
import alviz.base.algorithm.Algorithm;
import alviz.base.graph.BaseGraph;
import alviz.base.graph.BaseGraph.Edge;
import alviz.base.graph.BaseGraph.Node;
//import alviz.graph.Graph;
import java.util.PriorityQueue;
import alviz.util.Pair;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author baskaran
 */
public class SSSStar extends Algorithm {

    private int branchingFactor = 2;
    private BaseGraph graph;
    private PriorityQueue<State> open;
    private PriorityQueue<State> leaves;
    private LinkedList<Pair<BaseGraph.Node,BaseGraph.Node>> closed;
    private HashMap<Node, Node> parents;
    private Node startNode;
    public SSSStar(BaseGraph graph) {
        super();
        this.graph = graph;
        this.open = null;
    }

    private void openNode(State n, Node p) {
        if (n != null) {
            graph.openNode(n.getId(), p);
            open.offer(n);
        }
    }
    private void closeNode(Pair<Node,Node> pn) {
        BaseGraph.Node n = pn.fst;
        if (n != null) {
            graph.closeNode(n);
            closed.add(pn);
        }
    }

    public void execute() throws Exception {
        open = new PriorityQueue(20, new Comparator<State>(){
            @Override
            public int compare(State o1, State o2) {
                return (int) (o2.gethValue() - o1.gethValue());
            }
        });
        leaves = new PriorityQueue(20, new Comparator<State>(){
            @Override
            public int compare(State o1, State o2) {
                return (int) (o2.gethValue() - o1.gethValue());
            }
        });
        closed = new LinkedList();
        parents = new HashMap<Node, Node>();
        startNode = this.graph.getStartNode();
        parents.put(startNode, null);
        System.out.println(startNode.getChildren().size());
        openNode(new State(startNode, false, Integer.MAX_VALUE, true), null);
        sssstar();
        generatePath();
        setStateEnded();
        show();
    }
    
    private Double getCost(HashMap<Node, Node> p, Node n,Node startNode) {
        Double cost = 0.0;
        Node curr = n;
        Node parent = null;
        while(true) {
            parent = p.get(curr);
            if(curr == startNode)
                break;
            Edge edge = graph.getEdge(curr, parent);
            cost += edge.cost;
            curr = parent;
        }
        return cost;
    }
    
    private void purge(Node n) {
        List<Node> successors = getAllSuccessors(n);
        Queue<State> q = new LinkedList<State>(open);
        for(State d : q) {
            if(successors.contains(d.getId())) {
                open.remove(d);
            }
        }
    }

    private void sssstar() throws Exception {
        while(true) {
            State p = open.poll();
            
            if(p == null)
                continue;
            System.out.println(getCost(parents ,p.getId(),startNode));
            closeNode(new Pair<Node, Node>(p.getId(), null));
            show();
            
            if(p.getId() == startNode && p.isIsSolved()) {
                System.out.println("end");
            }
            else {
                if(!p.isIsSolved()) { // LIVE
                    System.out.println("live");
                    if(p.getId().getChildren().size() == 1) {
                        System.out.println("1.1");
//                        if(getCost(parents, p.getId(),startNode) < p.gethValue()) {
//                            p.sethValue(getCost(parents, p.getId(),startNode));
//                        }
//                        else{
//                            p.sethValue(p.gethValue());
//                        }
                        p.sethValue(p.getId().value);
                        p.setIsSolved(true);
                        leaves.add(p);
                        System.out.println("Leaves in pQueue: " + leaves.size());
                    }
                    else if(!p.isKind()) { // J is Min node
                        List<Node> children = p.getId().getChildren();
                        System.out.println("1.2");
                        open.add(new State(children.get(1), false, p.gethValue(),!p.isKind())); // Add First
                        parents.put(children.get(1), p.getId());
                    }
                    else {
                        System.out.println("1.3");
                        List<Node> children = p.getId().getChildren();    
                        if(parents.get(p.getId()) == null)
                            {
                             for(int i = 0; i < children.size(); i++) {            // Because all given cases are graph so also getchildren returns neighbours due to which we have to assume that first index will always be parent
                                open.add(new State(children.get(i), false, p.gethValue(),!p.isKind()));
                                parents.put(children.get(i), p.getId());
                            
                            }
                            }
                        else{
                            for(int i = 1; i < children.size(); i++) {            // Because all given cases are graph so also getchildren returns neighbours due to which we have to assume that first index will always be parent
                                open.add(new State(children.get(i), false, p.gethValue(),!p.isKind()));
                                parents.put(children.get(i), p.getId());
                            
                            }
                        }
                        
                    }
                }
                else { // SOLVED
                    System.out.println("solved");
                    Node dad = parents.get(p.getId());
                    if(!p.isKind()) {
                        System.out.println("1.4");
                        purge(dad);
                        open.add(new State(dad, true, p.gethValue(),!p.isKind()));
                        
                        
                    }
                    else {
                        List<Node> children = dad.getChildren();
                        if(children.get(children.size()-1).equals(p.getId()) && children.size() >= 2) {
                            System.out.println("1.5");
                            open.add(new State(dad, true, p.gethValue(),!p.isKind()));
                           
                        }
                        else if(children.size() > 1) {
                            System.out.println("1.6");
//                            open.add(new Descriptor(children.get(2), false, p.gethValue(),p.isKind()));
//                            parents.put(children.get(2),dad);
                                for(int i = 1; i < children.size(); i++) {
                                    if(children.get(i).equals(p.getId())){
                                        open.add(new State(children.get(i+1), false, p.gethValue(),p.isKind()));
                                        parents.put(children.get(i+1),dad);
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
    
    private List<BaseGraph.Node> generatePath() {
        List<BaseGraph.Node> path=null;
        if (closed == null) return path;
        if (closed.isEmpty()) return path;

        Iterator<Pair<BaseGraph.Node,BaseGraph.Node>> closedList = ((LinkedList)closed).descendingIterator();
        if (!closedList.hasNext()) return path;

        Pair<BaseGraph.Node,BaseGraph.Node> pair = closedList.next();
        if (!pair.fst.isGoal()) {
            //System.out.printf("generatePath: pair.fst %s\n", pair.fst.getState().toString());
            return path;
        }

        path = new LinkedList<BaseGraph.Node>();
        // add to path
        pair.fst.setPath();
        path.add(pair.fst);
        while (pair.snd != null) {

            // add to path
            pair.snd.setPath();
            path.add(pair.snd);

            // add edge to path
            BaseGraph.Edge e = graph.getEdge(pair.fst, pair.snd);
            if (e != null) {
                e.setPath();
            }

            // search for predecessor pair
            BaseGraph.Node n = pair.snd;
            while (closedList.hasNext()) {
                pair = closedList.next();
                if (pair.fst == n) break;
            }
        }

        return path;
    }

    private List<Node> getAllSuccessors(Node n) {
        Queue<Node> live = new LinkedList<Node>();
        Queue<Node> succ = new LinkedList<Node>();
        live.add(n);
        while(!live.isEmpty()) {
            Node front = live.poll();
            if(front == null)
                continue;
            List<Node> children = front.getChildren();
            for(int i = 1; i < children.size(); i++) {
                live.add(children.get(i));
            }
            if(!front.equals(n))
                succ.add(front);
        }
        return new LinkedList<Node>(succ);
    }

}
