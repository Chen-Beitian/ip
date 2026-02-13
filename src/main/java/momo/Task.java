package momo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    protected final String description;
    private boolean isDone;
    private final Set<String> tags = new HashSet<>();

    /**
     * Constructs a task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description should not be null";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns whether this task is completed.
     *
     * @return True if completed, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon of this task (done or not done).
     *
     * @return Status icon string.
     */
    protected String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns whether this task's description contains the given keyword.
     *
     * @param keyword Keyword to search for.
     * @return True if the description contains the keyword, false otherwise.
     */
    public boolean containsKeyword(String keyword) {
        assert keyword != null : "Keyword should not be null";
        return description.contains(keyword);
    }

    /**
     * Adds a tag to this task.
     * The tag will be normalized to lowercase and stored without '#'.
     *
     * @param tag Tag to add (with or without '#').
     */
    public void addTag(String tag) {
        String normalized = normalizeTag(tag);
        if (!normalized.isEmpty()) {
            tags.add(normalized);
        }
    }

    /**
     * Removes a tag from this task.
     * The tag will be normalized to lowercase and matched without '#'.
     *
     * @param tag Tag to remove (with or without '#').
     */
    public void removeTag(String tag) {
        String normalized = normalizeTag(tag);
        if (!normalized.isEmpty()) {
            tags.remove(normalized);
        }
    }

    /**
     * Returns whether this task has the given tag.
     *
     * @param tag Tag to check (with or without '#').
     * @return True if the task has the tag, false otherwise.
     */
    public boolean hasTag(String tag) {
        String normalized = normalizeTag(tag);
        return !normalized.isEmpty() && tags.contains(normalized);
    }

    /**
     * Returns a defensive copy of the tags set.
     *
     * @return Copy of tags.
     */
    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Returns the tags formatted for storage as a comma-separated string.
     *
     * @return Comma-separated tags (e.g., "cs,fun"), or empty string if none.
     */
    public String getTagsForStorage() {
        if (tags.isEmpty()) {
            return "";
        }
        List<String> sorted = new ArrayList<>(tags);
        Collections.sort(sorted);
        return String.join(",", sorted);
    }

    /**
     * Loads tags from a storage field (comma-separated).
     * Existing tags are cleared before loading.
     *
     * @param tagsField Tags field from storage (e.g., "cs,fun"). Can be null/blank.
     */
    public void loadTagsFromStorage(String tagsField) {
        tags.clear();
        if (tagsField == null || tagsField.isBlank()) {
            return;
        }

        String[] parts = tagsField.split(",", -1);
        for (String part : parts) {
            String normalized = normalizeTag(part);
            if (!normalized.isEmpty()) {
                tags.add(normalized);
            }
        }
    }

    /**
     * Returns tags formatted for display, prefixed with '#'.
     *
     * @return Display string (e.g., "#cs #fun"), or empty string if none.
     */
    protected String getTagsForDisplay() {
        if (tags.isEmpty()) {
            return "";
        }
        List<String> sorted = new ArrayList<>(tags);
        Collections.sort(sorted);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sorted.size(); i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append("#").append(sorted.get(i));
        }
        return sb.toString();
    }

    private static String normalizeTag(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        if (trimmed.charAt(0) == '#') {
            trimmed = trimmed.substring(1).trim();
        }
        trimmed = trimmed.toLowerCase();
        if (trimmed.isEmpty() || trimmed.contains(",")) {
            return "";
        }
        return trimmed;
    }

    @Override
    public String toString() {
        String tagsDisplay = getTagsForDisplay();
        if (tagsDisplay.isEmpty()) {
            return getStatusIcon() + " " + description;
        }
        return getStatusIcon() + " " + description + " " + tagsDisplay;
    }
}
