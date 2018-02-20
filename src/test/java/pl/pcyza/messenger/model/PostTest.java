package pl.pcyza.messenger.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PostTest {
	
	@Test
	public void testCreatePost() {
		String login = "adam";
		String message = "hello word";
		User user = new User(login);
		
		Post post = new Post(user, message);
		
		assertEquals(user, post.getOwner());
		assertEquals(message, post.getMessage());
		assertNotNull(post.getTimestamp());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullUser() {
		String message = "hello word";
		
		new Post(null, message);
	}
	
	@Test
	public void testNullMessage() {
		String login = "adam";
		User user = new User(login);
		
		Post post = new Post(user, null);

		assertEquals("", post.getMessage());
	}
}
