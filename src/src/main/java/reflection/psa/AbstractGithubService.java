package reflection.psa;

import lombok.RequiredArgsConstructor;
import me.baek.apicaller.GitHubRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class AbstractGithubService {

    private final RestTemplateBuilder restTemplateBuilder;

    public GitHubRepository[] getRepositories() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject("https://api.github.com/users/baekjungho/repos", GitHubRepository[].class);
    }
}
