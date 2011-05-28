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

import JavaApi.Samplers.DictionarySamplers.IDictionarySampler;

import java.util.HashMap;

/**
 * Class that associates each node to a specific d-sampler
 */
public class SamplerFunction {

    /**
     * Class Members
     */
    HashMap<String, IDictionarySampler> _samplerMap;
    boolean _isInitialised;

    /**
     * Constructor which has an empty mapping ans is set to not-initialized state
     */
    public SamplerFunction() {
        _samplerMap = new HashMap<String, IDictionarySampler>();
        _isInitialised = false;
    }

    /**
     * Public mehtods
     */

    /**
     * Sets the rdf-type --> DictionarySampler mapping to be the given mapping
     * @param map - The new mapping to be used by this instance
     */
    public void loadMap(HashMap<String, IDictionarySampler> map)
    {
        _samplerMap.clear();
        addMap(map);
    }

    /**
     * Adds the given mapping to the existing rdf-type --> DictionarySampler mapping
     * @param map - The new mapping to be added to this instance
     */
    public void addMap(HashMap<String, IDictionarySampler> map)
    {
        _samplerMap.putAll(map);
        _isInitialised = true;
        
    }

    /**
     * Adds a specific pair to the mapping
     * @param rdfType - The new rdf-type (key)
     * @param dSampler - The dictionarySampler associated with the given type (value)
     */
    public void addValue(String rdfType, IDictionarySampler dSampler)
    {
        _samplerMap.put(rdfType, dSampler);
        _isInitialised = true;
    }

    /**
     * Returns the DictionarySampler associated with the given rdf-type (if exists)
     * @param rdfType - The given rdf-type to be searched in the mappings
     * @return - The DictionarySampler associated with the given rdf-type (if exists)
     * @throws IllegalArgumentException - In case that the given rdf-type can't be found in the mappings 
     */
    public IDictionarySampler getDSampler(String rdfType)
    {
        if (!_samplerMap.containsKey(rdfType))
            throw new IllegalArgumentException("The given class: " + rdfType + " doesn't exist in this sampler-function");
        
        return _samplerMap.get(rdfType);
    }


}
