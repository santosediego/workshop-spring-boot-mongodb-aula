package com.santosediego.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.santosediego.workshopmongo.domain.Post;
import com.santosediego.workshopmongo.domain.User;
import com.santosediego.workshopmongo.dto.AuthorDTO;
import com.santosediego.workshopmongo.repository.PostRepository;
import com.santosediego.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		userRepository.deleteAll();
		postRepository.deleteAll();

		User diego = new User(null, "Diego Santos", "diego@gmail.com");
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(diego, maria, alex, bob));

		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu Viagem", "Vou viajar para São Paulo. Abraços",
				new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("15/06/2020"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		Post post3 = new Post(null, sdf.parse("20/06/2020"), "Eu sou um programador",
				"Tudo posso Naquele que me Fortalece!", new AuthorDTO(diego));

		postRepository.saveAll(Arrays.asList(post1, post2, post3));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		diego.getPosts().add(post3);
		
		userRepository.save(maria);
		userRepository.save(diego);
	}

}
