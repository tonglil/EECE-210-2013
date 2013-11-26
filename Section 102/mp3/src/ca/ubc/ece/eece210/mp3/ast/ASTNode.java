package ca.ubc.ece.eece210.mp3.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.ubc.ece.eece210.mp3.Element;
import ca.ubc.ece.eece210.mp3.Catalogue;

public abstract class ASTNode {
    protected List<ASTNode> children;
    protected Token token;

    // "by(...)", "matches(...)" and "in(...)" and matches all take arguments
    // e.g. matches("Title"). The String "Title" must be stored in this arguments
    // variable.
    protected String arguments = null;

    /**
     * This is the default constructor for ASTNode.
     * Creates a node given a token.
     */
    public ASTNode(Token token) {
	children = new ArrayList<ASTNode>(2);
	this.token = token;
    }

    /**
     * This method sets the arguments for the node.
     * @param arguments: a String that represents the arguments
     */
    public void setArguments(String arguments) {
	this.arguments = arguments;
    }

    /**
     * Add a child node to the ASTNode.
     * @param node that is to be added as a child node.
     */
    public void addChild(ASTNode node) {
	children.add(node);
    }

    /**
     * Obtain the text associated with this node's token.
     * @return payload string that is associated with the token.
     */
    public String getText() {
	return token.getPayload();
    }

    /**
     * This abstract method needs to be implemented by 
     * any concrete classes that extend this abstract class.
     */ 
    public abstract Set<Element> interpret(Catalogue argument);

    /**
     * This method returns a string representation of the ASTNode.
     * @return string representation of ASTNode.
     */
    @Override
    public String toString() {
	if (children == null || children.size() == 0) {
	    return this.getText();
	}
	StringBuffer buf = new StringBuffer();
	if (children != null) {
	    buf.append("(");
	    buf.append(this.getText());
	    buf.append(' ');
	}
	for (int i = 0; children != null && i < children.size(); i++) {
	    ASTNode t = children.get(i);
	    if (i > 0) {
		buf.append(' ');
	    }
	    buf.append(t.toString());
	}
	if (children != null) {
	    buf.append(")");
	}
	return buf.toString();
    }
}
