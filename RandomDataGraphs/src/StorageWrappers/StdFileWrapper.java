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
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.*;

/**
 * A simple implementation of the IFileWrapper interface
 */
public class StdFileWrapper implements IFileWrapper {

    /**
     * Loads the model from the given file and returns a model
     * @param file - The file that stores the model
     * @return - A model which represents the model that was stored in the given file
     * @throws IOException - In case of I/O problems while reading the input file
     */
    @Override
    public Model loadModel(String file) throws IOException {

        // try to open and read the file
        File f = new File(file);
        if (!f.exists())
            throw new IOException("Give file doesn't exist: " + file);
        InputStream in = new FileInputStream(f);

        // Create a default model
        Model model = ModelFactory.createDefaultModel();
        model.read(in, null); // null base URI, since model URIs are absolute

        in.close();
         
        return model;
    }

    /**
     * Stores the given model into the destination file
     * @param model - The model to be stored
     * @param file - The output file location
     * @param format - The RdfFormat that will be used for storing the model (see RdfFormat documentation)
     * @throws FileNotFoundException - In case that the destination file path isn't valid
     */
    @Override
    public void storeModel(Model model, String file, RdfFormat format) throws FileNotFoundException {

        File f = new File(file);
        if (f.exists())
            f.delete();
        FileOutputStream out = new FileOutputStream(f);

        model.write(out, format.toString());
    }
}
