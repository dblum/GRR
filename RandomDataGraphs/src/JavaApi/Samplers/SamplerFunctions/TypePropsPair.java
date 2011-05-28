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

package JavaApi.Samplers.SamplerFunctions;

/**
 * A wrapper class for pairing an rdf-type and an array of rdf-type which are his properties
 */
public class TypePropsPair {

    /**
     * Class Members
     */
    private String _type;
    private String[] _properties;

    /**
     * Constructor - sets the pair values
     * @param type - The rdf-type value
     * @param properties - The rdf-type[] which represents the properties of the given type in this pair
     */
    public TypePropsPair(String type, String[] properties) {
        _type = type;
        _properties = properties;
    }

    /**
     * Returns the rdf-type of this pair
     * @return - The rdf-type of this pair
     */
    public String getType() {
        return _type;
    }

    /**
     * Returns the rdf-type[] (properties) of this pair
     * @return - The rdf-type[] (properties) of this pair
     */
    public String[] getProperties() {
        return _properties;
    }
}