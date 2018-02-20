package pl.pcyza.messenger.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import pl.pcyza.messenger.model.Post;
import pl.pcyza.messenger.model.User;
import pl.pcyza.messenger.repository.impl.PostRepositoryImpl;

public class PostRepositoryTest {

	private PostRepository postRepository;
	
	@Before
	public void init() {
		postRepository = new PostRepositoryImpl();
	}
	
	@Test
	public void testAddPost() {
		User user = new User("adam");
		Post post = new Post(user, "hello word");
		
		postRepository.addPost(post);
		
		assertEquals(1, user.getPosts().size());
	}
	
	@Test
	public void testAddPostCheckOrder() throws Exception {
		User user = new User("adam");
		Post post1 = new Post(user, "hello word");
		Thread.sleep(2);
		Post post2 = new Post(user, "the newest post");
		
		postRepository.addPost(post1);
		postRepository.addPost(post2);
		
		List<Post> posts = user.getPosts().stream().collect(Collectors.toList());
		
		assertEquals(posts.get(0), post2);
		assertEquals(posts.get(1), post1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullPost() {
		Post post = null;
		
		postRepository.addPost(post);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddPostWithLargeMessage() {
		String message = Stream.generate(() -> "*")
				.limit(150).collect(Collectors.joining());
		User user = new User("adam");
		Post post = new Post(user, message);
		
		postRepository.addPost(post);
	}
}