package reflection.psa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PSAService {

    private final AbstractGithubService githubService;

    public int getPoint() {
        // API 호출
        GitHubRepository[] repositories = githubService.getRepositories();

        // 로직
        int point = 0;

        for (GitHubRepository repository : repositories) {
            if(repository.getName().contains("Spring")) {
                point += 10;
            } else {
                point++;
            }
        }

        return point;
    }
}
