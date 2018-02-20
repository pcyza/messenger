package pl.pcyza.messenger.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

public class UserTest {
	
	@Test
	public void testCreateUser() {
		final String login = "adam";
		
		User user = new User(login);
		
		assertEquals(login, user.getLogin());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateNullUser() {
		new User(null);
	}
	
	@Test
	public void testGetFollows() {
		User user = new User("adam");
		
		Set<User> follows = user.getFollows();
		
		assertNotNull(follows);
	}
	
	@Test
	public void testGetPosts() {
		User user = new User("adam");
		
		Set<Post> posts = user.getPosts();
		
		assertNotNull(posts);
	}
}
