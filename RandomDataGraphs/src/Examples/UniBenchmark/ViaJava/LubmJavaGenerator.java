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

package Examples.UniBenchmark.ViaJava;

import JavaApi.RandomDataGraph.GraphBuildingBlocks.Edge;
import JavaApi.RandomDataGraph.GraphBuildingBlocks.Node;
import JavaApi.RandomDataGraph.Matchers.IMatcher;
import JavaApi.RandomDataGraph.Matchers.StdMatcher;
import JavaApi.RandomDataGraph.Patterns.ConstructionPattern;
import JavaApi.RandomDataGraph.RandomGraphAPI;
import JavaApi.Samplers.DictionarySamplers.CounterDictionarySampler;
import JavaApi.Samplers.DictionarySamplers.IDictionarySampler;
import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.ConstantNumberSampler;
import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.CyclicCounterNumberSampler;
import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.INaturalNumberSampler;
import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.StdNaturalNumberSampler;
import JavaApi.Samplers.NumberSamplers.RealNumberSamplers.ConstantRealSampler;
import JavaApi.Samplers.NumberSamplers.RealNumberSamplers.IRealNumberSampler;
import JavaApi.Samplers.NumberSamplers.RealNumberSamplers.RangeRealNumberSampler;
import JavaApi.Samplers.QuerySamplers.QueryWrapper;
import JavaApi.Samplers.SamplerFunctions.SamplerFunction;
import JavaApi.Samplers.SamplerFunctions.TypePropertiesFunction;
import JavaApi.Samplers.SamplingMode;
import RdfApi.QueryOptimizationMode;
import SolutionConfig.Consts;
import SolutionConfig.SolutionConfigFile;
import StorageWrappers.*;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main that creates a whole Uni structure (Based on LUBM) while using the RandomGraphAPI 
 */
public class LubmJavaGenerator {

    /**
     * Consts
     */

    // Paths
    private static final String TDB_CONFIG_PATH = SolutionConfigFile.BASE_PATH + "TDB" + Consts.pathSep + "tdbConfig.xml";

    // Schema file path
    private static final String UNI_SCHEMA_FILE = "UniBenchRdfSchema.rdf";
    private static final String LOCAL_PATH = "./src/Examples.UniBenchmark/Data/";
    private static final String WEB_PATH = "http://www.cs.huji.ac.il/~danieb12/";
    private static final String LOCAL_UNI_SCHEMA_PATH = LOCAL_PATH + UNI_SCHEMA_FILE;
    private static final String WEB_UNI_SCHEMA_PATH = WEB_PATH + UNI_SCHEMA_FILE;

    // Output file path
    private static final String OUT_FILE_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "LubmJavaOutput.rdf";

    // NSs
    private static final String UB_NS = WEB_UNI_SCHEMA_PATH + "#";

    // Specific rdf:type values
    public static final String RDF_TYPE_UNI = "University";
    public static final String RDF_TYPE_DEP = "Department";
    public static final String RDF_TYPE_FACULTY = "Faculty";
    public static final String RDF_TYPE_PROFESSOR = "Professor";
    public static final String RDF_TYPE_FULL_PROF = "FullProfessor";
    public static final String RDF_TYPE_ASSO_PROF = "AssociateProfessor";
    public static final String RDF_TYPE_ASSI_PROF = "AssistantProfessor";
    public static final String RDF_TYPE_LECTURER = "Lecturer";
    public static final String RDF_TYPE_COURSE = "Course";
    public static final String RDF_TYPE_GRAD_COURSE = "GraduateCourse";
    public static final String RDF_TYPE_STUDENT = "Student";
    public static final String RDF_TYPE_UNDER_STUD = "UndergraduateStudent";
    public static final String RDF_TYPE_GRAD_STUD = "GraduateStudent";
    public static final String RDF_TYPE_RES_GROUP = "ResearchGroup";
    public static final String RDF_TYPE_PUB = "Publication";

    public static final String RDF_TYPE_SUB_ORG = "subOrganizationOf";
    public static final String RDF_TYPE_WORKS_FOR = "worksFor";
    public static final String RDF_TYPE_ADVISOR = "advisor";
    public static final String RDF_TYPE_HEAD_OF = "headOf";
    public static final String RDF_TYPE_TEACHER_OF = "teacherOf";
    public static final String RDF_TYPE_MEMBER_OF = "memberOf";
    public static final String RDF_TYPE_TAKES_COURSE = "takesCourse";
    public static final String RDF_TYPE_PUB_AUTHOR = "publicationAuthor";
    public static final String RDF_TYPE_UND_DEG_FROM = "undergraduateDegreeFrom";
    public static final String RDF_TYPE_MAS_DEG_FROM = "mastersDegreeFrom";
    public static final String RDF_TYPE_DOC_DEG_FROM = "doctoralDegreeFrom";

    // Min-Max values
    public static final int UNI_MAX = 10;
    public static final int UNI_MIN = 10;

    public static final int DEP_MAX = 25;
    public static final int DEP_MIN = 15;

    public static final int FULL_PROF_MAX = 10;
    public static final int FULL_PROF_MIN = 7;
    public static final int ASSO_PROF_MAX = 14;
    public static final int ASSO_PROF_MIN = 10;
    public static final int ASSI_PROF_MAX = 11;
    public static final int ASSI_PROF_MIN = 8;
    public static final int LECTURER_MAX = 7;
    public static final int LECTURER_MIN = 5;
    public static final int COURSE_MAX = 2;
    public static final int COURSE_MIN = 1;
    public static final int GRAD_COURSE_MAX = 2;
    public static final int GRAD_COURSE_MIN = 1;
    public static final int RES_GROUP_MAX = 20;
    public static final int RES_GROUP_MIN = 10;
    public static final int UND_STUD_MAX = 14;
    public static final int UND_STUD_MIN = 8;
    public static final int GRAD_STUD_MAX = 4;
    public static final int GRAD_STUD_MIN = 3;
    public static final int UND_STUD_COURSES_MAX = 4;
    public static final int UND_STUD_COURSES_MIN = 2;
    public static final int GRAD_STUD_COURSES_MAX = 3;
    public static final int GRAD_STUD_COURSES_MIN = 1;

    public static final int FULL_PROF_PUB_MAX = 20;
    public static final int FULL_PROF_PUB_MIN = 15;
    public static final int ASSO_PROF_PUB_MAX = 18;
    public static final int ASSO_PROF_PUB_MIN = 10;
    public static final int ASSI_PROF_PUB_MAX = 10;
    public static final int ASSI_PROF_PUB_MIN = 5;
    public static final int LECTURER_PUB_MAX = 5;
    public static final int LECTURER_PUB_MIN = 0;

    /**
     * Class members
     */
    private static Model _graphModel;
    private static InfModel _infModel;
    private static RandomGraphAPI _randGraphApi = new RandomGraphAPI();
    private static SamplerFunction _sFunction = new SamplerFunction();
    private static TypePropertiesFunction _typePropFunction = new TypePropertiesFunction();
    private static CyclicCounterNumberSampler _idSampler = new CyclicCounterNumberSampler();

    /**
     * Public static methods
     */

    /**
     * @param args
     */
    public static void main(String[] args) {

        // ---------------------------------------------------------------------
        // First we want to create a TDB based model
        // ---------------------------------------------------------------------
        try {
            String configFile = TDB_CONFIG_PATH;
            IDBWrapper _dbw = new TDBWrapper();
            _graphModel = TDBFactory.createModel();
            _dbw.init(configFile);

            Model _schema = FileManager.get().loadModel(WEB_UNI_SCHEMA_PATH);
            _infModel = ModelFactory.createRDFSModel(_schema, _graphModel);
            _infModel.setNsPrefix("ns", UB_NS);
            _graphModel.setNsPrefix("ns", UB_NS);

        }
        catch (IOException ioe) {
            System.out.println("Failed to load the configuration file for the TDB");
        }

        // ---------------------------------------------------------------------
        // Now we'll start generating the graph while each private method usually
        // represents one line in the LUBM example:
        // http://swat.cse.lehigh.edu/projects/lubm/profile.htm
        // ---------------------------------------------------------------------

        // Add University
        addUniversity();

        // Add departments which are connected to the university
        addDepartments();

        // Add Full-Professor
        addFullProfessor();
        // Add Assistant-Professor
        addAssistantProfessor();
        // Add Associate-Professor
        addAssociateProfessor();
        // Add lecturers
        addLecturer();

        // Add the head of each department
        addHeadOfDepartment();

        //add courses
        addCourses();
        //add graduate courses
        addGradCourses();

        // Add research Groups
        addResearchGroup();

        // Add Undergraduate-Students + Set member-of property
        addUnderStudents();
        // Add GraduateStudent + Set member-of property
        addGradStudents();


        // Set that 1/5 of the Undergraduate-Students have a professor as an advisor
        addAdvisorProertyForUnderStud();
        addAdvisorPropertyForGradStud();

        // Add the courses each student takes
        // TODO check hang problem
        //addUnderStudCourse();
        //addGradStudCourse();

        // Add publications
        addFullProfPub();
        addAssoProfPub();
        addAssiProfPub();
        addLecturerPub();

        // Add all of the degrees
        addDegrees();

        // Add Grad-Student's degree
        addGradDegree();



        // Print the graph
        _graphModel.write(System.out, RdfFormat.RDF_XML_FORMAT.toString());
        // Save it into a file
        IFileWrapper fw = new StdFileWrapper();
        try {
            fw.storeModel(_graphModel, OUT_FILE_PATH, RdfFormat.RDF_XML_FORMAT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Output file wasn't found!");
        }
    }

    /**
     *  Public Methods
     */


    /**
     * Private Methods
     */

    /**
     * Creates a University node in the model
     */
    private static void addUniversity() {
        try {

            // Create the number sampler
            INaturalNumberSampler uniNSampler = new ConstantNumberSampler(); // Default is to return 1

            // Create the uni d-sampler
            IDictionarySampler dSampler = new CounterDictionarySampler();
            dSampler.init(RDF_TYPE_UNI);

            // Update the Sampler Function
            _sFunction.addValue(RDF_TYPE_UNI, dSampler);

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            int id = _idSampler.getNextNatural();

            Node baseNode = new Node(null, RDF_TYPE_UNI, id, null);
            newNodes.put(id, baseNode);
            ConstructionPattern uniCPattern = new ConstructionPattern(null, newNodes, _sFunction, _typePropFunction);

            // Invoke the construction command
            _randGraphApi.construct(
                   _infModel,
                    null,
                    uniNSampler,
                    uniCPattern,
                    null,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates number of department nodes in the model
     */
    private static void addDepartments() {
        try {

            // Create the number sampler
            StdNaturalNumberSampler nSampler = new StdNaturalNumberSampler();
            nSampler.setMaxValue(DEP_MAX);
            nSampler.setMinValue(DEP_MIN);

            // Create the dep d-sampler
            IDictionarySampler dSampler = new CounterDictionarySampler();
            dSampler.init(RDF_TYPE_DEP);

            // Update the Sampler Function
            _sFunction.addValue(RDF_TYPE_DEP, dSampler);

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int id = _idSampler.getNextNatural();
            int depId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Uni) and the new node (dep)
            Edge edge = new Edge(RDF_TYPE_SUB_ORG, edgeId, id);

            // The new format of the dep node
            ArrayList<Edge> depEdges = new ArrayList<Edge>();
            depEdges.add(edge);
            Node depNode = new Node(null, RDF_TYPE_DEP, depId, depEdges);
            newNodes.put(depId, depNode);

            // The old node (Uni)
            Node baseNode = new Node(null, RDF_TYPE_UNI, id, null);
            oldNodes.put(id, baseNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String uniQueryAtt = "?Uni";
            matcher.addMapping(id, uniQueryAtt);

            // The construction pattern
            ConstructionPattern depCPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the Uni node
            String queryString =
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + uniQueryAtt + " WHERE { " + uniQueryAtt + " rdf:type ns:" + RDF_TYPE_UNI + " . } \n";
            QueryWrapper queryWrapper = new QueryWrapper(queryString, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    depCPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates full-professor nodes in the model
     */
    private static void addFullProfessor() {
        addFacultyMember(
                FULL_PROF_MAX,
                FULL_PROF_MIN,
                RDF_TYPE_FULL_PROF,
                RDF_TYPE_FULL_PROF);
    }

    /**
     * Creates associate-professor nodes in the model
     */
    private static void addAssociateProfessor() {
        addFacultyMember(
                ASSO_PROF_MAX,
                ASSO_PROF_MIN,
                RDF_TYPE_ASSO_PROF,
                RDF_TYPE_ASSO_PROF);
    }

    /**
     * Creates assistant-professor nodes in the model
     */
    private static void addAssistantProfessor() {
        addFacultyMember(
                ASSI_PROF_MAX,
                ASSI_PROF_MIN,
                RDF_TYPE_ASSI_PROF,
                RDF_TYPE_ASSI_PROF);
    }

    /**
     * Creates lecturer nodes in the model
     */
    private static void addLecturer() {
        addFacultyMember(
                LECTURER_MAX,
                LECTURER_MIN,
                RDF_TYPE_LECTURER,
                RDF_TYPE_LECTURER);
    }

    /**
     * Generic method for creating a faculty member in the model
     * @param max - The max instances to be created of this type
     * @param min - The min instances to be created of this type
     * @param label - The base label to be used (it will be added by a trailing counter number
     * @param rdfType - The type to be created
     */
    private static void addFacultyMember(
            int max,
            int min,
            String label,
            String rdfType) {
        try {
            // Create the number sampler
            StdNaturalNumberSampler nSampler = new StdNaturalNumberSampler();
            nSampler.setMaxValue(max);
            nSampler.setMinValue(min);

            // Create the dep d-sampler
            IDictionarySampler dSampler = new CounterDictionarySampler();
            dSampler.init(label); 

            // Update the Sampler Function
            _sFunction.addValue(rdfType, dSampler);

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int depId = _idSampler.getNextNatural();
            int profId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Dep) and the new node (FullProf)
            Edge edge = new Edge(RDF_TYPE_WORKS_FOR, edgeId, depId);

            // The new format of the fullProfessor node
            ArrayList<Edge> fullProfEdges = new ArrayList<Edge>();
            fullProfEdges.add(edge);
            Node fullProfNode = new Node(null, rdfType, profId, fullProfEdges);
            newNodes.put(profId, fullProfNode);

            // The old node (Dep)
            Node depNode = new Node(null, RDF_TYPE_DEP, depId, null);
            oldNodes.put(depId, depNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String depQueryAtt = "?Dep";
            matcher.addMapping(depId, depQueryAtt);

            // The construction pattern
            ConstructionPattern depCPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the Uni node
            String queryString =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + depQueryAtt + " WHERE { " + depQueryAtt + "  rdf:type ns:" + RDF_TYPE_DEP + " . }";
            QueryWrapper queryWrapper = new QueryWrapper(queryString, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    depCPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE, 
                    null);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the 'head-of-department' property which chooses one full-professor that will be acting as
     * the head of the department that it belongs to 
     */
    private static void addHeadOfDepartment() {
        try {
            // Create the number sampler
            INaturalNumberSampler nSampler = new ConstantNumberSampler();

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int depId = _idSampler.getNextNatural();
            int profId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Prof) and the child-node (Dep)
            Edge edge = new Edge(RDF_TYPE_HEAD_OF, edgeId, depId);

            //  The new edge that we wish to add for each chosen full-proffesor
            ArrayList<Edge> fullProfEdges = new ArrayList<Edge>();
            fullProfEdges.add(edge);

            // The old nodes (Prof and Under-Stud)
            Node depNode = new Node(null, RDF_TYPE_DEP, depId, null);
            oldNodes.put(depId, depNode);
            Node profNode = new Node(null, RDF_TYPE_FULL_PROF, profId, fullProfEdges);
            oldNodes.put(profId, profNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String depQueryAtt = "?Dep";
            matcher.addMapping(depId, depQueryAtt);
            String profQueryAtt = "?FullProf";
            matcher.addMapping(profId, profQueryAtt);

            // The construction pattern
            ConstructionPattern cPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the under-student node
            String queryString1 =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + depQueryAtt + " WHERE { " + depQueryAtt + "  rdf:type ns:" + RDF_TYPE_DEP + " . }";
            QueryWrapper queryWrapper1 = new QueryWrapper(queryString1, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Create the query that should return the full-professor node
            String dynamicVar = "_DYNAMIC_VAR_1_";
            String queryString2 =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + profQueryAtt +
                            " WHERE { " +
                            "" + profQueryAtt + "  rdf:type ns:" + RDF_TYPE_FULL_PROF + " . " +
                            "" + profQueryAtt + "  ns:worksFor <" + dynamicVar + "> . " +
                            "}";
            IRealNumberSampler realNumSampler = new ConstantRealSampler();
            QueryWrapper queryWrapper2  = new QueryWrapper(queryString2, SamplingMode.RANDOM_LOCAL_DISTINCT, realNumSampler, true);   // TODO check if this should be global or local!!!

            HashMap<String, String> queryAttributeVariableMap = new HashMap<String, String>();
            queryAttributeVariableMap.put(depQueryAtt, dynamicVar);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper1);
            queryWrappers.add(queryWrapper2);
            _randGraphApi.constructDynamic(
                    _infModel,
                    queryWrappers,
                    queryAttributeVariableMap,
                    nSampler,
                    cPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates course nodes in the model
     */
    private static void addCourses() {
        addGenericCourse(
                COURSE_MAX,
                COURSE_MIN,
                RDF_TYPE_COURSE,
                RDF_TYPE_COURSE);
    }

    /**
     * Creates graduate-course nodes in the model
     */
    private static void addGradCourses() {
        addGenericCourse(
                GRAD_COURSE_MAX,
                GRAD_COURSE_MIN,
                RDF_TYPE_GRAD_COURSE,
                RDF_TYPE_GRAD_COURSE);
    }

    /**
     * Generic method for creating course nodes in the model
     * @param max - The max instances to be created of this type
     * @param min - The min instances to be created of this type
     * @param label - The base label to be used (it will be added by a trailing counter number
     * @param rdfType - The type to be created
     */
    private static void addGenericCourse(
            int max,
            int min,
            String label,
            String rdfType
    ) {
        try {

            // Create the number sampler
            StdNaturalNumberSampler nSampler = new StdNaturalNumberSampler();
            nSampler.setMaxValue(max);
            nSampler.setMinValue(min);

            // Create the dep d-sampler
            IDictionarySampler dSampler = new CounterDictionarySampler();
            dSampler.init(label); 
            
            // Update the Sampler Function
            _sFunction.addValue(rdfType, dSampler);

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int facId = _idSampler.getNextNatural();
            int courseId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Uni) and the new node (dep)
            Edge edge = new Edge(RDF_TYPE_TEACHER_OF, edgeId, courseId);

            // The new format of the course node
            Node courseNode = new Node(null, rdfType, courseId, null);
            newNodes.put(courseId, courseNode);

            // The old node (Faculty-Member)
            ArrayList<Edge> facEdges = new ArrayList<Edge>();
            facEdges.add(edge);
            Node facNode = new Node(null, RDF_TYPE_FACULTY, facId, facEdges);
            oldNodes.put(facId, facNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String facQueryAtt = "?Faculty";
            matcher.addMapping(facId, facQueryAtt);

            // The construction pattern
            ConstructionPattern courseCPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the Uni node
            String queryString =
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + facQueryAtt + " WHERE { " + facQueryAtt + " rdf:type ns:" + RDF_TYPE_FACULTY + " . } \n";
            QueryWrapper queryWrapper = new QueryWrapper(queryString, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    courseCPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates Under-graduate Student nodes in the model
     */
    private static void addUnderStudents() {
        addGenericStudent(
                UND_STUD_MAX,
                UND_STUD_MIN,
                RDF_TYPE_UNDER_STUD,
                RDF_TYPE_UNDER_STUD);
    }

    /**
     * Creates Graduate Student nodes in the model
     */
    private static void addGradStudents() {
        addGenericStudent(
                GRAD_STUD_MAX,
                GRAD_STUD_MIN,
                RDF_TYPE_GRAD_STUD,
                RDF_TYPE_GRAD_STUD);
    }

    /**
     * Generic method for creating student nodes in the model
     * @param max - The max instances to be created of this type
     * @param min - The min instances to be created of this type
     * @param label - The base label to be used (it will be added by a trailing counter number
     * @param rdfType - The type to be created
     */
    private static void addGenericStudent(
            int max,
            int min,
            String label,
            String rdfType
    ) {
        try {

            // Create the number sampler
            StdNaturalNumberSampler nSampler = new StdNaturalNumberSampler();
            nSampler.setMaxValue(max);
            nSampler.setMinValue(min);

            // Create the dep d-sampler
            IDictionarySampler dSampler = new CounterDictionarySampler();
            dSampler.init(label); 

            // Update the Sampler Function
            _sFunction.addValue(rdfType, dSampler);

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int facId = _idSampler.getNextNatural();
            int studId = _idSampler.getNextNatural();
             int depId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Uni) and the new node (dep)
            Edge edge = new Edge(RDF_TYPE_MEMBER_OF, edgeId, depId);

            // The new format of the stud node
            ArrayList<Edge> studEdges = new ArrayList<Edge>();
            studEdges.add(edge);
            Node studNode = new Node(null, rdfType, studId, studEdges);
            newNodes.put(studId, studNode);

            // The old nodes (Faculty-Member and department)
            Node depNode = new Node(null, RDF_TYPE_DEP, depId, null);
            oldNodes.put(depId, depNode);
            Node facNode = new Node(null, RDF_TYPE_FACULTY, facId, null);
            oldNodes.put(facId, facNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String facQueryAtt = "?Faculty";
            matcher.addMapping(facId, facQueryAtt);
            String studQueryAtt = "?Student";
            matcher.addMapping(studId, studQueryAtt);
            String depQueryAtt = "?Dep";
            matcher.addMapping(depId, depQueryAtt);

            // The construction pattern
            ConstructionPattern studCPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the Uni node
            String queryString =
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + facQueryAtt + " " + depQueryAtt + " WHERE { " +
                                    facQueryAtt + " rdf:type ns:" + RDF_TYPE_FACULTY + " . " +
                                    facQueryAtt + " ns:" + RDF_TYPE_WORKS_FOR + " " + depQueryAtt + " .  " +
                            " } \n";
            QueryWrapper queryWrapper = new QueryWrapper(queryString, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    studCPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    /**
     * Creates Research-Group nodes in the model
     */
    private static void addResearchGroup() {
        try {
            // Create the number sampler
            StdNaturalNumberSampler nSampler = new StdNaturalNumberSampler();
            nSampler.setMaxValue(RES_GROUP_MAX);
            nSampler.setMinValue(RES_GROUP_MIN);

            // Create the dep d-sampler
            IDictionarySampler dSampler = new CounterDictionarySampler();
            dSampler.init(RDF_TYPE_RES_GROUP); 

            // Update the Sampler Function
            _sFunction.addValue(RDF_TYPE_RES_GROUP, dSampler);

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int depId = _idSampler.getNextNatural();
            int resId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Dep) and the new node (Res-Group)
            Edge edge = new Edge(RDF_TYPE_SUB_ORG, edgeId, depId);

            // The new format of the res-group node
            ArrayList<Edge> resEdges = new ArrayList<Edge>();
            resEdges.add(edge);
            Node resNode = new Node(null, RDF_TYPE_RES_GROUP, resId, resEdges);
            newNodes.put(resId, resNode);

            // The old node (Dep)
            Node depNode = new Node(null, RDF_TYPE_DEP, depId, null);
            oldNodes.put(depId, depNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String depQueryAtt = "?Dep";
            matcher.addMapping(depId, depQueryAtt);

            // The construction pattern
            ConstructionPattern resCPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the Uni node
            String queryString =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + depQueryAtt + " WHERE { " + depQueryAtt + "  rdf:type ns:" + RDF_TYPE_DEP + " . }";
            QueryWrapper queryWrapper = new QueryWrapper(queryString, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    resCPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the advisor property which connects some of the Under-Graduate Students to a professor (acting as an advisor)
     */
    private static void addAdvisorProertyForUnderStud() {
        try {

            // Create the number sampler
            INaturalNumberSampler nSampler = new ConstantNumberSampler();

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int profId = _idSampler.getNextNatural();
            int underStudId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Prof) and the child-node (Under-Stud)
            Edge edge = new Edge(RDF_TYPE_ADVISOR, edgeId, profId);

            //  The new edge that we wish to add for each undergraduate-student
            ArrayList<Edge> underStudEdges = new ArrayList<Edge>();
            underStudEdges.add(edge);

            // The old nodes (Prof and Under-Stud)
            Node profNode = new Node(null, RDF_TYPE_PROFESSOR, profId, null);
            oldNodes.put(profId, profNode);
            Node underStudNode = new Node(null, RDF_TYPE_UNDER_STUD, underStudId, underStudEdges);
            oldNodes.put(underStudId, underStudNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String profQueryAtt = "?Prof";
            matcher.addMapping(profId, profQueryAtt);
            String studQueryAtt = "?UnderStud";
            matcher.addMapping(underStudId, studQueryAtt);

            // The construction pattern
            ConstructionPattern cPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the real-number sampler
            RangeRealNumberSampler realNumSampler1 = new RangeRealNumberSampler();
            realNumSampler1.setMaxValue(1.0 / 5.0);
            realNumSampler1.setMinValue(1.0 / 5.0);

            // Create the query that should return the under-student node
            String queryString1 =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + studQueryAtt + " WHERE { " + studQueryAtt + "  rdf:type ns:" + RDF_TYPE_UNDER_STUD + " . }";
            QueryWrapper queryWrapper1 = new QueryWrapper(queryString1, SamplingMode.RANDOM_GLOBAL_DISTINCT, realNumSampler1);


            // Create the real-number sampler
            ConstantRealSampler realNumSampler2 = new ConstantRealSampler();

            // Create the query that should return the professor node
            String queryString2 =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + profQueryAtt + " WHERE { " + profQueryAtt + "  rdf:type ns:" + RDF_TYPE_PROFESSOR + " . }";

            QueryWrapper queryWrapper2 = new QueryWrapper(queryString2, SamplingMode.RANDOM_REPEATABLE, realNumSampler2);

            // Invoke the construction command
             ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper1);
            queryWrappers.add(queryWrapper2);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    cPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    /**
     * Adds the advisor property which connects each Graduate Student to a professor (acting as an advisor)Adds the advisor property which connects all of the professors and Under-Graduate Students
     */
    private static void addAdvisorPropertyForGradStud() {
        try {

            // Create the number sampler
            INaturalNumberSampler nSampler = new ConstantNumberSampler();

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int profId = _idSampler.getNextNatural();
            int gradStudId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Prof) and the child-node (Under-Stud)
            Edge edge = new Edge(RDF_TYPE_ADVISOR, edgeId, profId);

            //  The new edge that we wish to add for each undergraduate-student
            ArrayList<Edge> gradStudEdges = new ArrayList<Edge>();
            gradStudEdges.add(edge);

            // The old nodes (Prof and Under-Stud)
            Node profNode = new Node(null, RDF_TYPE_PROFESSOR, profId, null);
            oldNodes.put(profId, profNode);
            Node gradStudNode = new Node(null, RDF_TYPE_GRAD_STUD, gradStudId, gradStudEdges);
            oldNodes.put(gradStudId, gradStudNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String profQueryAtt = "?Prof";
            matcher.addMapping(profId, profQueryAtt);
            String studQueryAtt = "?GradStud";
            matcher.addMapping(gradStudId, studQueryAtt);

            // The construction pattern
            ConstructionPattern cPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the under-student node
            String queryString1 =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + studQueryAtt + " WHERE { " + studQueryAtt + "  rdf:type ns:" + RDF_TYPE_GRAD_STUD + " . }";
            QueryWrapper queryWrapper1 = new QueryWrapper(queryString1, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Create the real-number sampler
            ConstantRealSampler realNumSampler = new ConstantRealSampler();

            // Create the query that should return the full-professor node
            String queryString2 =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + profQueryAtt + " WHERE { " + profQueryAtt + "  rdf:type ns:" + RDF_TYPE_PROFESSOR + " . }";
            QueryWrapper queryWrapper2 = new QueryWrapper(queryString2, SamplingMode.RANDOM_REPEATABLE, realNumSampler);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper1);
            queryWrappers.add(queryWrapper2);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    cPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    private static void addUnderStudCourse() {
        addStudCourseGeneric(
                UND_STUD_COURSES_MAX,
                UND_STUD_COURSES_MIN,
                RDF_TYPE_UNDER_STUD,
                RDF_TYPE_COURSE,
                UB_NS);
    }

    private static void addGradStudCourse() {
        addStudCourseGeneric(
                GRAD_STUD_COURSES_MAX,
                GRAD_STUD_COURSES_MIN,
                RDF_TYPE_GRAD_STUD,
                RDF_TYPE_GRAD_COURSE,
                UB_NS);
    }

    private static void addStudCourseGeneric(
            int max,
            int min,
            String rdfStudType,
            String rdfCourseType,
            String nameSpace
    ) {
        try {

            // Create the number sampler
            StdNaturalNumberSampler nSampler = new StdNaturalNumberSampler();
            nSampler.setMaxValue(max);
            nSampler.setMinValue(min);

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int studId = _idSampler.getNextNatural();
            int courseId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (Prof) and the child-node (Under-Stud)
            Edge edge = new Edge(RDF_TYPE_TAKES_COURSE, nameSpace, edgeId, courseId);

            //  The new edge that we wish to add for each undergraduate-student
            ArrayList<Edge> studEdges = new ArrayList<Edge>();
            studEdges.add(edge);

            // The old nodes (Stud and Course)
            Node studNode = new Node(null, rdfStudType, nameSpace, studId, studEdges);
            oldNodes.put(studId, studNode);
            Node courseNode = new Node(null, rdfCourseType, nameSpace, courseId, null);
            oldNodes.put(courseId, courseNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String studQueryAtt = "?Stud";
            matcher.addMapping(studId, studQueryAtt);
            String courseQueryAtt = "?Course";
            matcher.addMapping(courseId, courseQueryAtt);

            // The construction pattern
            ConstructionPattern cPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction);

            // Create the query that should return the under-student node
            String queryString1 =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + nameSpace + "> " +
                            "SELECT " + studQueryAtt + " WHERE { " + studQueryAtt + "  rdf:type ns:" + rdfStudType + " . }";
            QueryWrapper queryModePair1 = new QueryWrapper(queryString1, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Create the query that should return the full-professor node
            String queryString2 =
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                            "PREFIX ns: <" + nameSpace + "> " +
                            "SELECT " + courseQueryAtt + " WHERE { " + courseQueryAtt + "  rdf:type ns:" + rdfCourseType + " . }";
            QueryWrapper queryModePair2 = new QueryWrapper(queryString2, SamplingMode.RANDOM_LOCAL_DISTINCT, null);

            // Invoke the construction command
            _randGraphApi.completeConstruction(
                    _infModel,
                    queryModePair1,
                    nSampler,
                    queryModePair2,
                    cPattern,
                    matcher);

        }
        catch (RdfNodeExistsInModelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
     */

    /**
     * Creates Publication nodes (that were published by Full-Professors) in the model
     */
    private static void addFullProfPub() {
           addGenericPublication(
                   FULL_PROF_PUB_MAX,
                   FULL_PROF_PUB_MIN,
                   RDF_TYPE_FULL_PROF + "Publication",
                   RDF_TYPE_FULL_PROF);
    }

    /**
     * Creates Publication nodes (that were published by Asso-Professors) in the model
     */
    private static void addAssoProfPub() {
           addGenericPublication(
                   ASSO_PROF_PUB_MAX,
                   ASSO_PROF_PUB_MIN,
                   RDF_TYPE_ASSO_PROF + "Publication",
                   RDF_TYPE_ASSO_PROF);
    }

    /**
     * Creates Publication nodes (that were published by Assi-Professors) in the model
     */
    private static void addAssiProfPub() {
        addGenericPublication(
                   ASSI_PROF_PUB_MAX,
                   ASSI_PROF_PUB_MIN,
                   RDF_TYPE_ASSI_PROF + "Publication",
                   RDF_TYPE_ASSI_PROF);
    }

    /**
     * Creates Publication nodes (that were published by Lecturers) in the model 
     */
    private static void addLecturerPub() {
        addGenericPublication(
                   LECTURER_PUB_MAX,
                   LECTURER_PUB_MIN,
                   RDF_TYPE_LECTURER + "Publication",
                   RDF_TYPE_LECTURER);
    }

    /**
     * Generic method for creating a Publication node
     */
    private static void addGenericPublication(
            int max,
            int min,
            String label,
            String rdfType)
    {
            try {

            // Create the number sampler
            StdNaturalNumberSampler nSampler = new StdNaturalNumberSampler();
            nSampler.setMaxValue(max);
            nSampler.setMinValue(min);

            // Create the dep d-sampler
            IDictionarySampler dSampler = new CounterDictionarySampler();
            dSampler.init(label);

            // Update the Sampler Function
            _sFunction.addValue(RDF_TYPE_PUB, dSampler);

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int facId = _idSampler.getNextNatural();
            int pubId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the base (faculty) and the new node (publication)
            Edge edge = new Edge(RDF_TYPE_PUB_AUTHOR, edgeId, pubId);

            // The new format of the pub node
            Node pubNode = new Node(null, RDF_TYPE_PUB, pubId, null);
            newNodes.put(pubId, pubNode);

            // The old node (Faculty-Member)
            ArrayList<Edge> facEdges = new ArrayList<Edge>();
            facEdges.add(edge);
            Node facNode = new Node(null, rdfType, facId, facEdges);
            oldNodes.put(facId, facNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String facQueryAtt = "?Faculty";
            matcher.addMapping(facId, facQueryAtt);

            // The construction pattern
            ConstructionPattern courseCPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the Uni node
            String queryString =
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + facQueryAtt + " WHERE { " + facQueryAtt + " rdf:type ns:" + rdfType + " . } \n";
            QueryWrapper queryWrapper = new QueryWrapper(queryString, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    courseCPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * Adds the different degrees that different faculty-members have
     */
     private static void addDegrees() {
        try {

            // Create the number sampler
            INaturalNumberSampler nSampler = new ConstantNumberSampler();

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int uniId = _idSampler.getNextNatural();
            int facId = _idSampler.getNextNatural();
            int edgeId1 = _idSampler.getNextNatural();
            int edgeId2 = _idSampler.getNextNatural();
            int edgeId3 = _idSampler.getNextNatural();

            // The new edge that will connect the Dep and the Uni
            Edge edge1 = new Edge(RDF_TYPE_UND_DEG_FROM, edgeId1, uniId);
            Edge edge2 = new Edge(RDF_TYPE_MAS_DEG_FROM, edgeId2, uniId);
            Edge edge3 = new Edge(RDF_TYPE_DOC_DEG_FROM, edgeId3, uniId);

            // The Uni node
           Node uniNode = new Node(null, RDF_TYPE_UNI, uniId, null);
            oldNodes.put(uniId, uniNode);

            // The Faculty-Member node
            ArrayList<Edge> facEdges = new ArrayList<Edge>();
            facEdges.add(edge1);
            facEdges.add(edge2);
            facEdges.add(edge3);
            Node facNode = new Node(null, RDF_TYPE_FACULTY, facId, facEdges);
            oldNodes.put(facId, facNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String uniQueryAtt = "?Uni";
            matcher.addMapping(uniId, uniQueryAtt);
            String facQueryAtt = "?Faculty";
            matcher.addMapping(facId, facQueryAtt);

            // The construction pattern
            ConstructionPattern studCPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the Faculty node
            String queryString1 =
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + facQueryAtt + " WHERE { " + facQueryAtt + " rdf:type ns:" + RDF_TYPE_FACULTY + " . } \n";
            QueryWrapper queryWrapper1 = new QueryWrapper(queryString1, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            String queryString2 =
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + uniQueryAtt + " WHERE { " + uniQueryAtt + " rdf:type ns:" + RDF_TYPE_UNI + " . } \n";
            IRealNumberSampler realNumSampler = new ConstantRealSampler();
            QueryWrapper queryWrapper2 = new QueryWrapper(queryString2, SamplingMode.RANDOM_REPEATABLE, realNumSampler);

            // Invoke the construction command
            ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper1);
            queryWrappers.add(queryWrapper2);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    studCPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
     }

    /**
     * Adds an under-graduate degree to a Graduate-Student
     */
     private static void addGradDegree() {
        try {

            // Create the number sampler
            INaturalNumberSampler nSampler = new ConstantNumberSampler();

            // Create the construction pattern
            HashMap<Integer, Node> newNodes = new HashMap<Integer, Node>();
            HashMap<Integer, Node> oldNodes = new HashMap<Integer, Node>();
            int uniId = _idSampler.getNextNatural();
            int gradId = _idSampler.getNextNatural();
            int edgeId = _idSampler.getNextNatural();

            // The new edge that will connect the Dep and the Uni
            Edge edge = new Edge(RDF_TYPE_UND_DEG_FROM, edgeId, uniId);

            // The Uni node
           Node uniNode = new Node(null, RDF_TYPE_UNI, uniId, null);
            oldNodes.put(uniId, uniNode);

            // The Grad-Student node
            ArrayList<Edge> gradEdges = new ArrayList<Edge>();
            gradEdges.add(edge);
            Node gradNode = new Node(null, RDF_TYPE_GRAD_STUD, gradId, gradEdges);
            oldNodes.put(gradId, gradNode);

            // Matcher that maps the old nodes to the query attributes
            IMatcher matcher = new StdMatcher();
            String uniQueryAtt = "?Uni";
            matcher.addMapping(uniId, uniQueryAtt);
            String gradQueryAtt = "?Grad";
            matcher.addMapping(gradId, gradQueryAtt);

            // The construction pattern
            ConstructionPattern studCPattern = new ConstructionPattern(oldNodes, newNodes, _sFunction, _typePropFunction);

            // Create the query that should return the Uni node
            String queryString1 =
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + gradQueryAtt + " WHERE { " + gradQueryAtt + " rdf:type ns:" + RDF_TYPE_GRAD_STUD + " . } \n";
            QueryWrapper queryWrapper1 = new QueryWrapper(queryString1, SamplingMode.RANDOM_GLOBAL_DISTINCT, null);

            String queryString2 =
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  " +
                            "PREFIX ns: <" + UB_NS + "> " +
                            "SELECT " + uniQueryAtt + " WHERE { " + uniQueryAtt + " rdf:type ns:" + RDF_TYPE_UNI + " . } \n";
            IRealNumberSampler realNumSampler = new ConstantRealSampler();
            QueryWrapper queryWrapper2 = new QueryWrapper(queryString2, SamplingMode.RANDOM_REPEATABLE, realNumSampler);

            // Invoke the construction command
             ArrayList<QueryWrapper> queryWrappers = new ArrayList<QueryWrapper>();
            queryWrappers.add(queryWrapper1);
            queryWrappers.add(queryWrapper2);
            _randGraphApi.construct(
                    _infModel,
                    queryWrappers,
                    nSampler,
                    studCPattern,
                    matcher,
                    QueryOptimizationMode.SMART_CACHE,
                    null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
