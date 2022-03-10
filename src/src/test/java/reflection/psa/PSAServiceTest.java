package reflection.psa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PSAServiceTest {

    private PSAService psaService;
    @Mock
    private AbstractGithubService abstractGithubService;

    @BeforeEach
    void setFixtures() {
        psaService = new PSAService(abstractGithubService);
    }

    @Test
    void getPoint() {
        GitHubRepository[] repositories = new GitHubRepository[]{new GitHubRepository()};
        when(abstractGithubService.getRepositories()).thenReturn(repositories);
        Assertions.assertThat(psaService.getPoint()).isNotPositive();
    }
}