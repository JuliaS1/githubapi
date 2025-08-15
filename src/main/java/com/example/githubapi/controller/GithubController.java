package com.example.githubapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.githubapi.dto.ErrorResponse;
import com.example.githubapi.dto.RepositoryResponse;
import com.example.githubapi.exception.GithubUserNotFoundException;
import com.example.githubapi.service.GithubService;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repos")
    public List<RepositoryResponse> getRepositories(@PathVariable String username) {
        return githubService.getRepositories(username);
    }

    @ExceptionHandler(GithubUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(GithubUserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }
}
