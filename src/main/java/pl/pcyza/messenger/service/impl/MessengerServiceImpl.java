package pl.pcyza.messenger.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pl.pcyza.messenger.model.Post;
import pl.pcyza.messenger.model.PostDTO;
import pl.pcyza.messenger.model.Request;
import pl.pcyza.messenger.model.User;
import pl.pcyza.messenger.repository.PostRepository;
import pl.pcyza.messenger.repository.UserRepository;
import pl.pcyza.messenger.service.MessengerService;

@RestController
public class MessengerServiceImpl implements MessengerService {
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	@Autowired
	public MessengerServiceImpl(UserRepository userRepository,
			PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@Override
	@RequestMapping(value = "/post/{login}", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public PostDTO addPost(@PathVariable String login, 
			@RequestBody Request message) {
		User user = userRepository.getUser(login);
		if (user == null) {
			user = new User(login);
			userRepository.addUser(user);
		}
		Post post = new Post(user, message.getMessage());
		postRepository.addPost(post);
		return mapPost(post);
	}

	@Override
	@RequestMapping(value = "/followed/{follower}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void addFollower(@RequestBody Request toBeFollow, 
			@PathVariable String follower) {
		userRepository.addFollower(toBeFollow.getUser(), follower);
	}

	@Override
	@RequestMapping(value = "/post/{login}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Set<PostDTO> getPostsAddedByUser(@PathVariable String login) {
		return mapPosts(userRepository.getPosts(login));
	}

	@Override
	@RequestMapping(value = "/followed/{login}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Set<PostDTO> getPostsAddedByFollows(@PathVariable String login) {
		Set<Post> posts = new TreeSet<>();
		Set<User> follows = userRepository.getFollows(login);
		for (User user : follows) {
			posts.addAll(user.getPosts());
		}
		return mapPosts(posts);
	}
	
	private Set<PostDTO> mapPosts(Set<Post> posts) {
		if (posts == null) {
			return null;
		}
		return posts.stream().map(this::mapPost).collect(
				Collectors.toCollection(() -> new LinkedHashSet<>()));
	}
	
	private PostDTO mapPost(Post post) {
		if (post == null) {
			return null;
		}
		PostDTO postDTO = new PostDTO();
		postDTO.setLogin(post.getOwner().getLogin());
		postDTO.setMessage(post.getMessage());
		postDTO.setTimestamp(post.getTimestamp());
		return postDTO;
	}
}
