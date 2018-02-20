package pl.pcyza.messenger.repository.impl;

import org.springframework.stereotype.Component;

import pl.pcyza.messenger.model.Post;
import pl.pcyza.messenger.repository.PostRepository;

@Component
public class PostRepositoryImpl implements PostRepository {

	private static final int MAX_MESSAGE_SIZE = 140;
	
	@Override
	public void addPost(Post post) {
		validate(post);
		post.getOwner().getPosts().add(post);
	}
	
	private void validate(Post post) {
		if (post == null) {
			throw new IllegalArgumentException("post cannot be null");
		}
		if (post.getMessage().length() > MAX_MESSAGE_SIZE) {
			throw new IllegalArgumentException("message cannot by larger than "
					+ MAX_MESSAGE_SIZE);
		}
	}

}
