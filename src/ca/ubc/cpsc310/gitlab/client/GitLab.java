package ca.ubc.cpsc310.gitlab.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.List;

import ca.ubc.cpsc310.gitlab.client.user.IUser;
import ca.ubc.cpsc310.gitlab.client.service.LoadUsersService;
import ca.ubc.cpsc310.gitlab.client.service.LoadUsersServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GitLab implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR_MSG = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final FlexTable flexTable = new FlexTable();
	final LoadUsersServiceAsync service = GWT.create(LoadUsersService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		service.getUsers(new AsyncCallback<List<IUser>>() {
			@Override
			public void onSuccess(List<IUser> result) {				
				displayUsers(result);
				Window.alert("Yay! Done");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(SERVER_ERROR_MSG);				
			}

			});
	}
	
	/**
	 * Used to display users 
	 * @param users
	 */
	public void displayUsers(List<IUser> users) {

		RootPanel.get("root").add(flexTable);
		
		flexTable.setText(0,0, "Name");
		
		flexTable.setText(0,1,"Language");
		flexTable.setText(0,2, "Cart Size");
		flexTable.setText(0,3, "Wish List Size");		
		flexTable.setStyleName("centered-table", true);
		flexTable.setStyleName("centered-text", true);
		
				
		for(int i=0; i < users.size(); i++)	{
		
			IUser user = users.get(i);
			
			flexTable.setText(i+1,0,user.getName());
			
			if(user.getLanguage().trim().equals("EN")) {
				flexTable.setWidget(i+1, 1, new Image("uk.png"));
			} else if (user.getLanguage().trim().equals("FR")) {
				flexTable.setWidget(i+1, 1, new Image("fr.png"));
			} else  {
				flexTable.setText(i+1,1,user.getLanguage());
			}
			
			flexTable.setText(i+1,2,String.valueOf(user.getShoppingCart().size()));
			flexTable.setText(i+1,3,String.valueOf(user.getWishList().size()));
		}
	}
}
