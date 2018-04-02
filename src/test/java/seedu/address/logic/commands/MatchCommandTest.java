package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.MatchCommand.MESSAGE_MATCH_FAILED;
import static seedu.address.logic.commands.MatchCommand.MESSAGE_MISMATCH_WRONG_LEVEL;
import static seedu.address.logic.commands.MatchCommand.MESSAGE_MISMATCH_WRONG_PRICE;
import static seedu.address.logic.commands.MatchCommand.MESSAGE_MISMATCH_WRONG_ROLE;
import static seedu.address.logic.commands.MatchCommand.MESSAGE_MISMATCH_WRONG_STATUS;
import static seedu.address.logic.commands.MatchCommand.MESSAGE_MISMATCH_WRONG_SUBJECT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class MatchCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }


    @Test
    public void execute_invalidIndexForPersonAUnfilteredList_throwsCommandException() throws Exception {
        Index indexA = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index indexB = Index.fromOneBased(model.getFilteredPersonList().size());
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexforPersonBUnfilteredList_throwsCommandException() {
        Index indexA = Index.fromOneBased(model.getFilteredPersonList().size());
        Index indexB = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_incompatibleSameRole_throwsCommandException() {
        Index indexA = Index.fromOneBased(7);
        Index indexB = Index.fromOneBased(8);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_ROLE));
    }

    @Test
    public void execute_incompatibleAlreadyMatched_throwsCommandException() {
        Index indexA = Index.fromOneBased(1);
        Index indexB = Index.fromOneBased(2);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_STATUS));
    }

    @Test
    public void execute_incompatibleDifferentSubject_throwsCommandException() {
        Index indexA = Index.fromOneBased(8);
        Index indexB = Index.fromOneBased(9);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_SUBJECT));
    }

    @Test
    public void execute_incompatibleDifferentLevel_throwsCommandException() {
        Index indexA = Index.fromOneBased(9);
        Index indexB = Index.fromOneBased(10);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_LEVEL));
    }

    @Test
    public void execute_incompatibleDifferentPrice_throwsCommandException() {
        Index indexA = Index.fromOneBased(10);
        Index indexB = Index.fromOneBased(11);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_PRICE));
    }

    /**
    * Returns a {@code MatchCommand} with the parameter {@code indexA}, {@code indexB}.
    */
    private MatchCommand prepareCommand(Index indexA, Index indexB) {
        MatchCommand matchCommand = new MatchCommand(indexA, indexB);
        matchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return matchCommand;
    }
}
