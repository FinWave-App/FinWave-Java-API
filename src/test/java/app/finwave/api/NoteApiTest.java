package app.finwave.api;

import org.junit.jupiter.api.*;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NoteApiTest {

    private FinWaveClient client;
    protected long noteId;

    @BeforeAll
    void setUp() throws ExecutionException, InterruptedException {
        client = DemoLogin.createDemoAndLogin();
    }

    @Test
    @Order(1)
    void newNote() throws ExecutionException, InterruptedException {
        OffsetDateTime notificationTime = OffsetDateTime.now().plusDays(1);
        var note = client.runRequest(new NoteApi.NewNoteRequest(notificationTime, "Test note")).get();
        assertNotNull(note);
        assertTrue(note.noteId() > 0);

        noteId = note.noteId();
    }

    @Test
    @Order(2)
    void getNote() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new NoteApi.GetNoteRequest(noteId)).get();
        assertNotNull(result);
        Assertions.assertEquals("Test note", result.text());
    }

    @Test
    @Order(3)
    void editNote() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new NoteApi.EditNoteRequest(noteId, "Edited test note")).get();
        assertNotNull(result);
        Assertions.assertEquals("Note edited", result.message());

        var editedNote = client.runRequest(new NoteApi.GetNoteRequest(noteId)).get();
        assertNotNull(editedNote);
        Assertions.assertEquals("Edited test note", editedNote.text());
    }

    @Test
    @Order(4)
    void editNoteNotificationTime() throws ExecutionException, InterruptedException {
        OffsetDateTime newNotificationTime = OffsetDateTime.now().plusDays(2);
        var result = client.runRequest(new NoteApi.EditNoteNotificationTimeRequest(noteId, newNotificationTime)).get();
        assertNotNull(result);
        Assertions.assertEquals("Note notification time edited", result.message());

        var editedNote = client.runRequest(new NoteApi.GetNoteRequest(noteId)).get();
        assertNotNull(editedNote);
        assertTrue(newNotificationTime.truncatedTo(ChronoUnit.SECONDS).isEqual(
                editedNote.notificationTime().truncatedTo(ChronoUnit.SECONDS)
        ));
    }

    @Test
    @Order(5)
    void getNotesList() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new NoteApi.GetNotesListRequest()).get();
        assertNotNull(result);
        assertNotNull(result.notes());

        var noteEntryOptional = result.notes().stream().filter(n -> n.noteId() == noteId).findAny();
        Assertions.assertTrue(noteEntryOptional.isPresent());

        var noteEntry = noteEntryOptional.get();
        Assertions.assertEquals("Edited test note", noteEntry.text());
    }

    @Test
    @Order(6)
    void getImportantNotes() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new NoteApi.GetImportantNotesRequest()).get();
        assertNotNull(result);
        assertNotNull(result.notes());
    }

    @Test
    @Order(7)
    void deleteNote() throws ExecutionException, InterruptedException {
        var result = client.runRequest(new NoteApi.DeleteNoteRequest(noteId)).get();
        assertNotNull(result);
        Assertions.assertEquals("Note deleted", result.message());
    }
}