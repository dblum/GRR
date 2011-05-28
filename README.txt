Introduction:
-------------
GRR (Generating Random RDF) is a powerful system for generating random RDF data, which can be used to test 
Semantic Web applications. GRR has a SPARQL-like syntax, which allows the system to be both powerful and convenient.
It is shown that GRR can easily be used to produce intricate datasets, such as the LUBM benchmark. 
Optimization techniques are employed, which make the generation process efficient and scalable.

License and Copyrights:
-----------------------
GRR (Genereting Random RDF) code is released under GNU GPL.
In order to make GRR work Jena jar files should be used which have specific license and copyright restrictions 
which are defined in the following link: http://jena.sourceforge.net/license.html

Installation Instructions:
--------------------------
1) Download the code / zip file and unzip.
2) Download TDB/ARQ/Jena jar files and place them in the 'GRR\RandomDataGraphs\jars':
	Link: http://www.sourceforge.net/projects/jena/files
	Jars needed:
	- arq-2.8.8.jar
	- icu4j-3.4.4.jar
	- iri-0.8.jar
	- jena-2.6.4.jar
	- log4j-1.2.14.jar
	- slf4j-api-1.5.8.jar
	- slf4j-log4j12-1.5.8.jar
	- tdb-0.8.10.jar
	- xercesImpl-2.7.1.jar

Note:	
	1) The above can be replaced with newer versions, but this is the setup that GRR was tested with
	2) By downloading these files you are obligated to review 'Jena License and Copyright' section:
	   http://jena.sourceforge.net/license.html
	   GRR relies on Jena, but we don't provide Jena lib files in our code.

2) Set the TDB path in the following location: TDB/tdbConfig.xml
   You can just set it the path of the folder that contains the unzipeed code and add to the end "TDB\Data".
   This is where all of the TDB files will be saved
3) Open the code while using some IDE and set the following:
	a. Set the location of your JDK.
	b. Set the 'Maximum heap size' under the compiler setting in your solution.
	   If this value is too big, you'll have problem in running/compiling the code.
	   In addition this has direct impact on the performance of the system.
	c. Set the location of the solution in the config file: RandomDataGraphs\src\SolutionConfig\SolutionConfigFile.Java
       and give the path of the directory that contains the unzipped files.
4) Run the RandomDataGraphs\gui\gui\GrrGui\GrrGui.Java in order to start the GUI	  