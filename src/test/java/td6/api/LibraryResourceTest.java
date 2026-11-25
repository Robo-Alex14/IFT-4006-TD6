package td6.api;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import td6.api.LibraryResource;
import td6.api.errors.ErrorResponse;
import td6.application.BookDto;
import td6.application.LibraryService;
import td6.domain.LibraryName;
import td6.domain.exceptions.BookAlreadyPresentException;
import td6.domain.exceptions.LibraryNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

//Tests composants pour tester les DTO et les exceptions des API calls
//On mock les services pour tester les ressources

@ExtendWith(MockitoExtension.class)
public class LibraryResourceTest extends JerseyTest {
    private LibraryService libraryServiceMock;

    private static final String ANY_LIBRARY_NAME = "Allo";
    private static final BookDto ANY_BOOK_DTO = new BookDto();

    @Override
    protected Application configure(){
        libraryServiceMock = mock(LibraryService.class);
        return new ResourceConfig().packages("td6")
                .register(JacksonFeature.withoutExceptionMappers())
                .register(new LibraryResource(libraryServiceMock));
    }

    @Test
    public void givenBookSuccessfullyAdded_whenAddBook_thenReturnCreated(){
        Response response = callPostBookApi();

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenNotExistingBook_whenAddBook_thenReturnNotFound(){
        doThrow(new LibraryNotFoundException())
                .when(libraryServiceMock)
                .addBook(eq(ANY_LIBRARY_NAME), any());

        Response response = callPostBookApi();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("LIBRARY_NOT_FOUND", errorResponse.error);
    }

    @Test
    public void givenBookAlreadyExists_whenAddBook_thenReturnBadRequest(){
        doThrow(new BookAlreadyPresentException())
                .when(libraryServiceMock)
                .addBook(eq(ANY_LIBRARY_NAME), any());

        Response response = callPostBookApi();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
        assertEquals("BOOK_ALREADY_PRESENT", errorResponse.error);
    }

    private Response callPostBookApi(){
        WebTarget target = target("/libraries/" + ANY_LIBRARY_NAME + "/books");
        return target.request().post(Entity.json(ANY_BOOK_DTO));
    }

}