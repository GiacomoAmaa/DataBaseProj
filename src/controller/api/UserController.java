package controller.api;

/**
 * The controller of the User profile, a user must be able to see his games, his stats,
 * to sign in for available tournaments and also search for other users
 */
public interface UserController {

	public void search(String input);
	
	public void tournaments();
	
	public void stats();
	
	public void games();
	
}
