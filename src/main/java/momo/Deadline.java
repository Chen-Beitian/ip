package momo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that has a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter OUTPUT_DATE_TIME = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
    private final LocalDateTime by;

    /**
     * Constructs a deadline task.
     *
     * @param description Description of the task.
     * @param by Deadline time string.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline date/time.
     *
     * @return Deadline date/time.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Parses a user input date/time string.
     * Accepts either "yyyy-MM-dd" or "yyyy-MM-dd HHmm".
     *
     * @param text User input.
     * @return Parsed LocalDateTime.
     */
    public static LocalDateTime parseBy(String text) {
        String trimmed = text.trim();
        if (trimmed.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return LocalDate.parse(trimmed).atStartOfDay();
        }
        if (trimmed.matches("\\d{4}-\\d{2}-\\d{2}\\s+\\d{4}")) {
            String[] parts = trimmed.split("\\s+");
            LocalDate date = LocalDate.parse(parts[0]);
            LocalTime time = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("HHmm"));
            return LocalDateTime.of(date, time);
        }
        throw new IllegalArgumentException("Invalid date/time format");
    }

    @Override
    public String toString() {
        String formatted = by.toLocalTime().equals(LocalTime.MIDNIGHT)
                ? by.toLocalDate().format(OUTPUT_DATE)
                : by.format(OUTPUT_DATE_TIME);
        return "[D]" + super.toString() + " (by: " + formatted + ")";
    }
}
