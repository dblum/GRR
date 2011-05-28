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

import com.hp.hpl.jena.rdf.model.Model;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A simple interface for storing and loading a model into a HDD based file.
 * Should be used mostly for debugging and small and simple models
 */
public interface IFileWrapper {

    /**
     * Loads the model from the given file path
     * @param file - The file that stores the model
     * @return - A model which represents the model stored in the given file
     * @throws IOException - In case of I/O problems while using the given file
     */
    public Model loadModel(String file) throws IOException;

    /**
     * Stores the given model into the destination file
     * @param model - The model to be stored
     * @param file - The output file location
     * @param format - The RdfFormat that will be used for storing the model (see RdfFormat documentation)
     * @throws FileNotFoundException - In case that the destination file path isn't valid
     */
    public void storeModel(Model model, String file, RdfFormat format) throws FileNotFoundException;
}
