package com.example.githubapi;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.githubapi.dto.RepositoryResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubapiApplicationTests {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void shouldReturnRepositoriesForValidUser() {
        String username = "octocat"; 
        var response = restTemplate.getForObject(
                "http://localhost:" + port + "/api/github/" + username + "/repos",
                RepositoryResponse[].class
        );

        assertThat(response).isNotNull();
        assertThat(response).allSatisfy(repo -> {
            assertThat(repo.name()).isNotEmpty();
            assertThat(repo.ownerLogin()).isEqualTo(username);
            assertThat(repo.branches()).allSatisfy(branch -> {
                assertThat(branch.name()).isNotEmpty();
                assertThat(branch.lastCommitSha()).isNotEmpty();
            });
        });
    }
}
