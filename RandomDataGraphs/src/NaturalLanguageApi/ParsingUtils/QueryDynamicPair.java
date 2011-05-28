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

package NaturalLanguageApi.ParsingUtils;

/**
 * Helper class that acts as a wrapper of String (query) and boolean (is-dynamic)
 */
public class QueryDynamicPair {

    /**
     * Class Members
     */
    private String _query;
    private boolean _isDynamic;

    /**
     * Constructor - Only sets the members with the given arguments
     * @param query - The newly set string query
     * @param isDynamic - The newly set boolean value of is-dynamic
     */
    public QueryDynamicPair(String query, boolean isDynamic) {
        _query = query;
        _isDynamic = isDynamic;
    }

    /**
     * Returns the string query
     * @return - The string query
     */
    public String getQuery() {
        return _query;
    }

    /**
     * Returns the boolean is-dynamic value
     * @return - The boolean is-dynamic value
     */
    public boolean isDynamic() {
        return _isDynamic;
    }
}
