package com.example.task_4.library_project.Elements;

import com.example.task_4.WikibooksController;
import com.example.task_4.library_project.Library.synonym.MultiLangSynonymFetcher;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Handler class for the synonym area
 */
public class SynonymArea extends UIElement{

    private final MultipleSelectionModel<String> synonymSelectionModel;

    private final ArrayList<String> synonymHistory = new ArrayList<>();

    private int historyPosition = -1;

    private boolean ignoreComboBoxEvents = false;

    public Button backButton;
    public ComboBox<String> synonymComboBox;
    public Button nextButton;
    public ListView<String> synonymListView;
    public Button searchSynonymButton;

    public SynonymArea(WikibooksController context, Button backButton, ComboBox<String> synonymInput, Button nextButton, ListView<String> synonymListView, Button searchSynonymButton) {
        super(context);
        this.backButton = backButton;
        this.synonymComboBox = synonymInput;
        this.nextButton = nextButton;
        this.synonymListView = synonymListView;
        this.searchSynonymButton = searchSynonymButton;

        this.synonymSelectionModel = synonymListView.getSelectionModel();

        nextButton.setDisable(true);
        backButton.setDisable(true);

        synonymInput.setEditable(false);

        synonymComboBox.setOnAction(event -> {
            if (ignoreComboBoxEvents) return;
            String newVal = synonymComboBox.getSelectionModel().getSelectedItem();
            if (newVal != null) {
                getContext().input.setText(newVal);

                // Update buttons
                int index = synonymHistory.indexOf(newVal);
                backButton.setDisable(index <= 0);
                nextButton.setDisable(index >= synonymHistory.size() - 1);
                historyPosition = index;

                getContext().openWikibook(true);
            }
        });

    }

    public void onSearchSynonymClicked() {
        String selected = synonymListView.getSelectionModel().getSelectedItem();
        if (selected == null) getContext().showErrorDialog("Error", "", "No synonym selected");
        getContext().input.setText(selected);
        getContext().openWikibook(false);
    }

    /**
     * Update the synonyms list with the synonyms of a new word
     * @param _word The word to find the synonyms to
     */
    public void updateSynonyms(String _word) {
       updateSynonyms(_word, false);
    }

    /**
     * Update the synonyms list with the synonyms of a new word
     * @param _word The word to find the synonyms to
     * @param _detached Updates in detached mode are not added to the history
     */
    public void updateSynonyms(String _word, boolean _detached) {
        try
        {
            String[] synonyms = MultiLangSynonymFetcher.getSynonyms(_word, MultiLangSynonymFetcher.Language.GERMAN, -1);
            synonymListView.getItems().clear();
            // Place the synonyms inside the list view
            if (synonyms.length > 0)
            {
                for (String s : synonyms)
                {
                    synonymListView.getItems().add(s);
                }
                synonymListView.setSelectionModel(synonymSelectionModel);
            }else
            {
                synonymListView.getItems().add("<none found>");
                searchSynonymButton.setDisable(true);
                synonymListView.setSelectionModel(null);
            }

            if (!_detached)
            {
                if (historyPosition == synonymHistory.size() - 1)
                {
                    synonymHistory.add(_word);
                    backButton.setDisable(false);
                    historyPosition = synonymHistory.size() - 1;
                    nextButton.setDisable(true);
                }
                else
                {
                    synonymHistory.subList(historyPosition + 1, synonymHistory.size()).clear();
                    synonymHistory.add(_word);
                    backButton.setDisable(false);
                    historyPosition = synonymHistory.size() - 1;
                    nextButton.setDisable(true);
                }

            }
            ignoreComboBoxEvents = true;
            updateComboBox();
            synonymComboBox.getSelectionModel().select(_word);
            ignoreComboBoxEvents = false;


        }catch (Exception e)
        {
            getContext().showErrorDialog("Error", "An error occurred while trying to fetch synonyms", e.toString() + " :: " + e.getMessage());
        }
    }

    public void synonymListViewClicked(MouseEvent mouseEvent) {
        if (mouseEvent == null || mouseEvent.getClickCount() >=2)
        {
            if (synonymListView.getSelectionModel() == null) return;
            String selected = synonymListView.getSelectionModel().getSelectedItem();
            if (selected == null) return;
            getContext().input.setText(selected);
            getContext().openWikibook(false);
        }
    }

    /**
     * Go back to the last element
     */
    public void onBackClicked() {
        if (historyPosition > 0)
        {
            String word = synonymHistory.get(--historyPosition);
            if (historyPosition <= 0) backButton.setDisable(true);
            getContext().input.setText(word);
            getContext().openWikibook(true);
            if (historyPosition < synonymHistory.size() - 1) nextButton.setDisable(false);
        }else
        {
            backButton.setDisable(true);
        }
    }

    /**
     * Go forward to the next element in the history
     */
    public void onNextClicked() {
        if (historyPosition < synonymHistory.size() - 1)
        {
            String word = synonymHistory.get(++historyPosition);
            if (historyPosition >= synonymHistory.size() - 1)
            {
                nextButton.setDisable(true);
            }
            if (historyPosition > 0) backButton.setDisable(false);
            getContext().input.setText(word);
            getContext().openWikibook(true);
        }else
        {
            nextButton.setDisable(true);
        }
    }

    /**
     * Update the combo box with the current history
     */
    private void updateComboBox()
    {
        synonymComboBox.getItems().clear();
        if (synonymHistory.isEmpty()) return;
         // Add all elements in reverse
        for (int i = synonymHistory.size() - 1; i >= 0; i--)
        {
            synonymComboBox.getItems().add(synonymHistory.get(i));
        }
    }
}
