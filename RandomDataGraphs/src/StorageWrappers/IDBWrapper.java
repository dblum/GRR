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

import java.io.IOException;

/**
 * A generic interface that decouples the implementation of the system from the actual DB that is
 * chosen to be used
 */
public interface IDBWrapper {

    /**
     * Initializes the DB by using a configuration file. This method must take care of all actions needed to be
     * performed, so the DB will be ready and in sync with the system
     * @param configFile - The configuration file to be used in order to initialize the DB
     * @throws IOException - I/O problems while reading the configuration file
     */
    public void init(String configFile) throws IOException;

    /**
     * Returns true iff the DB was initialized, otherwise false
     * @return - True iff the DB was initialized, otherwise false
     */
    public boolean isInitialized();

    /**
     * Stores the given model into the DB
     * @param model - The model to be stored in the DB
     */
    public void storeModel(Model model);

    /**
     * Returns a model backed by the DB
     * @return - A model backed by the DB
     */
    public Model getDbBasedModel();

    /**
     * Returns an empty model which is backed by the DB
     * @return - An empty model which is backed by the DB
     */
    public Model getEmptyDbBasedModel();

    /**
     * Closes all resources and connections to the DB
     */
    public void close();
}
