package com.example.task_4;

import com.example.task_4.library_project.Library.Library;
import com.example.task_4.library_project.Library.Medium.ElectronicalMediumWikiBooks;
import com.example.task_4.library_project.Library.Medium.Medium;
import com.example.task_4.library_project.Library.io.Message;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.io.Severity;
import com.example.task_4.library_project.Library.user_interface.CLI;
import com.example.task_4.library_project.Library.user_interface.NullCLI;
import com.example.task_4.library_project.Library.user_interface.cli_commands.Drop;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
* Controller for the wikiboooks application
* @author lkoeble 21487
*/
public class WikibooksController {
    public TextField input;
    public WebView browser;
    public Label lastChangeDate;
    public Label lastEditor;
    public Label shelf;

    private Medium tempMedBuffer = null;

    /**
     * This method runs automatically after FXML loading
     */
    @FXML
    public void initialize() {
        // Set a default page
        browser.getEngine().load("https://en.wikibooks.org");
    }

    public void onInputKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
           openWikibook();
        };
    }

    public void onSearchClicked(MouseEvent mouseEvent) {
        openWikibook();
    }

    /**
     * Open a new wikibook page in the browser based on the name provided in the input field
     */
    private void openWikibook()
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
    private void showErrorDialog(String _title, String _head, String _content)
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
                mostSevere + "\n-------------------\n" + CLI.getProcessOutputBufferFormated(_out).strip()
        );
    }

    public void onAddClicked(MouseEvent mouseEvent) {
        if (tempMedBuffer == null) {
            showErrorDialog("Error", "No medium to add", "The selected medium does not exist or is invalid. Nothing was added");
            return;
        }
        Library.collection.addMedium(tempMedBuffer);
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

    public void onSaveClicked(MouseEvent mouseEvent) {
        showErrorDialog("Not implemented", "Not implemented", "This feature is not jet supported");
    }

    public void onLoadClicked(MouseEvent mouseEvent) {
        showErrorDialog("Not implemented", "Not implemented", "This feature is not jet supported");
    }

    public void onImportClicked(MouseEvent mouseEvent) {
        showErrorDialog("Not implemented", "Not implemented", "This feature is not jet supported");
    }

    public void onExportClicked(MouseEvent mouseEvent) {
        showErrorDialog("Not implemented", "Not implemented", "This feature is not jet supported");
    }
}
