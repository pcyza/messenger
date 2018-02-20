package pl.pcyza.messenger.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import pl.pcyza.messenger.exception.DataNotFoundException;
import pl.pcyza.messenger.exception.DataViolationException;
import pl.pcyza.messenger.model.Post;
import pl.pcyza.messenger.model.User;
import pl.pcyza.messenger.repository.impl.UserRepositoryImpl;

public class UserRepositoryTest {

	private UserRepository userRepository;
	
	@Before
	public void init() {
		userRepository = new UserRepositoryImpl();
	}
	
	@Test
	public void testAddUser() {
		User user = new User("adam");
		
		userRepository.addUser(user);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullUser() {
		User user = null;
		
		userRepository.addUser(user);
	}
	
	@Test(expected=DataViolationException.class)
	public void testAddExistingUser() {
		User user1 = new User("adam");
		User user2 = new User("adam");
		
		userRepository.addUser(user1);
		userRepository.addUser(user2);
	}
	
	@Test
	public void testAddDifferentUsers() {
		User user1 = new User("adam");
		User user2 = new User("monica");
		
		userRepository.addUser(user1);
		userRepository.addUser(user2);
	}
	
	@Test
	public void testGetUser() {
		User user = new User("adam");
		userRepository.addUser(user);
		
		User userFromRepo = userRepository.getUser(user.getLogin());
		
		assertEquals(user, userFromRepo);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetUserByNullLogin() {
		String login = null;
		
		userRepository.getUser(login);
	}
	
	@Test
	public void testGetNotExistingUser() {
		String login = "adam";
		
		User user = userRepository.getUser(login);
		
		assertNull(user);
	}
	
	@Test
	public void testGetDifferentUsers() {
		User user1 = new User("adam");
		User user2 = new User("monica");
		userRepository.addUser(user1);
		userRepository.addUser(user2);
		
		User userFromRepo1 = userRepository.getUser(user1.getLogin());
		User userFromRepo2 = userRepository.getUser(user2.getLogin());
		
		assertEquals(user1, userFromRepo1);
		assertEquals(user2, userFromRepo2);
	}
	
	@Test
	public void testGetFollows() {
		User user = new User("adam");
		userRepository.addUser(user);
		
		Set<User> follows = userRepository.getFollows(user.getLogin());
		
		assertNotNull(follows);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testGetFollowsByNullUser() {
		String login = null;
		
		userRepository.getFollows(login);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testGetFollowsByNotExistingUser() {
		String login = "adam";
		
		userRepository.getFollows(login);
	}
	
	@Test
	public void testGetPosts() {
		User user = new User("adam");
		userRepository.addUser(user);
		
		Set<Post> posts = userRepository.getPosts(user.getLogin());
		
		assertNotNull(posts);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testGetPostsByNullUser() {
		String login = null;
		
		userRepository.getPosts(login);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testGetPostsByNotExistingUser() {
		String login = "adam";
		
		userRepository.getPosts(login);
	}
	
	@Test
	public void testAddFollower() {
		User adam = new User("adam");
		User monica = new User("monica");
		userRepository.addUser(adam);
		userRepository.addUser(monica);
		
		userRepository.addFollower(monica.getLogin(), 
				adam.getLogin());
		
		assertEquals(adam.getFollows().size(), 1);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testAddNullFollower() {
		User monica = new User("monica");
		userRepository.addUser(monica);
		
		userRepository.addFollower(monica.getLogin(), 
				null);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testAddNullUserToFollow() {
		User adam = new User("adam");
		userRepository.addUser(adam);
		
		userRepository.addFollower(null, 
				adam.getLogin());
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testAddNotExistingFollower() {
		User adam = new User("adam");
		User monica = new User("monica");
		userRepository.addUser(monica);
		
		userRepository.addFollower(monica.getLogin(), 
				adam.getLogin());
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testAddNotExistingUserToFollow() {
		User adam = new User("adam");
		User monica = new User("monica");
		userRepository.addUser(adam);
		
		userRepository.addFollower(monica.getLogin(), 
				adam.getLogin());
	}
	
	@Test(expected=DataViolationException.class)
	public void testAddAlreadyAddedFollower() {
		User adam = new User("adam");
		User monica = new User("monica");
		userRepository.addUser(adam);
		userRepository.addUser(monica);
		
		userRepository.addFollower(monica.getLogin(), 
				adam.getLogin());
		userRepository.addFollower(monica.getLogin(), 
				adam.getLogin());
	}
}
