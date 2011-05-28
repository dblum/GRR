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

package StorageWrappers;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * A class that implements the logic behind reading the TDB config file
 * This class enables the initialization of TDB that will be used for saving and loading the models 
 */
public class TDBConfigFileReader {

    /**
     * Consts
     */
    private static final String TDB_NODE = "TdbPath";
    private String _tdbPath;

    /**
     * The actual method that parses the TDB config file
     * @param configFile - The path to the configuration file
     */
    public void parseConfigFile(String configFile)
    {
        try {
            File file = new File(configFile);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            
            NodeList nodeList = doc.getElementsByTagName(TDB_NODE);
            if (nodeList.getLength() > 1)
                throw new IllegalStateException("configFile has multiple <" + TDB_NODE + "> tags!");
            if (nodeList.getLength() == 0)
                throw new IllegalStateException("configFile does not contain a <" + TDB_NODE + "> tag!");

            _tdbPath = nodeList.item(0).getTextContent();

            
        }
        catch (Exception e) {
            throw new InternalError("Failure in reading/parsing the config-file: " + configFile);
        }
    }

    /**
     * Method that returns the TDB folder path
     * @return - The TDB folder path
     */
    public String getTdbPath()
    {
        return _tdbPath;
    }
}
