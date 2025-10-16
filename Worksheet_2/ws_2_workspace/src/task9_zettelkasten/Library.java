package task9_zettelkasten;

import task9_zettelkasten.bib_tex.BibTexParser;
import task9_zettelkasten.bib_tex.BibTexStruct;
import task9_zettelkasten.io.*;

/**
 * Main class for managing the library
 * @author lkoelbel
 * @matnr 21487
 */
public class Library {
    public static void main(String[] argv)
    {
        Communication.registerGlobalOutputBufferListener(new IGlobalOutputBufferListener(){

            @Override
            public void onGlobalOutputBufferUpdate(Message _lastMessage) {
                System.out.println(_lastMessage.getSeverity() + ": " + _lastMessage.toString());
            }

            @Override
            public void onProcessOutputBufferClose(ProcessOutputBuffer _processOutputBuffer, String _key) {

                System.out.println("-------- Process end for " + _key + " --------");
                Message mostSevere = _processOutputBuffer.getMostSevere();
                System.out.println("Process exited with severity " + mostSevere.getSeverity() + ": ");
                System.out.println("\t" + mostSevere);
                System.out.println(_processOutputBuffer);
            }
        });


        Communication.acquireProcessOutputBuffer("bib-tex-parser");
        try
        {
            Medium medium = BibTexParser.parseFromBibTexString("@book{author = {-}, title = {Duden 01. Die deutsche Rechtschreibung}, publisher = {Bibliogr" +
                    "aphisches Institut, Mannheim}, year = 2004, isbn = {3-411-04013-0}}");
            Communication.writeToProcessOutputBuffer("bib-tex-parser",
                    "Result: \n" + medium.generateRepresentation());
        }catch (Exception e)
        {
           if (e instanceof IExceptionUserReadable)
           {
               Communication.writeToProcessOutputBuffer("bib-tex-parser",((IExceptionUserReadable) e).getUserMessage(), Severity.ERROR);
           }else
           {
               Communication.writeToProcessOutputBuffer("bib-tex-parser", e.toString(), Severity.FATAL);
           }
        }

        Communication.processOutputBufferClose("bib-tex-parser");
    }

}
