# janm: A Normal Mode Analysis library for Java 
janm is a library to perform normal mode analysis (Gaussian Network Model and Anisotropic Network Model) to protein structures. It makes use of the BioJava library to parse the PDB files and for some other facilities.

This library was developed with the purpose of contributing to BioJava. It needs a specific singular value decomposition (SVD) algorithm which is only available in [Java Matrix Toolkit](https://en.wikipedia.org/wiki/Matrix_Toolkit_Java). Because of this dependency, it is a bit difficult to embed it into BioJava for the moment. 

It is a Maven project so batteries are included.