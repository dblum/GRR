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

package NaturalLanguageApi.RdfGenObjects.ConstructParts;

import NaturalLanguageApi.RdfGenObjects.RdfObject;
import RdfApi.RdfGenTypes;
import RdfApi.RdfPrinter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents a query's attribute variable mappings in RDF format
 */
public class RdfQueryAttVarMap extends RdfObject {

    /**
     * Class Members
     */
    private HashMap<String, String> _attVarMap;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param attVarMap - The newly set attribute-variable mappings
     */
    public RdfQueryAttVarMap(HashMap<String, String> attVarMap) {
        _attVarMap = attVarMap;
    }

    /**
     * Returns a string representation of this class
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this class
     */
    @Override
    public String toRdfString(int tabOffset) {
        String map = "";

        if (_attVarMap==null || _attVarMap.size()==0)
            map += RdfPrinter.printTabbedTagWithNoContent(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_ATT_DYN_VAR_MAP);
        else {

            RdfAttVarPair[] pairs = new RdfAttVarPair[_attVarMap.size()];
            int index = 0;
            for (String att : _attVarMap.keySet())
            {
                pairs[index] = new RdfAttVarPair(att, _attVarMap.get(att));
                index++;
            }
            map = RdfPrinter.printTabbedRdfContainer(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_ATT_DYN_VAR_MAP, "Bag", pairs);
        }

        return map;
    }
}