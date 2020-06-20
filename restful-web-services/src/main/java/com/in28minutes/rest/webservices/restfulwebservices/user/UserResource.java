package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.post.PostNotFoundException;

@RestController
public class UserResource {
	@Autowired
	private UserDaoService service;
	
	@Autowired
	private PostRepository postRepository;

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	@GetMapping("/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if(user == null)
			throw new UserNotFoundException("id-" + id);
		
		Resource<User> model = new Resource<>(user);
		 
		ControllerLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
	 
		model.add(linkTo.withRel("all-users"));
		
		return model;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();

		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		
		if(user == null)
			throw new UserNotFoundException("id-" + id);
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveAllUserPosts(@PathVariable int id){
		User user = service.findOne(id);
		
		if(user == null)
			throw new UserNotFoundException("id-" + id);
		
		return null;//user.getPosts();
	}
	
	@GetMapping("/users/{idUser}/posts/{idPost}")
	public Post retrieveUserPost(@PathVariable int idUser, @PathVariable int idPost) {
		User user = service.findOne(idUser);
		
		if(user == null)
			throw new UserNotFoundException("id-" + idUser);
		
		Post post = service.findOnePost(user, idPost);
		
		if(post == null)
			throw new PostNotFoundException("id-" + idPost);
		
		return post;
	}
	
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Object> createUserPost(@PathVariable int id, @RequestBody Post post){
		User user = service.findOne(id);
		
		if(user == null)
			throw new UserNotFoundException("id-" + id);
		
		Post savedPost = service.savePost(user, post);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
}
