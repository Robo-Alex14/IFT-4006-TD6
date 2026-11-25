package td6.infra.persistence.library;

import org.apache.groovy.util.Maps;
import org.junit.jupiter.api.Test;
import td6.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//C'est des tests composants
class HibernateLibraryRepositoryTest {

    private static final LibraryName ANY_LIBRARY_NAME = new LibraryName("ww");
    private static final LibraryName ANY_OTHER_LIBRARY_NAME = new LibraryName("gg");
    private static final Author ANY_AUTHOR = new Author("Bob");
    private static final BookName ANY_BOOK_NAME = new BookName("ww");
    private static final Book ANY_BOOK = new Book(ANY_BOOK_NAME, ANY_AUTHOR);
    private static final  Map<BookName, Book> ANY_BOOKS = new HashMap<>(Maps.of(ANY_BOOK_NAME, ANY_BOOK));

    private HibernateLibraryRepository hibernateLibraryRepository = new HibernateLibraryRepository();

  @Test
    public void givenSavedLibrary_whenFindByName_thenReturnLibrary(){
      Library library = new Library(ANY_LIBRARY_NAME, ANY_BOOKS);
      hibernateLibraryRepository.saveOrUpdate(library);

      Library foundLibrary = hibernateLibraryRepository.findByName(ANY_LIBRARY_NAME);
      assertEquals("ww", foundLibrary.getName().toString());
      assertEquals(1, foundLibrary.getBooks().size());
  }

  @Test
    public void givenNotSavedLibrary_whenfFindByName_thenReturnNull(){
      Library foundLibrary = hibernateLibraryRepository.findByName(ANY_OTHER_LIBRARY_NAME);
      assertNull(foundLibrary);
  }

  @Test
    public void givenSavedLibrary_whenUpdate_thenUpdateLibrary(){
      givenSavedLibrary();
      Map<BookName, Book> NO_BOOKS = new HashMap<>();
      Library libraryToUpdate = new Library(ANY_LIBRARY_NAME, NO_BOOKS);

      hibernateLibraryRepository.saveOrUpdate(libraryToUpdate);

      Library updatedLibrary = hibernateLibraryRepository.findByName(ANY_LIBRARY_NAME);
      assertEquals(ANY_LIBRARY_NAME, updatedLibrary.getName());
      assertEquals(0, updatedLibrary.getBooks().size());
  }

  private void givenSavedLibrary(){
      Library library_saved = new Library(ANY_LIBRARY_NAME, ANY_BOOKS);
      hibernateLibraryRepository.saveOrUpdate(library_saved);
  }
}