package network.rembo.rss;

import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class ParseApplications {
    private String data;
    private ArrayList<Application> applications;

    public ParseApplications(String xmlData) {
        data = xmlData;
        applications = new ArrayList<Application>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public boolean process() {

        boolean operationStatus = true;
        Application currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(this.data));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                if(eventType == XmlPullParser.START_TAG) {
                    if(tagName.equalsIgnoreCase("item")) {
                        inEntry = true;
                        currentRecord = new Application();
                    }
                } else if(eventType == XmlPullParser.TEXT) {
                    textValue = xpp.getText();
                    //textValue = textValue.replace("<br>","\n");
                } else if(eventType == XmlPullParser.END_TAG) {
                    if(inEntry) {
                        if(tagName.equalsIgnoreCase("item")) {
                            applications.add(currentRecord);
                            inEntry = false;
                        }
                        if(tagName.equalsIgnoreCase("title")) {
                            textValue = textValue.replace("<br>","");
                            currentRecord.setTitle(textValue);
                        } else if(tagName.equalsIgnoreCase("link")) {
                            textValue = textValue.replace("<br>","");
                            currentRecord.setLink(textValue);
                        } else if(tagName.equalsIgnoreCase("description")) {
                            textValue = textValue.replace("<br>","\n");
                            currentRecord.setDescription(textValue);
                        } else if(tagName.equalsIgnoreCase("pubDate")) {
                            textValue = textValue.replace("<br>","");
                            textValue = textValue.substring(0,16);
                            currentRecord.setPubDate(textValue);
                        }

                    }

                }

                eventType = xpp.next();
            }
        } catch(Exception e) {
            e.printStackTrace();
            operationStatus = false;
        }
        /*
        for(Application app : applications ) {
            Log.d("LOG", "**************");
            Log.d("LOG", app.getName());
            Log.d("LOG", app.getArtist());
            Log.d("LOG", app.getReleaseDate());
        }
        */
        return operationStatus;
    }



}
