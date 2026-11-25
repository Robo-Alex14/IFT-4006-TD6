package td6.multiComponent.glue;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Étantdonné;
import td6.Main;
import td6.application.BookDto;
import td6.multiComponent.utils.ResponseMemory;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class BookStepDefinition {

    private static final String BOOK_URI = "/libraries/" + Main.DEFAULT_LIBRARY_NAME + "/books";

    @Étantdonné("Un livre avec le titre {string} et l'auteur {string} est ajouté à la librairie")
    public void unLivreAjoute(String titre, String auteur){
        BookDto bookDtoAdded = new BookDto(titre, auteur);
        given().header("Content-Type", "application/json")
                .port(Main.PORT)
                .body(bookDtoAdded)
                .when()
                .post(BOOK_URI);
    }

    @Quand("le contenu de la librairie est obtenu")
    public void contenuLibrairieEstObtenue(){
        ResponseMemory.response = given()
                .port(Main.PORT)
                .when()
                .get(BOOK_URI);
    }

    @Alors("le livre {string} est présent")
    public void livreEstPresent(String titre){
        var books = ResponseMemory.response
                .then().extract().jsonPath().getObject("books", BookDto[].class);

        assertTrue(Arrays.stream(books).anyMatch(book -> book.title.equals(titre)));
    }

    @Alors("le code de statut est {int}")
    public void codeDeStatutEst(int code){
        ResponseMemory.response.then().statusCode(code);
    }

}
