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
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;

import java.io.File;
import java.io.IOException;

/**
 * The TDB based wrapper which implements the IDBWrapper interface
 */
public class TDBWrapper implements IDBWrapper {

    /**
     * Class members
     */
    private String _tdbPath;
    private boolean _isInitialized;

    /**
     * Public Methods
     */

    /**
     * Initializes the DB by using a configuration file. This method must take care of all actions needed to be
     * performed, so the DB will be ready and in sync with the system
     * @param configFile - The configuration file to be used in order to initialize the DB
     * @throws IOException - I/O problems while reading the configuration file
     */
    @Override
    public void init(String configFile) throws IOException {

        TDBConfigFileReader configReader = new TDBConfigFileReader();
        configReader.parseConfigFile(configFile);
        _tdbPath = configReader.getTdbPath();
        _isInitialized = true;
    }

    /**
     * Returns true iff the DB was initialized, otherwise false
     * @return - True iff the DB was initialized, otherwise false
     */
    @Override
    public boolean isInitialized() {
        return _isInitialized;
    }

    /**
     * Stores the given model into the DB
     * @param model - The model to be stored in the DB
     */
    @Override
    public void storeModel(Model model) {
        if (!_isInitialized)
            throw new IllegalStateException("TDBWrapper was not initialized!");

        Model newModel = TDBFactory.createModel(_tdbPath);
        newModel.add(model);
    }

    /**
     * Returns a model backed by the DB
     * @return - A model backed by the DB
     */
    @Override
    public Model getDbBasedModel() {
        if (!_isInitialized)
            throw new IllegalStateException("TDBWrapper was not initialized!");

        return TDBFactory.createModel(_tdbPath);
    }

    /**
     * Returns an empty model which is backed by the DB
     * @return - An empty model which is backed by the DB
     */
    @Override
    public Model getEmptyDbBasedModel() {
        if (!_isInitialized)
            throw new IllegalStateException("TDBWrapper was not initialized!");

        TDB.closedown();

        File dir = new File(_tdbPath);
        
        /*if (dir.exists()) {
            boolean deleted = deleteDirectory(dir);
            if (!deleted)
                throw new IllegalStateException("Failed to delete TDB directory located at: " + _tdbPath);
        }
        
        if (!dir.exists())
            if (!dir.mkdir())
                throw new IllegalStateException("Failed to create the TDB directory at: " + _tdbPath);
        */
        Model model = TDBFactory.createModel(_tdbPath);
        TDB.sync(model);
        model.removeAll();
        return model;
    }

    /**
     * Closes all resources and connections to the DB
     */
    @Override
    public void close() {
        TDB.closedown();
    }

    /**
     * A static (recursive) method for deleting all of the TDB folder
     * @param path - The path to the TDB folder location
     * @return - True iff the folder was deleted successfully, otherwise false
     */
    static public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    boolean del = file.delete();
                    if (!del)
                        return false;
                }
            }
        }
        return (path.delete());
    }

}
