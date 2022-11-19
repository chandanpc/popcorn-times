package com.telusko.joblisting.controller;

import com.telusko.joblisting.model.Post;
import com.telusko.joblisting.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostRepository repo;

    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/allPosts")
    public List<Post> getAllPosts()
    {
        System.out.println("---"+repo.findAll());
        return repo.findAll();
    }
    @PostMapping("/post")
    public Post postJob(@RequestBody Post post)
    {
        System.out.println("saveing---");
        return repo.save(post);
    }

}
