package ca.ubc.ece.eece210.mp3.ast;

import java.util.Set;

import ca.ubc.ece.eece210.mp3.Element;
import ca.ubc.ece.eece210.mp3.Catalogue;

public class OrNode extends ASTNode {

    public OrNode(Token token) {
	super(token);
    }

    /**
     * This method interprets the OrNode and returns the set of Elements 
     * that satisfies the conditions of the OrNode.
     * 
     * @return Set of Elements that satisfy the criteria indcated by the OrNode.
     */
    @Override
    public Set<Element> interpret(Catalogue argument) {
	// TODO Auto-generated method stub
	return null;
    }

}
