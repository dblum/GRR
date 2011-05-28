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

import NaturalLanguageApi.RdfGenObjects.QueryParts.RdfMode;
import NaturalLanguageApi.RdfGenObjects.QueryParts.RdfQueryResultSelection;
import NaturalLanguageApi.RdfGenObjects.RdfObject;
import RdfApi.RdfGenTypes;
import RdfApi.RdfPrinter;

import java.util.ArrayList;

/**
 * Class that acts as a wrapper for a creation pattern (see members) in RDF format
 */
public class RdfQuery extends RdfObject {

    /**
     * Class Members
     */
    private RdfMode _mode;
    private RdfQueryResultSelection _qResSelection;
    private String _query;
    private String _queryFilePath;
    private boolean _isDynamic;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param mode - The newly set RdfMode
     * @param qResSelection - The newly set RdfQueryResultSelection
     * @param query - The newly set query
     * @param queryFilePath - The newly set query-file path
     * @param isDynamic - The newly set is-dynamic boolean
     */
    public RdfQuery(RdfMode mode, RdfQueryResultSelection qResSelection, String query, String queryFilePath, boolean isDynamic) {
        _mode = mode;
        _qResSelection = qResSelection;
        _query = query;
        _queryFilePath = queryFilePath;
        _isDynamic = isDynamic;
    }

    /**
     * Returns a string representation of this class
     * @param tabOffset - The offset for all printed lines of this instance
     * @return - A string representation of this class
     */
    @Override
    public String toRdfString(int tabOffset) {
        String query = "";

        ArrayList<String> queryLines = new ArrayList<String>();
        String modeEnum = RdfPrinter.printTabbedTagWithNoContent(tabOffset + 2, RdfGenTypes.RDF_GEN_PREFIX + _mode.toString());
        ArrayList<String> modeLines = new ArrayList<String>();
        modeLines.add(modeEnum);
        String mode = RdfPrinter.printTabbedComposite(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_MODE, modeLines);
        queryLines.add(mode);
        ArrayList<String> queryResLine = new ArrayList<String>();
        String qResSel = _qResSelection.toRdfString(tabOffset + 2);
        queryResLine.add(qResSel);
        String queryResSelection = RdfPrinter.printTabbedComposite(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_QUERY_RES_SELECTION, queryResLine);
        queryLines.add(queryResSelection);
        String sparqlQuery = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_SPARQL_QUERY, _queryFilePath);
        queryLines.add(sparqlQuery);
        String isDynamic = RdfPrinter.printTabbedLine(tabOffset + 1, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.PROP_RDF_TYPE_IS_DYNAMIC, "" + _isDynamic);
        queryLines.add(isDynamic);

        query += RdfPrinter.printTabbedComposite(tabOffset, RdfGenTypes.RDF_GEN_PREFIX + RdfGenTypes.RES_RDF_TYPE_QUERY, queryLines);

        return query;

    }
}
