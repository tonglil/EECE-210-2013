package ca.ubc.ece.eece210.mp3.ast;

import java.util.Set;

import ca.ubc.ece.eece210.mp3.Element;
import ca.ubc.ece.eece210.mp3.Catalogue;

public class InNode extends ASTNode {

    public InNode(Token token) {
	super(token);
    }

    /**
     * This method interprets the ANDNode and returns the set of Elements 
     * that satisfies the conditions of the ORNode.
     * 
     * @return Set of Elements that satisfy the criteria indcated by the ORNode.
     */
    @Override
    public Set<Element> interpret(Catalogue argument) {
	// TODO Auto-generated method stub
	return null;
    }

}
