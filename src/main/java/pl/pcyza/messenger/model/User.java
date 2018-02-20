package pl.pcyza.messenger.model;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class User implements Comparable<User> {
	private String login;
	private Set<Post> posts = new ConcurrentSkipListSet<>();
	private Set<User> follows = new ConcurrentSkipListSet<>();
	
	public User(String login) {
		if (login == null) {
			throw new IllegalArgumentException("login cannot be null");
		}
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}
	
	public Set<Post> getPosts() {
		return posts;
	}
	
	public Set<User> getFollows() {
		return follows;
	}
	
	@Override
	public String toString() {
		return login.toString();
	}
	
	@Override
	public int compareTo(User o) {
		if (o == null) {
			return 1;
		}
		else {
			return login.compareTo(o.login);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null
				|| !(obj instanceof User)) {
			return false;
		}
		return login.equals(((User)obj).login);
	}
	
	@Override
	public int hashCode() {
		return login.hashCode();
	}
}
