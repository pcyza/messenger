package pl.pcyza.messenger.repository;

import java.util.Set;

import pl.pcyza.messenger.model.Post;
import pl.pcyza.messenger.model.User;

public interface UserRepository {
	void addUser(User user);
	void addFollower(String toBeFollow, String follower);
	User getUser(String login);
	Set<User> getFollows(String login);
	Set<Post> getPosts(String login);
}
