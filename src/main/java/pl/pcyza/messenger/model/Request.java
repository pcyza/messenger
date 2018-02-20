package pl.pcyza.messenger.model;

public class Request {
	private String message;
	private String user;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public static Request getMessageRequest(String message) {
		Request request = new Request();
		request.setMessage(message);
		return request;
	}
	
	public static Request getUserRequest(String user) {
		Request request = new Request();
		request.setUser(user);
		return request;
	}
}
