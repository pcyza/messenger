package pl.pcyza.messenger.repository.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import pl.pcyza.messenger.exception.DataNotFoundException;
import pl.pcyza.messenger.exception.DataViolationException;
import pl.pcyza.messenger.model.Post;
import pl.pcyza.messenger.model.User;
import pl.pcyza.messenger.repository.UserRepository;

@Component
public class UserRepositoryImpl implements UserRepository {

	private Map<String, User> users = new ConcurrentHashMap<>();
	
	@Override
	public synchronized void addUser(User user) {
		validateUserAbsence(user);
		users.put(user.getLogin(), user);
	}

	@Override
	public void addFollower(String toBeFollow, String follower) {
		User userToFollow = getUserIfExists(toBeFollow);
		User newFollower = getUserIfExists(follower);
		synchronized (newFollower) {
			if (newFollower.getFollows().contains(userToFollow)) {
				throw new DataViolationException();
			}
			newFollower.getFollows().add(userToFollow);
		}
		
	}
	
	@Override
	public User getUser(String login) {
		if (login == null) {
			throw new IllegalArgumentException("login cannot be null");
		}
		return users.get(login);
	}

	@Override
	public Set<User> getFollows(String login) {
		User user = getUserIfExists(login);
		return user.getFollows();
		
	}

	@Override
	public Set<Post> getPosts(String login) {
		User user = getUserIfExists(login);
		return user.getPosts();
	}

	private User getUserIfExists(String login) {
		if (login == null) {
			throw new DataNotFoundException();
		}
		User user = users.get(login);
		if (user == null) {
			throw new DataNotFoundException();
		}
		return user;
	}
	
	private void validateUserAbsence(User user) {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		validateUserAbsence(user.getLogin());
	}
	
	private void validateUserAbsence(String login) {
		if (isUserExists(login)) {
			throw new DataViolationException();
		}
	}
	
	private boolean isUserExists(String login) {
		return users.get(login) != null;
	}
	
}
