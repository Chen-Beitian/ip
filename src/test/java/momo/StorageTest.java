package momo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void load_fileMissing_returnsEmptyList() {
        Storage storage = new Storage(tempDir.resolve("momo.txt").toString());
        List<Task> tasks = storage.load();
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    @Test
    public void saveThenLoad_roundTrip_preservesTypeAndDoneState() throws Exception {
        Path file = tempDir.resolve("momo.txt");
        Storage storage = new Storage(file.toString());
        Todo t1 = new Todo("read");
        Deadline d1 = new Deadline("return book", LocalDateTime.of(2019, 12, 2, 18, 0));
        Event e1 = new Event("meeting", "2020-01-01", "2020-01-02");
        d1.markAsDone();
        storage.save(List.of(t1, d1, e1));
        List<Task> loaded = storage.load();
        assertEquals(3, loaded.size());
        assertTrue(loaded.get(0) instanceof Todo);
        assertEquals("[T][ ] read", loaded.get(0).toString());
        assertTrue(loaded.get(1) instanceof Deadline);
        assertTrue(loaded.get(1).isDone());
        assertEquals(d1.toString(), loaded.get(1).toString());
        assertTrue(loaded.get(2) instanceof Event);
        assertEquals(e1.toString(), loaded.get(2).toString());
    }
}
