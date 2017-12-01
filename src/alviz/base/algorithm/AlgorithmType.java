/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alviz.base.algorithm;

import alviz.base.graph.GraphClass;

/**
 *
 * @author baskaran
 */
public enum AlgorithmType {

    BFS("BFS", GraphClass.GRAPH),
    DFS("DFS", GraphClass.GRAPH),
    SSSSTAR("SSS Star", GraphClass.GRAPH),
            ;
    
    private String MenuItemName;
    public String getMenuItemName() { return MenuItemName; }

    private GraphClass graphClass;
    public GraphClass getGraphClass() { return graphClass; }

    private AlgorithmType(String name, GraphClass gc) {
        MenuItemName = name;
        graphClass = gc;
    }

    
    public boolean isGameTree() {
        switch (this) {
//            case MIN_MAX:
//            case ALPHA_BETA:
            case SSSSTAR:
                return true;
            default:
                return false;
        }
//    return true;
    }
    

    //public boolean isSeqAlign() {
    //    switch (this) {
    //        case SEQUENCE_ALIGNMENT:
    //            return true;
    //        default:
    //            return false;
    //    }
    //}

}
