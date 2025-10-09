package task9_zettelkasten;

import task9_zettelkasten.bib_tex.BibTexParser;
import task9_zettelkasten.bib_tex.BibTexStruct;
import task9_zettelkasten.io.Communication;
import task9_zettelkasten.io.IGlobalOutputBufferListener;
import task9_zettelkasten.io.Message;
import task9_zettelkasten.io.Severity;

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
        });

        try
        {
            Medium medium = BibTexParser.parseFromBibTexString("@elMed{something = 3}");
            System.out.println(medium.generateRepresentation());
        }catch (Exception e)
        {
           if (e instanceof IExceptionUserReadable)
           {
               Communication.writeToGlobalOutputBuffer(((IExceptionUserReadable) e).getUserMessage(), Severity.ERROR);
           }else
           {
               e.printStackTrace();
           }
        }
    }

}
