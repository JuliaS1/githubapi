package com.example.githubapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.githubapi.dto.BranchResponse;
import com.example.githubapi.dto.RepositoryResponse;
import com.example.githubapi.exception.GithubUserNotFoundException;

@Service
public class GithubService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String REPOS_URL = "https://api.github.com/users/{username}/repos";
    private static final String BRANCHES_URL = "https://api.github.com/repos/{owner}/{repo}/branches";

    @SuppressWarnings("unchecked")
    public List<RepositoryResponse> getRepositories(String username) {
        try {
            Object[] repos = restTemplate.getForObject(REPOS_URL, Object[].class, username);

            return Arrays.stream(repos)
                    .map(repo -> (Map<String, Object>) repo)
                    .filter(repo -> !(Boolean) repo.get("fork"))
                    .map(repo -> {
                        String repoName = (String) repo.get("name");
                        String ownerLogin = ((Map<String, Object>) repo.get("owner")).get("login").toString();

                        Object[] branchesArray = restTemplate.getForObject(BRANCHES_URL, Object[].class, ownerLogin, repoName);
                        List<BranchResponse> branches = Arrays.stream(branchesArray)
                                .map(b -> (Map<String, Object>) b)
                                .map(b -> {
                                    Map<String, Object> commit = (Map<String, Object>) b.get("commit");
                                    return new BranchResponse((String) b.get("name"), (String) commit.get("sha"));
                                })
                                .toList();

                        return new RepositoryResponse(repoName, ownerLogin, branches);
                    })
                    .toList();

        } catch (HttpClientErrorException.NotFound e) {
            throw new GithubUserNotFoundException("User " + username + " not found");
        }
    }
}
