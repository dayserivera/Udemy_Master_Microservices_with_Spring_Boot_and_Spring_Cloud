package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	
	private static int usersCount = 3;
	private static int postsCount = 9;
	
	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
		
	}
	
	public User findOne(int id) {
		for(User user:users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(int id) {
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
	
	public Post savePost(User user, Post post) {
		if(post.getId() == null) {
			post.setId(++postsCount);
		}
		/*if(user.getPosts() == null) {
			user.setPosts(new ArrayList<Post>());
		}
		user.getPosts().add(post);*/
		return post;
	}
	
	public Post findOnePost(User user, int id) {
		/*for(Post post: user.getPosts()) {
			if(post.getId() == id) {
				return post;
			}
		}*/
		return null;
	}
}
