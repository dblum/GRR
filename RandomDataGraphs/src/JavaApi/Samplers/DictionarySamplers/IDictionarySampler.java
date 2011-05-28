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
 * An interface for a Dictionary Value Sampler, called d-sampler for short, which
 * returns a random value from a dictionary of values.
 * When generating graphs, the labels of these graphs will be chosen using dictionary value samplers.
 */
public interface IDictionarySampler {

    /**
     * Initializes this instance with the data found in the given file. Invoking this methods will re-initialize any
     * previous invocation of any of the init methods.
     * @param fileName - A file containing the data that this instance will use for returning random values.
     * @throws IOException - In case of IO problems while handling the file
     */
    public void init(String fileName) throws IOException;
 
    /**
     * Returns true iff the Dictionary Sampler was initialized by invoking one of the init() methods
     * @return - True iff the Dictionary Sampler was initialized by invoking one of the init() methods
     */
    public boolean isInitialized();

    /**
     * Returns a random label value from the dictionary that was supplied in the init() method
     * @return - A random label value from the dictionary that was supplied in the init() method
     * @throws IllegalStateException - If the instance wasn't initialized by an invocation of init()
     */
    public String getRandomLabel() throws IllegalStateException;

}
