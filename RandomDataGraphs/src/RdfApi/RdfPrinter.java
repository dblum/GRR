/****************************************************************************************
 * Copyright (C) 2010,2011 The Hebrew University of Jerusalem, 
 * Department of Computer Science and Engineering
 * Project: GRR - Generating Random RDF
 * Author:	Daniel Blum, The Hebrew University of Jerusalem, 
 * Department of Computer Science and Engineering, http://www.cs.huji.ac.il/~danieb12/
 *
 * This file is part of GRR.
 * GRR is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Please note that no part of this code is officially supported by HUJI
 * (The Hebrew University of Jerusalem) or any of its developers.
 *
 *  GRR is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GRR (COPYRIGHT-gpl.txt).  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************************/

package RdfApi;

import NaturalLanguageApi.RdfGenObjects.RdfObject;

import java.util.ArrayList;

/**
 * Helper class the enables 'pretty-printing' of rdf resources and properties in xml tag style
 */
public class RdfPrinter {

    /**
     * Static method for printing a line with the given tag name and content (no tabs)
     * @param tagName - The tag name
     * @param content - The content to be added as tag content
     * @return - A string representation of the tag in the format <tagName>Content</tagName>
     */
    public static String printLine(String tagName, String content) {
        String line = "<" + tagName + ">" + content + "</" + tagName + ">";
        return line;
    }

    /**
     * Static method for printing a tabbed tag without content
     * @param tabOffset - Number of tabs to be added before the tag in this line
     * @param tagName - The tag name
     * @return - A string representation of the tabbed tag in the format /t../t<tagName />
     */
    public static String printTabbedTagWithNoContent(int tabOffset, String tagName)
    {
        String line = printTabs(tabOffset);
        line += "<" + tagName + " />";
        return line;
    }

    /**
     * Static method for printing a line with multiple tabs followed by the given content
     * @param tabOffset - Number of tabs to be added before the tag in this line
     * @param wrappedContent - The content at the end of the tabs
     * @return - A string representation of the tabbed line in the format /t.../t[Content]
     */
    public static String printTabbedLine(int tabOffset, String wrappedContent) {
        String line = printTabs(tabOffset);
        line += wrappedContent;
        return line;
    }

    /**
     * Static method for printing tabs
     * @param tabOffset - The number of tabs
     * @return - A string representation of the tabbed line in the format /t.../t
     */
    public static String printTabs(int tabOffset) {
        String tabs = "";
        for (int i = 0; i < tabOffset; i++)
            tabs += "\t";
        return tabs;
    }

    /**
     * Static method for printing multiple tabs followed by the given content which is surrounded by the given tag name
     * @param tabOffset - The number of tabs
     * @param tagName - The tag name
     * @param content - The content to be added as tag content
     * @return - A string representation of the tag in the format /t.../t<tagName>Content</tagName>
     */
    public static String printTabbedLine(int tabOffset, String tagName, String content) {
        String line = printTabs(tabOffset);
        line += printLine(tagName, content);
        return line;
    }

    /**
     * Static method for printing a tabbed composite tag:
     * /t.../t<tagName>
     * /t.../t/t[wrapper-line: <tag1>...</tag1>
     * ...
     * /t.../t/t[wrapper-line: <tagk>...</tagk>
     * /t.../t</tagName>
     * @param tabOffset - The base number of tags
     * @param tagName - The wrapper tag name
     * @param wrappedLines - The inner wrapped tabbed lines
     * @return - A string representation of the tabbed composite tag as defined above
     */
    public static String printTabbedComposite(int tabOffset, String tagName, ArrayList<String> wrappedLines) {
        String composite = printTabs(tabOffset) +  "<" + tagName + ">\n";
        for (String line : wrappedLines)
            composite += line + "\n";
        composite += printTabs(tabOffset) + "</" + tagName + ">";
        return composite;
    }

    /**
     * Static method for printing a tabbed rdf container (e.g. bag) in the following format:
     * /t.../t<tagName>
     * /t.../t/t<rdf:[container-type]>
     * /t.../t/t/t<rdf:li> // entry 1 in the container
     * /t.../t/t/t/t[RdfObject - Can be simple or composite]
     * /t.../t/t/t</rdf:li>
     * ...
     * /t.../t/t/t<rdf:li> // entry K in the container
     * /t.../t/t/t/t[RdfObject - Can be simple or composite]
     * /t.../t/t/t</rdf:li>
     * /t.../t/t</rdf:[container-type]>
     * /t.../t</tagName>
     * @param tabOffset - The base number of tags
     * @param tagName - The wrapper tag name
     * @param containerType - The type of the rdf-container (e.g. Bag)
     * @param objects - The RdfObjects to be added in the container list
     * @return - A string representation of the tabbed rdf container as defined above
     */
    public static String printTabbedRdfContainer(int tabOffset, String tagName, String containerType, RdfObject[] objects) {
        String container = printTabs(tabOffset) + "<" + tagName + ">\n";
        container += printTabs(tabOffset + 1) + "<rdf:" + containerType + ">\n";
        for (RdfObject object : objects)
        {
            container += printTabs(tabOffset + 2) + "<rdf:li>\n";
            container += object.toRdfString(tabOffset + 3) + "\n";
            container += printTabs(tabOffset + 2) + "</rdf:li>\n";    
        }
        container += printTabs(tabOffset + 1) + "</rdf:" + containerType + ">\n";
        container += printTabs(tabOffset) + "</" + tagName + ">";
        return container;
    }
}
