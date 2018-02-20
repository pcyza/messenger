package pl.pcyza.messenger.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Post implements Comparable<Post> {
	private UUID uuid;
	private LocalDateTime timestamp;
	private String message;
	private User owner;
	
	public Post(User owner, String message) {
		this.uuid = UUID.randomUUID();
		this.timestamp = LocalDateTime.now();
		this.message = Objects.toString(message, "");
		if (owner == null) {
			throw new IllegalArgumentException("owner cannot be null");
		}
		this.owner = owner;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public User getOwner() {
		return owner;
	}
	
	@Override
	public int compareTo(Post anotherPost) {
		if (anotherPost == null) {
			return 1;
		}
		if (uuid.equals(anotherPost.uuid)) {
			return 0;
		}
		if (timestamp.equals(anotherPost.timestamp)) {
			return 1;
		}
		return -1 * timestamp.compareTo(anotherPost.timestamp);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Post)) {
			return false;
		}
		Post anotherPost = (Post)o;
		return uuid.equals(anotherPost.uuid);
	}
}
