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

package JavaApi.Samplers.DictionarySamplers;


import java.io.IOException;

/**
 * A special const dictionary sampler, that is very similar to the ConstDictionarySampler, but this one will be
 * initialized
 */
public class ExternalConstDictionarySampler implements IDictionarySampler {

    /**
     * Class Members
     */
    private boolean _isInitialized;
    private String _label;

    /**
     * Constructor
     */
    public ExternalConstDictionarySampler() {
        _isInitialized = false;
    }

    /**
     * IDictionarySampler - Interface Implementation
     */

    /**
     * Initiates the instance by setting the label value to be the given value
     * Note that this is an ugly workaround that uses the string value (which
     * should be the name of the file that holds the labels). This will be replaced
     * in a later stage)
     * @param label - The label value that this sampler will return
     * @throws IOException - In practice can't happen (again bad interface implementation
     * design)
     */
    @Override
    public void init(String label) throws IOException {

        _label = label;
        _isInitialized = true;
    }

    /**
     * Returns true iff the Dictionary Sampler was initialized by invoking one of the init() methods.
     * @return - True iff the Dictionary Sampler was initialized by invoking one of the init() methods.
     */
    @Override
    public boolean isInitialized() {
        return _isInitialized;
    }

    /**
     * Returns a const value that was set upon init
     * @return - A const value that was set upon init
     * @throws IllegalStateException - If the instance wasn't initialized by an invocation of init().
     */
    @Override
    public String getRandomLabel() throws IllegalStateException {

        if (!_isInitialized)
            throw new IllegalStateException("Instance wasn't initialized by calling init()");

        return _label;
    }

}