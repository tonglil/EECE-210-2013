package ca.ubc.ece.eece210.mp3.ast;

import java.util.Set;

import ca.ubc.ece.eece210.mp3.Element;
import ca.ubc.ece.eece210.mp3.Catalogue;

public class MatchesNode extends ASTNode {

    public MatchesNode(Token token) {
	super(token);
    }

    /**
     * This method interprets the MatchesNode and returns the set of Elements 
     * that satisfies the conditions of the MatchesNode.
     * 
     * @return Set of Elements that satisfy the criteria indcated by the MatchesNode.
     */
    @Override
    public Set<Element> interpret(Catalogue argument) {
	// TODO Auto-generated method stub
	return null;
    }

}
