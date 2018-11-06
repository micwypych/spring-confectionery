package pl.edu.agh.kis.databases.confectionery.infrastructure;

import lombok.val;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.kis.databases.confectionery.domain.Chocolate;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChocolateResourceTests {
    @Mock
    private ChocolateRepository repository;

    private ChocolateResource endpoint;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        endpoint = new ChocolateResource(repository);
    }

    @Test
    public void getOnChocolateEndpointReturnsAllChocolatesFromRepository() {
        //setup:
        Chocolate choco = new Chocolate("123", "choco", "walnut", "dark 80%", "strawberry", "short description", Money.of(0.89, "PLN"), 43);
        Chocolate chico = new Chocolate("456", "chico", "none", "milk", "marzepan", "longer description", Money.of(1.12, "PLN"), 52);
        Chocolate checo = new Chocolate("789", "checo", "hazelnut", "dark 60%", "cream", "ciekawy opis", Money.of(0.67, "PLN"), 76);

        when(repository.findAll()).thenReturn(Arrays.asList(choco, chico, checo));

        //when:
        val chocolates = endpoint.retrieveAllChocolates();
        //then:
        assertThat(chocolates).containsExactlyInAnyOrder(choco, chico, checo);
    }

    @Test
    public void postOnChocolateEndpointStoresNewChocolateIntoRepository() {
        //setup:
        Chocolate checo = new Chocolate("789", "checo", "hazelnut", "dark 60%", "cream", "ciekawy opis", Money.of(0.67, "PLN"), 76);

        when(repository.save(checo)).thenReturn(checo);

        //when:
        val chocolateResource = endpoint.createChocolate(checo);
        //then:
        verify(repository).save(checo);
        assertThat(chocolateResource.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(chocolateResource.getHeaders().get("Location")).containsExactly("http://localhost/789");
        assertThat(chocolateResource.getBody()).isNull();
    }

    @Test
    public void putOnChocolateEndpointStoresNewChocolateIntoRepository() {
        //setup:
        Chocolate checo = new Chocolate("789", "checo", "hazelnut", "dark 60%", "cream", "ciekawy opis", Money.of(0.67, "PLN"), 76);

        when(repository.save(checo)).thenReturn(checo);

        //when:
        val chocolateResource = endpoint.updateChocolate("789", checo);
        //then:
        verify(repository).save(checo);
        assertThat(chocolateResource.getLinks()).containsExactlyInAnyOrder(new Link("http://localhost/chocolates/789", "self"), new Link("http://localhost/chocolates", "all-chocolates"));
        assertThat(chocolateResource.getContent()).isEqualTo(checo);
    }

    @Test
    public void putOnChocolateEndpointUpdatesAlreadyExistingChocolateInRepository() {
        //setup:
        Chocolate chocolateAfter = new Chocolate("416", "name after", "nuts after", "chocolate after", "ganache after", "description after", Money.of(0.87, "PLN"), 44);
        Chocolate chocolateBefore = new Chocolate("416", "name before", "nuts before", "chocolate before", "ganache before", "description before", Money.of(0.13, "PLN"), 33);

        when(repository.findById("416")).thenReturn(Optional.of(chocolateBefore));
        when(repository.save(chocolateAfter)).thenReturn(chocolateAfter);

        //when:
        val chocolateResource = endpoint.updateChocolate("416", chocolateAfter);
        //then:
        assertThat(chocolateResource.getLinks()).containsExactlyInAnyOrder(new Link("http://localhost/chocolates/416", "self"), new Link("http://localhost/chocolates", "all-chocolates"));
        assertThat(chocolateResource.getContent()).isEqualTo(chocolateAfter);
    }

    @Test
    public void getSingleOnChocolateEndpointRetrievesChocolateFromRepository() {
        //setup:
        Chocolate checo = new Chocolate("678", "checo", "hazelnut", "dark 60%", "cream", "ciekawy opis", Money.of(0.67, "PLN"), 76);

        when(repository.findById("678")).thenReturn(Optional.of(checo));

        //when:
        val chocolateResource = endpoint.retrieveChocolate("678");
        //then:
        assertThat(chocolateResource.getLinks()).containsExactlyInAnyOrder(new Link("http://localhost/chocolates/678", "self"), new Link("http://localhost/chocolates", "all-chocolates"));
        assertThat(chocolateResource.getContent()).isEqualTo(checo);
    }

    @Test
    public void getSingleNonExistingChocolateOnEndpointThrowsException() {
        //setup:
        when(repository.findById("123")).thenReturn(Optional.empty());

        //expect:
        assertThatThrownBy(() -> endpoint.retrieveChocolate("123")).hasMessage("id-123").hasCause(null);
    }

    @Test
    public void deleteOnEndpointDeletesChocolateFromRepository() {
        //when:
        endpoint.deleteChocolate("L39");

        //expect:
        verify(repository).deleteById("L39");
    }
}
