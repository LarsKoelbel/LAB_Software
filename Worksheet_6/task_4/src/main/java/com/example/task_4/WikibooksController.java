package com.example.task_4;

import com.example.task_4.library_project.Elements.SynonymArea;
import com.example.task_4.library_project.Library.Library;
import com.example.task_4.library_project.Library.Medium.ElectronicalMediumWikiBooks;
import com.example.task_4.library_project.Library.Medium.Medium;
import com.example.task_4.library_project.Library.io.Communication;
import com.example.task_4.library_project.Library.io.Message;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.io.Severity;
import com.example.task_4.library_project.Library.persistency.HumanReadablePersistency;
import com.example.task_4.library_project.Library.user_interface.CLI;
import com.example.task_4.library_project.Library.user_interface.ICLIHelpContainer;
import com.example.task_4.library_project.Library.user_interface.NullCLI;
import com.example.task_4.library_project.Library.user_interface.cli_commands.*;
import com.example.task_4.library_project.Library.utils.TextUtils;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
* Controller for the wikiboooks application
* @author lkoeble 21487
*/
public class WikibooksController {
    public VBox consoleWindow;
    public MenuBar menu;
    @FXML
    private VBox root;
    public TextField input;
    public WebView browser;
    public Label lastChangeDate;
    public Label lastEditor;
    public Label shelf;
    public TextField consoleInput;
    public TextArea consoleOutput;
    public Button backButton;
    public ComboBox<String> synonymComboBox;
    public Button nextButton;
    public ListView<String> synonymListView;
    public Button searchSynonymButton;
    public Button deleteArchiveButton;
    public Button addArchiveButton;
    public Button sortArchiveButton;
    public ListView<String> archiveListView;
    public Button searchButton;

    private MultipleSelectionModel<String> synonymSelectionModel;

    private ArrayList<String> synonymHistory = new ArrayList<>();

    private Medium tempMedBuffer = null;

    public CLI cli = new CLI();

    private SynonymArea synonymArea;

    /**
     * This method runs automatically after FXML loading
     */
    @FXML
    public void initialize() {
        // Set a default page
        browser.getEngine().load("https://en.wikibooks.org");
        cli.init();

        // Add global event filter
        root.addEventFilter(javafx.event.Event.ANY, event -> {
            onGlobalEvent(event);

            if (event instanceof javafx.scene.input.KeyEvent keyEvent) if (keyEvent.getCode() != KeyCode.ENTER) return;
            if (event instanceof MouseEvent mouseEvent
                    && mouseEvent.getEventType() != MouseEvent.MOUSE_CLICKED
                    && !(mouseEvent.getTarget() instanceof Button)) return;


            onGlobalUIUpdate(event);
        });

        synonymArea = new SynonymArea(this, backButton, synonymComboBox, nextButton, synonymListView, searchSynonymButton);
        browser.setFocusTraversable(false);
        consoleOutput.setFocusTraversable(false);
        archiveListView.setFocusTraversable(false);
        menu.setFocusTraversable(true);
    }

    /**
     * This methode is called on every global event (only use for super light weigh actions)
     * @param _event The event
     */
    private void onGlobalEvent(Event _event)
    {
        if (_event instanceof KeyEvent && ((KeyEvent) _event).getCode().equals(KeyCode.F1)) onInfoAbout(null);
    }

    /**
     * This function is called any time any event is performed on the global UI (other than key presser, apart from ENTERT)
     * @param event The event
     */
    private void onGlobalUIUpdate(Event event)
    {
        if (!isEventFromNodeOrChild(event, archiveListView)) updateArchiveListView();
    }

    /**
     * Checks if the event target is the given node or a child of it
     */
    private boolean isEventFromNodeOrChild(Event event, Node node) {
        Node target = (Node) event.getTarget();
        while (target != null) {
            if (target == node) return true;
            target = target.getParent();
        }
        return false;
    }

    public void onInputKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
           openWikibook(false);
        };
    }

    public void onSearchClicked(MouseEvent mouseEvent) {
        openWikibook(false);
    }

    /**
     * Open a new wikibook page in the browser based on the name provided in the input field
     * @param _detached In detached mode, the search is not added to the history
     */
    public void openWikibook(boolean _detached)
    {
        String url = WikiBooks.getURL(input.getText());
        if (url != null) updateBrowserWindow(url);
        else return;
        // Update the medium
        ProcessOutputBuffer out = new ProcessOutputBuffer("fetch-medium-data");
        Medium medium = WikiBooks.getMedium(input.getText(), out);
        // Update info labels
        if (medium != null)
        {
            ElectronicalMediumWikiBooks elmed = (ElectronicalMediumWikiBooks) medium;
            LocalDateTime local = elmed.getTimestamp();
            ZonedDateTime berlinTime = local.atZone(ZoneId.of("Europe/Berlin"));
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            lastChangeDate.setText(berlinTime.format(fmt));
            lastEditor.setText(elmed.getContributor());
            shelf.setText(elmed.getShelf());
            tempMedBuffer = medium;
        }else
        {
            tempMedBuffer = null;
            lastChangeDate.setText(" --- ");
            lastEditor.setText(" --- ");
            shelf.setText(" --- ");
            showProcessOutputBufferInErrorDialog(out);
        }
        updateSynonyms(input.getText(), _detached);
    }

    /**
     * Open a new path in the browser
     * @param _path Path to open
     */
    private void updateBrowserWindow(String _path)
    {
        browser.getEngine().load(_path);

    }

    /**
     * Open an error dialog and show the content of a processoutputbuffer to the user
     */
    public void showErrorDialog(String _title, String _head, String _content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(_title);
        alert.setHeaderText(_head);
        alert.setContentText(_content);
        alert.showAndWait();
    }

    /**
     * Open an success dialog and show the content of a processoutputbuffer to the user
     */
    private void showSuccessDialog(String _title, String _head, String _content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(_title);
        alert.setHeaderText(_head);
        alert.setContentText(_content);
        alert.showAndWait();
    }

    /**
     * Open an error dialog and show the content of a processoutputbuffer to the user
     * @param _out The process output buffer
     */
    private void showProcessOutputBufferInErrorDialog(ProcessOutputBuffer _out)
    {
        Message mostSevere = _out.getMostSevere();
        showErrorDialog(
                "Process failed",
                "Error: Process " + _out.getProcessName() + " failed with severety " + mostSevere.getSeverity(),
                mostSevere + "\n-------------------\n" + TextUtils.stripAnsi(CLI.getProcessOutputBufferFormated(_out).strip())
        );
    }

    public void onAddClicked(MouseEvent mouseEvent) {
        if (tempMedBuffer == null) {
            showErrorDialog("Error", "No medium to add", "The selected medium does not exist or is invalid. Nothing was added");
            return;
        }
        tempMedBuffer.setInventoryID(Library.collection.getNextID(Communication.NULL_BUFFER));
        Library.collection.addMedium(tempMedBuffer);
        tempMedBuffer = null;
    }

    public void onSortClicked(MouseEvent mouseEvent) {
        Library.toggleSort();
    }

    public void onDeleteClicked(MouseEvent mouseEvent) {
        String name = input.getText();
        if (name == null || name.isBlank()) showErrorDialog("Error", "Invalid name", "Cant delete book with an empty or invalid name");
        // Use the cli delete function to delete the book
        ProcessOutputBuffer out = new ProcessOutputBuffer(null);
        new Drop().call(new String[] {"title", name},out, new NullCLI());
        if (out.getMostSevere().getSeverity().compareTo(Severity.WARNING) >= 0) showProcessOutputBufferInErrorDialog(out);
        else showSuccessDialog("Medium deleted", "Medium deleted successfully", out.toString());
    }

    public void onSaveClicked(ActionEvent mouseEvent) {

        String path = showFileDialog((Stage) root.getScene().getWindow(), true);
        if (path == null) return;

        ProcessOutputBuffer out = new ProcessOutputBuffer(null);
        new SaveBinary().call(new String[] {path},out, new NullCLI());
        if (out.getMostSevere().getSeverity().compareTo(Severity.WARNING) >= 0) showProcessOutputBufferInErrorDialog(out);
        else showSuccessDialog("Archive saved", "Archive saved successfully", out.toString());
    }

    public void onLoadClicked(ActionEvent mouseEvent) {

        String path = showFileDialog((Stage) root.getScene().getWindow(), false);
        if (path == null) return;

        ProcessOutputBuffer out = new ProcessOutputBuffer(null);
        new LoadBinary().call(new String[] {path},out, new NullCLI());
        if (out.getMostSevere().getSeverity().compareTo(Severity.WARNING) >= 0) showProcessOutputBufferInErrorDialog(out);
        else showSuccessDialog("Archive loaded", "Archive saved successfully from binary file", out.toString());
        updateArchiveListView();
    }

    public void onImportClicked(ActionEvent mouseEvent) {

        String path = showFileDialog((Stage) root.getScene().getWindow(), false);
        if (path == null) return;

        ProcessOutputBuffer out = new ProcessOutputBuffer(null);
        new LoadBibtex().call(new String[] {path},out, new NullCLI());
        if (out.getMostSevere().getSeverity().compareTo(Severity.WARNING) >= 0) showProcessOutputBufferInErrorDialog(out);
        else showSuccessDialog("Archive imported", "Archive imported successfully", out.toString());

        updateArchiveListView();
    }

    public void onConsoleCommandEntered(ActionEvent actionEvent) {
        String cmd = consoleInput.getText().trim();
        if (!cmd.isBlank())
        {
            boolean execution_failed = true;
            ProcessOutputBuffer out = new ProcessOutputBuffer("unnamed-cli-process");
            // Handle internal commands
            //switch (cmd)
            //{
            //    case "clear":
            //    {
            //        consoleOutput.clear();
            //        execution_failed = false;
            //    }
            //}

            // Defer all other commands to cmd
            if (execution_failed)
            {
                execution_failed = cli.call(cmd,out);
                System.out.println(cmd);
                consoleOutput.clear();
                consoleOutput.appendText(TextUtils.stripAnsi(CLI.getProcessOutputBufferFormated(out)));
            }

            // Handle input field output
            consoleInput.setEditable(false);
            if (out.hasMessages())
            {
                switch (out.getMostSevere().getSeverity())
                {
                    case SUCCESS, BASIC -> consoleInput.setText("OK: " + cmd);
                    case ERROR -> consoleInput.setText("ERROR: " + cmd);
                    case REMARK -> consoleInput.setText("NOT OK: " + cmd);
                    case WARNING -> consoleInput.setText("WARNING: " + cmd);
                    case FATAL -> consoleInput.setText("FATAL: " + cmd);
                    default -> consoleInput.setText("UNKNOWN: " + cmd);
                }
            } else
            {
                if (execution_failed) consoleInput.setText("ERROR: " + cmd);
                else consoleInput.setText("Ok: " + cmd);
            }
            // Auto clear the console input after a shot amount of time
            PauseTransition delay = new PauseTransition(Duration.millis(500));
            delay.setOnFinished(event -> {
                consoleInput.clear();
                consoleInput.setEditable(true);
            });
            delay.play();
        }

    }

    public void onBackClicked(MouseEvent mouseEvent) {
        synonymArea.onBackClicked();
    }

    public void onNextClicked(MouseEvent mouseEvent) {
        synonymArea.onNextClicked();
    }

    public void onSearchSynonymClicked(MouseEvent mouseEvent) {
        synonymArea.onSearchSynonymClicked();
    }

    /**
     * Update the archive list view
     */
    private void updateArchiveListView()
    {
        archiveListView.getItems().clear();
        if (!Library.collection.isEmpty())
        {
            // Add all titles to the list view in the correct order
            for (Medium m : Library.collection)
            {
                archiveListView.getItems().add(m.getTitle() + " | " + m.getType());
            }
        }
    }

    /**
     * Update the synonyms list with the synonyms of a new word
     * @param _word The word to find the synonyms to
     * @param _detached In detached mode, searches are not added to the history
     */
    private void updateSynonyms(String _word, boolean _detached)
    {
        synonymArea.updateSynonyms(_word, _detached);
    }

    public void synonymListViewClicked(MouseEvent mouseEvent) {
        synonymArea.synonymListViewClicked(mouseEvent);
    }

    /**
     * Show about text in the language specified by the user
     * @param actionEvent --
     */
    public void onInfoAbout(ActionEvent actionEvent) {
        // 1. Create a list of languages
        java.util.List<String> languages = java.util.Arrays.asList("English", "German");

        // 2. Create the ChoiceDialog with default value "English"
        ChoiceDialog<String> langDialog = new ChoiceDialog<>("English", languages);
        langDialog.setTitle("Select Language");
        langDialog.setHeaderText("Please select your language");
        langDialog.setContentText("Language:");

        // 3. Show the dialog and get the selected language
        String selectedLanguage = langDialog.showAndWait().orElse(null);
        if (selectedLanguage == null) return; // user cancelled

        // 4. Prepare content based on language
        String content;
        if ("German".equals(selectedLanguage)) {
            content = "Alle redaktionellen Inhalte stammen von den Internetseiten der Projekte Wikibooks und Wortschatz.\n" +
                    "Die von Wikibooks bezogenen Inhalte unterliegen seit dem 22. Juni 2009 unter der Lizenz CC-BY-SA 3.0 Unported zur Verfügung.\n" +
                    "Eine deutschsprachige Dokumentation für Weiternutzer findet man in den Nutzungsbedingungen der Wikimedia Foundation.\n" +
                    "Für alle Inhalte von Wikibooks galt bis zum 22. Juni 2009 standardmäßig die GNU FDL (GNU Free Documentation License).\n" +
                    "Der Text der GNU FDL ist unter http://de.wikipedia.org/wiki/Wikipedia:GNU_Free_Documentation_License verfügbar.\n\n" +
                    "Die Inhalte des Wortschatzsystems werden von OpenTsurus bezogen.\n" +
                    "Weitere Informationen hierzu finden Sie hier: https://github.com/LarsKoelbel/MultiLangSynonymFetcher/tree/main\n\n" +
                    "Dieses Programm ist nur zur Nutzung durch den Programmierer selbst gedacht.\n" +
                    "Es dient der Demonstration und dem Erlernen von Prinzipien der Programmierung mit Java.\n" +
                    "Eine Verwendung des Programms für andere Zwecke verletzt möglicherweise die Urheberrechte der Originalautoren der redaktionellen Inhalte\n" +
                    "und ist daher untersagt.";
        } else { // English
            content = "All editorial content comes from the websites of the Wikibooks and Wortschatz projects.\n" +
                    "Content obtained from Wikibooks has been available under the CC-BY-SA 3.0 Unported license since June 22, 2009.\n" +
                    "A German documentation for further users can be found in the terms of use of the Wikimedia Foundation.\n" +
                    "For all Wikibooks content, the GNU FDL (GNU Free Documentation License) applied by default until June 22, 2009.\n" +
                    "The text of the GNU FDL is available at: http://de.wikipedia.org/wiki/Wikipedia:GNU_Free_Documentation_License\n\n" +
                    "Content from the Wortschatz system is obtained from OpenTsurus.\n" +
                    "More information can be found here: https://github.com/LarsKoelbel/MultiLangSynonymFetcher/tree/main\n\n" +
                    "This program is intended only for the programmer’s personal use.\n" +
                    "It serves to demonstrate and learn principles of programming with Java.\n" +
                    "Using this program for other purposes may violate the copyrights of the original authors of the editorial content\n" +
                    "and is therefore prohibited.";
        }

        // 5. Show About dialog
        showSuccessDialog("About", "", content);
    }

    /**
     * Show the cli documentation
     * @param actionEvent --
     */
    public void onCLIDocumentation(ActionEvent actionEvent) {
        TextArea textArea = new TextArea(ICLIHelpContainer.CLI_HELP);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setPrefWidth(600);
        textArea.setPrefHeight(400);

        VBox contentBox = new VBox(textArea);
        contentBox.setPadding(new Insets(10));

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Integrated CLI Documentation");
        dialog.setHeaderText("Integrated CLI Documentation");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.getDialogPane().setContent(contentBox);

        dialog.showAndWait();
    }

    private double lastConsoleHeight = 150;

    public void onToggleCLI(ActionEvent actionEvent) {
        boolean visible = consoleWindow.isVisible();

        if (visible) {
            lastConsoleHeight = consoleWindow.getHeight();
            consoleWindow.setPrefHeight(0);
            consoleWindow.setMinHeight(0);
            consoleWindow.setMaxHeight(0);
            consoleWindow.setVisible(false);
        } else {
            consoleWindow.setPrefHeight(lastConsoleHeight);
            consoleWindow.setMinHeight(50);
            consoleWindow.setMaxHeight(Double.MAX_VALUE);
            consoleWindow.setVisible(true);
        }

        consoleWindow.setManaged(!visible);
    }

    public void synonymListViewEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) synonymArea.synonymListViewClicked(null);
    }

    private String lastPath = null;

    public String showFileDialog(Stage ownerStage, boolean isSaveDialog) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export file");

        if (lastPath != null) fileChooser.setInitialDirectory(new File(lastPath));

        File file = null;

        if (isSaveDialog) file = fileChooser.showSaveDialog(ownerStage);
        else file = fileChooser.showOpenDialog(ownerStage);

        lastPath = file.getParentFile().getPath();

        return file.getPath();
    }

    /**
     * Export to a bibtex os a hr file
     * @param filetype Type of file to export
     */
    private void exportAs(String filetype)
    {
        String path = showFileDialog((Stage) root.getScene().getWindow(), true);
        if (path == null) return;
        ProcessOutputBuffer out = new ProcessOutputBuffer(null);

        switch (filetype)
        {
            case "bibtex" -> new SaveBibtex().call(new String[] {path},out, new NullCLI());
            case "hr" -> {
                try
                {
                    HumanReadablePersistency hr = new HumanReadablePersistency();
                    hr.save(Library.collection, path);
                    out.write("Archive exportes successfully to: " + path, Severity.SUCCESS);
                }catch (Exception e)
                {
                    out.write("Filed to export archive: " + e.getMessage(), Severity.ERROR);
                }

            }
        }

        if (out.getMostSevere().getSeverity().compareTo(Severity.WARNING) >= 0) showProcessOutputBufferInErrorDialog(out);
        else showSuccessDialog("Archive exported", "Archive exported successfully", out.toString());
    }

    public void onExportBibTex(ActionEvent actionEvent) {
        exportAs("bibtex");
    }

    public void onExportHR(ActionEvent actionEvent) {
        exportAs("hr");
    }
}
