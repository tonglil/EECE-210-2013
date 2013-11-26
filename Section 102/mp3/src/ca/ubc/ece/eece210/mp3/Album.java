package ca.ubc.ece.eece210.mp3;

import java.util.ArrayList;

/**
 * 
 * @author Sathish Gopalakrishnan
 * 
 * This class contains the information needed to represent 
 * an album in our application.
 * 
 */

public final class Album extends Element {
	/**
	 * Builds an album with the given title, performer and song list
	 * 
	 * @param title
	 *            the title of the album
	 * @param author
	 *            the performer 
	 * @param songlist
	 * 			  the list of songs in the album
	 */
	public Album(String title, String performer, ArrayList<String> songlist) {
		// TODO implement this
	}

	/**
	 * Builds an album from the string representation of the object. It is used
	 * when restoring an album from a file.
	 * 
	 * @param stringRepresentation
	 *            the string representation
	 */
	public Album(String stringRepresentation) {
		// TODO implement this
	}

	/**
	 * Returns the string representation of the given album. The representation
	 * contains the title, performer and songlist, as well as all the genre
	 * that the book belongs to.
	 * 
	 * @return the string representation
	 */
	public String getStringRepresentation() {
		// TODO implement this
		return "";
	}

	/**
	 * Add the book to the given genre
	 * 
	 * @param genre
	 *            the genre to add the album to.
	 */
	public void addToGenre(Genre genre) {
		// TODO implement this
	}

	/**
	 * Returns the genre that this album belongs to.
	 * 
	 * @return the genre that this album belongs to
	 */
	public Genre getGenre() {
		// TODO implement this
		return null;
	}

	/**
	 * Returns the title of the album
	 * 
	 * @return the title
	 */
	public String getTitle() {
		// TODO implement this
		return null;
	}

	/**
	 * Returns the performer of the album
	 * 
	 * @return the performer
	 */
	public String getPerformer() {
		// TODO implement this
		return null;
	}

	/**
	 * An album cannot have any children (it cannot contain anything).
	 */
	@Override
	public boolean hasChildren() {
		return false;
	}
}
