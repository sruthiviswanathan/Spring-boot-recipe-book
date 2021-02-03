package com.example.recipebook.converters;

import com.example.recipebook.commands.NotesCommand;
import com.example.recipebook.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if(notesCommand == null) {
            return null;
        }
        final Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setRecipeNotes(notesCommand.getRecipeNotes());
        System.out.println("notes id: " + notesCommand.getId());
        System.out.println("notes description: " + notesCommand.getRecipeNotes());
        return notes;
    }
}
