package p02_ExtendedDatabase;

import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import javax.xml.crypto.Data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DatabaseTest {

    private Database database;
    private static final Person [] PEOPLE = {new Person(1, "Desi")
                                           , new Person(2 , "Plamen")
                                           , new Person(3 , "Vasil")};
    @Before
    public void prepareDatabase() throws OperationNotSupportedException {
        database = new Database(PEOPLE);
    }


    @Test
    public void testConstructorHasCreatedValidObject() throws OperationNotSupportedException {


         Person [] databaseElements = database.getElements();
        assertArrayEquals("Arrays are not the same!", PEOPLE, databaseElements);

        assertEquals("Count of elements is incorrect", database.getElementsCount(), PEOPLE.length);

        assertEquals("Index is incorrect", PEOPLE.length - 1, database.getIndex());

    }

    @Test(expected = OperationNotSupportedException.class)
    public void testConstructorThrowExceptionMoreThanSixteenElements() throws OperationNotSupportedException {
         Person [] people = new Person [17];

        new Database(people);


    }

    @Test(expected = OperationNotSupportedException.class)
    public void testConstructorThrowExceptionLessOneElement() throws OperationNotSupportedException {
        Person [] people = new Person[0];
        new Database(people);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testAddShouldThrowExceptionNullParam() throws OperationNotSupportedException {

        database.add(null);
    }

    @Test
    public void testAddShouldAddElement() throws OperationNotSupportedException {


        Person [] elementsBefore = database.getElements();
        database.add(new Person(4, "Hristo"));

        Person [] elementsAfter = database.getElements();

        assertEquals("Invalid add operation!", elementsBefore.length + 1, elementsAfter.length);

        Person lastPerson = elementsAfter[elementsAfter.length - 1];
        assertEquals(lastPerson.getId(), 4);
        assertEquals(lastPerson.getUsername(), "Hristo");

    }

    @Test
    public void testRemoveLastElement() throws OperationNotSupportedException {

        Person [] elementsBefore = database.getElements();

        database.remove();

        Person [] elementsAfter = database.getElements();

        assertEquals("Invalid remove operation!" , elementsBefore.length - 1 , elementsAfter.length);

        Person currentLast = elementsAfter[elementsAfter.length - 1];

        assertEquals(currentLast.getUsername() , "Plamen");
        assertEquals(currentLast.getId() , 2);


    }

    @Test(expected = OperationNotSupportedException.class)
    public void testRemoveThrowExceptionInvalidIndex() throws OperationNotSupportedException {


        for (int i = 0; i < PEOPLE.length; i++) {
            database.remove();
        }

        database.remove();
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByUsernameThrowExceptionNullParam() throws OperationNotSupportedException {
        database.findByUsername(null);
    }

    @Test
    public void testFindByUsername() throws OperationNotSupportedException {
        Person person = database.findByUsername("Plamen");

        assertEquals("Invalid if of the person" , person.getId() , 2);
        assertEquals("Invalid username of the person" , person.getUsername(), "Plamen");
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByUsernameMoreThanPerson() throws OperationNotSupportedException {

        database.add(new Person(4,"Vasil"));

        database.findByUsername("Vasil");
    }


    @Test(expected = OperationNotSupportedException.class)
    public void testFindByUsernameNonExistingUser() throws OperationNotSupportedException {

        database.findByUsername("Pesho");

    }

    @Test
    public void testFindById() throws OperationNotSupportedException {

        Person person = database.findById(3);

        assertEquals(person.getId() , 3 );
        assertEquals(person.getUsername(), "Vasil");

    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByIdMoreThanOneId() throws OperationNotSupportedException {

        database.add(new Person(3 , "Ivan"));

        database.findById(3);


    }

    @Test(expected = OperationNotSupportedException.class)
    public void testFindByIdThrowExceptionNonExistingId() throws OperationNotSupportedException {
        database.findById(4);
    }

}
