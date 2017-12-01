/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alviz.algorithm;
import alviz.base.graph.BaseGraph.Node;


public class State {
    private Node id;
    private boolean isSolved;
    private Double hValue;
    private boolean isKind;
    
    public boolean isKind() { //true for max false for min
        return isKind;
    }
 
    public void setIsKind(boolean isKind) { // true for max fase for min
        this.isKind = isKind;
    }

    public State(Node id, boolean isSolved, Double hValue,boolean isKind) {
        this.id = id;
        this.isSolved = isSolved;
        this.hValue = hValue;
        this.isKind = isKind;
    }

    public Node getId() {
        return id;
    }

    public void setId(Node id) {
        this.id = id;
    }

    public boolean isIsSolved() {
        return isSolved;
    }

    public void setIsSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    public Double gethValue() {
        return hValue;
    }

    public void sethValue(Double hValue) {
        this.hValue = hValue;
    }


    
}
