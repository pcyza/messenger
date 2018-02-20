package pl.pcyza.messenger.service;

import java.util.Set;

import pl.pcyza.messenger.model.PostDTO;
import pl.pcyza.messenger.model.Request;

public interface MessengerService {
	
	PostDTO addPost(String login, Request message);
	
	void addFollower(Request toBeFollow, String follower);
	
	Set<PostDTO> getPostsAddedByUser(String login);
	
	Set<PostDTO> getPostsAddedByFollows(String login);
}
