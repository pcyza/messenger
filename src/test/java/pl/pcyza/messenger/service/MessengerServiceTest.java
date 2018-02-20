package pl.pcyza.messenger.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import pl.pcyza.messenger.model.Post;
import pl.pcyza.messenger.model.PostDTO;
import pl.pcyza.messenger.model.Request;
import pl.pcyza.messenger.model.User;
import pl.pcyza.messenger.repository.PostRepository;
import pl.pcyza.messenger.repository.UserRepository;
import pl.pcyza.messenger.service.impl.MessengerServiceImpl;

public class MessengerServiceTest {

	PostRepository postRepository;
	UserRepository userRepository;
	MessengerService messengerService;
	
	@Before
	public void init() {
		postRepository = mock(PostRepository.class);
		userRepository = mock(UserRepository.class);
		messengerService = new MessengerServiceImpl(
				userRepository, postRepository);
	}
	
	@Test
	public void testAddPost() {
		Request message = Request.getMessageRequest("hello word");
		String login = "adam";
		User user = new User(login);
		when(userRepository.getUser(login)).thenReturn(user);
		
		PostDTO post = messengerService.addPost(login, message);
		
		verify(userRepository, never()).addUser(user);
		verify(postRepository, times(1)).addPost(any());
		assertEquals(message.getMessage(), post.getMessage());
		assertEquals(login, post.getLogin());
		assertNotNull(post.getTimestamp());
		
	}
	
	@Test
	public void testAddPostWithNewUser() {
		Request message = Request.getMessageRequest("hello word");
		String login = "adam";
		User user = new User(login);
		when(userRepository.getUser(login)).thenReturn(null);
		
		PostDTO post = messengerService.addPost(login, message);
		
		verify(userRepository, times(1)).addUser(user);
		verify(postRepository, times(1)).addPost(any());
		assertEquals(message.getMessage(), post.getMessage());
		assertEquals(login, post.getLogin());
		assertNotNull(post.getTimestamp());
		
	}
	
	@Test
	public void testGetPostsAddedByUser() {
		String login = "adam";
		User user = new User(login);
		Post[] posts = {new Post(user, "hello word"),
				new Post(user, "newest post")};
		Set<Post> postsSet = Arrays.stream(posts)
				.collect(Collectors.toCollection(() -> new LinkedHashSet<>()));
		when(userRepository.getPosts(login)).thenReturn(postsSet);
		
		Set<PostDTO> results = messengerService.getPostsAddedByUser(login);
		
		int i = 0;
		for (PostDTO post : results) {
			assertEquals(posts[i].getMessage(), post.getMessage());
			assertEquals(login, post.getLogin());
			assertNotNull(post.getTimestamp());
			i++;
		}
	}
	
	@Test
	public void testPostsAddedByFollows() throws Exception {
		String dominicLogin = "dominic";
		String adamLogin = "adam";
		User adam = new User(adamLogin);
		String monicaLogin = "monica";
		User monica = new User(monicaLogin);
		Post post1 = new Post(adam, "post1");
		Thread.sleep(1);
		Post post2 = new Post(monica, "post2");
		Thread.sleep(1);
		Post post3 = new Post(adam, "post3");
		Thread.sleep(1);
		Post post4 = new Post(monica, "post4");
		adam.getPosts().add(post1);
		adam.getPosts().add(post3);
		monica.getPosts().add(post2);
		monica.getPosts().add(post4);
		User[] users = {adam, monica};
		when(userRepository.getFollows(dominicLogin)).thenReturn(
				Arrays.stream(users).collect(Collectors.toSet()));
		
		Set<PostDTO> results = messengerService.getPostsAddedByFollows(dominicLogin);
		
		int i = 4;
		for(PostDTO post : results) {
			assertEquals("post"+i, post.getMessage());
			assertNotNull(post.getTimestamp());
			i--;
		}
		
	}
}
