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

package Examples.Utils;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.resultset.ResultSetMem;

import java.util.HashMap;

/**
 *
 */
public class ExpUtils {

     public static HashMap<String, Integer> countResources(Model model, HashMap<String, String> nsMap, String[] types)
    {

        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (String type : types) {
            String queryStr = createQuery(nsMap, type);
            Query query = QueryFactory.create(queryStr);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet resultSet = qe.execSelect();
            ResultSetMem rsm = new ResultSetMem(resultSet);
            int total = rsm.size();
            map.put(type, total);
            
        }

        return map;
    }


    public static long getNumberOfTriples(Model model)
    {
        return model.size();
    }

    private static String createQuery(HashMap<String, String> nsMap, String rdfType)
    {
        String query = "";
        query += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n";
        query += "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n";
        for(String fullAlias : nsMap.keySet()) {
            query += "PREFIX " + fullAlias + " " + "<" + nsMap.get(fullAlias) + "> \n";
        }
        query += "SELECT ?Var \n";
        query += "WHERE { ?Var rdf:type " + rdfType + " . } \n";
        return query;
    }



}
