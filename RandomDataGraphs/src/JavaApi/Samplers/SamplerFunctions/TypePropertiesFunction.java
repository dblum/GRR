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

import java.util.HashMap;

/**
 * Class that associates each rdf-type to a list of rdf-type properties
 */
public class TypePropertiesFunction {

    /**
     * Class members
     */
    HashMap<String, String[]> _typePropsMap;
    boolean _isInitialised;

    /**
     * Constructor that creates an empty mapping and sets the instance to not-initialized state
     */
    public TypePropertiesFunction() {
        _typePropsMap = new HashMap<String, String[]>();
        _isInitialised = false;
    }

    /**
     * Public methods
     */

    /**
     * Sets the rdf-type --> rdf-type[] mapping to be the given mapping
     * @param map - The new mapping to be used by this instance
     */
    public void loadMap(HashMap<String, String[]> map)
    {
        _typePropsMap.clear();
        addMap(map);
    }

    /**
     * Adds the given mapping to the existing rdf-type --> rdf-type[] mapping
     * @param map - The new mapping to be added to this instance
     */
    public void addMap(HashMap<String, String[]> map)
    {
        _typePropsMap.putAll(map);
        _isInitialised = true;
    }

    /**
     * Adds a specific value to the mappings
     * @param rdfType - The added value (key)
     * @param rdfProperties - The list of rdf-type[] which are the properties of this type (value)
     */
    public void addValue(String rdfType, String[] rdfProperties)
    {
        _typePropsMap.put(rdfType, rdfProperties);
        _isInitialised = true;
    }

     /**
     * Returns the rdf-type[] associated with the given rdf-type (if exists)
     * @param rdfType - The given rdf-type to be searched in the mappings
     * @return - The rdf-type[] associated with the given rdf-type (if exists)
     * @throws IllegalArgumentException - In case that the given rdf-type can't be found in the mappings
     */
    public String[] getProperties(String rdfType)
    {
        if (!_typePropsMap.containsKey(rdfType))
            return new String[0];

        return _typePropsMap.get(rdfType);
    }


}