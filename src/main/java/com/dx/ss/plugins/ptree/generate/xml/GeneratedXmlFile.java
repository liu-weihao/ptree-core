/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.dx.ss.plugins.ptree.generate.xml;

import com.dx.ss.plugins.ptree.config.xml.Document;
import com.dx.ss.plugins.ptree.generate.GeneratedFile;

/**
 * The Class GeneratedXmlFile.
 *
 * @author Jeff Butler
 */
public class GeneratedXmlFile extends GeneratedFile {
    
    /** The document. */
    private Document document;

    /** The file name. */
    private String fileName;

    /** The target package. */
    private String targetPackage;
    
    /** The xml formatter. */
    private XmlFormatter xmlFormatter;

    /**
     * Instantiates a new generated xml file.
     *
     * @param document
     *            the document
     * @param fileName
     *            the file name
     * @param targetPackage
     *            the target package
     * @param targetProject
     *            the target project
     * @param isMergeable
     *            true if the file can be merged by the built in XML file merger.
     * @param xmlFormatter
     *            the xml formatter
     */
    public GeneratedXmlFile(Document document, String fileName,
            String targetPackage, String targetProject, XmlFormatter xmlFormatter) {
        super(targetProject);
        this.document = document;
        this.fileName = fileName;
        this.targetPackage = targetPackage;
        this.xmlFormatter = xmlFormatter;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getTargetPackage() {
        return targetPackage;
    }

    @Override
    public String getFormattedContent() {
        return xmlFormatter.getFormattedContent(document);
    }
}
