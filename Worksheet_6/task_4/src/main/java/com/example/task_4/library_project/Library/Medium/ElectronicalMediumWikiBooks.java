package com.example.task_4.library_project.Library.Medium;

import com.example.task_4.library_project.Library.bib_tex.*;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.io.Severity;
import com.example.task_4.library_project.Library.utils.BibTexUtils;
import com.example.task_4.library_project.Library.utils.URLUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * This class is used to represent an electronic storage medium from WikiBooks
 * @author lkoelbel
 * @matnr 21487
 */
public class ElectronicalMediumWikiBooks extends ElectronicalMedium implements Serializable {
    private String contributor;
    private String tableOfContents;
    private LocalDateTime timestamp;
    private String shelf;

    public ElectronicalMediumWikiBooks()
    {
        setType(BibTexType.EL_MED_WIKI);
        setDataFormat("WikiBooks book");
        setStatus(Status.AVAILABLE);
    }

    public String getContributor() {
        return contributor;
    }

    public ElectronicalMediumWikiBooks setContributor(String contributor) {
        this.contributor = contributor;
        return this;
    }

    public String getTableOfContents() {
        return tableOfContents;
    }

    public ElectronicalMediumWikiBooks setTableOfContents(String tableOfContents) {
        this.tableOfContents = tableOfContents;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ElectronicalMediumWikiBooks setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getShelf() {
        return shelf;
    }

    public ElectronicalMediumWikiBooks setShelf(String shelf) {
        this.shelf = shelf;
        return this;
    }

    @Override
    public String generateRepresentation() {
        StringBuilder sb = new StringBuilder(super.generateRepresentation()).append("\n");
        sb.append("Contributor: ").append(getContributor()).append("\n");
        sb.append("Timestamp: ").append(getTimestamp()).append("\n");
        sb.append("Shelf: ").append(getShelf()).append("\n");
        sb.append("Table of contents: ").append("\n").append(getTableOfContents());
        return sb.toString();
    }

    /**
     * Get a bibtex struct of the object
     * @return the BibTexStruct
     */
    @Override
    public BibTexStruct getBibtex() {
        BibTexStruct bibTex = new BibTexStruct();
        bibTex.init().setType(BibTexType.EL_MED_WIKI)
                .addParameter(new BibTexParameter()
                        .setName("Title")
                        .setSvalue(BibTexUtils.makeBibtexSave(getTitle()))
                        .setType(BibTexParameterType.STRING))
                .addParameter(new BibTexParameter()
                        .setName("URL")
                        .setSvalue(BibTexUtils.makeBibtexSave(getURL()))
                        .setType(BibTexParameterType.STRING))
                .addParameter(new BibTexParameter()
                        .setName("DataFormat")
                        .setSvalue(BibTexUtils.makeBibtexSave(getDataFormat()))
                        .setType(BibTexParameterType.STRING))
                .addParameter(new BibTexParameter()
                        .setName("Size")
                        .setFvalue((double) getSizeInBytes())
                        .setType(BibTexParameterType.NUMERIC_VALUE))
                .addParameter(new BibTexParameter()
                        .setName("Contributor")
                        .setSvalue(BibTexUtils.makeBibtexSave(getContributor()))
                        .setType(BibTexParameterType.STRING))
                .addParameter(new BibTexParameter()
                    .setName("TableOfContents")
                    .setSvalue("!!This value is not (yet) supported on bibtex ex-/ import!!")
                    .setType(BibTexParameterType.STRING))
                .addParameter(new BibTexParameter()
                    .setName("Shelf")
                    .setSvalue(BibTexUtils.makeBibtexSave(getShelf()))
                    .setType(BibTexParameterType.STRING))
                .addParameter(new BibTexParameter()
                        .setName("Timestamp")
                        .setSvalue(BibTexUtils.makeBibtexSave(getTimestamp().toString()))
                        .setType(BibTexParameterType.STRING));

        return bibTex;
    }

    /**
     * Parse values from a BibTex struct object
     * @param _bibTexStruct BibTex struct to parse from
     * @param _out Output buffer for the process
     */
    public ElectronicalMediumWikiBooks parseFromBibTexStruct(BibTexStruct _bibTexStruct, ProcessOutputBuffer _out)
    {
        try
        {
            for (BibTexParameter bibTexParameter : _bibTexStruct.getParameterList())
            {
                switch (bibTexParameter.getName().toLowerCase().strip()){
                    case "title" -> setTitle(bibTexParameter.getSvalue());
                    case "url" -> setURL(bibTexParameter.getSvalue());
                    case "dataformat" -> setDataFormat(bibTexParameter.getSvalue());
                    case "size" -> setSizeInBytes((long) bibTexParameter.getFvalue());
                    case "contributor" -> setContributor(bibTexParameter.getSvalue());
                    case "tableofcontents" -> setTableOfContents(bibTexParameter.getSvalue());
                    case "shelf" -> setShelf(bibTexParameter.getSvalue());
                    case "timestamp" -> setTimestamp(LocalDateTime.parse(bibTexParameter.getSvalue()));
                    default -> _out.write("Parameter " + bibTexParameter.getName() + " is not available for type electronic medium of subtype WikiBook", Severity.WARNING);

                }
            }

            // Check if all the parameters are met
            if (this.getTitle() == null || this.getTitle().isEmpty())
                throw new BibTexException("Incomplete parameter exception",
                        "Medium can not be created. The parameter 'title' is missing or empty.");

            if (this.getURL() == null || this.getURL().isEmpty())
                throw new BibTexException("Incomplete parameter exception",
                        "Medium can not be created. The parameter 'url' is missing or empty.");

            if (this.getDataFormat() == null || this.getDataFormat().isEmpty())
                throw new BibTexException("Incomplete parameter exception",
                        "Medium can not be created. The parameter 'dataformat' is missing or empty.");

            if (this.getContributor() == null || this.getContributor().isEmpty())
                throw new BibTexException("Incomplete parameter exception",
                        "Medium can not be created. The parameter 'contributor' is missing or empty.");

            if (this.getTableOfContents() == null)
                throw new BibTexException("Incomplete parameter exception",
                        "Medium can not be created. The parameter 'TableOfContents' is missing.");

            if (this.getShelf() == null || this.getShelf().isEmpty())
                throw new BibTexException("Incomplete parameter exception",
                        "Medium can not be created. The parameter 'shelf' is missing or empty.");

            if (this.getTimestamp() == null)
                throw new BibTexException("Incomplete parameter exception",
                        "Medium can not be created. The parameter 'timestamp' is missing or invalid.");

            return this;
        }catch (URLUtils.URLException e)
        {
            throw new BibTexException("URL exception", "Medium can not be created. " + e.getMessage());
        }
        catch (DateTimeParseException e)
        {
            throw new BibTexException("DateTime exception", "Medium can not be created. " + e.getMessage());
        }

    }
}
